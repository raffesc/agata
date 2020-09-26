<?php


// include database and object files
include_once '../config/database.php';
include_once '../objects/project.php';
include_once '../objects/auth.php';
include_once '../objects/front-end_image.php';

// instantiate database and product object
$database = new Database();
$db = $database->getConnection();




// *** VERIFICA TOKEN VALIDO *** //
$auth = new Auth();
$auth->setDB($db);
$header = apache_request_headers(); 
  
foreach ($header as $headers => $value) { 
    if ($headers == "Authentication-Token") {
        $auth->userToken = $value;
    }
} 
if (!$auth->authenticate() or !$_GET["id_front_end"]) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //


include 'init.php';


$response  = new Response();
if(Helper::is_post()){
    if(isset($_FILES["image_name"])) {

        $upload = new Upload($_FILES["image_name"]);
        $upload->set_max_size(MAX_IMAGE_SIZE);
        if($upload->upload()){
            

            //$response->create(200, "Success", $upload);
            
            $frontend = new FrontEndImage();
            $frontend->setDB($db);
            
            $stmt = $frontend->read();
            $num = $stmt->rowCount();
            // check if more than 0 record found
            $max_id = 1;
            if($num>0){
                // retrieve our table contents
                while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

                    extract($row);
                    $attribute_item=array(
                        "id" => $id,
                    );
                    $max_id = $max_id + 1;
                }
            } 
            
            $data_en = json_encode($upload, JSON_UNESCAPED_SLASHES);
            $data_dec = json_decode($data_en);
            $frontend->image = $data_dec->file_name;
            $frontend->id = $max_id;
            $frontend->id_front_end = $_GET["id_front_end"];
            if ($frontend->insert()) {
                $response->create(200, "Success", $upload);
            } else {
                http_response_code(503);
                echo json_encode(array("errore" => "Errore durante l'upload. Riprovare"));
            }

        } else $response->create(201, $upload->get_errors()->format(), null);
    } else $response->create(201, "Invalid Parameter", null);
}else $response->create(201, "Invalid request", null);

echo $response->print_response();

?>