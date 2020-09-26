//
//  BackEndViewController.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class BackEndViewController: UIViewController {
    
    
    var listBackEnd: [BackEndRecordResponse] = []
    
    var listEndPoint: [EndpointRecordResponse] = []
    
    var listTagsQuery: [Int] = []
    var listTagsHeaders: [Int] = []
    var listTagsBody: [Int] = []
    
    @IBOutlet weak var segmentControl: UISegmentedControl!
    
    @IBOutlet weak var tableViewAPI: UITableView!
    
    
    @IBOutlet weak var tableViewEndPoint: UITableView!
    
    
    @IBOutlet weak var popUpShadowView: ShadowView!
    
    
    @IBOutlet weak var endPointTextField: UITextField!
    
    @IBOutlet weak var valoreTextField: UITextField!
    
    @IBOutlet weak var aggiungiButton: UIButton!
    
    @IBOutlet weak var annullaButton: UIButton!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupLayoutPopup()
        setupTableViews()
        let titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        segmentControl.setTitleTextAttributes(titleTextAttributes, for: .selected)
        segmentControl.selectedSegmentTintColor = ProjectSingleton.shared.mainColor
        fetchDataAPI()
        fetchDataEndpoint()
        //Looks for single or multiple taps.
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    
    func setupLayoutPopup() {
        popUpShadowView.isHidden = true
        popUpShadowView.changeColor(with: ProjectSingleton.shared.mainColor!)

        aggiungiButton.backgroundColor = ProjectSingleton.shared.mainColor
        aggiungiButton.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
        aggiungiButton.layer.shadowOffset = CGSize(width: 0, height: 1)
        aggiungiButton.layer.shadowOpacity = 0.5
        aggiungiButton.layer.shadowRadius = 3
        aggiungiButton.layer.masksToBounds = false
        aggiungiButton.layer.cornerRadius = 10
        
        
        annullaButton.layer.shadowColor = UIColor.red.cgColor
        annullaButton.layer.shadowOffset = CGSize(width: 0, height: 1)
        annullaButton.layer.shadowOpacity = 0.5
        annullaButton.layer.shadowRadius = 3
        annullaButton.layer.masksToBounds = false
        annullaButton.layer.cornerRadius = 10

    }
    
    @IBAction func segmentControlChange(_ sender: Any) {
        
        if segmentControl.selectedSegmentIndex == 0 {
            self.tableViewEndPoint.isHidden = true
            self.tableViewAPI.isHidden = false
        } else {
            self.tableViewEndPoint.isHidden = false
            self.tableViewAPI.isHidden = true
        }

    }
    
    
    
    @IBAction func plusDidPress(_ sender: Any) {
        
        
        switch segmentControl.selectedSegmentIndex {
        case 0:
            performSegue(withIdentifier: "goToCreateAPI", sender: self)
            break
        default:
            popUpShadowView.fadeIn()
            break
        }
        
        
        
    }
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        super.touchesBegan(touches, with: event)

        let touch = touches.first
        guard let location = touch?.location(in: self.view) else { return }
        if !popUpShadowView.isHidden {
            if !popUpShadowView.frame.contains(location) {
                print("Tapped outside the view")
                popUpShadowView.fadeOut()
            } else {
                print("Tapped inside the view")
            }
        }
        
    }

    
    
    @IBAction func aggiungiEndPointHandler(_ sender: Any) {
        
        guard let valore = valoreTextField.text  else {
            return
        }
        guard let endpoint = endPointTextField.text   else {
            return
        }
        if valore.count > 0 && endpoint.count > 0 {
            
            
            let spinner = Spinner.shared.showSpinner(onView: self.view)
                               
            NetworkManager.shared.addEnpointInProject(idProject: (ProjectSingleton.shared.progetto?.id!)!, title: endpoint, descrizione: valore, completition: {
                 (response) in
                 
                 // CORRECT RESPONSE
                Spinner.shared.removeSpinner(spinner: spinner)
               
               
                guard let response = response else {
                    Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                    return
                   }
                
                self.listEndPoint.append(response)
                
                self.popUpShadowView.fadeOut()
               
               self.tableViewEndPoint.reloadData()
               
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
    
    
    @IBAction func annullaEndPointHandler(_ sender: Any) {
        
        
        popUpShadowView.fadeOut()
        
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

extension BackEndViewController {
    
    
    func fetchDataAPI () {
     
        
       let spinner = Spinner.shared.showSpinner(onView: self.view)
                    
                NetworkManager.shared.listBackEndAPI(idProgetto: (ProjectSingleton.shared.progetto?.id!)!, completition: {
                      (response) in
                      
                      // CORRECT RESPONSE
                     Spinner.shared.removeSpinner(spinner: spinner)
                    
                    
                     guard let response = response else {
                       //  Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                         return
                        }
                    
                    guard let records = response.records else {
                        return
                    }
                  self.listBackEnd = records
                    self.tableViewAPI.reloadData()
                    print(self.listBackEnd)
                    
                  }, completitionError: { (error) in
                      
                    Spinner.shared.removeSpinner(spinner: spinner)
                      // HANDLE ERROR
                      guard error != nil else {
                        //Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                          return
                      }
                   // Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")


                  })
                   
        
    }
    
    func fetchDataEndpoint() {
        
        
                     
         NetworkManager.shared.listBackEndEndpoint(idProgetto: (ProjectSingleton.shared.progetto?.id!)!, completition: {
               (response) in
               
             
             
              guard let response = response else {
               //   Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                  return
                 }
             
             guard let records = response.records else {
                 return
             }
           self.listEndPoint = records
             self.tableViewEndPoint.reloadData()

             
           }, completitionError: { (error) in
               

           })

    }
    
}

extension BackEndViewController: UITableViewDelegate, UITableViewDataSource {
    
    func setupTableViews() {
        self.tableViewAPI.register(UINib(nibName: "BackEndTableViewCell", bundle: nil), forCellReuseIdentifier: "back-end-cell")
        self.tableViewAPI.separatorStyle = .none
        self.tableViewEndPoint.register(UINib(nibName: "EndpointTableViewCell", bundle: nil), forCellReuseIdentifier: "endpoint-cell")
        self.tableViewEndPoint.separatorStyle = .none
        self.tableViewEndPoint.isHidden = true
        self.tableViewAPI.isHidden = false
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        switch tableView {
        case tableViewAPI:
            return listBackEnd.count
        case tableViewEndPoint:
            return listEndPoint.count
        default:
            return 0
        }
         
        
     }
          
      func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let correctIndex = indexPath.row
        let mainColor = ProjectSingleton.shared.mainColor
        switch tableView {
        case tableViewAPI:
            let cell = tableView.dequeueReusableCell(withIdentifier: "back-end-cell", for: indexPath) as! BackEndTableViewCell
            let backEnd = listBackEnd[correctIndex]
            cell.titleLabel.text = backEnd.nome
            cell.titleLabel.textColor = mainColor
            cell.lineLabel.backgroundColor = mainColor
            cell.status.textColor = .white
            cell.status.clipsToBounds = true
            cell.status.layer.cornerRadius = 25
            cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
            cell.status.isUserInteractionEnabled = true
            cell.status.tag = indexPath.row
            cell.status.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleChangeStatus)))
            if backEnd.status == "1" {
                cell.status.text = "TO DO"
                cell.status.backgroundColor = .red
            }
            if backEnd.status == "2" {
                cell.status.text = "DOING"
                cell.status.backgroundColor = .yellow
            }
            if backEnd.status == "3" {
                cell.status.text = "DONE"
                cell.status.backgroundColor = .green
            }
            if listBackEnd[indexPath.row].query?.count ?? 0 > 0 {
                cell.queryTitle.isHidden = false
                cell.collectionViewQuery.isHidden = false
            } else {
                cell.queryTitle.isHidden = true
                cell.collectionViewQuery.isHidden = true
            }
            if listBackEnd[indexPath.row].header?.count ?? 0 > 0{
                cell.headerTitle.isHidden = false
                cell.collectionViewHeader.isHidden = false
            } else {
                cell.headerTitle.isHidden = true
                cell.collectionViewHeader.isHidden = true
            }
            if listBackEnd[indexPath.row].body?.count ?? 0 > 0 {
                cell.bodyTitle.isHidden = false
                cell.collectionViewBody.isHidden = false
            } else {
                cell.bodyTitle.isHidden = true
                cell.collectionViewBody.isHidden = true
            }
            setupCollectionViewQuery(with: cell.collectionViewQuery,tag: Int("0" + String(indexPath.row))!)
            setupCollectionViewHeader(with: cell.collectionViewHeader,tag: Int("1" + String(indexPath.row))!)
            setupCollectionViewBody(with: cell.collectionViewBody,tag: Int("2" + String(indexPath.row))!)
            cell.collectionViewQuery.reloadData()
            cell.collectionViewHeader.reloadData()
            cell.collectionViewBody.reloadData()
            cell.queryTitle.textColor = mainColor
            cell.headerTitle.textColor = mainColor
            cell.bodyTitle.textColor = mainColor
            cell.selectionStyle = .none
            return cell
        case tableViewEndPoint:
            let cell = tableView.dequeueReusableCell(withIdentifier: "endpoint-cell", for: indexPath) as! EndpointTableViewCell
            cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
            cell.lineLabel.backgroundColor = ProjectSingleton.shared.mainColor
            cell.titleLabel.textColor = ProjectSingleton.shared.mainColor
            cell.valueLabel.textColor = ProjectSingleton.shared.mainColor
            cell.titleLabel.text = listEndPoint[correctIndex].title
            cell.valueLabel.text = listEndPoint[correctIndex].descrizione
            cell.lineLabel.backgroundColor = mainColor
            cell.selectionStyle = .none
            return cell
        default:
            return UITableViewCell()
        }
      }
          
      func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch tableView {
        case tableViewEndPoint:
            return 100
        default:
            var height = 0
            if listBackEnd[indexPath.row].query?.count ?? 0 > 0 {
                height = height + 77
            }
            if listBackEnd[indexPath.row].header?.count ?? 0 > 0 {
                height = height + 137
            }
            if listBackEnd[indexPath.row].body?.count ?? 0 > 0 {
                height = height + 77
            }
            return CGFloat(height + 90)
        }
      }
    
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
        
        // action two
        let deleteAction = UITableViewRowAction(style: .default, title: "Elimina", handler: { (action, indexPath) in
            let alert = UIAlertController(title: "Attenzione", message: tableView == self.tableViewAPI ? "Sei sicuro di voler eliminare l'API?" : "Sei sicuro di voler eliminare l'endpoint?", preferredStyle: UIAlertController.Style.alert)

                let alertOKAction = UIAlertAction(title:"Si", style: UIAlertAction.Style.default,handler: { action in
                    
                    switch tableView {
                    case self.tableViewEndPoint:
                        self.eliminaEndpoint(position: indexPath.row)
                        break
                    default:
                        self.eliminaBackEnd(position: indexPath.row)
                        break
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
    
    @objc func handleChangeStatus (_ sender: UIGestureRecognizer) {
        let index = sender.view!.tag
        
        var status = Int(listBackEnd[index].status!)
        switch (status) {
        case 1:
            status = 2
        case 2:
            status = 3
        case 3:
            status = 1
        default:
            status = 1
        }
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)

        
        NetworkManager.shared.aggiornaStatusBackEnd(id: listBackEnd[index].id!,status: String(status!), completition: {
                     (response) in
                     
                     // CORRECT RESPONSE
                    Spinner.shared.removeSpinner(spinner: spinner)
                   
                   
                   guard response != nil else {
                        Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                        return
                       }
            
            self.listBackEnd[index].status = String(status!)
            self.tableViewAPI.reloadData()
                   
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

     func eliminaEndpoint(position: Int) {
         
         let spinner = Spinner.shared.showSpinner(onView: self.view)
             
         NetworkManager.shared.eliminaEndpoint(id: listEndPoint[position].id!, completition: {
               (response) in
               
               // CORRECT RESPONSE
              Spinner.shared.removeSpinner(spinner: spinner)
             
             
             guard response != nil else {
                  Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                  return
                 }
             
            self.listEndPoint.remove(at: position)
            self.tableViewEndPoint.reloadData()
             
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
     
     
    func eliminaBackEnd(position: Int) {
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
            
        NetworkManager.shared.eliminaBackEnd(id: listBackEnd[position].id!, completition: {
              (response) in
              
              // CORRECT RESPONSE
             Spinner.shared.removeSpinner(spinner: spinner)
            
            
            guard response != nil else {
                 Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                 return
                }
            
           self.listBackEnd.remove(at: position)
           self.tableViewAPI.reloadData()
            
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


extension BackEndViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let tag = collectionView.tag
        var tagStringed = String(tag)
        if tagStringed.count == 1 {
            tagStringed = "0" + tagStringed
        }
        switch tagStringed.first {
        case "0":
            tagStringed.removeFirst()
            return listBackEnd[Int(tagStringed)!].query!.count
        case "1":
            tagStringed.removeFirst()
            return listBackEnd[Int(tagStringed)!].header!.count
        default:
            tagStringed.removeFirst()
            return listBackEnd[Int(tagStringed)!].body!.count
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let tag = collectionView.tag
        var tagStringed = String(tag)
        if tagStringed.count == 1 {
            tagStringed = "0" + tagStringed
        }
        let mainColor = ProjectSingleton.shared.mainColor
        switch tagStringed.first {
        case "0":
            tagStringed.removeFirst()
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "back-end-query-cell", for: indexPath) as! BackEndQueryCollectionViewCell
            cell.backGround.backgroundColor = mainColor
            cell.mainLabel.text = listBackEnd[Int(tagStringed)!].query?[indexPath.row].title
            cell.mainLabel.textColor = .white
            return cell
        case "1":
            tagStringed.removeFirst()
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "back-end-header-cell", for: indexPath) as! BackEndHeaderCollectionViewCell
            cell.backGround.backgroundColor = mainColor
            cell.typeLabel.text = listBackEnd[Int(tagStringed)!].header?[indexPath.row].title
            cell.valueLabel.text = listBackEnd[Int(tagStringed)!].header?[indexPath.row].value
            cell.typeLabel.textColor = .white
            cell.valueLabel.textColor = .white
            return cell
        default:
            tagStringed.removeFirst()
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "back-end-body-cell", for: indexPath) as! BackEndBodyCollectionViewCell
            cell.backGround.backgroundColor = mainColor
            cell.mainLabel.text = listBackEnd[Int(tagStringed)!].body?[indexPath.row].title
            cell.mainLabel.textColor = .white
            return cell
        }
    }
    
    func setupCollectionViewQuery(with collectionView: UICollectionView, tag: Int){
        collectionView.register(UINib(nibName: "BackEndQueryCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-query-cell")
        collectionView.register(UINib(nibName: "BackEndHeaderCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-header-cell")
        collectionView.register(UINib(nibName: "BackEndBodyCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-body-cell")
        collectionView.tag = tag
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    func setupCollectionViewHeader(with collectionView: UICollectionView, tag: Int){
        collectionView.register(UINib(nibName: "BackEndQueryCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-query-cell")
        collectionView.register(UINib(nibName: "BackEndHeaderCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-header-cell")
        collectionView.register(UINib(nibName: "BackEndBodyCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-body-cell")
        collectionView.tag = tag
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    func setupCollectionViewBody(with collectionView: UICollectionView, tag: Int){
        collectionView.register(UINib(nibName: "BackEndQueryCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-query-cell")
        collectionView.register(UINib(nibName: "BackEndHeaderCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-header-cell")
        collectionView.register(UINib(nibName: "BackEndBodyCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "back-end-body-cell")
        collectionView.tag = tag
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    

}

extension BackEndViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let tag = collectionView.tag
        var tagStringed = String(tag)
        if tagStringed.count == 1 {
            tagStringed = "0" + tagStringed
        }
        switch tagStringed.first {
        case "0":
            tagStringed.removeFirst()
            let width = (listBackEnd[Int(tagStringed)!].query?[indexPath.row].title?.count ?? 0) * 14
            return CGSize(width: width + 10, height: 50)
        case "1":
            tagStringed.removeFirst()
            let title = listBackEnd[Int(tagStringed)!].header?[indexPath.row].title
            let subtitle = listBackEnd[Int(tagStringed)!].header?[indexPath.row].value
            let max = title!.count > subtitle!.count ? title : subtitle
            let width = max!.count * 14
            return CGSize(width: width + 10, height: 60)
        default:
            tagStringed.removeFirst()
            let width = (listBackEnd[Int(tagStringed)!].body?[indexPath.row].title?.count ?? 0) * 14
            return CGSize(width: width + 10, height: 50)
        }
    }
}


extension BackEndViewController {
    
    //Calls this function when the tap is recognized.
      @objc func dismissKeyboard() {
          //Causes the view (or one of its embedded text fields) to resign the first responder status.
          view.endEditing(true)
      }
    
}
