<?php


  //  error_reporting(0);


    class Database{
     
        // specify your own database credentials
        private $host = "#######";
        private $db_name = "#######";
        private $username = "#######";
        private $password = "#######";
        private $port = "#######";
        public $conn;
     
        // get the database connection
        public function getConnection(){
     
            $this->conn = null;
            
            $dsn = 'mysql:dbname=#######;host=#######';
            $user = '#######';
            $password = '#######';

            try {
                $this->conn = new PDO($dsn, $user, $password);
            } catch (PDOException $e) {
                echo 'Connection failed: ' . $e->getMessage();
            }

            
            /*

            try{
               // $this->conn = new PDO("pgsql:host=" . $this->host . ";dbname=" . $this->db_name, $this->username, $this->password);
				// Create connection
                //$this->conn = mysqli_connect($host, $username, $password, $db_name);
			    
			    $this->conn = new PDO("mysql:" . sprintf(
			    "host=%s;port=%s;user=%s;password=%s;dbname=%s",
			    $this->host,
			    $this->port,
			    $this->username,
			    $this->password,
			    $this->db_name
				));
                $this->conn->exec("set names utf8");

            }catch(PDOException $exception){
                echo "Connection error: " . $exception->getMessage();
            }
            
            */
			

     
            return $this->conn;
        }
        
    }
?>
