//
//  AppuntiResponse.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct AppuntiResponse : Codable{
    var records: [AppuntiRecordResponse]?
}

struct AppuntiRecordResponse : Codable {
    var id: String?
    var id_category: String?
    var id_project: String?
    var title: String?
    var descrizione: String?
    var nome_category: String?
}


