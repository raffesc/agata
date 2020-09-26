//
//  CreaAPIViewController.swift
//  Agata
//
//  Created by Raffaele on 04/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class CreaAPIViewController: UIViewController {
    
    var query: [BackEndAttributeResponse] = []
    var header: [BackEndAttributeResponse] = []
    var body: [BackEndAttributeResponse] = []
    
    var whatToAdd = 0

    
    @IBOutlet weak var pathAPITextField: UITextField!
    
    @IBOutlet weak var queryParameterLabel: UILabel!
    
    @IBOutlet weak var headerParameterLabel: UILabel!
    
    @IBOutlet weak var bodyParameterLabel: UILabel!
    
    @IBOutlet weak var creaButton: UIButton!
    
    
    @IBOutlet weak var collectionViewQuery: UICollectionView!
    
    @IBOutlet weak var collectionViewHeader: UICollectionView!
    
    @IBOutlet weak var collectionViewBody: UICollectionView!
    
    @IBOutlet weak var shadowViewSingolaModal: ShadowView!
    
    @IBOutlet weak var valoreSingoloModal: UITextField!
    
    @IBOutlet weak var aggiungiButtonSingoloModal: UIButton!
    
    
    @IBOutlet weak var annullaButtonSingoloModal: UIButton!
    
    @IBOutlet weak var shadowViewMultiplaModal: ShadowView!
    
    @IBOutlet weak var chiaveMultiplaModal: UITextField!
    
    @IBOutlet weak var valoreMultiplaModal: UITextField!
    
    @IBOutlet weak var aggiungiMultiplaModal: UIButton!
    
    @IBOutlet weak var annullaMultiplaModal: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setupLayout()
        setupCollectionViews()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))

        //Uncomment the line below if you want the tap not not interfere and cancel other interactions.
        //tap.cancelsTouchesInView = false

        view.addGestureRecognizer(tap)

    }
    
    //Calls this function when the tap is recognized.
    @objc func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }
    

    @IBAction func creaAPI(_ sender: Any) {
        
        
        guard let pathAPI = pathAPITextField.text else {
            Alert.shared.showAlert(in: self, title: "Attenzione", message: "Controllare di aver inserito il path")
            return
        }
        if pathAPI.count == 0 {
            Alert.shared.showAlert(in: self, title: "Attenzione", message: "Controllare di aver inserito il path")
            return
        }
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
               
        NetworkManager.shared.addBackEnd(backEndRequest:BackEndRequest(id_project: ProjectSingleton.shared.progetto!.id!, id_owner: ProjectSingleton.shared.progetto!.id_owner!, nome: pathAPI, status: "1", type: "1"), completition: {
             (response) in
             
             // CORRECT RESPONSE
            Spinner.shared.removeSpinner(spinner: spinner)
           
            guard let response = response else {
                Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                return
               }
           
            let spinnerInterno = Spinner.shared.showSpinner(onView: self.view)
          
            let attributes = self.getAttributes(id_backend: response.id!)
            
            if attributes.count > 0 {
                NetworkManager.shared.addAttributeBackEnd(backendAttributes: attributes, completition: {
                             (response) in
                             
                             // CORRECT RESPONSE
                            Spinner.shared.removeSpinner(spinner: spinnerInterno)
                           
                    guard response != nil else {
                                Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                                return
                               }
                           
                             let alert = UIAlertController(title: "Successo", message: "API inserita correttamente!", preferredStyle: UIAlertController.Style.alert)

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

                
                let alert = UIAlertController(title: "Successo", message: "API inserita correttamente!", preferredStyle: UIAlertController.Style.alert)

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
    
    

    @IBAction func annullaSIngolaHandlerModal(_ sender: Any) {
        
        shadowViewSingolaModal.fadeOut()

    }
    
    
    
    @IBAction func aggiungiSingolaHandlerModal(_ sender: Any) {
        
        guard let testo = valoreSingoloModal.text else {
            return
        }
        
        if testo.count == 0 {
            return
        }
        
        switch whatToAdd {
        case 0:
            query.append(BackEndAttributeResponse(id: "", id_back_end: "", title: testo, priv: "1", type: "", value: " "))
            self.collectionViewQuery.reloadData()
            break
        case 2:
            body.append(BackEndAttributeResponse(id: "", id_back_end: "", title: testo, priv: "1", type: "", value: " "))
            self.collectionViewBody.reloadData()
            break
        default:
            break
        }
        valoreSingoloModal.text = ""
        shadowViewSingolaModal.fadeOut()
    }
    

    @IBAction func aggiungiMultiplaHandlerModal(_ sender: Any) {
        
        guard let chiave = chiaveMultiplaModal.text else {
            return
        }
        
        if chiave.count == 0 {
            return
        }
        
        guard let valore = valoreMultiplaModal.text else {
            return
        }
        
        if valore.count == 0 {
            return
        }
        
        if whatToAdd == 1 {
            header.append(BackEndAttributeResponse(id: "", id_back_end: "", title: chiave, priv: "1", type: " ", value: valore))
            self.collectionViewHeader.reloadData()
            chiaveMultiplaModal.text = ""
            valoreMultiplaModal.text = ""
        }
        
        shadowViewMultiplaModal.fadeOut()
        
    }
    
    @IBAction func annullaMultiplaHandlerModal(_ sender: Any) {
        
        shadowViewMultiplaModal.fadeOut()
        
    }
    
    @IBAction func aggiungiQuery(_ sender: Any) {
        
        shadowViewSingolaModal.fadeIn()
        whatToAdd = 0
        
        
    }
    
    
    @IBAction func aggiungiHeader(_ sender: Any) {
        
        shadowViewMultiplaModal.fadeIn()
        whatToAdd = 1

        
    }
    
    @IBAction func aggiungiBody(_ sender: Any) {
        
        shadowViewSingolaModal.fadeIn()
        whatToAdd = 2

        
    }
    
}

extension CreaAPIViewController {
    
    func getAttributes(id_backend: String) -> [BackEndAttributeRequest] {
        
        var backendAtt: [BackEndAttributeRequest] = []

        for qu in query {
            
            backendAtt.append(BackEndAttributeRequest(id_back_end: id_backend, title: qu.title, priv: "1", type: "1", value: " "))
            
        }
        
        for he in header {
            
            backendAtt.append(BackEndAttributeRequest(id_back_end: id_backend, title: he.title, priv: "1", type: "2", value: he.value))
            
        }
        
        for bo in body {
            
            backendAtt.append(BackEndAttributeRequest(id_back_end: id_backend, title: bo.title, priv: "1", type: "3", value: " "))
            
        }
        return backendAtt
        
    }
    
    func setupLayout() {
        
        queryParameterLabel.textColor = ProjectSingleton.shared.mainColor
        headerParameterLabel.textColor = ProjectSingleton.shared.mainColor
        bodyParameterLabel.textColor = ProjectSingleton.shared.mainColor

        pathAPITextField.borderStyle = .none
        pathAPITextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        pathAPITextField.layer.cornerRadius = 10
        //To apply border
        pathAPITextField.layer.borderWidth = 0.25
        pathAPITextField.layer.borderColor = UIColor.white.cgColor
        //To apply Shadow
        pathAPITextField.layer.shadowOpacity = 0.5
        pathAPITextField.layer.shadowRadius = 3.0
        pathAPITextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        pathAPITextField.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
        //To apply padding
        let paddingView : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: pathAPITextField.frame.height))
        pathAPITextField.leftView = paddingView
        pathAPITextField.leftViewMode = .always
        pathAPITextField.translatesAutoresizingMaskIntoConstraints = false

        
        shadowViewSingolaModal.isHidden = true
        aggiungiButtonSingoloModal.layer.shadowOffset = CGSize(width: 0, height: 3)
        aggiungiButtonSingoloModal.backgroundColor = ProjectSingleton.shared.mainColor
        aggiungiButtonSingoloModal.layer.shadowOpacity = 0.5
        aggiungiButtonSingoloModal.layer.shadowRadius = 3
        aggiungiButtonSingoloModal.layer.masksToBounds = false
        aggiungiButtonSingoloModal.layer.cornerRadius = 10
        
        annullaButtonSingoloModal.layer.shadowOffset = CGSize(width: 0, height: 3)
        annullaButtonSingoloModal.layer.shadowOpacity = 0.5
        annullaButtonSingoloModal.layer.shadowRadius = 3
        annullaButtonSingoloModal.layer.masksToBounds = false
        annullaButtonSingoloModal.layer.cornerRadius = 10

        shadowViewSingolaModal.changeColor(with: ProjectSingleton.shared.mainColor!)
        
        shadowViewMultiplaModal.isHidden = true
        aggiungiMultiplaModal.layer.shadowOffset = CGSize(width: 0, height: 3)
        aggiungiMultiplaModal.backgroundColor = ProjectSingleton.shared.mainColor
        aggiungiMultiplaModal.layer.shadowOpacity = 0.5
        aggiungiMultiplaModal.layer.shadowRadius = 3
        aggiungiMultiplaModal.layer.masksToBounds = false
        aggiungiMultiplaModal.layer.cornerRadius = 10
        
        annullaMultiplaModal.layer.shadowOffset = CGSize(width: 0, height: 3)
        annullaMultiplaModal.layer.shadowOpacity = 0.5
        annullaMultiplaModal.layer.shadowRadius = 3
        annullaMultiplaModal.layer.masksToBounds = false
        annullaMultiplaModal.layer.cornerRadius = 10

        shadowViewMultiplaModal.changeColor(with: ProjectSingleton.shared.mainColor!)

        
        creaButton.layer.backgroundColor = ProjectSingleton.shared.mainColor?.cgColor
        
        
    }
    
    func setupCollectionViews() {
        collectionViewQuery.register(UINib(nibName: "BackEndQueryCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-query-cell")
        collectionViewHeader.register(UINib(nibName: "BackEndHeaderCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-header-cell")
        collectionViewBody.register(UINib(nibName: "BackEndBodyCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-body-cell")
    }

    
}

extension CreaAPIViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        switch collectionView {
        case collectionViewHeader:
            return header.count
        case collectionViewQuery:
            return query.count
        default:
            return body.count
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let mainColor = ProjectSingleton.shared.mainColor
        switch collectionView {
        case collectionViewHeader:
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "back-end-header-cell", for: indexPath) as! BackEndHeaderCollectionViewCell
            cell.backGround.backgroundColor = mainColor
            cell.typeLabel.text = header[indexPath.row].title
            cell.valueLabel.text = header[indexPath.row].value
            cell.typeLabel.textColor = .white
            cell.valueLabel.textColor = .white
            return cell
        case collectionViewQuery:
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "back-end-query-cell", for: indexPath) as! BackEndQueryCollectionViewCell
            cell.backGround.backgroundColor = mainColor
            cell.mainLabel.text = query[indexPath.row].title
            cell.mainLabel.textColor = .white
            return cell
        default:
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "back-end-body-cell", for: indexPath) as! BackEndBodyCollectionViewCell
            cell.backGround.backgroundColor = mainColor
            cell.mainLabel.text = body[indexPath.row].title
            cell.mainLabel.textColor = .white
            return cell
        }

    }
    
   
    
  
    

}

extension CreaAPIViewController: UICollectionViewDelegateFlowLayout {
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        switch collectionView {
        case collectionViewHeader:
            let title = header[indexPath.row].title
            let subtitle = header[indexPath.row].value
            let max = title!.count > subtitle!.count ? title : subtitle
            let width = max!.count * 14
            return CGSize(width: width + 10, height: 60)
        case collectionViewQuery:
            let width = (query[indexPath.row].title?.count ?? 0) * 14
            return CGSize(width: width + 10, height: 50)
        default:
            let width = (body[indexPath.row].title?.count ?? 0) * 14
            return CGSize(width: width + 10, height: 50)
        }
    }
    
}
