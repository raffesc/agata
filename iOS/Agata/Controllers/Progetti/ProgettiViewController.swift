//
//  ProgettiViewController.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class ProgettiViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    var listProgetti: [ProgettiRecordResponse]?

    @IBOutlet weak var tableView: UITableView!
    

    override func viewDidAppear(_ animated: Bool) {
        


    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        // Do any additional setup after loading the view.
        fetchData()
         setupTableView()
        // navigationController?.navigationBar.prefersLargeTitles = true
         self.title = "Progetti"
         let textAttributes = [NSAttributedString.Key.foregroundColor:Utils.defaultColor.color]

         navigationController?.navigationBar.largeTitleTextAttributes = textAttributes
         navigationController?.navigationBar.titleTextAttributes = textAttributes
        let insets = UIEdgeInsets(top: 0, left: 0, bottom: 100, right: 0)
        self.tableView.contentInset = insets
        self.tableView.isHidden = true
    }

    override func viewDidLoad() {
        super.viewDidLoad()

       
    }
    
    
    
    
    

    @IBAction func addProject(_ sender: Any) {
        
        performSegue(withIdentifier: "goToCreaProj", sender: self)
        
    }
    


}

extension ProgettiViewController {
    
    func fetchData() {
        
        let spinner = Spinner.shared.showSpinner(onView: self.view)
        
        NetworkManager.shared.listProgetti(idUser: (UserSingleton.shared.user?.id!)!, completition: {
              (response) in
              
              // CORRECT RESPONSE
             Spinner.shared.removeSpinner(spinner: spinner)
             guard let response = response else {
                 Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                 return
                }
            
            self.listProgetti = response.records
            self.tableView.reloadData()
            self.tableView.isHidden = false

                  
          }, completitionError: { (error) in
              
            Spinner.shared.removeSpinner(spinner: spinner)
              // HANDLE ERROR
              guard error != nil else {
                  Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                  return
              }
            Alert.shared.showAlert(in: self.topmostController()!, title: "Errore", message: error?.error! ?? "")
            self.tableView.isHidden = false

              

          })
             
        
        NetworkManager.shared.listUsers(completition: { (users) in
            ProjectSingleton.shared.listUsers = users?.records
            self.tableView.reloadData()
        }) { (error) in

        }
        

    }
    
    func topmostController() -> UIViewController? {
        if var topController = UIApplication.shared.windows.first!.rootViewController {
            while let presentedViewController = topController.presentedViewController {
                topController = presentedViewController
            }
            return topController
        }
        return nil
    }

}

// MARK: Table View
extension ProgettiViewController {
    
    private func setupTableView() {
        self.tableView.register(UINib(nibName: "ProgettoTableViewCell", bundle: nil), forCellReuseIdentifier: "progetto-cell")
        self.tableView.separatorStyle = .none
        
    }
    
    @objc func refreshData(_ sender: Any) {
        fetchData()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listProgetti?.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let correctIndex = indexPath.row
        guard let progettoCurr = listProgetti?[correctIndex] else {
            let cell = UITableViewCell()
            cell.isHidden = true
            cell.isUserInteractionEnabled = false
            return cell
        }
        let cell = tableView.dequeueReusableCell(withIdentifier: "progetto-cell", for: indexPath) as! ProgettoTableViewCell
        cell.selectionStyle = .none
        cell.title.text = progettoCurr.title
        guard let icon = progettoCurr.icon else {
            return UITableViewCell()
        }
        cell.icon.image = Utils.getIcon(for: Int(icon)!)
        print(progettoCurr.id_users!.getArray())
        guard let users = progettoCurr.id_users, let mainColor = progettoCurr.main_color else {
            let cell = UITableViewCell()
            cell.isHidden = true
            cell.isUserInteractionEnabled = false
            return cell
        }
        var arrayUsers = users.getArray()
        let currColor = Utils.getColor(for: Int(mainColor)!)
        cell.firstName.backgroundColor = currColor
        cell.secondName.backgroundColor = currColor
        cell.thirdName.backgroundColor = currColor
        cell.fourthName.backgroundColor = currColor
        cell.fifthName.backgroundColor = currColor
        cell.backGround.backgroundColor = currColor
        cell.shadowView.changeColor(with: currColor)
        cell.firstName.isHidden = true
        cell.secondName.isHidden = true
        cell.thirdName.isHidden = true
        cell.fourthName.isHidden = true
        cell.fifthName.isHidden = true
        guard let listUsers = ProjectSingleton.shared.listUsers else {
            return cell
        }
        for i in 0..<arrayUsers.count {
            let userString = getUser(byId: arrayUsers[i],listUser: listUsers)
            arrayUsers[i] = userString
        }
        if arrayUsers.count >= 5 {
            cell.firstName.text = arrayUsers[0]
            cell.firstName.isHidden = false
            cell.secondName.text = arrayUsers[1]
            cell.secondName.isHidden = false
            cell.thirdName.text = arrayUsers[2]
            cell.thirdName.isHidden = false
            cell.fourthName.text = arrayUsers[3]
            cell.fourthName.isHidden = false
            let val = arrayUsers.count - 4
            cell.fifthName.text = String(val) + "+"
            cell.fifthName.isHidden = false
        }
        if arrayUsers.count == 4 {
            cell.firstName.text = arrayUsers[0]
            cell.firstName.isHidden = false
            cell.secondName.text = arrayUsers[1]
            cell.secondName.isHidden = false
            cell.thirdName.text = arrayUsers[2]
            cell.thirdName.isHidden = false
            cell.fourthName.text = arrayUsers[3]
            cell.fourthName.isHidden = false
            cell.fifthName.isHidden = true
        }
        if arrayUsers.count == 3 {
            cell.firstName.text = arrayUsers[0]
            cell.firstName.isHidden = false
            cell.secondName.text = arrayUsers[1]
            cell.secondName.isHidden = false
            cell.thirdName.text = arrayUsers[2]
            cell.thirdName.isHidden = false
            cell.fourthName.isHidden = true
            cell.fifthName.isHidden = true
        }
        if arrayUsers.count == 2 {
            cell.firstName.text = arrayUsers[0]
            cell.firstName.isHidden = false
            cell.secondName.text = arrayUsers[1]
            cell.secondName.isHidden = false
            cell.thirdName.isHidden = true
            cell.fourthName.isHidden = true
            cell.fifthName.isHidden = true
        }
        if arrayUsers.count == 1 {
            cell.firstName.text = arrayUsers[0]
            cell.firstName.isHidden = false
            cell.secondName.isHidden = true
            cell.thirdName.isHidden = true
            cell.fourthName.isHidden = true
            cell.fifthName.isHidden = true
        }
        if arrayUsers.count == 0 {
            cell.firstName.isHidden = true
            cell.secondName.isHidden = true
            cell.thirdName.isHidden = true
            cell.fourthName.isHidden = true
            cell.fifthName.isHidden = true
        }
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 200
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let mainColor = listProgetti![indexPath.row].main_color
        ProjectSingleton.shared.mainColor = Utils.getColor(for: Int(mainColor!)!)
        ProjectSingleton.shared.progetto = listProgetti![indexPath.row]
        self.performSegue(withIdentifier: "goToDettaglio", sender: self)
    }
    
    func getUser(byId id: String,listUser: [UserRecordListResponse]) -> String{
        
        for user in listUser {
            if user.id == id {
                let str = String(user.username!.prefix(2))
                return str.uppercased()
            }
        }
        return ""
    }
    
    
}
