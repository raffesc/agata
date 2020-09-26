<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
 
// include database and object files
include_once '../config/database.php';
include_once '../objects/back-end_endpoint.php';
include_once '../objects/auth.php';

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
$backend = new BackEndEndpoint();
$backend->setDB($db);

$backend->id_project = $_GET["id_project"];


// query products
$stmt = $backend->read();
$num = $stmt->rowCount();
 
// check if more than 0 record found
if($num>0){
 
    // products array
    $appunti_arr=array();
    $appunti_arr["records"]=array();
 
    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);

        $appunti_item=array(
            "id" => $id,
            "id_project" => $id_project,
            "title" => $title,
            "descrizione" => $descrizione
        );

        array_push($appunti_arr["records"], $appunti_item);
         
 
    }
	
	  // set response code - 200 OK
    http_response_code(200);
 
    // show products data in json format
    echo json_encode($appunti_arr);


} else {
	// set response code - 404 Not found
    http_response_code(404);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Nessun endpoint trovato.")
    );

}

?>