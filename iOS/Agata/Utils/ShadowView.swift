//
//  ShadowView.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

class ShadowView: UIView {
    override var bounds: CGRect {
        didSet {
            setupShadow()
        }
    }
    
    override func layoutSubviews() {
        self.setupShadow()
    }

    public func setupShadow() {
       // self.layer.cornerRadius = 8
        self.layer.shadowOffset = CGSize(width: 0, height: 3)
        self.layer.shadowRadius = 3
        self.layer.shadowOpacity = 0.4
        self.layer.shadowPath = UIBezierPath(roundedRect: self.bounds, byRoundingCorners: .allCorners, cornerRadii: CGSize(width: 20, height: 20)).cgPath
        self.layer.shouldRasterize = true
        self.layer.rasterizationScale = UIScreen.main.scale
      //  self.clipsToBounds = true
        self.layer.cornerRadius = 20
        
    }
    
    public func changeColor(with color: UIColor) {
        self.layer.shadowColor = color.cgColor
    }
    
    public func changeCornerRadius(with radius: CGFloat) {
        self.layer.cornerRadius = radius
    }
}
