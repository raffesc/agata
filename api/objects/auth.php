<?php
class Auth{
 
    // database connection and table name
    private $conn;
    private $table_name = "users";
 
    // object properties
	public $userToken = "";

 
 
	public function setDB($db) {
		  $this->conn = $db;
	}
    
    // read products
    public function authenticate(){
     
        // select all query
        $query = "SELECT
                   *
				FROM ". $this->table_name ;
     
        // prepare query statement
        $stmt = $this->conn->prepare($query);
     
        // execute query
        $stmt->execute();
     
     
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
                    "userToken" => $userToken,
                );
        		
        		if ($this->userToken == $userToken and $this->userToken != "") {
        		    return true;
        			
        		}
     
            }
        	
        
        
        } 
        
        return false;
     
    }
	
	

	

	

	
}
?>
