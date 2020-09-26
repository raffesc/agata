//
//  UtentiViewController.swift
//  Agata
//
//  Created by Raffaele on 03/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class UtentiViewController: UIViewController {
    @IBOutlet weak var searchBar: UISearchBar!
    
    @IBOutlet weak var segmentControl: UISegmentedControl!
    
    @IBOutlet weak var tableView: UITableView!
    
    @IBOutlet weak var topView: UIView!
    
    
    var currentUserInProject: [String] = []
    
    var currentListUsers: [UserRecordListResponse] = []
    override func viewDidLoad() {
        super.viewDidLoad()

        if ProjectSingleton.shared.progetto != nil {
            if ProjectSingleton.shared.progetto!.id_users != nil {
                currentUserInProject = ProjectSingleton.shared.progetto!.id_users!.getArray()
            }
        }
        setupTableView()
        searchBar.delegate = self
        searchBar.autocapitalizationType = .none
        let titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        segmentControl.setTitleTextAttributes(titleTextAttributes, for: .selected)
        setupLayout()
        changeCurrentList()
        self.tableView.reloadData()
        // Do any additional setup after loading the view.
        //Looks for single or multiple taps.
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.dismissKeyboard))
        view.addGestureRecognizer(tap)
    }
    
    
    
    @IBAction func segmentValChanged(_ sender: Any) {
        changeCurrentList()
        self.tableView.reloadData()
    }
    
    
    func changeCurrentList() {
        currentListUsers = []
        
        switch segmentControl.selectedSegmentIndex {
        case 0:
            guard let searchBarText = searchBar.text else {
                for user in currentUserInProject {
                    currentListUsers.append(UserRecordListResponse(id: user, email: nil, username: user.getUser()))
                }
                return
            }
            
            if searchBarText.count == 0 {
                for user in currentUserInProject {
                    currentListUsers.append(UserRecordListResponse(id: user, email: nil, username: user.getUser()))
                }
                return
            }
            for user in currentUserInProject {
                if user.getUser().hasPrefix(searchBar.text ?? "") {
                    currentListUsers.append(UserRecordListResponse(id: user, email: nil, username: user.getUser()))
                }
            }
            break
        default:
            guard let allUsers = ProjectSingleton.shared.listUsers else {
                return
            }
            guard let searchBarText = searchBar.text else {
                for user in allUsers {
                    var isAlreadyPresent = false
                    for userInProject in currentUserInProject {
                        if user.id == userInProject {
                            isAlreadyPresent = true
                        }
                    }
                    if !isAlreadyPresent {
                        currentListUsers.append(user)
                    }
                }
                return
            }
            
            if searchBarText.count == 0 {
                for user in allUsers {
                    var isAlreadyPresent = false
                    for userInProject in currentUserInProject {
                        if user.id == userInProject {
                            isAlreadyPresent = true
                        }
                    }
                    if !isAlreadyPresent {
                        currentListUsers.append(user)
                    }
                }
                return
            }
            for user in allUsers {
                if user.username != nil {
                    var isAlreadyPresent = false
                    for userInProject in currentUserInProject {
                        if user.id == userInProject {
                            isAlreadyPresent = true
                        }
                    }
                    if !isAlreadyPresent {
                        if user.username!.hasPrefix(searchBar.text ?? "") {
                            currentListUsers.append(user)
                        }
                    }
                }
            }
            break
        }
        
        
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


extension UtentiViewController {
    
    func setupLayout() {
        
        topView.backgroundColor = ProjectSingleton.shared.mainColor
        searchBar.backgroundColor = .none
        searchBar.searchBarStyle = .minimal
        searchBar.searchTextField.backgroundColor = .white
        
        segmentControl.selectedSegmentTintColor = ProjectSingleton.shared.mainColor
        segmentControl.backgroundColor = .white
        
    }
    
}

extension UtentiViewController: UITableViewDelegate, UITableViewDataSource {
    
    
    
    
    
    func setupTableView(){
        self.tableView.register(UINib(nibName: "UtentiTableViewCell", bundle: nil), forCellReuseIdentifier: "utenti-cell")
        tableView.delegate = self
        tableView.dataSource = self
        tableView.separatorStyle = .none
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       
        return currentListUsers.count
        
       
    }
         
     func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "utenti-cell", for: indexPath) as! UtentiTableViewCell
        cell.usernameLabel.text = currentListUsers[indexPath.row].username
        cell.shadowView.changeColor(with: ProjectSingleton.shared.mainColor!)
        cell.selectionStyle = .none
        return cell
    
    }
         
     func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
       
        return 60
        
     }
    
    func tableView(_ tableView: UITableView, titleForDeleteConfirmationButtonForRowAt indexPath: IndexPath) -> String? {
        switch segmentControl.selectedSegmentIndex {
        case 0:
            return "Elimina"
        default:
            return "Aggiungi"
        }
    }

    
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {

        switch segmentControl.selectedSegmentIndex {
        case 1:
            let editAction = UITableViewRowAction(style: .default, title: "Aggiungi", handler: { (action, indexPath) in
                print("Aggiungi tapped")
                
                if UserSingleton.shared.user?.id == ProjectSingleton.shared.progetto?.id_owner {
                    
                    
                      let spinner = Spinner.shared.showSpinner(onView: self.view)
                                      
                    NetworkManager.shared.addUserInProject(idOwner: ProjectSingleton.shared.progetto!.id_owner!,
                                                           id: ProjectSingleton.shared.progetto!.id! ,
                                                           id_new_user: self.currentListUsers[indexPath.row].id!,
                                                           completition: {
                        (response) in
                        
                        // CORRECT RESPONSE
                        Spinner.shared.removeSpinner(spinner: spinner)
                        
                        
                        guard let response = response else {
                            Alert.shared.showAlert(in: self, title: "Errore", message: "Errore generico.")
                            return
                        }
                                                            
                    
                        Alert.shared.showAlert(in: self, title: "Successo", message: response.response!)

                        
                        self.currentUserInProject.append(self.currentListUsers[indexPath.row].id!)
                        
                        
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
                                     
                         
                    
                    
                    
                } else {
                    
                    Alert.shared.showAlert(in: self, title: "Errore", message: "Non puoi inserire un nuovo utente perchè non sei il creatore del progetto.")

                }
                
            })
            editAction.backgroundColor = .green


            return [editAction]

        default:
            return []
        }
        // action one
    }

    
   
   
    
    

}



extension UtentiViewController: UISearchBarDelegate, UISearchDisplayDelegate {
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
        changeCurrentList()
        self.tableView.reloadData()
    }
    
}

extension UtentiViewController {
    
    //Calls this function when the tap is recognized.
      @objc func dismissKeyboard() {
          //Causes the view (or one of its embedded text fields) to resign the first responder status.
          view.endEditing(true)
      }
    
}
