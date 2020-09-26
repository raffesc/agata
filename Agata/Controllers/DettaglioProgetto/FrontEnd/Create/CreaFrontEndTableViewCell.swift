//
//  CreaFrontEndTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 05/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class CreaFrontEndTableViewCell: UITableViewCell {

    
    
    @IBOutlet weak var shadowView: ShadowView!
    
    @IBOutlet weak var mainLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
