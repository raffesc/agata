//
//  DesignImageResponse.swift
//  Agata
//
//  Created by Raffaele on 04/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation


struct DesignImageResponse : Codable{
    var status_code: Int?
    var message: String?
    var data: DesignImageDataResponse?
}

struct DesignImageDataResponse : Codable{
    var size: Int?
    var file_name: String?
    var type: String?
    var resolution: String?
    var thumb: Bool?
}
