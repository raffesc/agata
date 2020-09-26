//
//  ProfiloViewController.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class ProfiloViewController: UIViewController {

    
    @IBOutlet weak var topView: UIView!
    
    
    @IBOutlet weak var profileView: UIImageView!
    
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        setupLayout()
        setupTableView()
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

extension ProfiloViewController {
    
    private func setupLayout() {
        
        self.topView.backgroundColor = Utils.defaultColor.color
        guard let user = UserSingleton.shared.user else {
            return
        }
        self.titleLabel.text = "Ciao, @" + (user.username ?? "")
        guard let imageUser = user.image else {
            return
        }
        NetworkManager.shared.downloadImage(nome: imageUser) { (result) in
            self.profileView.image = result
        }
        
        
    }
    
}



// MARK: Table View
extension ProfiloViewController: UITableViewDelegate, UITableViewDataSource {
    
    private func setupTableView() {
        self.tableView.register(UINib(nibName: "ProfiloTableViewCell", bundle: nil), forCellReuseIdentifier: "profilo-cell")
        self.tableView.separatorStyle = .none
        
    }
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.section {
        case 0:
            switch indexPath.row{
            case 0:
                break
            default:
                let viewController:UIViewController = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "LoginViewController") as UIViewController
                let defaults = UserDefaults.standard
                defaults.removeObject(forKey: "email")
                defaults.removeObject(forKey: "password")

                self.present(viewController, animated: false, completion: nil)
            }
            break
        default:
            switch indexPath.row {
            case 0:
                // aiuto
                guard let url = URL(string: "https://stackoverflow.com") else { return }
                UIApplication.shared.open(url)
                break
            case 1:
                // Privacy Policy
                guard let url = URL(string: "https://stackoverflow.com") else { return }
                UIApplication.shared.open(url)
                break
            default:
                // Termini e condizioni
                guard let url = URL(string: "https://stackoverflow.com") else { return }
                UIApplication.shared.open(url)
            }
            break
        }
    }
    


    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return 2
        default:
            return 3
        }
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        switch section {
        case 0:
            return "ACCOUNT"
        default:
            return "INFO"
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let correctIndex = indexPath.row
        switch indexPath.section {
        case 0:
            let cell = tableView.dequeueReusableCell(withIdentifier: "profilo-cell", for: indexPath) as! ProfiloTableViewCell
            switch correctIndex {
            case 0:
                cell.immaginePrincipale.image = UIImage(named: "color-wheel")!
                cell.testoPrincipale.text = "Cambia il colore primario"
            default:
                cell.immaginePrincipale.image = UIImage(named: "logout")!
                cell.testoPrincipale.text = "Effettua il logout"
            }
            return cell
        default:
            let cell = tableView.dequeueReusableCell(withIdentifier: "profilo-cell", for: indexPath) as! ProfiloTableViewCell
            switch correctIndex {
            case 0:
                cell.immaginePrincipale.image = UIImage(named: "about")!
                cell.testoPrincipale.text = "Aiuto"
            case 1:
                cell.immaginePrincipale.image = UIImage(named: "password")!
                cell.testoPrincipale.text = "Privacy"
            default:
                cell.immaginePrincipale.image = UIImage(named: "terms-and-conditions")!
                cell.testoPrincipale.text = "Termini e Condizioni"
            }
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 70
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
