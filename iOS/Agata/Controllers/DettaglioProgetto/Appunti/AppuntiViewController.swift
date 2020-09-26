//
//  AppuntiViewController.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class AppuntiViewController: UIViewController  {

    
    var listAppunti: [[AppuntiRecordResponse]] = []
    var currIndex = -1

    @IBOutlet weak var collectionView: UICollectionView!
    
    
    
    @IBOutlet weak var shadowViewSingolaModal: ShadowView!
    
    @IBOutlet weak var valoreSingolaLabelModal: UITextField!
    
    @IBOutlet weak var aggiungiSingolaModal: UIButton!
    
    @IBOutlet weak var annullaSingolaModal: UIButton!
    
    
    @IBOutlet weak var shadowViewMultiplaModal: ShadowView!
    
    
    @IBOutlet weak var categoriaMultiplaLabelModal: UITextField!
    
    @IBOutlet weak var valoreMultiplaLabelModal: UITextField!
    
    
    @IBOutlet weak var aggiungiButtonModal: UIButton!
    
    @IBOutlet weak var annullaButtonModal: UIButton!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        setupLayout()
        setupCollectionView()
       // fetchData()
    }
    
    
    @IBAction func aggiungiSingolaModalHandler(_ sender: Any) {
        
        
        guard let valore = valoreSingolaLabelModal.text else {
            return
        }

        if valore.count == 0 {
            return
        }
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
                                      
        NetworkManager.shared.addAppuntoInCategory(idProject: ProjectSingleton.shared.progetto!.id!, idCategory: listAppunti[currIndex].first!.id!, title: valore,
            completition: {
                        (response) in
                        
                        // CORRECT RESPONSE
                        Spinner.shared.removeSpinner(spinner: spinner)
                        
                        
                        guard let response = response else {
                            Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                            return
                        }
                                                            
                    self.listAppunti[self.currIndex].append(response)
                        
                        self.collectionView.reloadData()
                                                               
                        self.shadowViewSingolaModal.fadeOut()
                        
                        
                    }, completitionError: { (error) in
                        
                        Spinner.shared.removeSpinner(spinner: spinner)
                        // HANDLE ERROR
                        guard error != nil else {
                            Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                            return
                        }
                        Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")
                        
                       self.shadowViewSingolaModal.fadeOut()

                        
                    })
               
               
        
        
    }
    

    @IBAction func annullaSingolaModalHandler(_ sender: Any) {
        
        shadowViewSingolaModal.fadeOut()
    }
    
    @IBAction func aggiungiMultiplaModalHandler(_ sender: Any) {
        
        
    }
    
    
    
    @IBAction func annullaMultiplaModalHandler(_ sender: Any) {
        
        shadowViewMultiplaModal.fadeOut()

    }
    
    func setupLayout() {

        
        shadowViewSingolaModal.isHidden = true
        aggiungiSingolaModal.layer.shadowOffset = CGSize(width: 0, height: 3)
        aggiungiSingolaModal.backgroundColor = ProjectSingleton.shared.mainColor
        aggiungiSingolaModal.layer.shadowOpacity = 0.5
        aggiungiSingolaModal.layer.shadowRadius = 3
        aggiungiSingolaModal.layer.masksToBounds = false
        aggiungiSingolaModal.layer.cornerRadius = 10
        
        annullaSingolaModal.layer.shadowOffset = CGSize(width: 0, height: 3)
        annullaSingolaModal.layer.shadowOpacity = 0.5
        annullaSingolaModal.layer.shadowRadius = 3
        annullaSingolaModal.layer.masksToBounds = false
        annullaSingolaModal.layer.cornerRadius = 10

        shadowViewSingolaModal.changeColor(with: ProjectSingleton.shared.mainColor!)
        
        shadowViewMultiplaModal.isHidden = true
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

        shadowViewMultiplaModal.changeColor(with: ProjectSingleton.shared.mainColor!)

        
        
    }
    
    
   
    
}

