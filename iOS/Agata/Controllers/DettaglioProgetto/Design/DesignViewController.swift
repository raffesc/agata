//
//  DesignViewController.swift
//  Agata
//
//  Created by Raffaele on 07/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class DesignViewController: UIViewController {
    
    
    @IBOutlet weak var tableView: UITableView!
    
    
    @IBOutlet weak var shadowViewModal: ShadowView!
    
    @IBOutlet weak var nomeCategoriaModal: UITextField!
    
    
    @IBOutlet weak var aggiungiButtonModal: UIButton!
    @IBOutlet weak var annullaButtonModal: UIButton!
    
    var listDesigns: [[DesignRecordResponse]] = []
    var listImages: [[UIImage]] = []
    var imagePicker = UIImagePickerController()

    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupLayout()
        fetchData()
        setupTableViews()
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    
    @IBAction func aggiungiButtonHandlerModal(_ sender: Any) {
        
        guard let nomeCategoria = nomeCategoriaModal.text else {
            return
        }

        if nomeCategoria.count == 0 {
            return
        }
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
                           
        NetworkManager.shared.addCategoryDesign(idProject: (ProjectSingleton.shared.progetto?.id!)!,titleCategory: nomeCategoria, completition: {
             (response) in
             
             // CORRECT RESPONSE
            Spinner.shared.removeSpinner(spinner: spinner)
            
            guard let response = response else {
                Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                return
            }
           
            self.uploadImage(in: response.id!)
           
            self.shadowViewModal.fadeOut()
           
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
    
    

    @IBAction func annullaButtonModalHandler(_ sender: Any) {
        
        shadowViewModal.fadeOut()
    }
    

}

extension DesignViewController {
    

    
    func fetchData() {
     
        
       let spinner = Spinner.shared.showSpinner(onView: self.view)
                    
                NetworkManager.shared.listDesign(idProgetto: (ProjectSingleton.shared.progetto?.id!)!, completition: {
                      (response) in
                      
                      // CORRECT RESPONSE
                     Spinner.shared.removeSpinner(spinner: spinner)
                    
                    
                     guard let response = response else {
                      //   Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                         return
                        }
                    
                    guard let records = response.records else {
                        return
                    }
                    
                    for design in records {
                        var isInside = false
                        for i in 0..<self.listDesigns.count {
                            if self.listDesigns[i].first?.id_category == design.id_category {
                                isInside = true
                                self.listDesigns[i].append(design)
                                self.listImages[i].append(UIImage())
                            }
                        }
                        if !isInside {
                            self.listDesigns.append([design])
                            self.listImages.append([UIImage()])
                        }
                    }
                    self.tableView.reloadData()

                    
                  }, completitionError: { (error) in
                      
                    Spinner.shared.removeSpinner(spinner: spinner)
                      // HANDLE ERROR
                      guard error != nil else {
                    //      Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                          return
                      }
                 //   Alert.shared.showAlert(in: self, title: "Errore", message: error?.error! ?? "")


                  })
                   
        
    }
    
    
    func setupLayout(){
        
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


extension DesignViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    func setupTableViews() {
           self.tableView.register(UINib(nibName: "DesignTableViewCell", bundle: nil), forCellReuseIdentifier: "design-cell")
        self.tableView.register(UINib(nibName: "AggiungiTableViewCell", bundle: nil), forCellReuseIdentifier: "aggiungi-cell")

           self.tableView.separatorStyle = .none
       }
       
       
       func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
           
            return listDesigns.count + 1
            
           
        }
             
         func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            if indexPath.row == listDesigns.count {
                let cell = tableView.dequeueReusableCell(withIdentifier: "aggiungi-cell", for: indexPath) as! AggiungiTableViewCell
                cell.aggiungiButton.layer.shadowColor = ProjectSingleton.shared.mainColor?.cgColor
                cell.aggiungiButton.layer.backgroundColor = ProjectSingleton.shared.mainColor?.cgColor
                cell.aggiungiButton.layer.shadowOffset = CGSize(width: 0, height: 3)
                cell.aggiungiButton.layer.shadowOpacity = 0.5
                cell.aggiungiButton.layer.shadowRadius = 3
                cell.aggiungiButton.layer.masksToBounds = false
                cell.aggiungiButton.layer.cornerRadius = 10
                cell.aggiungiButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleAggiungiCategory))) // Each item should be able to trigger and action on tap
                return cell
            }
           let correctIndex = indexPath.row
           let mainColor = ProjectSingleton.shared.mainColor
            let cell = tableView.dequeueReusableCell(withIdentifier: "design-cell", for: indexPath) as! DesignTableViewCell
            let design = listDesigns[correctIndex]
            cell.mainLabel.text = design.first?.nome_category
            cell.mainLabel.textColor = mainColor
            setupCollectionView(with: cell.collectionView,tag: correctIndex)
            cell.collectionView.reloadData()
            cell.selectionStyle = .none
            cell.plusButton.tag = indexPath.row
            cell.plusButton.isUserInteractionEnabled = true
            cell.plusButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleTapPlus))) // Each item should be able to trigger and action on tap
            return cell
        
        }
             
         func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
           
            if indexPath.row == listDesigns.count {
                return 60
            }
            return 300
            
         }
    
    
        @objc func handleTapPlus(_ sender: UIGestureRecognizer) {
            let index = sender.view!.tag
            
            guard let idCategory = listDesigns[index].first?.id_category else {
                return
            }
            
            self.uploadImage(in: idCategory)
            
            

        }

    
    @objc func handleAggiungiCategory(_ sender: UIGestureRecognizer) {
        
        shadowViewModal.fadeIn()
        
    }
    
    
    func uploadImage(in idCategory: String) {
        
        let imagePickerManager = ImagePickerManager()

        
        imagePickerManager.pickImage(self){ image in
            //here is the image
                print(image)
            
            
            let spinner = Spinner.shared.showSpinner(onView: self.view)
                                             
            NetworkManager.shared.uploadImageDesignInCategory(idCategory: idCategory, idProject: ProjectSingleton.shared.progetto!.id!, image: image, completition: {
                   (response) in
                   
                   // CORRECT RESPONSE
                   Spinner.shared.removeSpinner(spinner: spinner)
                   
                   
                   guard let response = response else {
                       Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                       return
                   }
                                                       
            
                    self.fetchData()
                   
                   
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
    
    
}


extension DesignViewController: UICollectionViewDelegate, UICollectionViewDataSource {
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        let tag = collectionView.tag
        return listDesigns[tag].count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let tag = collectionView.tag
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "design-col-cell", for: indexPath) as! DesignCollectionViewCell
        cell.removeImage.tag = indexPath.row
        cell.removeImage.currIndex = tag
        cell.removeImage.isUserInteractionEnabled = true
        cell.removeImage.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleRemoveDesign))) // Each item should be able to trigger and action on tap

        NetworkManager.shared.downloadImage(nome: listDesigns[tag][indexPath.row].nome ?? "") { (result) in
            guard let result = result else {
                return
            }
            cell.mainImage.image = result
            let tap = UITapGestureRecognizer(target: self, action: #selector(self.imageTapped))
            cell.mainImage.isUserInteractionEnabled = true
            cell.mainImage.addGestureRecognizer(tap)

           // collectionView.reloadData()
        }
        return cell

    }
    
    @objc func handleRemoveDesign(_ sender: UIGestureRecognizer) {
        
        let image = sender.view! as! ImageViewCustom
        let index = sender.view!.tag
        let spinner = Spinner.shared.showSpinner(onView: self.view)

        NetworkManager.shared.eliminaDesign(id: listDesigns[image.currIndex][index].id!, completition: {
                     (response) in
                     
                     // CORRECT RESPONSE
                    Spinner.shared.removeSpinner(spinner: spinner)
                   
                   
                   guard response != nil else {
                        Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                        return
                       }
            

            self.listDesigns[image.currIndex].remove(at: index)
            self.tableView.reloadData()
                   
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
    
    func setupCollectionView(with collectionView: UICollectionView, tag: Int){
        collectionView.register(UINib(nibName: "DesignCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "design-col-cell")
        collectionView.tag = tag
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    
    @IBAction func imageTapped(_ sender: UITapGestureRecognizer) {
        let imageView = sender.view as! UIImageView
        let newImageView = UIImageView(image: imageView.image)
        newImageView.frame = UIScreen.main.bounds
        newImageView.backgroundColor = .black
        newImageView.contentMode = .scaleAspectFit
        newImageView.isUserInteractionEnabled = true
        let tap = UITapGestureRecognizer(target: self, action: #selector(dismissFullscreenImage))
        newImageView.addGestureRecognizer(tap)
        self.view.addSubview(newImageView)
        self.navigationController?.isNavigationBarHidden = true
        self.tabBarController?.tabBar.isHidden = true
    }

    @objc func dismissFullscreenImage(_ sender: UITapGestureRecognizer) {
        self.navigationController?.isNavigationBarHidden = false
        self.tabBarController?.tabBar.isHidden = false
        sender.view?.removeFromSuperview()
    }

   
    
    

}

extension DesignViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 120, height: 200)
    }
}



extension DesignViewController: UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    
    func imagePickerController(picker: UIImagePickerController!, didFinishPickingImage image: UIImage!, editingInfo: NSDictionary!){
        self.dismiss(animated: true, completion: { () -> Void in

        })

        print(image)

        
    }
    
}

extension DesignViewController {
    
    //Calls this function when the tap is recognized.
      @objc func dismissKeyboard() {
          //Causes the view (or one of its embedded text fields) to resign the first responder status.
          view.endEditing(true)
      }
    
}
