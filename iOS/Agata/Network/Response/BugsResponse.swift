//
//  BugsResponse.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct BugsResponse : Codable{
    var records: [BugsRecordResponse]?
}

struct BugsRecordResponse : Codable {
    var id: String?
    var id_category: String?
    var id_project: String?
    var title: String?
    var descrizione: String?
    var nome_category: String?
}

struct BugsCategoryResponse: Codable {
    var id: String?
    var id_project: String?
    var nome: String?
}