extension AppuntiViewController {
    
    
    func fetchData() {
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
        
        NetworkManager.shared.listAppunti(idProgetto: (ProjectSingleton.shared.progetto?.id!)!, completition: {
              (response) in
              
              // CORRECT RESPONSE
             Spinner.shared.removeSpinner(spinner: spinner)
            
            
            
            
             guard let response = response else {
                 Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                 return
                }
            
            guard let appunti = response.records else {
                return
            }
            // algoritmo per raggruppare gli appunti
            for appunto in appunti {
                var isInside = false
                for i in 0..<self.listAppunti.count {
                    if self.listAppunti[i].first?.id_category == appunto.id_category {
                        isInside = true
                        self.listAppunti[i].append(appunto)
                    }
                }
                if !isInside {
                    self.listAppunti.append([appunto])
                }
            }
            self.listAppunti.append([AppuntiRecordResponse(id: "-1", id_category: "-1", id_project: "", title: "", descrizione: "", nome_category: "Inserisci Nuova")])
            print(self.listAppunti)
            self.collectionView.reloadData()

            
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


extension AppuntiViewController : UICollectionViewDelegate, UICollectionViewDataSource {
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return listAppunti.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "appunti-col-cell", for: indexPath) as! AppuntiCollectionViewCell
        cell.shadowView.clipsToBounds = true
        cell.shadowView.layer.cornerRadius = 10
        cell.shadowView.backgroundColor = ProjectSingleton.shared.mainColor
        cell.titleLabel.text = listAppunti[indexPath.row].first?.nome_category
        cell.titleLabel.textColor = .white
        cell.tableView.tag = indexPath.row
        setupTableView(tableView: cell.tableView)
        cell.tableView.reloadData()
        return cell
    }
    
    func setupCollectionView(){
        self.collectionView.register(UINib(nibName: "AppuntiCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "appunti-col-cell")
        self.collectionView.isPagingEnabled = true
    }
    
    

}

extension AppuntiViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: UIScreen.main.bounds.width - 60, height: UIScreen.main.bounds.height - 80)
    }
}



extension AppuntiViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    
    func setupTableView(tableView: UITableView) {
           tableView.register(UINib(nibName: "AppuntiTableViewCell", bundle: nil), forCellReuseIdentifier: "appunti-cell")
        tableView.register(UINib(nibName: "AggiungiTableViewCell", bundle: nil), forCellReuseIdentifier: "aggiungi-cell")
           tableView.separatorStyle = .none
        tableView.delegate = self
        tableView.dataSource = self
        tableView.backgroundColor = .none
        tableView.reloadData()
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        let index = tableView.tag
        if listAppunti[index].first?.id == "-1" {
            return 1
        }
        if listAppunti.count > index {
            return listAppunti[index].count + 1
        }
        return 1
    }
         
     func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let absoluteIndex = tableView.tag
        if indexPath.row >= listAppunti[absoluteIndex].count || listAppunti[absoluteIndex].first?.id == "-1" {
            let cell = tableView.dequeueReusableCell(withIdentifier: "aggiungi-cell", for: indexPath) as! AggiungiTableViewCell
            cell.selectionStyle = .none
            cell.aggiungiButton.layer.shadowColor = UIColor.white.cgColor
            cell.aggiungiButton.layer.shadowOffset = CGSize(width: 0, height: 3)
            cell.aggiungiButton.layer.shadowOpacity = 0.5
            cell.aggiungiButton.layer.shadowRadius = 3
            cell.aggiungiButton.layer.masksToBounds = false
            cell.aggiungiButton.layer.cornerRadius = 10
            cell.aggiungiButton.tag = absoluteIndex
            cell.aggiungiButton.backgroundColor = .systemBlue
            cell.aggiungiButton.tintColor = ProjectSingleton.shared.mainColor
            cell.backgroundColor = .none
            cell.aggiungiButton.setTitleColor(.white, for: .normal)
            cell.aggiungiButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleAggiungi))) // Each item should be able to trigger and action on tap
            return cell
        }
          let cell = tableView.dequeueReusableCell(withIdentifier: "appunti-cell", for: indexPath) as! AppuntiTableViewCell
          cell.selectionStyle = .none
        cell.backgroundColor = .none
        cell.mainLabel.text = listAppunti[absoluteIndex][indexPath.row].title
        cell.mainLabel.backgroundColor = .white
        cell.shadowView.changeColor(with: Utils.lightGray)
        cell.shadowView.changeCornerRadius(with: CGFloat(10))
        cell.shadowView.layer.cornerRadius = 10
        cell.shadowView.clipsToBounds = true
        return cell
     }
         
     func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let absoluteIndex = tableView.tag
        if listAppunti[absoluteIndex].first?.id == "-1" {
            return 60
        }
        if indexPath.row >= listAppunti[absoluteIndex].count {
            return 60
        }
        guard let text = listAppunti[absoluteIndex][indexPath.row].title else {
            return 0
        }
        return text.height(withWidth: tableView.bounds.width, font: UIFont.systemFont(ofSize: 17)) + 25
        
        
     }
    
    @objc func handleAggiungi(_ sender: UIGestureRecognizer) {
        let index = sender.view!.tag
        currIndex = index
        if listAppunti[index].first?.id == "-1" {
            
            shadowViewMultiplaModal.fadeIn()
            return
        }
        
        shadowViewSingolaModal.fadeIn()
        
    }
    
    
    
}
