//
//  FrontEndViewController.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class FrontEndViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    let listPatternMVC = ["Model", "View", "Controller"]
    
    var frontEndResponse: FrontEndResponse?
    
    var currTypeToPass = -1
    
    @IBOutlet weak var tableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setupTableView()
        fetchData()
    }
    


}

extension FrontEndViewController {
    
    
    func fetchData() {
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
        
        NetworkManager.shared.listFrontEnd(idProgetto: (ProjectSingleton.shared.progetto?.id!)!, completition: {
              (response) in
              
              // CORRECT RESPONSE
             Spinner.shared.removeSpinner(spinner: spinner)
             print(response)
             guard let response = response else {
                 Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                 return
                }
            
            self.frontEndResponse = response
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

    
}

extension FrontEndViewController {
    
    
    private func setupTableView() {
           self.tableView.register(UINib(nibName: "FrontEndTableViewCell", bundle: nil), forCellReuseIdentifier: "front-end-cell")
           self.tableView.separatorStyle = .none
           
       }
       

       
       func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
           return listPatternMVC.count
       }
       
       func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            let correctIndex = indexPath.row
            let cell = tableView.dequeueReusableCell(withIdentifier: "front-end-cell", for: indexPath) as! FrontEndTableViewCell
            cell.selectionStyle = .none
            cell.titleMVC.text = listPatternMVC[correctIndex]
            cell.titleMVC.textColor = ProjectSingleton.shared.mainColor
            setupCollectionView(collectionView: cell.collectionView)
            cell.collectionView.dataSource = self
            cell.collectionView.delegate = self
            cell.collectionView.tag = indexPath.row
        cell.plusButton.tag = indexPath.row
            cell.plusButton.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleTapPlus)))
                cell.collectionView.reloadData()
            return cell
       }
       
       func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.row {
        case 0:
            if  frontEndResponse?.model == nil || frontEndResponse?.model?.count == 0  {
                return 70
            }
            break
        case 1:
            if  frontEndResponse?.view == nil || frontEndResponse?.view?.count == 0 {
                return 70
            }
            break
        default:
            if  frontEndResponse?.controller == nil || frontEndResponse?.controller?.count == 0 {
                return 70
            }
            break
        }
           return 350
       }
    
    @objc func handleTapPlus(_ sender: UIGestureRecognizer) {
        currTypeToPass = sender.view!.tag
        
        performSegue(withIdentifier: "goToCreaFrontEnd", sender: self)
        
    }
       
      
       
    
}


extension FrontEndViewController: UICollectionViewDelegate, UICollectionViewDataSource{
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        guard let frontEndCurr = frontEndResponse else {
            return 0
        }
        switch collectionView.tag {
        case 0:
            guard let model = frontEndCurr.model else {
                return 0
            }
            return model.count
        case 1:
            guard let view = frontEndCurr.view else {
                return 0
            }
            return view.count
        case 2:
            guard let controller = frontEndCurr.controller else {
                return 0
            }
            return controller.count
        default:
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "front-end-col-cell", for: indexPath) as! FrontEndCollectionViewCell
        cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
        cell.status.clipsToBounds = true
        cell.status.layer.cornerRadius = 25
        cell.lineLabel.backgroundColor = ProjectSingleton.shared.mainColor

