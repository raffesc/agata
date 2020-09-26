<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


 
// include database and object files
include_once '../config/database.php';
include_once '../objects/back-end.php';
include_once '../objects/auth.php';

// instantiate database and product object
$database = new Database();
$db = $database->getConnection();


// get id of product to be edited
$data = json_decode(file_get_contents("php://input"));


// *** VERIFICA TOKEN VALIDO *** //
$auth = new Auth();
$auth->setDB($db);
$header = apache_request_headers(); 
  
foreach ($header as $headers => $value) { 
    if ($headers == "Authentication-Token" ||$headers == "authentication-token") {
        $auth->userToken = $value;
    }
} 
if (!$auth->authenticate()) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //




// initialize object
$appunti = new BackEnd();
$appunti->setDB($db);


$appunti->id = $data->id;
$appunti->status = $data->status;

if (empty($appunti->id)) {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("error" => "Controllare di aver inserito tutti i parametri."));
    exit();
}


if ($appunti->update()) {
    // set response code - 200 OK
    
    http_response_code(200);
    // show products data in json format
    echo json_encode(array("success" => "API eliminata con successo"));

} else {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("errore" => "Errore durante l'eliminazione dell'API. Riprovare"));

}


?>