//
//  FrontEndCollectionViewCell.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class FrontEndCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var removeImage: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var status: UILabel!
    
    @IBOutlet weak var shadowView: ShadowView!
    @IBOutlet weak var otherLabel: UILabel!
    @IBOutlet weak var attributesLabel: UILabel!
    
    @IBOutlet weak var lineLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

}
