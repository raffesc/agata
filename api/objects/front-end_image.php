<?php
class FrontEndImage {
 
    // database connection and table name
    private $conn;
    private $table_name = "frontend_image";

    // object properties
    public $id = 0;
    public $id_front_end = 0;
    public $image = "";


 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
    
    // read products
    public function read(){
     
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
	
	
	
	public function insert() {
	    

	    $query = "INSERT INTO " . $this->table_name . " VALUES (:id,:id_front_end,:image)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_front_end=htmlspecialchars(strip_tags($this->id_front_end));
	 	$this->image=htmlspecialchars(strip_tags($this->image));

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':id_front_end', $this->id_front_end);
	 	$stmt->bindParam(':image', $this->image);

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
