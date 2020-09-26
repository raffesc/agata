//
//  DesignResponse.swift
//  Agata
//
//  Created by Raffaele on 07/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct DesignResponse : Codable{
    var records: [DesignRecordResponse]?
}

struct DesignRecordResponse : Codable {
    var id: String?
    var id_category: String?
    var id_project: String?
    var nome: String?
    var nome_category: String?
    var descrizione: String?
}


