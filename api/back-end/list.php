<?php


// include database and object files
include_once '../config/database.php';
include_once '../objects/project.php';
include_once '../objects/auth.php';
include_once '../objects/back-end_attribute.php';
include_once '../objects/back-end.php';

// instantiate database and product object
$database = new Database();
$db = $database->getConnection();




// *** VERIFICA TOKEN VALIDO *** //
$auth = new Auth();
$auth->setDB($db);
$header = apache_request_headers(); 
  
foreach ($header as $headers => $value) { 
    if ($headers == "Authentication-Token" || $headers == "authentication-token") {
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
$back_end_array = array();




$frontend_attribute = new BackEndAttribute();
$frontend_attribute->setDB($db);

$stmt = $frontend_attribute->read();
$num = $stmt->rowCount();
if($num>0){
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
        $attribute_item=array(
            "id" => $id,
            "id_back_end" => $id_back_end,
            "title" => $title,
            "priv" => $priv,
            "type" => $type,
            "value" => $value
        );
        
        array_push($attribute_array,$attribute_item);
    }
} 


$frontend = new BackEnd();
$frontend->setDB($db);
$frontend->id_project = $_GET["id_project"];
$stmt = $frontend->read();
$num = $stmt->rowCount();
// check if more than 0 record found

if($num>0){
    // retrieve our table contents
    while ($row_curr = $stmt->fetch(PDO::FETCH_ASSOC)) {

        extract($row_curr);
        $attribute_array_curr = array();
        $query_array = array();
        $header_array = array();
        $body_array = array();

        
        foreach ($attribute_array as &$elem) {
            if ($elem["id_back_end"] == $id) {
               // array_push($attribute_array_curr,$elem);
               if ($elem["type"] == "1") {
                    array_push($query_array,$elem);
                }
                if ($elem["type"] == "2") {
                    array_push($header_array,$elem);
                }
                if ($elem["type"] == "3") {
                     array_push($body_array,$elem);
                }
               
            }
        }
        
        $attribute_item=array(
            "id" => $id,
            "id_project" => $id_project,
            "nome" => $nome,
            "id_owner" => $id_owner,
            "status" => $status,
            "type" => $type,
            "query" => $query_array,
            "header" => $header_array,
            "body" => $body_array
        );
        
        
        array_push($back_end_array,$attribute_item);
        
    }
} 

http_response_code(200);
echo json_encode(array("records" => $back_end_array));




?>