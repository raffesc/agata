<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


 
// include database and object files
include_once '../config/database.php';
include_once '../objects/bugs.php';
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
$appunti = new Bugs();
$appunti->setDB($db);


$appunti->id_project = $data->id_project;
$appunti->id_category = $data->id_category;
$appunti->title = $data->title;
$appunti->descrizione = $data->descrizione;

if (empty($appunti->id_project) || empty($appunti->id_category) || empty($appunti->title) || empty($appunti->descrizione) ) {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("errore" => "Controllare di aver inserito tutti i parametri."));
    exit();
}


// query products
$stmt = $appunti->readAll();
$num = $stmt->rowCount();
 
$max_id = 1; 
// check if more than 0 record found
if($num>0){
 
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        // just $name only
        extract($row);
        $project_item=array(
            "id" => $id,
        );

        $max_id = $max_id + 1;
    }

} 
$appunti->id = $max_id;
if ($appunti->insert()) {
    // set response code - 200 OK
    
    http_response_code(200);
    // show products data in json format
    echo json_encode($appunti);

} else {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("errore" => "Errore durante la creazione dell'appunto. Riprovare"));

}


?>