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


if (empty($email_param) || empty($passwd_param)) {
	 // set response code - 503 service unavailable
    http_response_code(503);
 
    		// tell the user
    echo json_encode(array("error" => "Impossibile effettuare la login."));
	exit();
}


// query products
$stmt = $user->read();
$num = $stmt->rowCount();
 
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
            "username" => $username,
            "image" => $image,
            "nome" => $nome,
            "cognome" => $cognome,
            "age" => $age,
   );
		
		if ($email_param == $email and $passwd_param == $password) {
			// set response code - 200 OK
			
			$user->id = $id;
			$user->email = $email;
			$user->userToken = $user->saveToken();
			$user->contract = $contract;
			$user->email = $email;
			$user->password = $password;
			$user->username = $username;
			$user->age = $age;
			$user->cognome = $cognome;
			$user->nome = $nome;
			$user->image = $image;

		    if ($user->userToken != '') {
		            http_response_code(200);
 
            		echo json_encode($user);
        
        	    	exit();

		    }
			
		}
 
        array_push($users_arr["records"], $user_item);
    }
	
	// set response code - 401 Not found
    http_response_code(401);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Accesso negato. Controlla di aver inserito correttamente le credenziali.")
    );


} else {
	// set response code - 404 Not found
    http_response_code(404);
 
    // tell the user no products found
    echo json_encode(
        array("error" => "Accesso negato. Controlla di aver inserito correttamente le credenziali.")
    );
} 

?>
