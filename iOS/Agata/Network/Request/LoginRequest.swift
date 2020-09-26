//
//  LoginRequest.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

struct LoginRequest: Encodable {
    let email: String
    let password: String
}
