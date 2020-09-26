//
//  FrontEndResponse.swift
//  Agata
//
//  Created by Raffaele on 04/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation


struct FrontEndResponse : Codable{
    var model: [FrontEndRecordResponse]?
    var view: [FrontEndRecordResponse]?
    var controller: [FrontEndRecordResponse]?

}

struct FrontEndRecordResponse : Codable {
    var id: String?
    var id_project: String?
    var nome: String?
    var id_owner: String?
    var status: String?
    var type: String?
    var attributes: [FrontEndAttributeResponse]?

}

struct FrontEndAttributeResponse: Codable {
    var id: String?
    var id_front_end: String?
    var value: String?
    var priv: String?
    var type: String?
}

struct FrontEndCreateResponse: Codable {
    var id: String?
    var id_project: String?
    var nome: String?
    var id_owner: String?
    var status: String?
    var type: String?
}

struct FrontEndAttributeCreateResponse: Codable {
    var records: [FrontEndAttributeResponse]?
}
