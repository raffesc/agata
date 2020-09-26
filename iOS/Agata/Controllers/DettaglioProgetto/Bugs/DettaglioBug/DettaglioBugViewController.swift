//
//  DettaglioBugViewController.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class DettaglioBugViewController: UIViewController,UITableViewDelegate, UITableViewDataSource {
    
    var listBugs: [BugsRecordResponse] = []

    @IBOutlet weak var tableView: UITableView!
    
    @IBOutlet weak var shadowViewModal: ShadowView!
    
    @IBOutlet weak var descrizioneBugModal: UITextField!
    
    @IBOutlet weak var aggiungiButtonModal: UIButton!
    
    @IBOutlet weak var annullaButtonModal: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupTableView()
        setupLayout()

    }
    

    @IBAction func aggiungiButtonHandlerModal(_ sender: Any) {
        
        guard let descrizione = descrizioneBugModal.text else {
            return
        }
        
        if descrizione.count == 0 {
            return
        }
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
                               
        NetworkManager.shared.addBugInCategory(idProject: ProjectSingleton.shared.progetto!.id!, idCategory: listBugs.first!.id_category!, descrizione: descrizione,
                                                    completition: {
                 (response) in
                 
                 // CORRECT RESPONSE
                 Spinner.shared.removeSpinner(spinner: spinner)
                 
                 
                 guard let response = response else {
                     Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                     return
                 }
                                                     
                self.listBugs.append(response)
                 
                 
                 self.tableView.reloadData()
                                                        
                                                        self.shadowViewModal.fadeOut()
                 
                 
             }, completitionError: { (error) in
                 
                 Spinner.shared.removeSpinner(spinner: spinner)
                 // HANDLE ERROR
                 guard error != nil else {
                     Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                     return
                 }
                 Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")
                 
                self.shadowViewModal.fadeOut()

                 
             })
        
        
    }
    
    
    @IBAction func annullaButtonHandlerModal(_ sender: Any) {
        
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

// MARK: Table View
extension DettaglioBugViewController {
    
    private func setupTableView() {
        self.tableView.register(UINib(nibName: "DettaglioBugTableViewCell", bundle: nil), forCellReuseIdentifier: "dettaglio-bug-cell")
        self.tableView.register(UINib(nibName: "AggiungiTableViewCell", bundle: nil), forCellReuseIdentifier: "aggiungi-cell")
        self.tableView.separatorStyle = .none
    }
    

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listBugs.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == listBugs.count {
            let cell = tableView.dequeueReusableCell(withIdentifier: "aggiungi-cell", for: indexPath) as! AggiungiTableViewCell
            cell.aggiungiButton.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
            cell.aggiungiButton.layer.backgroundColor = ProjectSingleton.shared.mainColor?.cgColor
            cell.aggiungiButton.tag = Int(listBugs.first!.id_category!) ?? 0
            cell.aggiungiButton.layer.shadowOffset = CGSize(width: 0, height: 3)
            cell.aggiungiButton.layer.shadowOpacity = 0.5
            cell.aggiungiButton.layer.shadowRadius = 3
            cell.aggiungiButton.layer.masksToBounds = false
            cell.aggiungiButton.layer.cornerRadius = 10
            cell.aggiungiButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleAggiungiBug))) // Each item should be able to trigger and action on tap
            return cell
        }
        let cell = tableView.dequeueReusableCell(withIdentifier: "dettaglio-bug-cell", for: indexPath) as! DettaglioBugTableViewCell
        cell.selectionStyle = .none
        cell.descrizione.text = listBugs[indexPath.row].descrizione
        cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == listBugs.count {
            return 60
        }
        let label = UILabel()
        label.text = listBugs[indexPath.row].descrizione
        let val = CGFloat(label.calculateMaxLines() * 60)
        label.removeFromSuperview()
        return val
    }
    
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {

        

           // action two
           let deleteAction = UITableViewRowAction(style: .default, title: "Elimina", handler: { (action, indexPath) in
                    let alert = UIAlertController(title: "Attenzione", message: "Sei sicuro di voler eliminare il bug?", preferredStyle: UIAlertController.Style.alert)

                    let alertOKAction = UIAlertAction(title:"Si", style: UIAlertAction.Style.default,handler: { action in
                        
                        
                        self.eliminaBug(position: indexPath.row)
                        
                       
                     })
               
               let alertNoAction = UIAlertAction(title:"Annulla", style: UIAlertAction.Style.default,handler: { action in
                   
                  
                  
                          })

                    alert.addAction(alertOKAction)
               alert.addAction(alertNoAction)

               self.present(alert, animated: true, completion: nil)
           })
           deleteAction.backgroundColor = UIColor.red

           return [deleteAction]
       }

    func eliminaBug(position: Int) {
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
            
        NetworkManager.shared.eliminaBug(id: listBugs[position].id!, completition: {
              (response) in
              
              // CORRECT RESPONSE
             Spinner.shared.removeSpinner(spinner: spinner)
            
            
            guard response != nil else {
                 Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                 return
                }
            
            self.presentingViewController?.dismiss(animated: false, completion: nil)
            self.presentingViewController?.dismiss(animated: true, completion: nil)

            
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
    
    
    @objc func handleAggiungiBug(_ sender: UIGestureRecognizer) {
        
        shadowViewModal.fadeIn()
      

    }

    
}

extension DettaglioBugViewController {
    
    func setupLayout() {
        
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
        
    }

    
}
