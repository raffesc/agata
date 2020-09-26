//
//  AppuntiCollectionViewCell.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class AppuntiCollectionViewCell: UICollectionViewCell {


    @IBOutlet weak var shadowView: ShadowView!
    
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var tableView: UITableView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

}
