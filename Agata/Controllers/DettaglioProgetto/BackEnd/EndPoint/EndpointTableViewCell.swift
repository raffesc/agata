//
//  EndpointTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 06/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class EndpointTableViewCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var valueLabel: UILabel!
    
    @IBOutlet weak var lineLabel: UILabel!
    
    @IBOutlet weak var shadowView: ShadowView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
