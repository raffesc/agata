//
//  TabItem.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

enum TabItem: String, CaseIterable {
    case progetti = "progetti"
    case esplora = "esplora"
    case profilo = "profilo"
    
    
    var viewController: UIViewController {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        switch self {
        case .progetti:
            return storyboard.instantiateViewController(identifier: "ProgettiNavigationController") as! ProgettiNavViewController
        case .esplora:
            return storyboard.instantiateViewController(identifier: "EsploraViewController") as! EsploraViewController
        case .profilo:
            return storyboard.instantiateViewController(identifier: "ProfiloViewController") as! ProfiloViewController
        }
    }
    
    var icon: UIImage? {
        switch self {
        case .progetti:
            return UIImage(named: "home")!
        case .esplora:
            return UIImage(named: "esplora")!
        case .profilo:
            return UIImage(named: "profilo")!
        }
    }
    
    var iconActive: UIImage? {
        switch self {
        case .progetti:
            return UIImage(named: "home")!
        case .esplora:
            return UIImage(named: "esplora")!
        case .profilo:
            return UIImage(named: "profilo")!
        }
    }
    
    var iconDeactive: UIImage? {
        switch self {
        case .progetti:
            return UIImage(named: "home_deactive")!
        case .esplora:
            return UIImage(named: "esplora_deactive")!
        case .profilo:
            return UIImage(named: "profilo_deactive")!
        }
    }
    
    var displayTitle: String {
        return self.rawValue.capitalized(with: nil)
    }
}
