<?php


// include database and object files
include_once '../config/database.php';
include_once '../objects/project.php';
include_once '../objects/auth.php';
include_once '../objects/front-end_attribute.php';
include_once '../objects/front-end.php';

// instantiate database and product object
$database = new Database();
$db = $database->getConnection();




// *** VERIFICA TOKEN VALIDO *** //
$auth = new Auth();
$auth->setDB($db);
$header = apache_request_headers(); 
  
foreach ($header as $headers => $value) { 
    if ($headers == "authentication-token") {
        $auth->userToken = $value;
    }
} 
if (!$auth->authenticate() or !$_GET["id_project"]) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //

$attribute_array = array();
$model_array = array();
$view_array = array();
$controller_array = array();



$frontend_attribute = new FrontEndAttribute();
$frontend_attribute->setDB($db);

$stmt = $frontend_attribute->read();
$num = $stmt->rowCount();
if($num>0){
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
        $attribute_item=array(
            "id" => $id,
            "id_front_end" => $id_front_end,
            "value" => $value,
            "value" => $value,
            "priv" => $priv,
            "type" => $type,
        );
        
        array_push($attribute_array,$attribute_item);
    }
} 


$frontend = new FrontEnd();
$frontend->setDB($db);
$frontend->id_project = $_GET["id_project"];
$stmt = $frontend->read();
$num = $stmt->rowCount();
// check if more than 0 record found

if($num>0){
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
        $attribute_array_curr = array();
        
        
        foreach ($attribute_array as &$elem) {
            if ($elem["id_front_end"] == $id) {
                array_push($attribute_array_curr,$elem);
            }
        }
        $attribute_item=array(
            "id" => $id,
            "id_project" => $id_project,
            "nome" => $nome,
            "id_owner" => $id_owner,
            "status" => $status,
            "type" => $type,
            "attributes" => $attribute_array_curr
        );
        if ($type == 1) {
            array_push($model_array,$attribute_item);
        }
        if ($type == 2) {
            array_push($view_array,$attribute_item);
        }
        if ($type == 3) {
             array_push($controller_array,$attribute_item);
        }
        
    }
} 

http_response_code(200);
echo json_encode(array("model" => $model_array,"view" => $view_array,"controller" => $controller_array));




?>