<?php
class BackEnd{
 
    // database connection and table name
    private $conn;
    private $table_name = "backend";

    // object properties
    public $id = 0;
    public $id_project = 0;
    public $nome = "";
    public $id_owner = 0;
	public $status = 0;
	public $type = 0;

 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
	
	 // read products
    public function readAll(){
     
        $query = "SELECT
               *
                FROM ". $this->table_name . "
                " ;
     
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
        

        // execute query
        $stmt->execute();
     
        return $stmt;
    }
    
    // read products
    public function read(){
     
        $query = "SELECT
               *
                FROM ". $this->table_name . "
                    WHERE id_project=:id_project
                " ;
     
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
        
	    $this->id_project=htmlspecialchars(strip_tags($this->id_project));

     	$stmt->bindParam(':id_project', $this->id_project);

        // execute query
        $stmt->execute();
     
        return $stmt;
    }
	
	
	
	public function insert() {
	    

	    $query = "INSERT INTO " . $this->table_name . " VALUES (:id,:id_project,:nome,:id_owner,:status,:type)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_project=htmlspecialchars(strip_tags($this->id_project));
	    $this->nome=htmlspecialchars(strip_tags($this->nome));
	 	$this->id_owner=htmlspecialchars(strip_tags($this->id_owner));
	 	$this->status=htmlspecialchars(strip_tags($this->status));
	 	$this->type=htmlspecialchars(strip_tags($this->type));
	 	

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':id_project', $this->id_project);
	    $stmt->bindParam(':nome', $this->nome);
	 	$stmt->bindParam(':id_owner', $this->id_owner);
	 	$stmt->bindParam(':status', $this->status);
	 	$stmt->bindParam(':type', $this->type);
	 	

	    // execute the query
	    if($stmt->execute()){
	        return true;
	    }
	    return false;
	}
	
	public function update() {
    
    
        $query = "UPDATE " . $this->table_name . " SET status=:status WHERE id=:id";
        
        // prepare query statement
        $stmt = $this->conn->prepare($query);
        
        // sanitize
        $this->status=htmlspecialchars(strip_tags($this->status));
        $this->id=htmlspecialchars(strip_tags($this->id));

        
        // bind new values
        $stmt->bindParam(':status', $this->status);
        $stmt->bindParam(':id', $this->id);

        
        // execute the query
        if($stmt->execute()){
            return true;
        }
        return false;
    }
	
	
	public function delete() {
		 // delete query
	    $query = "DELETE FROM " . $this->table_name . " WHERE id = ?";
	    // prepare query
	    $stmt = $this->conn->prepare($query);
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    // bind id of record to delete
	    $stmt->bindParam(1, $this->id);
		
		$stmt->execute();
		
		$count = $stmt->rowCount();
		
	    // execute query
	    if($count > 0){
	        return true;
	    }
		
	    return false;
		
	}
	

}
?>
