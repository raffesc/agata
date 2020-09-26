//
//  FrontEndRequest.swift
//  Agata
//
//  Created by Raffaele on 05/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation


struct FrontEndRequest: Codable {
    var id_project: String?
    var id_owner: String?
    var nome: String?
    var status: String?
    var type: String?
}


struct FrontEndAttributeRequest: Codable {
    var id_front_end: String?
    var value: String?
    var priv: String?
    var type: String?
    public func convertToDict() -> [String: Any] {
        return ["id_front_end": id_front_end!,
                "value": value!,
                "priv": priv!,
                "type": type!,]
    }
}
