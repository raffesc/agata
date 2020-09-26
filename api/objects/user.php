<?php
class User{
 
    // database connection and table name
    private $conn;
    private $table_name = "users";
 
    // object properties
    public $email = "";
    public $password = "";
	public $userToken = "";
	public $username = "";
	public $image = "";
	public $nome = "";
	public $cognome = "";
	public $age;
	public $contract = -1;
	public $id;

 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
    
    // read products
    public function read(){
     
        // select all query
        $query = "SELECT
                   *
				FROM ". $this->table_name ;
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
     
        // execute query
        $stmt->execute();
     
        return $stmt;
    }
	
	
	
	public function register() {
		 // select all query
         // update query
	    $query = "INSERT INTO 
	                " . $this->table_name . "
	                VALUES (:id,:email,:password,'',-1,:username,:image,:nome,:cognome,:age)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->email=htmlspecialchars(strip_tags($this->email));
	    $this->password=htmlspecialchars(strip_tags($this->password));
	 	$this->username=htmlspecialchars(strip_tags($this->username));
	 	$this->image=htmlspecialchars(strip_tags($this->image));
	 	$this->nome=htmlspecialchars(strip_tags($this->nome));
	 	$this->cognome=htmlspecialchars(strip_tags($this->cognome));
	 	$this->age=htmlspecialchars(strip_tags($this->age));

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':email', $this->email);
	    $stmt->bindParam(':password', $this->password);
	 	$stmt->bindParam(':username', $this->username);
	 	$stmt->bindParam(':image', $this->image);
	 	$stmt->bindParam(':nome', $this->nome);
	 	$stmt->bindParam(':cognome', $this->cognome);
	 	$stmt->bindParam(':age', $this->age);

	    // execute the query
	    if($stmt->execute()){
	        return true;
	    }
	    return false;
	}
	
	

	
	public function delete() {
		 // delete query
	    $query = "DELETE FROM " . $this->table_name . " WHERE email = ?";
	    // prepare query
	    $stmt = $this->conn->prepare($query);
	    // sanitize
	    $this->email=htmlspecialchars(strip_tags($this->email));
	    // bind id of record to delete
	    $stmt->bindParam(1, $this->email);
		
		$stmt->execute();
		
		$count = $stmt->rowCount();
		
	    // execute query
	    if($count > 0){
	        return true;
	    }
		
	    return false;
		
	}
	
	public function saveToken() {
	     // select all query
         $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < 500; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        $this->userToken = $randomString;

         // update query
	    $query = "UPDATE  
	                " . $this->table_name . "
	            SET userToken=:userToken WHERE id=:id";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->userToken=htmlspecialchars(strip_tags($this->userToken));
	    $this->id=htmlspecialchars(strip_tags($this->id));
	 
	    // bind new values
	    $stmt->bindParam(':userToken', $this->userToken);
	    $stmt->bindParam(':id', $this->id);
	 
	    // execute the query
	    if($stmt->execute()){
	        return $this->userToken;
	    }
	    return '';
	}
	
	
	public function updateImage() {
	    

         // update query
	    $query = "UPDATE  
	                " . $this->table_name . "
	            SET image=:image WHERE id=:id";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	    

	    // sanitize
	    $this->image=htmlspecialchars(strip_tags($this->image));
	    $this->id=htmlspecialchars(strip_tags($this->id));
	 
	    // bind new values
	    $stmt->bindParam(':image', $this->image);
	    $stmt->bindParam(':id', $this->id);
	 
	    // execute the query
	    if($stmt->execute()){
	        return true;
	    }
	    return false;
	}

}
?>
