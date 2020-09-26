//
//  UtentiTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 03/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class UtentiTableViewCell: UITableViewCell {

    @IBOutlet weak var shadowView: ShadowView!
    
    @IBOutlet weak var usernameLabel: UILabel!
    
    @IBOutlet weak var imageUser: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
