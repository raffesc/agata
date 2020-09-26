<?php


// include database and object files
include_once '../config/database.php';
include_once '../objects/project.php';
include_once '../objects/auth.php';
include_once '../objects/design.php';

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
if (!$auth->authenticate() or !$_GET["id_project"] or !$_GET["id_category"]) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //


include '../init.php';


$response  = new Response();
if(Helper::is_post()){
    if(isset($_FILES["image_name"])) {

        $upload = new Upload($_FILES["image_name"]);
        $upload->set_max_size(MAX_IMAGE_SIZE);
        if($upload->upload()){
            

            //$response->create(200, "Success", $upload);
            
            $user = new Design();
            $user->setDB($db);
            $data_en = json_encode($upload, JSON_UNESCAPED_SLASHES);
            $data_dec = json_decode($data_en);
            $user->nome = $data_dec->file_name;
            $user->id_project = $_GET["id_project"];
            $user->id_category = $_GET["id_category"];
            $user->descrizione = " ";
            $stmt = $user->readAll();
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
            $user->id = $max_id;
            if ($user->insert()) {
                $response->create(200, "Success", $upload);
            } else {
                http_response_code(503);
                // show products data in json format
                echo json_encode(array("errore" => "Errore durante l'upload. Riprovare"));
                exit();
            }

        } else $response->create(201, $upload->get_errors()->format(), null);
    } else $response->create(201, "Invalid Parameter", null);
}else $response->create(201, "Invalid request", null);

echo $response->print_response();

?>