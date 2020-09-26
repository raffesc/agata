//
//  FrontEndTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class FrontEndTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var titleMVC: UILabel!
    
    @IBOutlet weak var plusButton: UIButton!
    
    @IBOutlet weak var collectionView: UICollectionView!
    

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
