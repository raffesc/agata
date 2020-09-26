<?php
class Bugs {
 
    // database connection and table name
    private $conn;
    private $table_name = "bugs";
 

    // object properties
    public $id;
    public $id_category;
    public $id_project;
	public $title = "";
	public $descrizione = "";

 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
	
	public function readAll() {
	    
	    $query = "SELECT *
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
               p.id, p.id_category, p.id_project, p.title, p.descrizione, 
               u.nome as nome_category
                FROM ". $this->table_name . "  p 
                 LEFT JOIN category_bugs u
                    ON p.id_category = u.id
                    WHERE p.id_project=:id_project
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
		 // select all query
         // update query
	    $query = "INSERT INTO 
	                " . $this->table_name . "
	                VALUES (:id,:id_category,:id_project,:title,:descrizione)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_category=htmlspecialchars(strip_tags($this->id_category));
	    $this->id_project=htmlspecialchars(strip_tags($this->id_project));
	 	$this->title=htmlspecialchars(strip_tags($this->title));
	 	$this->descrizione=htmlspecialchars(strip_tags($this->descrizione));

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':id_category', $this->id_category);
	    $stmt->bindParam(':id_project', $this->id_project);
	 	$stmt->bindParam(':title', $this->title);
	 	$stmt->bindParam(':descrizione', $this->descrizione);

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