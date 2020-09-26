//
//  BackEndRequest.swift
//  Agata
//
//  Created by Raffaele on 05/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct BackEndRequest: Codable {
    var id_project: String?
    var id_owner: String?
    var nome: String?
    var status: String?
    var type: String?
}


struct BackEndAttributeRequest: Codable {
    var id_back_end: String?
    var title: String?
    var priv: String?
    var type: String?
    var value: String?
    public func convertToDict() -> [String: Any] {
        return ["id_back_end": id_back_end!,
                "title": title!,
                "priv": priv!,
                "type": type!,
                "value": value!]
    }
}
