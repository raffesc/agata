
<?php

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;



class Mailer2{

    private $user;
    private $email;
    private $smtp_config;

    public function __construct($user_id,$email){
		
		
		
		$this->user = $user_id;
		$this->email = $email;
        $smtp_config = new Smtp_Config();
        $this->smtp_config = $smtp_config->where(["admin_id" => $user_id])->one();
    }


    function sendConfirmOrder(){

        $mail = new PHPMailer(true);                              // Passing `true` enables exceptions
        try {

            $mail->ClearAllRecipients( );

            //Server settings
            // $mail->SMTPDebug = 2;                                 // Enable verbose debug output
           $mail->isSMTP();                                      // Set mailer to use SMTP
            //$mail->Host = $this->smtp_config->host;  // Specify main and backup SMTP servers
            $mail->SMTPAuth = true;                              
            //$mail->Username = $this->smtp_config->username;                
            //$mail->Password = $this->smtp_config->password;                          
			//$mail->SMTPSecure = $this->smtp_config->encryption;                            
            //$mail->Port = $this->smtp_config->port;                                    
			//$mail->addAddress('ellen@example.com');              
            //$mail->addReplyTo('info@example.com', 'Information');
			//$mail->setFrom($this->smtp_config->sender_email, 'Mailer');

            //Recipients
            
            $mail->addAddress($this->email);     // Add a recipient
            
            // $mail->addCC('cc@example.com');
            // $mail->addBCC('bcc@example.com');

            //Attachments
            // $mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
            // $mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
            
            //Content

            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = 'Ordine completato';
            $mail_body = "<h2>Complimenti!!!!</h2>";
            $mail_body .= "<h4>Grazie per il tuo ordine con pagamento alla consegna.</h4>";
            $mail_body .= "<p>Il tuo codice dell'ordine : <b>" . $order_id . "</b></p>";
            $mail_body .= "<p>Non condividere questo codice con nessuno.</p>";

            //$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
            $mail->Body = $mail_body;
            $mail->SMTPOptions = array(
                'ssl' => array(
                    'verify_peer' => false,
                    'verify_peer_name' => false,
                    'allow_self_signed' => true
                )
            );
		
		
		
		$mail->Host = $this->smtp_config->host;;
        $mail->Username = $this->smtp_config->username;            
        $mail->Password = $this->smtp_config->password;     
        $mail->SetFrom($this->smtp_config->sender_email, 'Verification Email');
        $mail->Port =  $this->smtp_config->port;  
		$mail->AddReplyTo($this->user->email);
        $mail->SMTPSecure = $this->smtp_config->encryption; 
		
		



            $mail->send();
			
			

            return true;
            // echo 'Message has been sent';
        } catch (Exception $e) {
            echo 'Message could not be sent. Mailer Error: ', $mail->ErrorInfo;
            return false;
        }
    }
    
    function sendConfirmOrderBrainTree($order_id,$email){

        $mail = new PHPMailer(true);                              // Passing `true` enables exceptions
        try {

            $mail->ClearAllRecipients( );

            //Server settings
            // $mail->SMTPDebug = 2;                                 // Enable verbose debug output
           $mail->isSMTP();                                      // Set mailer to use SMTP
            //$mail->Host = $this->smtp_config->host;  // Specify main and backup SMTP servers
            $mail->SMTPAuth = true;                              
            //$mail->Username = $this->smtp_config->username;                
            //$mail->Password = $this->smtp_config->password;                          
			//$mail->SMTPSecure = $this->smtp_config->encryption;                            
            //$mail->Port = $this->smtp_config->port;                                    
			//$mail->addAddress('ellen@example.com');              
            //$mail->addReplyTo('info@example.com', 'Information');
			//$mail->setFrom($this->smtp_config->sender_email, 'Mailer');

            //Recipients
            
            $mail->addAddress($email);     // Add a recipient
            
            // $mail->addCC('cc@example.com');
            // $mail->addBCC('bcc@example.com');

            //Attachments
            // $mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
            // $mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
            
            //Content

            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = 'Ordine completato';
            $mail_body = "<h2>Complimenti!!!!</h2>";
            $mail_body .= "<h4>Grazie per il tuo ordine</h4>";
            $mail_body .= "<p>Il tuo codice dell'ordine : <b>" . $order_id . "</b></p>";
            $mail_body .= "<p>Non condividere questo codice con nessuno.</p>";

            //$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
            $mail->Body = $mail_body;
            $mail->SMTPOptions = array(
                'ssl' => array(
                    'verify_peer' => false,
                    'verify_peer_name' => false,
                    'allow_self_signed' => true
                )
            );
		
		
		
		$mail->Host = $this->smtp_config->host;;
        $mail->Username = $this->smtp_config->username;            
        $mail->Password = $this->smtp_config->password;     
        $mail->SetFrom($this->smtp_config->sender_email, 'Verification Email');
        $mail->Port =  $this->smtp_config->port;  
		$mail->AddReplyTo($this->user->email);
        $mail->SMTPSecure = $this->smtp_config->encryption; 
		
		



            $mail->send();
			
			

            return true;
            // echo 'Message has been sent';
        } catch (Exception $e) {
            echo 'Message could not be sent. Mailer Error: ', $mail->ErrorInfo;
            return false;
        }
    }


}