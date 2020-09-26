//
//  Utils.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

class Utils {
    
    
    
    public static let defaultColor = CustomColor(color: UIColor(red: 68/255, green: 176/255, blue: 255/255, alpha: 1), id: -1)
    
    public static let lightGray =  #colorLiteral(red: 1.0, green: 1.0, blue: 1.0, alpha: 1.0)
    
    public static let defaultImage = UIImage(named: "icon1")!

    
    public static let arrayColor: [CustomColor] = [CustomColor(color: UIColor(red: 53/255, green: 196/255, blue: 255/255, alpha: 1), id: 1),
    CustomColor(color: UIColor(red: 130/255, green: 53/255, blue: 255/255, alpha: 1), id: 3),
    CustomColor(color: UIColor(red: 255/255, green: 53/255, blue: 137/255, alpha: 1), id: 2),
    CustomColor(color: UIColor(red: 255/255, green: 91/255, blue: 53/255, alpha: 1), id: 4)]
    
    
    
    public static func getAllColors() -> [CustomColor] {
        return arrayColor
    }
    
    public static func getAllIcons() -> [UIImage] {
            var arrayImages: [UIImage] = []
            for i in 1...3 {
                arrayImages.append(getIcon(for: i))
            }
            return arrayImages
    }

    
    public static func getColor(for id: Int) -> UIColor {
        for color in arrayColor {
            if color.id == id {
                return color.color
            }
        }
        return defaultColor.color
    }
    
    public static func getIcon(for id: Int) -> UIImage {
        
        switch id {
        case 1:
            return UIImage(named: "icon1")!
        case 2:
            return UIImage(named: "icon2")!
        case 3:
            return UIImage(named: "icon3")!
        default:
            return defaultImage
        }
        
        
    }
    
    
    public static func randomString(length: Int) -> String {
      let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
      return String((0..<length).map{ _ in letters.randomElement()! })
    }
    
    
    
    public static func createNoResult(in view: UIView, title: String) -> (UIImageView, UILabel) {
        
        let imageView: UIImageView = {
            
            let image = UIImageView()
            image.image = UIImage(named: "noResult")
            image.contentMode = .scaleAspectFit
            image.translatesAutoresizingMaskIntoConstraints = false
            return image
            
        }()
        
        let label: UILabel = {
            
            let label = UILabel()
            label.text = title
            label.textColor = .black
            label.textAlignment = .center
            label.translatesAutoresizingMaskIntoConstraints = false
            return label

        }()
        
        view.addSubview(imageView)
        view.addSubview(label)
        
        NSLayoutConstraint.activate([
            
            // CONSTRAINT IMAGE
            imageView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            imageView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            imageView.widthAnchor.constraint(equalToConstant: 100),
            imageView.heightAnchor.constraint(equalToConstant: 100),

            // CONSTRAINT LABEL
            label.topAnchor.constraint(equalTo: imageView.bottomAnchor,constant: 20),
            label.centerXAnchor.constraint(equalTo: view.centerXAnchor)

        ])

        
        return (imageView,label)
        
        
    }
    
}


extension UIColor {
    
    public static let mainColor = Utils.defaultColor.color
    
}
