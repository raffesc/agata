//
//  BackEndTableViewCell.swift
//  Agata
//
//  Created by Raffaele on 06/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import UIKit

class BackEndTableViewCell: UITableViewCell {

    @IBOutlet weak var shadowView: ShadowView!
    
    @IBOutlet weak var titleLabel: UILabel!
    
    @IBOutlet weak var status: UILabel!
    
    @IBOutlet weak var lineLabel: UILabel!
    
    @IBOutlet weak var queryTitle: UILabel!
    
    @IBOutlet weak var collectionViewQuery: UICollectionView!
    
    @IBOutlet weak var headerTitle: UILabel!
    
    @IBOutlet weak var collectionViewHeader: UICollectionView!
    
    
    @IBOutlet weak var bodyTitle: UILabel!
    
    
    @IBOutlet weak var collectionViewBody: UICollectionView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
