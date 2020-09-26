<?php
class Project{
 
    // database connection and table name
    private $conn;
    private $table_name = "projects";
 
    // object properties
    public $id;
    public $id_owner;
	public $id_users;
	public $title;
	public $published = 0;
	public $num_like = 0;
	public $ranking = 5;
	public $main_color;
	public $icon;

 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
    
    // read products
    public function read(){
     
        // select all query
        $query = "SELECT
                   *
				FROM ". $this->table_name;
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
     
        // execute query
        $stmt->execute();
     
        return $stmt;
    }
    
    public function readPublished(){
     
        // select all query
        $query = "SELECT
                   *
				FROM ". $this->table_name . " WHERE published=1" ;
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
     
        // execute query
        $stmt->execute();
     
        return $stmt;
    }
    
	
	
	
	public function create() {
		 // select all query
         // update query
	    $query = "INSERT INTO 
	                " . $this->table_name . "
	                VALUES (:id,:id_owner,:id_users,:title,:icon,:main_color,0,0,5)";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_owner=htmlspecialchars(strip_tags($this->id_owner));
	    $this->id_users=htmlspecialchars(strip_tags($this->id_users));
	    $this->title=htmlspecialchars(strip_tags($this->title));
	    $this->icon=htmlspecialchars(strip_tags($this->icon));
	    $this->main_color=htmlspecialchars(strip_tags($this->main_color));

	    // bind new values
	    $stmt->bindParam(':id', $this->id);
	    $stmt->bindParam(':id_owner', $this->id_owner);
	    $stmt->bindParam(':id_users', $this->id_users);
	    $stmt->bindParam(':title', $this->title);
	    $stmt->bindParam(':icon', $this->icon);
	    $stmt->bindParam(':main_color', $this->main_color);

	    // execute the query
	    if($stmt->execute()){
	        return true;
	    }

	    return false;
	}
	
	public function publish() {
	    
        $query = "UPDATE 
                    " . $this->table_name . "
                SET  published=1
                WHERE id = :id";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));

	    // bind new values
	    $stmt->bindParam(':id', $this->id);

	    // execute the query
	    if($stmt->execute()){
	        return true;
	    }

	    return false;
	    
	}
	
	
	public function addUser($id_new_user) {
	    
	    $json = str_replace('&quot;', '"', $this->id_users);
        $id_users_decoded = json_decode( $json );

        $new_users_array = array();
        foreach ($id_users_decoded as &$value) {
            if ($value == $id_new_user) {
                return true;
            }
            array_push($new_users_array,$value);
        }
        
        array_push($new_users_array,$id_new_user);
        
        $this->id_users = json_encode($new_users_array);
        
        $query = "UPDATE 
                    " . $this->table_name . "
                SET  id_users=:id_users
                WHERE id = :id";
	 
	    // prepare query statement
	    $stmt = $this->conn->prepare($query);
	 
	    // sanitize
	    $this->id=htmlspecialchars(strip_tags($this->id));
	    $this->id_users=htmlspecialchars(strip_tags($this->id_users));

	    // bind new values
	    $stmt->bindParam(':id_users', $this->id_users);
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
