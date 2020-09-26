//
//  Alert.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

class Alert {
    
    static let shared = Alert()
    
    
    public func showAlert(in viewController: UIViewController, title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertController.Style.alert)

        let alertOKAction = UIAlertAction(title:"OK", style: UIAlertAction.Style.default,handler: { action in
                   })
        alert.addAction(alertOKAction)
        viewController.present(alert, animated: true, completion: nil)

    }
    
    
    
    public func showAlert(in viewController: UIViewController, title: String, message: String, completition: @escaping () -> ()) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertController.Style.alert)

        let alertOKAction = UIAlertAction(title:"OK", style: UIAlertAction.Style.default,handler: { action in
            completition()
                   
        })
        alert.addAction(alertOKAction)
        viewController.present(alert, animated: true, completion: nil)

    }
    
}
