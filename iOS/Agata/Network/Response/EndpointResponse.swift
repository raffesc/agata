//
//  EndpointResponse.swift
//  Agata
//
//  Created by Raffaele on 06/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation



struct EndpointResponse : Codable{
    var records: [EndpointRecordResponse]?
}

struct EndpointRecordResponse : Codable {
    var id: String?
    var id_project: String?
    var title: String?
    var descrizione: String?
}

