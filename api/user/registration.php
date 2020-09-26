<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);


 
// include database and object files
include_once '../config/database.php';
include_once '../objects/user.php';
 
// instantiate database and product object
$database = new Database();
$db = $database->getConnection();

// initialize object
$user = new User();
$user->setDB($db);

session_start();

// get id of product to be edited
$data = json_decode(file_get_contents("php://input"));



// get parameter 
$email_param = $data->email;
$passwd_param = $data->password;

$user->email = $email_param;
$user->password = $passwd_param;
$user->username = $data->username;
$user->image = $data->image;
$user->nome = $data->nome;
$user->cognome = $data->cognome;
$user->age = $data->age;


if (empty($email_param) || empty($passwd_param) || empty($data->username) || empty($data->image) || empty($data->nome) || empty($data->cognome) || empty($data->age)) {
	 // set response code - 503 service unavailable
    http_response_code(503);
 
    		// tell the user
    echo json_encode(array("error" => "Controlla di aver inserito correttamente tutti i parametri."));
	exit();
}


// query products
$stmt = $user->read();
$num = $stmt->rowCount();

$id = 1;
 
// check if more than 0 record found
if($num>0){
 
    // products array
    $users_arr=array();
    $users_arr["records"]=array();
 
    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);
 
        $user_item=array(
            "id" => $id,
            "email" => $email,
            "password" => $password,
            "userToken" => $userToken,
            "contract" => $contract,
            "username" => $username
        );
		
		if ($email_param == $email or $data->username == $username) {
		
			// set response code - 404 Not found
            http_response_code(401);
         
            // tell the user no products found
            echo json_encode(
                array("error" => "Utente già presente. Effettuare la login")
            );
            
            exit();

		}
		
		$id = $id + 1;
 
        //        array_push($users_arr["records"], $user_item);
    }
	

}

$user->id = $id;

if ($user->register()) {
    // set response code - 404 Not found
    http_response_code(200);
 
    // tell the user no products found
    echo json_encode(array("response" => "Utente registrato correttamente! Effettua la login"));
    
} else {
    // set response code - 404 Not found
    http_response_code(503);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Impossibile registrare l'utente al momento.. si prega di riprovare più tardi.")
    );
}

?>
