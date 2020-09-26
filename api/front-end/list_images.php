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
if (!$auth->authenticate() ) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //


$frontend = new FrontEndImage();
$frontend->setDB($db);

$stmt = $frontend->read();
$num = $stmt->rowCount();
// check if more than 0 record found
$image_array = array();
if($num>0){
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
        $attribute_item=array(
            "id" => $id,
            "id_front-end" => $id_front_end,
            "image" => $image
        );
        
        array_push($image_array,$attribute_item);
    }
} 

http_response_code(200);
echo json_encode(array("records" => $image_array));




?>