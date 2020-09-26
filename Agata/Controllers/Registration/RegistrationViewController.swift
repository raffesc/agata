//
//  RegistrationViewController.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class RegistrationViewController: UIViewController {

    
    
    
    @IBOutlet weak var nomeTextField: UITextField!
    
    @IBOutlet weak var cognomeTextField: UITextField!
    
    
    @IBOutlet weak var usernameTextField: UITextField!
    
    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var passwordTextField: UITextField!
    
    @IBOutlet weak var passwordConfirmTextField: UITextField!
    
    @IBOutlet weak var ageTextField: UITextField!
    
    @IBOutlet weak var registrationButton: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        //Looks for single or multiple taps.
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))

        //Uncomment the line below if you want the tap not not interfere and cancel other interactions.
        //tap.cancelsTouchesInView = false

        view.addGestureRecognizer(tap)

        setupLayout()
    }
    

    @IBAction func nomeChanged(_ sender: Any) {
    }
    
    
    @IBAction func cognomeChanged(_ sender: Any) {
    }
    
    @IBAction func usernameChanged(_ sender: Any) {
    }
    
    @IBAction func emailChanged(_ sender: Any) {
    }
    
    @IBAction func passwordChanged(_ sender: Any) {
    }
    
    @IBAction func registerMe(_ sender: Any) {
        
        let nome = nomeTextField.text
        
        if nome == nil || nome == "" {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire un nome valido")
            return
        }
        
        let cognome = cognomeTextField.text
        
        if cognome == nil || cognome == "" {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire un cognome valido")
            return
        }
        
        let username = usernameTextField.text
        
        if username == nil || username == "" {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire un username valido")
            return
        }
        
        
       let email = emailTextField.text
       
        if email == nil || email == "" {
           Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire un'email valida")
           return
       }
        
        if !email!.isValidEmail() {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire un'email valida")
            return
        }
       
       let password = passwordTextField.text
       
        if password == nil || password == "" {
           Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire una password valida")
           return
       }
        
        let passwordConfirm = passwordConfirmTextField.text
        
         if passwordConfirm == nil || passwordConfirm == "" || passwordConfirm != password {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire una password di conferma valida")
            return
        }
        
        if password!.count < 8 {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire una password di almeno 8 caratteri")
            return
        }
        
        let age = ageTextField.text
               
        if age == nil || age == "" {
           Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire una età valida")
           return
       }
       
        let image = " "
        
       let spinner = Spinner.shared.showSpinner(onView: self.view)
        
        
    
       
        NetworkManager.shared.register(registerRequest: RegistrationRequest(email: email!, password: password!, username: username!, image: image, nome: nome!, cognome: cognome!, age: age!), completion: {
            (response) in
            
            // CORRECT RESPONSE
           Spinner.shared.removeSpinner(spinner: spinner)
           print(response)
           guard let response = response else {
               Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
               return
           }
        
            Alert.shared.showAlert(in: self, title: "Congratulazioni!", message: response.response!, completition: {
                
                self.dismiss(animated: true, completion: nil)

               // _ = self.navigationController?.popViewController(animated: true)

            })
                
        }, completitionError: { (error) in
            
            Spinner.shared.removeSpinner(spinner: spinner)
            // HANDLE ERROR
            guard error != nil else {
                Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                return
            }
            Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")

            

        })
           


        
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

// MARK: Layout
extension RegistrationViewController {
    
    func setupLayout() {
        
        // NOME TEXT FIELD
        nomeTextField.borderStyle = .none
        nomeTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        nomeTextField.layer.cornerRadius = 10
        //To apply border
        nomeTextField.layer.borderWidth = 0.25
        nomeTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        nomeTextField.layer.shadowOpacity = 0.5
        nomeTextField.layer.shadowRadius = 3.0
        nomeTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        nomeTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: nomeTextField.frame.height))
        nomeTextField.leftView = paddingView
        nomeTextField.leftViewMode = .always
        nomeTextField.translatesAutoresizingMaskIntoConstraints = false

        // COGNOME TEXT FIELD
        cognomeTextField.borderStyle = .none
        cognomeTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        cognomeTextField.layer.cornerRadius = 10
        //To apply border
        cognomeTextField.layer.borderWidth = 0.25
        cognomeTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        cognomeTextField.layer.shadowOpacity = 0.5
        cognomeTextField.layer.shadowRadius = 3.0
        cognomeTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        cognomeTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView2 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: cognomeTextField.frame.height))
        cognomeTextField.leftView = paddingView2
        cognomeTextField.leftViewMode = .always
        cognomeTextField.translatesAutoresizingMaskIntoConstraints = false

        // USERNAME TEXT FIELD
        usernameTextField.borderStyle = .none
        usernameTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        usernameTextField.layer.cornerRadius = 10
        //To apply border
        usernameTextField.layer.borderWidth = 0.25
        usernameTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        usernameTextField.layer.shadowOpacity = 0.5
        usernameTextField.layer.shadowRadius = 3.0
        usernameTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        usernameTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView3 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: usernameTextField.frame.height))
        usernameTextField.leftView = paddingView3
        usernameTextField.leftViewMode = .always
        usernameTextField.translatesAutoresizingMaskIntoConstraints = false

        
        // EMAIL TEXT FIELD
        emailTextField.borderStyle = .none
        emailTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        emailTextField.layer.cornerRadius = 10
        //To apply border
        emailTextField.layer.borderWidth = 0.25
        emailTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        emailTextField.layer.shadowOpacity = 0.5
        emailTextField.layer.shadowRadius = 3.0
        emailTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        emailTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView4 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: emailTextField.frame.height))
        emailTextField.leftView = paddingView4
        emailTextField.leftViewMode = .always
        emailTextField.translatesAutoresizingMaskIntoConstraints = false
        
        // PASSWORD TEXT FIELD
        passwordTextField.borderStyle = .none
        passwordTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        passwordTextField.layer.cornerRadius = 10
        //To apply border
        passwordTextField.layer.borderWidth = 0.25
        passwordTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        passwordTextField.layer.shadowOpacity = 0.5
        passwordTextField.layer.shadowRadius = 3.0
        passwordTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        passwordTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView5 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: passwordTextField.frame.height))
        passwordTextField.leftView = paddingView5
        passwordTextField.leftViewMode = .always
        passwordTextField.translatesAutoresizingMaskIntoConstraints = false

        // PASSWORD CONFIRM TEXT FIELD
        passwordConfirmTextField.borderStyle = .none
        passwordConfirmTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        passwordConfirmTextField.layer.cornerRadius = 10
        //To apply border
        passwordConfirmTextField.layer.borderWidth = 0.25
        passwordConfirmTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        passwordConfirmTextField.layer.shadowOpacity = 0.5
        passwordConfirmTextField.layer.shadowRadius = 3.0
        passwordConfirmTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        passwordConfirmTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView6 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: passwordConfirmTextField.frame.height))
        passwordConfirmTextField.leftView = paddingView6
        passwordConfirmTextField.leftViewMode = .always
        passwordConfirmTextField.translatesAutoresizingMaskIntoConstraints = false

        // AGE CONFIRM TEXT FIELD
        ageTextField.borderStyle = .none
        ageTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        ageTextField.layer.cornerRadius = 10
        //To apply border
        ageTextField.layer.borderWidth = 0.25
        ageTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        ageTextField.layer.shadowOpacity = 0.5
        ageTextField.layer.shadowRadius = 3.0
        ageTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        ageTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView7 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: ageTextField.frame.height))
        ageTextField.leftView = paddingView7
        ageTextField.leftViewMode = .always
        ageTextField.translatesAutoresizingMaskIntoConstraints = false
        ageTextField.keyboardType = .numberPad

        // Register Button
        registrationButton.layer.shadowColor = Utils.defaultColor.color.cgColor
        registrationButton.layer.shadowOffset = CGSize(width: 0, height: 1)
        registrationButton.layer.shadowOpacity = 0.5
        registrationButton.layer.shadowRadius = 3
        registrationButton.layer.masksToBounds = false
        registrationButton.layer.cornerRadius = 10

        
    }
}

// MARK: Action
extension RegistrationViewController {

    //Calls this function when the tap is recognized.
    @objc func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }

    
}
