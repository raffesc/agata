//
//  CreaFrontEndViewController.swift
//  Agata
//
//  Created by Raffaele on 05/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class CreaFrontEndViewController: UIViewController {

    
    var listAttributes: [FrontEndAttributeResponse] = []
    
    var type = -1
    
    @IBOutlet weak var patternTitleTextField: UITextField!
    
    @IBOutlet weak var attributiEFunzioniLabel: UILabel!
    
    
    @IBOutlet weak var shadowViewModal: ShadowView!
    
    @IBOutlet weak var attributoLabelModal: UITextField!
    
    @IBOutlet weak var tipoLabelModal: UITextField!
    @IBOutlet weak var privatoLabelModal: UILabel!
    
    
    @IBOutlet weak var switchModal: UISwitch!
    
    
    @IBOutlet weak var aggiungiButtonModal: UIButton!
    
    @IBOutlet weak var annullaButtonModal: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        setupLayout()
        setupTableView()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    @IBAction func aggiungiAttributiFunzioni(_ sender: Any) {
        
        shadowViewModal.fadeIn()
        
    }
    
    @IBOutlet weak var tableView: UITableView!
    
    @IBAction func aggiungiModalHandler(_ sender: Any) {
        
        guard let valore = attributoLabelModal.text else {
            return
        }
        if valore.count == 0 {
            return
        }
        
        guard let tipo = tipoLabelModal.text else {
            return
        }
        
        let attribute = FrontEndAttributeResponse(id: "", id_front_end: "", value: valore, priv: (switchModal.isOn ? "1" : "2"), type: (tipo.count > 0 ? tipo : " "))
        
        listAttributes.append(attribute)
        
        self.tableView.reloadData()
        shadowViewModal.fadeOut()
    }
    
    
    @IBAction func annullaModalHandler(_ sender: Any) {
        
        shadowViewModal.fadeOut()
        
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


extension CreaFrontEndViewController {
    
    
    func setupLayout () {
        
        
        attributiEFunzioniLabel.textColor = ProjectSingleton.shared.mainColor
        
        shadowViewModal.isHidden = true
       aggiungiButtonModal.layer.shadowOffset = CGSize(width: 0, height: 3)
       aggiungiButtonModal.backgroundColor = ProjectSingleton.shared.mainColor
       aggiungiButtonModal.layer.shadowOpacity = 0.5
       aggiungiButtonModal.layer.shadowRadius = 3
       aggiungiButtonModal.layer.masksToBounds = false
       aggiungiButtonModal.layer.cornerRadius = 10
       
       annullaButtonModal.layer.shadowOffset = CGSize(width: 0, height: 3)
       annullaButtonModal.layer.shadowOpacity = 0.5
       annullaButtonModal.layer.shadowRadius = 3
       annullaButtonModal.layer.masksToBounds = false
       annullaButtonModal.layer.cornerRadius = 10

       shadowViewModal.changeColor(with: ProjectSingleton.shared.mainColor!)
        
        patternTitleTextField.borderStyle = .none
        patternTitleTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        patternTitleTextField.layer.cornerRadius = 10
        //To apply border
        patternTitleTextField.layer.borderWidth = 0.25
        patternTitleTextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        patternTitleTextField.layer.shadowOpacity = 0.5
        patternTitleTextField.layer.shadowRadius = 3.0
        patternTitleTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        patternTitleTextField.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
        //To apply padding
        let paddingView : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: patternTitleTextField.frame.height))
        patternTitleTextField.leftView = paddingView
        patternTitleTextField.leftViewMode = .always
        patternTitleTextField.translatesAutoresizingMaskIntoConstraints = false

        
        
    }
    
    private func setupTableView() {
              self.tableView.register(UINib(nibName: "CreaFrontEndTableViewCell", bundle: nil), forCellReuseIdentifier: "crea-front-end-cell")
        self.tableView.register(UINib(nibName: "AggiungiTableViewCell", bundle: nil), forCellReuseIdentifier: "aggiungi-cell")

              self.tableView.separatorStyle = .none
              
    }
    
    
}

