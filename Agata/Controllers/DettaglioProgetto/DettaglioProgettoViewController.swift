//
//  DettaglioProgettoViewController.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class DettaglioProgettoViewController: UIViewController,UITableViewDelegate, UITableViewDataSource {
    
    var listSections = ["Front-End", "Back-End", "Design","Bugs","Appunti","Lista Utenti"]

    
    @IBOutlet weak var tableView: UITableView!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupTableView()
        // Do any additional setup after loading the view.
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
extension DettaglioProgettoViewController {
    
    private func setupTableView() {
        self.tableView.register(UINib(nibName: "DettaglioProgettoTableViewCell", bundle: nil), forCellReuseIdentifier: "dettaglio-cell")
        self.tableView.separatorStyle = .none
        
    }
    

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return listSections.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let correctIndex = indexPath.row
        let cell = tableView.dequeueReusableCell(withIdentifier: "dettaglio-cell", for: indexPath) as! DettaglioProgettoTableViewCell
        cell.selectionStyle = .none
        cell.titleLabel.text = listSections[correctIndex]
        cell.titleLabel.textColor = ProjectSingleton.shared.mainColor
        cell.backGround.backgroundColor = Utils.defaultColor.color
        cell.backGround.clipsToBounds = true
        cell.backGround.layer.cornerRadius = 10
        cell.rightLabel.text = ">"
        cell.rightLabel.backgroundColor = ProjectSingleton.shared.mainColor
        cell.rightLabel.textColor = .white
        cell.shadowBackground.changeColor(with: ProjectSingleton.shared.mainColor!)
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch listSections[indexPath.row] {
        case "Front-End":
            self.performSegue(withIdentifier: "goToFrontEnd", sender: self)
            break
        case "Back-End":
            self.performSegue(withIdentifier: "goToBackEnd", sender: self)
            break
        case "Design":
            self.performSegue(withIdentifier: "goToDesign", sender: self)
            break
        case "Bugs":
            self.performSegue(withIdentifier: "goToBugs", sender: self)
            break
        case "Appunti":
            self.performSegue(withIdentifier: "goToAppunti", sender: self)
            break
        case "Lista Utenti":
            self.performSegue(withIdentifier: "goToUtenti", sender: self)
            break
        default:
            break
        }
    }
    
}
