<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


 
// include database and object files
include_once '../config/database.php';
include_once '../objects/project.php';
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
$project = new Project();
$project->setDB($db);


$project->id_owner = $data->id_owner;
$project->id = $data->id;


if (empty($project->id_owner) || empty($project->id) || empty($data->id_new_user)) {
    http_response_code(404);
    // show products data in json format
    echo json_encode(array("errore" => "Controllare di aver inserito tutti i parametri."));
    exit();
}


// query products
$stmt = $project->read();
$num = $stmt->rowCount();
 
// check if more than 0 record found
if($num>0){
 
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        // just $name only
        extract($row);
        $project_item=array(
            "id" => $id,
            "id_users" => $id_users
        );
        
        if ($id == $project->id and $id_owner == $project->id_owner) {
            
            $project->id_users = $id_users;
            if ($project->addUser($data->id_new_user)) {
                http_response_code(200);
                // show products data in json format
                echo json_encode(array("response" => "User aggiunto correttamente!"));
                exit();
            }
            
        }

    }

} 

http_response_code(404);
// show products data in json format
echo json_encode(array("errore" => "Errore durante l'inserimento del nuovo utente del progetto. Riprovare"));




?>