extension CreaFrontEndViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listAttributes.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == listAttributes.count {
            let cell = tableView.dequeueReusableCell(withIdentifier: "aggiungi-cell", for: indexPath) as! AggiungiTableViewCell
            cell.aggiungiButton.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
            cell.aggiungiButton.layer.backgroundColor = ProjectSingleton.shared.mainColor?.cgColor
            cell.aggiungiButton.layer.shadowOffset = CGSize(width: 0, height: 3)
            cell.aggiungiButton.layer.shadowOpacity = 0.5
            cell.aggiungiButton.layer.shadowRadius = 3
            cell.aggiungiButton.layer.masksToBounds = false
            cell.aggiungiButton.layer.cornerRadius = 10
            cell.aggiungiButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleAggiungiCategory))) // Each item should be able to trigger and action on tap
            cell.aggiungiButton.setTitle("INSERISCI NUOVO PATTERN", for: .normal)
            return cell
        }
        let cell = tableView.dequeueReusableCell(withIdentifier: "crea-front-end-cell", for: indexPath) as! CreaFrontEndTableViewCell
        cell.selectionStyle = .none
        cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
        let attribute = listAttributes[indexPath.row]
        var stringToAppend = ""
        if attribute.priv == "1" {
            stringToAppend = stringToAppend + "- "
        } else {
            stringToAppend = stringToAppend + "+ "
        }
        stringToAppend = stringToAppend + (attribute.value ?? "") + ": " + (attribute.type ?? "")
        cell.mainLabel.text = stringToAppend
         return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == listAttributes.count {
            return 60
        }
        let label = UILabel()
        let attribute = listAttributes[indexPath.row]
        var stringToAppend = ""
        if attribute.priv == "1" {
            stringToAppend = stringToAppend + "- "
        } else {
            stringToAppend = stringToAppend + "+ "
        }
        stringToAppend = stringToAppend + (attribute.value ?? "") + ": " + (attribute.type ?? "")
        label.text = stringToAppend
        let val = CGFloat(label.calculateMaxLines() * 60)
        label.removeFromSuperview()
        return val
    }
    
    @objc func handleAggiungiCategory(_ sender: UIGestureRecognizer) {
        
        guard let titoloFront = patternTitleTextField.text else {
                   Alert.shared.showAlert(in: self, title: "Attenzione", message: "Controllare di aver inserito un titolo per il pattern")
                   return
               }
               if titoloFront.count == 0 {
                   Alert.shared.showAlert(in: self, title: "Attenzione", message: "Controllare di aver inserito un titolo per il pattern")
                   return
               }
               
               let spinner = Spinner.shared.showSpinner(onView: self.view)
                      
                type = type + 1
        
        NetworkManager.shared.addFrontEnd(frontEndRequest:FrontEndRequest(id_project: ProjectSingleton.shared.progetto!.id!, id_owner: ProjectSingleton.shared.progetto!.id_owner!, nome: titoloFront, status: "1", type: String(type)), completition: {
                    (response) in
                    
                    // CORRECT RESPONSE
                   Spinner.shared.removeSpinner(spinner: spinner)
                  
                   guard let response = response else {
                       Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                       return
                      }
                  
                   let spinnerInterno = Spinner.shared.showSpinner(onView: self.view)
                 
                   let attributes = self.getAttributes(id_front_end: response.id!)
                   
                   if attributes.count > 0 {
                       NetworkManager.shared.addAttributeFrontEnd(frontendAttributes: attributes, completition: {
                                    (response) in
                                    
                                    // CORRECT RESPONSE
                                   Spinner.shared.removeSpinner(spinner: spinnerInterno)
                                  
                                   guard let response = response else {
                                       Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                                       return
                                      }
                                  
                                    let alert = UIAlertController(title: "Successo", message: "Pattern inserito correttamente!", preferredStyle: UIAlertController.Style.alert)

                                    let alertOKAction = UIAlertAction(title:"OK", style: UIAlertAction.Style.default,handler: { action in
                                        
                                       self.presentingViewController?.dismiss(animated: false, completion: nil)
                                       self.presentingViewController?.dismiss(animated: true, completion: nil)

                                               })
                                    alert.addAction(alertOKAction)
                               self.present(alert, animated: true, completion: nil)

                           
                               
                                    
                                  
                                }, completitionError: { (error) in
                                    
                                  Spinner.shared.removeSpinner(spinner: spinnerInterno)
                                    // HANDLE ERROR
                                    guard error != nil else {
                                        Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                                        return
                                    }
                                  Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")


                                })
                   } else {
                       
                       Spinner.shared.removeSpinner(spinner: spinnerInterno)

                       
                       let alert = UIAlertController(title: "Successo", message: "Pattern inserito correttamente!", preferredStyle: UIAlertController.Style.alert)

                       let alertOKAction = UIAlertAction(title:"OK", style: UIAlertAction.Style.default,handler: { action in
                           
                           self.presentingViewController?.dismiss(animated: false, completion: nil)
                           self.presentingViewController?.dismiss(animated: true, completion: nil)

                                  })
                       alert.addAction(alertOKAction)
                       self.present(alert, animated: true, completion: nil)
                       
                   }
                   
                
                   
                  
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
    
    public func getAttributes(id_front_end: String) -> [FrontEndAttributeRequest] {
        var attributesCurr:[FrontEndAttributeRequest] = []
        for attrib in listAttributes {
            attributesCurr.append(FrontEndAttributeRequest(id_front_end: id_front_end, value: attrib.value, priv: attrib.priv, type: attrib.type))
        }
        return attributesCurr
    }
    
}

extension CreaFrontEndViewController {
    
    //Calls this function when the tap is recognized.
      @objc func dismissKeyboard() {
          //Causes the view (or one of its embedded text fields) to resign the first responder status.
          view.endEditing(true)
      }
    
}
