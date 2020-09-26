//
//  ProjectSingleton.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

class ProjectSingleton {
    
    static let shared = ProjectSingleton()
    
    var mainColor : UIColor?
    var progetto : ProgettiRecordResponse?
    
    var listUsers: [UserRecordListResponse]?
    

}
