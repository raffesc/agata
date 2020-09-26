//
//  BackEnd.swift
//  Agata
//
//  Created by Raffaele on 06/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct BackEndResponse : Codable{
    var records: [BackEndRecordResponse]?
}

struct BackEndRecordResponse : Codable {
    var id: String?
    var id_project: String?
    var nome: String?
    var id_owner: String?
    var status: String?
    var type: String?
    var query: [BackEndAttributeResponse]?
    var header: [BackEndAttributeResponse]?
    var body: [BackEndAttributeResponse]?
}

struct BackEndAttributeResponse: Codable {
    var id: String?
    var id_back_end: String?
    var title: String?
    var priv: String?
    var type: String?
    var value: String?
}

struct BackEndCreateResponse: Codable {
    var id: String?
    var id_project: String?
    var nome: String?
    var id_owner: String?
    var status: String?
    var type: String?
}

struct BackEndAttributeCreateResponse: Codable {
    var records: [BackEndAttributeResponse]?
}
