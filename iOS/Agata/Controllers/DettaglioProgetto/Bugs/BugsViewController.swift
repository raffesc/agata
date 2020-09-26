//
//  BugsViewController.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class BugsViewController: UIViewController , UITableViewDelegate, UITableViewDataSource {
    
    var listBugs: [[BugsRecordResponse]] = []
    
    var bugDaPassare: [BugsRecordResponse] = []
    
    var imageViewNoResult: UIImageView = UIImageView()
    var labelNoResult: UILabel = UILabel()

    @IBOutlet weak var tableView: UITableView!
    
    
    @IBOutlet weak var shadowViewTop: ShadowView!
    
    @IBOutlet weak var titleNuovaCategoriaLabel: UITextField!
    
    @IBOutlet weak var descrizioneNuovoBugLabel: UITextField!
    
    @IBOutlet weak var aggiungiNuovoBugButton: UIButton!
    
    @IBOutlet weak var annullaAggiuntaNuovoBugButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupLayout()
        fetchData()
        setupTableView()
    }

    
    
    @IBAction func aggiungiNuovoBugDialog(_ sender: Any) {
        
        guard let title = titleNuovaCategoriaLabel.text else {
            return
        }
        
        guard let bugVal = descrizioneNuovoBugLabel.text else {
            return
        }
        
        if title.count > 0 && bugVal.count > 0 {
            
            let spinner = Spinner.shared.showSpinner(onView: self.view)

            
            NetworkManager.shared.addCategoryBug(idProject: (ProjectSingleton.shared.progetto?.id!)!,titleCategory: title, completition: {
                  (response) in
                  
                  // CORRECT RESPONSE
                 Spinner.shared.removeSpinner(spinner: spinner)
                 guard let response = response else {
                     Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                     return
                }
                
                let spinnerInterno = Spinner.shared.showSpinner(onView: self.view)
                
                NetworkManager.shared.addBugInCategory(idProject: (ProjectSingleton.shared.progetto?.id!)!, idCategory: response.id!, descrizione: bugVal, completition: { (response) in
                    
                    Spinner.shared.removeSpinner(spinner: spinnerInterno)
                     guard let response = response else {
                         Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                         return
                    }
                    self.shadowViewTop.fadeOut()
                    
                    self.fetchData()
                    
                }) { (error) in
                    Spinner.shared.removeSpinner(spinner: spinnerInterno)
                      // HANDLE ERROR
                      guard error != nil else {
                          Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                          return
                      }
                    Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")

                    
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
        
        
        
        
    }
    
    
    @IBAction func annullaNuovoBugDialog(_ sender: Any) {
        
        shadowViewTop.fadeOut()
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

extension BugsViewController {
    
    func setupLayout() {
        
        shadowViewTop.isHidden = true
        aggiungiNuovoBugButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        aggiungiNuovoBugButton.backgroundColor = ProjectSingleton.shared.mainColor
        aggiungiNuovoBugButton.layer.shadowOpacity = 0.5
        aggiungiNuovoBugButton.layer.shadowRadius = 3
        aggiungiNuovoBugButton.layer.masksToBounds = false
        aggiungiNuovoBugButton.layer.cornerRadius = 10
        
        annullaAggiuntaNuovoBugButton.layer.shadowOffset = CGSize(width: 0, height: 3)
        annullaAggiuntaNuovoBugButton.layer.shadowOpacity = 0.5
        annullaAggiuntaNuovoBugButton.layer.shadowRadius = 3
        annullaAggiuntaNuovoBugButton.layer.masksToBounds = false
        annullaAggiuntaNuovoBugButton.layer.cornerRadius = 10

        shadowViewTop.changeColor(with: ProjectSingleton.shared.mainColor!)
    }
    
}

extension BugsViewController {
    
    func fetchData() {
        
        listBugs = []
        let spinner = Spinner.shared.showSpinner(onView: self.view)
        
        NetworkManager.shared.listBugs(idProgetto: (ProjectSingleton.shared.progetto?.id!)!, completition: {
              (response) in
              
              // CORRECT RESPONSE
             Spinner.shared.removeSpinner(spinner: spinner)
             guard let response = response else {
                // Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
              //  (self.imageViewNoResult, self.labelNoResult) = Utils.createNoResult(in: self.view, title: "Nessun bug trovato")
                 return
                }
            
            // algoritmo per raggruppare i bug per categoria
            for elem in response.records! {
                var isInside = false
                for i in 0..<self.listBugs.count {
                    if self.listBugs[i].first?.id_category == elem.id_category {
                        self.listBugs[i].append(elem)
                        isInside = true
                    }
                }
                if !isInside {
                    self.listBugs.append([elem])
                }
            }
           // self.listBugs = response.records
            self.tableView.reloadData()
            self.imageViewNoResult.isHidden = false
            self.labelNoResult.isHidden = false
          
                  
          }, completitionError: { (error) in
              
            Spinner.shared.removeSpinner(spinner: spinner)
          //  (self.imageViewNoResult, self.labelNoResult) = Utils.createNoResult(in: self.view, title: "Nessun bug trovato")
              // HANDLE ERROR
              guard error != nil else {
              //    Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                  return
              }
           // Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")


          })
             
        

    }

    
}



// MARK: Table View
extension BugsViewController {
    
    private func setupTableView() {
        self.tableView.register(UINib(nibName: "BugsTableViewCell", bundle: nil), forCellReuseIdentifier: "bugs-cell")
        self.tableView.register(UINib(nibName: "AggiungiTableViewCell", bundle: nil), forCellReuseIdentifier: "aggiungi-cell")
        self.tableView.separatorStyle = .none
    }
    

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listBugs.count + 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let correctIndex = indexPath.row
        if indexPath.row == listBugs.count {
            let cell = tableView.dequeueReusableCell(withIdentifier: "aggiungi-cell", for: indexPath) as! AggiungiTableViewCell
            cell.aggiungiButton.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
            cell.aggiungiButton.layer.backgroundColor = ProjectSingleton.shared.mainColor?.cgColor
            cell.aggiungiButton.layer.shadowOffset = CGSize(width: 0, height: 3)
            cell.aggiungiButton.layer.shadowOpacity = 0.5
            cell.aggiungiButton.layer.shadowRadius = 3
            cell.aggiungiButton.layer.masksToBounds = false
            cell.aggiungiButton.layer.cornerRadius = 10
            cell.aggiungiButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleAggiungiBug))) // Each item should be able to trigger and action on tap
            return cell
        }
        let cell = tableView.dequeueReusableCell(withIdentifier: "bugs-cell", for: indexPath) as! BugsTableViewCell
        cell.selectionStyle = .none
        cell.titleLabel.text = listBugs[indexPath.row].first?.nome_category
        cell.titleLabel.textColor = Utils.defaultColor.color
        cell.counter.text = String(listBugs[indexPath.row].count)
        cell.counter.textColor = UIColor.white
        cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
        if listBugs[indexPath.row].count > 99 {
            cell.counter.text = "99+"
        }
        cell.backGround.backgroundColor = UIColor.red
        cell.backGround.clipsToBounds = true
        cell.backGround.layer.cornerRadius = 10
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == listBugs.count {
            return 60
        }
        return 80
    }

    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        self.bugDaPassare = listBugs[indexPath.row]
        self.performSegue(withIdentifier: "goToDettaglioBug", sender: self)
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "goToDettaglioBug" {
            if let destinationVC = segue.destination as? DettaglioBugViewController {
                destinationVC.listBugs = self.bugDaPassare
            }
        }
    }
    
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {

             // action two
             let deleteAction = UITableViewRowAction(style: .default, title: "Elimina", handler: { (action, indexPath) in
                      let alert = UIAlertController(title: "Attenzione", message: "Sei sicuro di voler eliminare il bug?", preferredStyle: UIAlertController.Style.alert)

                      let alertOKAction = UIAlertAction(title:"Si", style: UIAlertAction.Style.default,handler: { action in
                          
                        let spinner = Spinner.shared.showSpinner(onView: self.view)
                        
                        for i in 0..<self.listBugs[indexPath.row].count {
                            
                            self.eliminaBug(spinner: spinner,position: indexPath.row,index: i)

                            
                        }
                          
                          
                         
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
    
    
    func eliminaBug(spinner: UIView,position: Int, index: Int) {
        
            
        NetworkManager.shared.eliminaBug(id: listBugs[position][index].id!, completition: {
              (response) in
            
            guard response != nil else {
                        Spinner.shared.removeSpinner(spinner: spinner)
                            Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                            return
            }
            
            
            self.listBugs[position].removeFirst()
            if self.listBugs[position].count == 0 {
                // CORRECT RESPONSE
                Spinner.shared.removeSpinner(spinner: spinner)
                self.listBugs.remove(at: position)
                self.tableView.reloadData()
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


    
}


extension BugsViewController {
    
    @objc func handleAggiungiBug(_ sender: UIGestureRecognizer) {
        shadowViewTop.fadeIn()
    }
    
}
