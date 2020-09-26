//
//  UserResponse.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct UserResponse : Codable{
    var email: String?
    var password: String?
    var userToken: String?
    var username: String?
    var image: String?
    var nome: String?
    var cognome: String?
    var age: String?
    var contract: String?
    var id: String?
}
