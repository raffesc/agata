//
//  RegistrationRequest.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct RegistrationRequest: Encodable {
    
    var email: String
    var password: String
    var username: String
    var image: String
    var nome: String
    var cognome: String
    var age: String
    
    
}
