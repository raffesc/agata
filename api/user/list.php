<?php


// include database and object files
include_once '../config/database.php';
include_once '../objects/user.php';
include_once '../objects/auth.php';

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
if (!$auth->authenticate()) {
    
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Permesso non autorizzato. Effettuare la login.")
    );
    exit();
}
// *** FINE VERIFICA TOKEN VALIDO *** //




$user = new User();
$user->setDB($db);

$stmt = $user->read();
$num = $stmt->rowCount();
if($num>0){
    
    $attribute_array = array();
    
    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);
 
        $user_item=array(
            "id" => $id,
            "email" => $email,
            "username" => $username
        );
        

        array_push($attribute_array,$user_item);
    }
} 


http_response_code(200);
echo json_encode(array("records" => $attribute_array));




?>