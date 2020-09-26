//
//  LoginViewController.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    @IBOutlet weak var registrationButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        setupLayout()
        setupIsAlreadyLogged()
        
        //Looks for single or multiple taps.
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))

        //Uncomment the line below if you want the tap not not interfere and cancel other interactions.
        //tap.cancelsTouchesInView = false

        view.addGestureRecognizer(tap)

    }
    
    func setupIsAlreadyLogged() {
        let defaults = UserDefaults.standard
        guard let email = defaults.string(forKey:"email" ) else {
            return
        }
        guard let password = defaults.string(forKey: "password") else {
            return
        }
        emailTextField.text = email
        passwordTextField.text = password
        doLogin(self)
        
    }
    

    @IBAction func doLogin(_ sender: Any) {
        
        let email = emailTextField.text
        
        if email == nil || email == "" {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire un'email valida")
            return
        }
        
        let password = passwordTextField.text
        if password == nil || password == "" {
            Alert.shared.showAlert(in: self, title: "Errore", message: "Inserire una password valida")
            return
        }
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
        
        NetworkManager.shared.login(loginRequestParameters: LoginRequest(email: email!, password: password!)) { (response) in
            Spinner.shared.removeSpinner(spinner: spinner)
            guard let response = response else {
                Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                return
            }
            
            UserSingleton.shared.user = response
            let defaults = UserDefaults.standard
            defaults.set(email, forKey: "email")
            defaults.set(password, forKey: "password")
            // go to main page
            self.performSegue(withIdentifier: "goToHome", sender: self)
            
        }
        
        
    }
    @IBAction func goToRegistration(_ sender: Any) {
        


        self.performSegue(withIdentifier: "register", sender: self)
        
        //let vc = RegistrationViewController()
        //self.present(vc, animated: true, completion: nil)

        
    }
    
    


}

// MARK: LAYOUT
extension LoginViewController {
    
    func setupLayout() {
        
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
        let paddingView : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: emailTextField.frame.height))
        emailTextField.leftView = paddingView
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
        let paddingView2 : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: emailTextField.frame.height))

        passwordTextField.leftView = paddingView2
        passwordTextField.leftViewMode = .always
        passwordTextField.translatesAutoresizingMaskIntoConstraints = false

        
        // Login Button
        loginButton.layer.shadowColor = Utils.defaultColor.color.cgColor
        loginButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        loginButton.layer.shadowOpacity = 0.5
        loginButton.layer.shadowRadius = 3
        loginButton.layer.masksToBounds = false
        loginButton.layer.cornerRadius = 10

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
extension LoginViewController {

    //Calls this function when the tap is recognized.
    @objc func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }

    
}
