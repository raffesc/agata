

<?php


// include database and object files
include_once '../config/database.php';
include_once '../objects/project.php';
include_once '../objects/auth.php';
include_once '../objects/user.php';

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
    if ($headers == "Authentication-Token") {
        $auth->userToken = $value;
    }
} 
if (!$auth->authenticate() or !$_GET["id_user"]) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //


include '../init.php';


$data = json_decode(file_get_contents("php://input"));


$response  = new Response();
$DS = DIRECTORY_SEPARATOR;

if(Helper::is_post()){
    $item_image = new Item_Image();
    $item_image->image_name = $data->image_name;

    if($item_image->image_name){
            if (Upload::delete($item_image->image_name)){

                
                $user = new User();
                $user->setDB($db);
                $data_en = json_encode($upload, JSON_UNESCAPED_SLASHES);
                $data_dec = json_decode($data_en);
                $user->image = "";
                $user->id = $_GET["id_user"];
                if ($user->updateImage()) {
                    $response->create(200, "Success", $item_image->to_valid_array());
                } else {
                    http_response_code(503);
                    // show products data in json format
                    echo json_encode(array("errore" => "Errore durante la rimozione. Riprovare"));
                }


            }else $response->create(201, "Something Went Wrong.", null);
    }else $response->create(201, "Invalid Parameter", null);
}else $response->create(201, "Invalid request", null);

echo $response->print_response();

?>