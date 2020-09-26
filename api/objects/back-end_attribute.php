<?php
class BackEndAttribute {
 
    // database connection and table name
    private $conn;
    private $table_name = "backend_attribute";

    // object properties
    public $id = 0;
    public $id_back_end = 0;
    public $title = "";
    public $priv = 0;
	public $type = 0;
	public $value = "";


 
 
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
	    

	    $query = "INSERT INTO " . $this->table_name . " VALUES (:id,:id_back_end,:title,:priv,:type,:value)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_back_end=htmlspecialchars(strip_tags($this->id_back_end));
	    $this->title=htmlspecialchars(strip_tags($this->title));
	    $this->priv=htmlspecialchars(strip_tags($this->priv));
	 	$this->type=htmlspecialchars(strip_tags($this->type));
	 	$this->value=htmlspecialchars(strip_tags($this->value));
	 	

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':id_back_end', $this->id_back_end);
	    $stmt->bindParam(':title', $this->title);
	    $stmt->bindParam(':priv', $this->priv);
	 	$stmt->bindParam(':type', $this->type);
	 	$stmt->bindParam(':value', $this->value);
	 	

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
