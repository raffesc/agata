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
    if ($headers == "Authentication-Token" || $headers == "authentication-token") {
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
$frontend = new BackEnd();
$frontend->setDB($db);


$frontend->id_project = $data->id_project;
$frontend->nome = $data->nome;
$frontend->id_owner = $data->id_owner;
$frontend->status = $data->status;
$frontend->type = $data->type;

if (empty($frontend->id_project) || empty($frontend->nome) || empty($frontend->id_owner) || empty($frontend->status) || empty($frontend->type)) {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("errore" => "Controllare di aver inserito tutti i parametri."));
    exit();
}


// query products
$stmt = $frontend->readAll();
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
$frontend->id = $max_id;
if ($frontend->insert()) {
    // set response code - 200 OK
    
    http_response_code(200);
    // show products data in json format
    echo json_encode($frontend);

} else {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("errore" => "Errore durante la creazione dell'elemento Back-End. Riprovare"));

}


?>