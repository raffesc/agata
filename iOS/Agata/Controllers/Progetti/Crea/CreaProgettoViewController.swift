//
//  CreaProgettoViewController.swift
//  Agata
//
//  Created by Raffaele on 07/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class CreaProgettoViewController: UIViewController,UITextFieldDelegate {
    
    
    @IBOutlet weak var titoloProjTextField: UITextField!
    
    @IBOutlet weak var coloreLabel: UILabel!
    
    
    @IBOutlet weak var collectionViewColore: UICollectionView!
    
    
    @IBOutlet weak var labelIconaPrincipale: UILabel!
    
    
    @IBOutlet weak var collectionViewIconaPrincipale: UICollectionView!
    
    
    @IBOutlet weak var creaButton: UIButton!
    
    var colors = Utils.getAllColors()
    
    var icons = Utils.getAllIcons()
    
    var selectedColor = 0
    var selectedIcon = 0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        setupLayout()
        setupCollectionViews()
       // let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))

        //Uncomment the line below if you want the tap not not interfere and cancel other interactions.
        //tap.cancelsTouchesInView = false

       // view.addGestureRecognizer(tap)
        self.titoloProjTextField.delegate = self
        
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.view.endEditing(true)
        return false
    }

    /*
    //Calls this function when the tap is recognized.
    @objc func dismissKeyboard() {
        //Causes the view (or one of its embedded text fields) to resign the first responder status.
        view.endEditing(true)
    }
*/
    
    
    @IBAction func createButton(_ sender: Any) {
        
        let id_users = UserSingleton.shared.user!.id!
        
        guard let title = titoloProjTextField.text else {
            Alert.shared.showAlert(in: self, title: "Attenzione", message: "Ogni progetto deve avere un titolo.")
            return
        }
        if title.count == 0 {
            Alert.shared.showAlert(in: self, title: "Attenzione", message: "Ogni progetto deve avere un titolo.")
            return
        }
        
        var iconSel = selectedIcon + 1
        
        var mainColor = selectedColor + 1
        
        let projRequest = CreaProjRequest(id_owner: UserSingleton.shared.user!.id!, id_users: id_users, title: title, icon: String(iconSel), main_color: String(mainColor))
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
                           
        NetworkManager.shared.addProgetto(projRequest: projRequest, completition: {
             (response) in
             
             // CORRECT RESPONSE
            Spinner.shared.removeSpinner(spinner: spinner)
            
            guard let response = response else {
                Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                return
            }
            
            
             let alert = UIAlertController(title: "Successo", message: "Progetto creato correttamente!", preferredStyle: UIAlertController.Style.alert)

             let alertOKAction = UIAlertAction(title:"OK", style: UIAlertAction.Style.default,handler: { action in
                 

                self.navigationController?.popViewController(animated: true)

                
                        })
             alert.addAction(alertOKAction)
            self.present(alert, animated: true, completion: nil)

           
            
            
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

extension CreaProgettoViewController {
    
    
    func setupLayout() {
        
        titoloProjTextField.borderStyle = .none
        titoloProjTextField.backgroundColor = .white // Use anycolor that give you a 2d look.
               //To apply corner radius
        titoloProjTextField.layer.cornerRadius = 10
        //To apply border
        titoloProjTextField.layer.borderWidth = 0.25
        titoloProjTextField.layer.borderColor = Utils.defaultColor.color.cgColor
        //To apply Shadow
        titoloProjTextField.layer.shadowOpacity = 0.5
        titoloProjTextField.layer.shadowRadius = 3.0
        titoloProjTextField.layer.shadowOffset = CGSize.zero // Use any CGSize
        titoloProjTextField.layer.shadowColor = Utils.defaultColor.color.cgColor
        //To apply padding
        let paddingView : UIView = UIView(frame: CGRect(x: 0, y: 0, width: 20, height: titoloProjTextField.frame.height))
        titoloProjTextField.leftView = paddingView
        titoloProjTextField.leftViewMode = .always
        titoloProjTextField.translatesAutoresizingMaskIntoConstraints = false

        
        creaButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        creaButton.backgroundColor = Utils.defaultColor.color
        creaButton.layer.shadowOpacity = 0.5
        creaButton.layer.shadowRadius = 3
        creaButton.layer.masksToBounds = false
        creaButton.layer.cornerRadius = 10

    }
    
    func setupCollectionViews() {
            collectionViewColore.register(UINib(nibName: "CreaProjCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "crea-proj-cell")
        collectionViewColore.dataSource = self
        collectionViewColore.delegate = self
        collectionViewIconaPrincipale.register(UINib(nibName: "CreaProjCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "crea-proj-cell")
        collectionViewIconaPrincipale.dataSource = self
        collectionViewIconaPrincipale.delegate = self
    }
    
}

extension CreaProgettoViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        switch collectionView {
        case collectionViewColore:
            return colors.count
        default:
            return icons.count
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "crea-proj-cell", for: indexPath) as! CreaProjCollectionViewCell
        switch collectionView {
        case collectionViewColore:
            if indexPath.row == selectedColor {
                cell.mainLabel.backgroundColor = Utils.defaultColor.color
            } else {
                cell.mainLabel.backgroundColor = .black
            }
            cell.mainImageView.image = UIImage()
            cell.mainImageView.clipsToBounds = true
            cell.mainImageView.layer.cornerRadius = 30
            cell.mainImageView.backgroundColor = colors[indexPath.row].color
            return cell
        default:
            if indexPath.row == selectedIcon {
                cell.mainLabel.backgroundColor = Utils.defaultColor.color
            } else {
                cell.mainLabel.backgroundColor = .black
            }
            cell.mainImageView.image = icons[indexPath.row]
            return cell
        }
        

    }
    
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        switch collectionView {
        case collectionViewColore:
            selectedColor = indexPath.row
            collectionViewColore.reloadData()
            break
        default:
            selectedIcon = indexPath.row
            collectionViewIconaPrincipale.reloadData()
            break
        }
    }
    
    func setupCollectionView(with collectionView: UICollectionView, tag: Int){
        collectionView.register(UINib(nibName: "DesignCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "design-col-cell")
        collectionView.tag = tag
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    


   
    
    

}

extension CreaProgettoViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 100, height: 100)
    }
}



