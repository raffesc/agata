//
//  UserListResponse.swift
//  Agata
//
//  Created by Raffaele on 02/09/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct UserListResponse : Codable{
    var records: [UserRecordListResponse]?
}

struct UserRecordListResponse: Codable {
    var id: String?
    var email: String?
    var username: String?
}