        let underlineAttribute = [NSAttributedString.Key.underlineStyle: NSUnderlineStyle.thick.rawValue]
        // ALTRO
        let underlineAttributedString = NSAttributedString(string: "", attributes: underlineAttribute)
        cell.otherLabel.attributedText = underlineAttributedString
        cell.removeImage.isUserInteractionEnabled = true
        cell.status.isUserInteractionEnabled = true
        switch collectionView.tag {
        case 0:
            cell.titleLabel.text = frontEndResponse!.model?[indexPath.row].nome
            cell.titleLabel.textColor = ProjectSingleton.shared.mainColor
            if frontEndResponse!.model?[indexPath.row].status == "1" {
                cell.status.text = "TO DO"
                cell.status.textColor = .white
                cell.status.backgroundColor = .red
            }
            if frontEndResponse!.model?[indexPath.row].status == "2" {
                cell.status.text = "DOING"
                cell.status.textColor = .white
                cell.status.backgroundColor = .yellow
            }
            if frontEndResponse!.model?[indexPath.row].status == "3" {
                cell.status.text = "DONE"
                cell.status.textColor = .white
                cell.status.backgroundColor = .green
            }
            cell.status.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleUpdateStatusModel(_:))))
            var attributes = ""
            var counter = 0
            cell.removeImage.tag = indexPath.row
            cell.removeImage.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleRemoveModel))) // Each item should be able to trigger and action on tap
            for attribute in frontEndResponse!.model![indexPath.row].attributes! {
                var stringToAppend = ""
                if attribute.priv == "1" {
                    stringToAppend = stringToAppend + "- "
                } else {
                    stringToAppend = stringToAppend + "+ "
                }
                stringToAppend = stringToAppend + (attribute.value ?? "") + ": " + (attribute.type ?? "")
                stringToAppend = stringToAppend + "\n"
                attributes = attributes + stringToAppend
                counter = counter + 1
            }
            if counter < 5 {
                cell.otherLabel.isHidden = true
            }
            cell.attributesLabel.text = attributes
            break
        case 1:
            cell.titleLabel.text = frontEndResponse!.view?[indexPath.row].nome
            cell.titleLabel.textColor = ProjectSingleton.shared.mainColor
            if frontEndResponse!.view?[indexPath.row].status == "1" {
                cell.status.text = "TO DO"
                cell.status.textColor = .white
                cell.status.backgroundColor = .red
            }
            if frontEndResponse!.view?[indexPath.row].status == "2" {
                cell.status.text = "DOING"
                cell.status.textColor = .white
                cell.status.backgroundColor = .yellow
            }
            if frontEndResponse!.view?[indexPath.row].status == "3" {
                cell.status.text = "DONE"
                cell.status.textColor = .white
                cell.status.backgroundColor = .green
            }
            cell.status.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleUpdateStatusView(_:))))
            cell.removeImage.tag = indexPath.row
            cell.removeImage.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleRemoveView))) // Each item should be able to trigger and action on tap
            var attributes = ""
            var counter = 0
            for attribute in frontEndResponse!.view![indexPath.row].attributes! {
                var stringToAppend = ""
                if attribute.priv == "1" {
                    stringToAppend = stringToAppend + "- "
                } else {
                    stringToAppend = stringToAppend + "+ "
                }
                stringToAppend = stringToAppend + (attribute.value ?? "") + ": " + (attribute.type ?? "")
                stringToAppend = stringToAppend + "\n"
                attributes = attributes + stringToAppend
                counter = counter + 1
            }
            if counter < 5 {
                cell.otherLabel.isHidden = true
            }
            cell.attributesLabel.text = attributes
            break
        default:
            cell.titleLabel.text = frontEndResponse!.controller?[indexPath.row].nome
            cell.titleLabel.textColor = ProjectSingleton.shared.mainColor
            if frontEndResponse!.controller?[indexPath.row].status == "1" {
                cell.status.text = "TO DO"
                cell.status.textColor = .white
                cell.status.backgroundColor = .red
            }
            if frontEndResponse!.controller?[indexPath.row].status == "2" {
                cell.status.text = "DOING"
                cell.status.textColor = .white
                cell.status.backgroundColor = .yellow
            }
            if frontEndResponse!.controller?[indexPath.row].status == "3" {
                cell.status.text = "DONE"
                cell.status.textColor = .white
                cell.status.backgroundColor = .green
            }
            cell.status.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleUpdateStatusController(_:))))
            var attributes = ""
            var counter = 0
            cell.removeImage.tag = indexPath.row
            cell.removeImage.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleRemoveController))) // Each item should be able to trigger and action on tap
            for attribute in frontEndResponse!.controller![indexPath.row].attributes! {
                var stringToAppend = ""
                if attribute.priv == "1" {
                    stringToAppend = stringToAppend + "- "
                } else {
                    stringToAppend = stringToAppend + "+ "
                }
                stringToAppend = stringToAppend + (attribute.value ?? "") + ": " + (attribute.type ?? "")
                stringToAppend = stringToAppend + "\n"
                attributes = attributes + stringToAppend
                counter = counter + 1
            }
            if counter < 5 {
                cell.otherLabel.isHidden = true
            }
            cell.attributesLabel.text = attributes
            break
        }
        return cell
    }
    
    func setupCollectionView(collectionView: UICollectionView){
        collectionView.register(UINib(nibName: "FrontEndCollectionViewCell", bundle: nil), forCellWithReuseIdentifier: "front-end-col-cell")
    }
    
    @objc func handleRemoveModel (_ sender: UIGestureRecognizer) {
        let index = sender.view!.tag
        eliminaFrontEnd(id: (frontEndResponse?.model?[index].id ?? ""), completition: { result in
            self.frontEndResponse?.model?.remove(at: index)
            self.tableView.reloadData()
        })
    }
    
    @objc func handleRemoveView (_ sender: UIGestureRecognizer) {
           let index = sender.view!.tag
           eliminaFrontEnd(id: (frontEndResponse?.view?[index].id ?? ""), completition: { result in
               self.frontEndResponse?.view?.remove(at: index)
                self.tableView.reloadData()
           })
       }
    
    
    @objc func handleRemoveController (_ sender: UIGestureRecognizer) {
           let index = sender.view!.tag
           eliminaFrontEnd(id: (frontEndResponse?.controller?[index].id ?? ""), completition: { result in
               self.frontEndResponse?.controller?.remove(at: index)
            self.tableView.reloadData()
           })
    }
    
    @objc func handleUpdateStatusModel (_ sender: UIGestureRecognizer) {
        let index = sender.view!.tag
        var status = frontEndResponse!.model![index].status!
        switch status {
        case "1":
            status = "2"
        case "2":
            status = "3"
        case "3":
            status = "1"
        default:
            status = "1"
        }
        aggiornaStatus(id: (frontEndResponse?.model?[index].id ?? ""),status: status, completition: { result in
            self.frontEndResponse!.model![index].status = status
            self.tableView.reloadData()
        })
    }
    
    @objc func handleUpdateStatusView (_ sender: UIGestureRecognizer) {
           let index = sender.view!.tag
        var status = frontEndResponse!.view![index].status!
        switch status {
        case "1":
            status = "2"
        case "2":
            status = "3"
        case "3":
            status = "1"
        default:
            status = "1"
        }
        aggiornaStatus(id: (frontEndResponse?.view?[index].id ?? ""),status: status, completition: { result in
               self.frontEndResponse!.view![index].status = status
                self.tableView.reloadData()
           })
       }
    
    
    @objc func handleUpdateStatusController (_ sender: UIGestureRecognizer) {
           let index = sender.view!.tag
            var status = frontEndResponse!.controller![index].status!
            switch status {
            case "1":
                status = "2"
            case "2":
                status = "3"
            case "3":
                status = "1"
            default:
                status = "1"
            }
        aggiornaStatus(id: (frontEndResponse?.controller?[index].id ?? ""), status: status, completition: { result in
               self.frontEndResponse!.controller![index].status = status
            self.tableView.reloadData()
           })
    }
    
    
    func aggiornaStatus(id: String, status: String, completition: @escaping (_ response: String) ->()) {
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)

        
        NetworkManager.shared.aggiornaStatusFrontEnd(id: id,status: status, completition: {
                     (response) in
                     
                     // CORRECT RESPONSE
                    Spinner.shared.removeSpinner(spinner: spinner)
                   
                   
                   guard response != nil else {
                        Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                        return
                       }
            
                    completition("Tutt ok")
            
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
    func eliminaFrontEnd(id: String, completition: @escaping (_ response: String) ->()) {
        let spinner = Spinner.shared.showSpinner(onView: self.view)
                   
        NetworkManager.shared.eliminaFrontEnd(id: id, completition: {
                     (response) in
                     
                     // CORRECT RESPONSE
                    Spinner.shared.removeSpinner(spinner: spinner)
                   
                   
                   guard response != nil else {
                        Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                        return
                       }
            
                    completition("Tutt ok")
                   
                    
                   
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

extension FrontEndViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: 250, height: 280)
    }
}

extension FrontEndViewController {
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "goToCreaFrontEnd" {
            if let destinationVC = segue.destination as? CreaFrontEndViewController {
                destinationVC.type = self.currTypeToPass
            }
        }
    }

    
}
