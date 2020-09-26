//
//  ProfiloTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 02/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class ProfiloTableViewCell: UITableViewCell {

    @IBOutlet weak var immaginePrincipale: UIImageView!
    @IBOutlet weak var testoPrincipale: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
