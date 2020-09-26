//
//  Spinner.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

class Spinner {
    
    
    static let shared = Spinner()

    public func showSpinner(onView : UIView) -> UIView {
        let spinnerView = UIView.init(frame: onView.bounds)
        spinnerView.backgroundColor = UIColor.init(red: 0.5, green: 0.5, blue: 0.5, alpha: 0.5)
        let ai = UIActivityIndicatorView.init(style: .large)
        ai.startAnimating()
        ai.center = spinnerView.center
        ai.color = UIColor.blue
        
        DispatchQueue.main.async {
            spinnerView.addSubview(ai)
            onView.addSubview(spinnerView)
        }
        return spinnerView
    }
    
    func removeSpinner(spinner: UIView) {
        DispatchQueue.main.async {
            spinner.removeFromSuperview()
        }
    }
    
}
