//
//  ProgettiResponse.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct ProgettiResponse : Codable{
    var records: [ProgettiRecordResponse]?
}

struct ProgettiRecordResponse : Codable{
    var id: String?
    var id_owner: String?
    var title: String?
    var id_users: String?
    var icon: String?
    var main_color: String?
    var published: String?
    var num_like: String?
    var ranking: String?
}

struct ProgettiCreateRecordResponse : Codable{
    var id: String?
    var id_owner: String?
    var id_users: String?
    var title: String?
    var published: Int?
    var num_like: Int?
    var ranking: Int?
    var main_color: String?
    var icon: String?
}
