//
//  DesignTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 07/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class DesignTableViewCell: UITableViewCell {

    @IBOutlet weak var plusButton: UIImageView!
    
    @IBOutlet weak var mainLabel: UILabel!
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

class ImageViewCustom: UIImageView {
    
    var currIndex = 0
    
}
