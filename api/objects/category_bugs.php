<?php
class CategoryBugs {
 
    // database connection and table name
    private $conn;
    private $table_name = "category_bugs";
 

    // object properties
    public $id;
    public $id_project;
	public $nome = "";

 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
    
    // read products
    public function read(){
     
         // select all query
        $query = "SELECT
                   *
				FROM ". $this->table_name . "
				    WHERE id_project=:id_project
				";
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
        
	    $this->id_project=htmlspecialchars(strip_tags($this->id_project));

     	$stmt->bindParam(':id_project', $this->id_project);

        // execute query
        $stmt->execute();
     
        return $stmt;
    }
	
	
	
	public function insert() {
		 // select all query
         // update query
	    $query = "INSERT INTO 
	                " . $this->table_name . "
	                VALUES (:id,:id_project,:nome)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_project=htmlspecialchars(strip_tags($this->id_project));
	 	$this->nome=htmlspecialchars(strip_tags($this->nome));

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':id_project', $this->id_project);
	 	$stmt->bindParam(':nome', $this->nome);

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
