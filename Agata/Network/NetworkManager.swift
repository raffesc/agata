//
//  NetworkManager.swift
//  Agata
//
//  Created by Raffaele on 31/07/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import Alamofire
import UIKit

class NetworkManager {
    
    static let shared = NetworkManager()
    
    private let BASE_URL = "https://therichesthumanintheworld.com/api/"

    public func login(loginRequestParameters: LoginRequest, completion: @escaping (_ result : UserResponse?) -> ()) {
           
           let url = BASE_URL + "user/login.php"
        AF.request(url,method: .post, parameters: ["email": loginRequestParameters.email, "password": loginRequestParameters.password],encoding: JSONEncoding.default).responseJSON { response in
            
                guard let status = response.response?.statusCode else {
                    completion(nil)
                    return
                }
                switch(status){
                   // caso 200, TUTTO OK
                   case 200:
                       print(" success chiamata login")
                       if let data = response.data {
                           do {
                               let decoder: JSONDecoder = JSONDecoder.init()
                               let user: UserResponse = try decoder.decode(UserResponse.self, from: data)
                               completion(user)
                           } catch let e {
                               completion(nil)
                               print(e)
                           }
                       }
                   default:
                       print("error with response status: \(status)")
                       completion(nil)
                       return
                }
               
            }
               
    }

    public func register(registerRequest: RegistrationRequest, completion: @escaping (_ result : Response?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
    
          
          let url = BASE_URL + "user/registration.php"
       AF.request(url,method: .post, parameters: [
        "email": registerRequest.email,
        "password": registerRequest.password,
        "username": registerRequest.username,
        "image": registerRequest.image,
        "nome": registerRequest.nome,
        "cognome": registerRequest.cognome,
        "age": registerRequest.age
       ],encoding: JSONEncoding.default).responseJSON { response in
           
               guard let status = response.response?.statusCode else {
                   completion(nil)
                   return
               }
               switch(status){
                  // caso 200, TUTTO OK
                  case 200:
                      print(" success chiamata login")
                      if let data = response.data {
                          do {
                              let decoder: JSONDecoder = JSONDecoder.init()
                              let response: Response = try decoder.decode(Response.self, from: data)
                              completion(response)
                          } catch let e {
                              completion(nil)
                              print(e)
                          }
                      }
                  default:
                        if let data = response.data {
                            do {
                                let decoder: JSONDecoder = JSONDecoder.init()
                                let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                completitionError(response)
                            } catch let e {
                                completitionError(nil)
                                print(e)
                            }
                        }
                      print("error with response status: \(status)")
                      completion(nil)
                      return
               }
              
           }
              
   }

    
    public func listProgetti(idUser: String, completition: @escaping (_ result : ProgettiResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "project/list_user.php" + "?id_user=" + idUser
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: ProgettiResponse = try decoder.decode(ProgettiResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }
    
    public func listBugs(idProgetto: String, completition: @escaping (_ result : BugsResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "bugs/list.php" + "?id_project=" + idProgetto
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: BugsResponse = try decoder.decode(BugsResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }

    public func listFrontEnd(idProgetto: String, completition: @escaping (_ result : FrontEndResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "front-end/list.php" + "?id_project=" + idProgetto
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: FrontEndResponse = try decoder.decode(FrontEndResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }

    
    public func listAppunti(idProgetto: String, completition: @escaping (_ result : AppuntiResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "appunti/list.php" + "?id_project=" + idProgetto
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: AppuntiResponse = try decoder.decode(AppuntiResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }

    
    public func listBackEndAPI(idProgetto: String, completition: @escaping (_ result : BackEndResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "back-end/list.php" + "?id_project=" + idProgetto
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: BackEndResponse = try decoder.decode(BackEndResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }

    
    public func listBackEndEndpoint(idProgetto: String, completition: @escaping (_ result : EndpointResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "back-end/list-endpoint.php" + "?id_project=" + idProgetto
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: EndpointResponse = try decoder.decode(EndpointResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }

    public func listDesign(idProgetto: String, completition: @escaping (_ result : DesignResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "design/list.php" + "?id_project=" + idProgetto
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: DesignResponse = try decoder.decode(DesignResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }
    
    
    public func listUsers(completition: @escaping (_ result : UserListResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "user/list.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .get, headers: headers).responseJSON { response in
               
                   guard let status = response.response?.statusCode else {
                       completition(nil)
                       return
                   }
                   switch(status) {
                      // caso 200, TUTTO OK
                      case 200:
                          print(" success chiamata list")
                          if let data = response.data {
                              do {
                                  let decoder: JSONDecoder = JSONDecoder.init()
                                  let response: UserListResponse = try decoder.decode(UserListResponse.self, from: data)
                                  completition(response)
                              } catch let e {
                                  completition(nil)
                                  print(e)
                              }
                          }
                      default:
                            if let data = response.data {
                                do {
                                    let decoder: JSONDecoder = JSONDecoder.init()
                                    let response: ErrorResponse = try decoder.decode(ErrorResponse.self, from: data)
                                    completitionError(response)
                                } catch let e {
                                    completitionError(nil)
                                    print(e)
                                }
                            }
                          print("error with response status: \(status)")
                          completition(nil)
                          return
                   }
                  
               }
    }

    public func downloadImage(nome: String, completition: @escaping (_ result : UIImage?) -> ()) {
        
        let url = BASE_URL + "profile/uploads/" + nome
        AF.request(url, method: .get).response { response in
        
            guard let status = response.response?.statusCode else {
                completition(nil)
                return
            }
            switch(status) {
               // caso 200, TUTTO OK
               case 200:
                   print(" success chiamata list")
                   if let data = response.data {
                       completition(UIImage(data: data, scale:1))
                        return
                   }
                    
               default:
                   completition(nil)
                   return
            }
            completition(nil)
        }

    }
    
    
    public func addUserInProject(idOwner: String,id: String, id_new_user: String,  completition: @escaping (_ result : Response?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "project/add_user.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id, "id_owner": idOwner, "id_new_user": id_new_user], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: Response = try decoder.decode(Response.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }
    
    
    public func addEnpointInProject(idProject: String,title: String, descrizione: String,  completition: @escaping (_ result : EndpointRecordResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "back-end/insert-endpoint.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id_project": idProject, "title": title, "descrizione": descrizione], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: EndpointRecordResponse = try decoder.decode(EndpointRecordResponse.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    public func eliminaBug(id: String,  completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "bugs/delete.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Eliminiazione avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }
    
    public func eliminaEndpoint(id: String,  completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "back-end/delete-endpoint.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Eliminazione avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    public func eliminaBackEnd(id: String,  completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "back-end/delete-backend.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Eliminazione avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    public func eliminaFrontEnd(id: String,  completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "front-end/delete.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Eliminazione avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    public func eliminaDesign(id: String,  completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "design/delete.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Eliminazione avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }
    
    public func aggiornaStatusFrontEnd(id: String, status: String, completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "front-end/update_status.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id,
                                                   "status": status], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Aggiornamento avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    public func aggiornaStatusBackEnd(id: String, status: String, completition: @escaping (_ result : String?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "back-end/update_status.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id": id,
                                                   "status": status], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     completition("Aggiornamento avvenuta con successo")
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }



    public func uploadImageDesignInCategory(idCategory: String,idProject: String, image: UIImage, completition: @escaping (_ result : DesignImageResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
         let url = BASE_URL + "design/upload.php" + "?id_project=" + idProject + "&id_category=" + idCategory
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        
        AF.upload(multipartFormData:
            {
                (multipartFormData) in
                multipartFormData.append(image.jpegData(compressionQuality: 0.2)!, withName: "image_name", fileName: Utils.randomString(length: 14) + ".jpg", mimeType: "image/jpeg")

            
        }, to:url,headers:headers).responseJSON(completionHandler: { (response) in
            guard let status = response.response?.statusCode else {
                completition(nil)
                return
            }
            switch(status){
               // caso 200, TUTTO OK
               case 200:
                   print(" success chiamata login")
                   if let data = response.data {
                       do {
                           let decoder: JSONDecoder = JSONDecoder.init()
                           let response: DesignImageResponse = try decoder.decode(DesignImageResponse.self, from: data)
                           completition(response)
                       } catch let e {
                           completition(nil)
                           print(e)
                       }
                   }
               default:
                   print("error with response status: \(status)")
                   completition(nil)
                   return
            }


        })

        
    }
    
    
    public func addProgetto(projRequest: CreaProjRequest,  completition: @escaping (_ result : ProgettiCreateRecordResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "project/create.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )

        AF.request(url,method: .post, parameters: ["id_owner": projRequest.id_owner, "id_user": projRequest.id_users,"title": projRequest.title, "icon": projRequest.icon, "main_color": projRequest.main_color], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: ProgettiCreateRecordResponse = try decoder.decode(ProgettiCreateRecordResponse.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    
    public func addCategoryBug(idProject: String,titleCategory: String,  completition: @escaping (_ result : BugsCategoryResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "bugs/insert_category.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id_project": idProject, "nome": titleCategory], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: BugsCategoryResponse = try decoder.decode(BugsCategoryResponse.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    public func addCategoryDesign(idProject: String,titleCategory: String,  completition: @escaping (_ result : BugsCategoryResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
         let url = BASE_URL + "design/insert_category.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id_project": idProject, "nome": titleCategory], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata design/insert_category.php")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: BugsCategoryResponse = try decoder.decode(BugsCategoryResponse.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    public func addBackEnd(backEndRequest: BackEndRequest,  completition: @escaping (_ result : BackEndCreateResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "back-end/insert.php"
            guard let token = UserSingleton.shared.user?.userToken else {
                completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
                return
            }
            let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
            AF.request(url,method: .post, parameters: [
                "id_project" : backEndRequest.id_project!,
                "id_owner" : backEndRequest.id_owner!,
            "nome": backEndRequest.nome!,
            "status" : backEndRequest.status!,
            "type": backEndRequest.type!
], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                      
                          guard let status = response.response?.statusCode else {
                              completition(nil)
                              return
                          }
                          switch(status){
                             // caso 200, TUTTO OK
                             case 200:
                                 print(" success chiamata login")
                                 if let data = response.data {
                                     do {
                                         let decoder: JSONDecoder = JSONDecoder.init()
                                         let response: BackEndCreateResponse = try decoder.decode(BackEndCreateResponse.self, from: data)
                                         completition(response)
                                     } catch let e {
                                         completition(nil)
                                         print(e)
                                     }
                                 }
                             default:
                                 print("error with response status: \(status)")
                                 completition(nil)
                                 return
                          }
                         
                      }
    }
    
    public func addFrontEnd(frontEndRequest: FrontEndRequest,  completition: @escaping (_ result : FrontEndCreateResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        let url = BASE_URL + "front-end/insert.php"
            guard let token = UserSingleton.shared.user?.userToken else {
                completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
                return
            }
            let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
            AF.request(url,method: .post, parameters: [
                "id_project" : frontEndRequest.id_project!,
                "id_owner" : frontEndRequest.id_owner!,
            "nome": frontEndRequest.nome!,
            "status" : frontEndRequest.status!,
            "type": frontEndRequest.type!
            ], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                      
                          guard let status = response.response?.statusCode else {
                              completition(nil)
                              return
                          }
                          switch(status){
                             // caso 200, TUTTO OK
                             case 200:
                                 print(" success chiamata login")
                                 if let data = response.data {
                                     do {
                                         let decoder: JSONDecoder = JSONDecoder.init()
                                         let response: FrontEndCreateResponse = try decoder.decode(FrontEndCreateResponse.self, from: data)
                                         completition(response)
                                     } catch let e {
                                         completition(nil)
                                         print(e)
                                     }
                                 }
                             default:
                                 print("error with response status: \(status)")
                                 completition(nil)
                                 return
                          }
                         
                      }
    }

    
    public func addAttributeBackEnd(backendAttributes: [BackEndAttributeRequest],  completition: @escaping (_ result : BackEndAttributeCreateResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
            let url = BASE_URL + "back-end/insert_attributes.php"
                guard let token = UserSingleton.shared.user?.userToken else {
                    completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
                    return
                }
                let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        var dict: [[String: Any]] = []
        for elem in backendAttributes {
            dict.append(contentsOf: [elem.convertToDict()])
        }
        let parameter: Parameters = ["attributes": dict]
        AF.request(url,method: .post, parameters: parameter, encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                                  
                                      guard let status = response.response?.statusCode else {
                                          completition(nil)
                                          return
                                      }
                                      switch(status){
                                         // caso 200, TUTTO OK
                                         case 200:
                                             print(" success chiamata login")
                                             if let data = response.data {
                                                 do {
                                                     let decoder: JSONDecoder = JSONDecoder.init()
                                                     let response: BackEndAttributeCreateResponse = try decoder.decode(BackEndAttributeCreateResponse.self, from: data)
                                                     completition(response)
                                                 } catch let e {
                                                     completition(nil)
                                                     print(e)
                                                 }
                                             }
                                         default:
                                             print("error with response status: \(status)")
                                             completition(nil)
                                             return
                                      }
                                     
                                  }

        
        
        }

    public func addAttributeFrontEnd(frontendAttributes: [FrontEndAttributeRequest],  completition: @escaping (_ result : FrontEndAttributeCreateResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
            let url = BASE_URL + "front-end/insert_attributes.php"
                guard let token = UserSingleton.shared.user?.userToken else {
                    completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
                    return
                }
                let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        var dict: [[String: Any]] = []
        for elem in frontendAttributes {
            dict.append(contentsOf: [elem.convertToDict()])
        }
        let parameter: Parameters = ["attributes": dict]
        AF.request(url,method: .post, parameters: parameter, encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                                  
                                      guard let status = response.response?.statusCode else {
                                          completition(nil)
                                          return
                                      }
                                      switch(status){
                                         // caso 200, TUTTO OK
                                         case 200:
                                             print(" success chiamata login")
                                             if let data = response.data {
                                                 do {
                                                     let decoder: JSONDecoder = JSONDecoder.init()
                                                     let response: FrontEndAttributeCreateResponse = try decoder.decode(FrontEndAttributeCreateResponse.self, from: data)
                                                     completition(response)
                                                 } catch let e {
                                                     completition(nil)
                                                     print(e)
                                                 }
                                             }
                                         default:
                                             print("error with response status: \(status)")
                                             completition(nil)
                                             return
                                      }
                                     
                                  }

        
        
        }

    
    
    public func addBugInCategory(idProject: String,idCategory: String, descrizione: String, completition: @escaping (_ result : BugsRecordResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "bugs/insert.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id_project": idProject, "id_category": idCategory,"title": " ", "descrizione": descrizione], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: BugsRecordResponse = try decoder.decode(BugsRecordResponse.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    public func addAppuntoInCategory(idProject: String,idCategory: String,title: String, completition: @escaping (_ result : AppuntiRecordResponse?) -> (), completitionError: @escaping (_ error: ErrorResponse?) -> ()) {
        
         let url = BASE_URL + "appunti/insert.php"
        guard let token = UserSingleton.shared.user?.userToken else {
            completitionError(ErrorResponse(error: "Non autorizzato. Effettuare la login"))
            return
        }
        let headers: HTTPHeaders = HTTPHeaders( ["Authentication-Token": token] )
        AF.request(url,method: .post, parameters: ["id_project": idProject, "id_category": idCategory,"title": title, "descrizione": " "], encoding: JSONEncoding.default, headers: headers).responseJSON { response in
                  
                      guard let status = response.response?.statusCode else {
                          completition(nil)
                          return
                      }
                      switch(status){
                         // caso 200, TUTTO OK
                         case 200:
                             print(" success chiamata login")
                             if let data = response.data {
                                 do {
                                     let decoder: JSONDecoder = JSONDecoder.init()
                                     let response: AppuntiRecordResponse = try decoder.decode(AppuntiRecordResponse.self, from: data)
                                     completition(response)
                                 } catch let e {
                                     completition(nil)
                                     print(e)
                                 }
                             }
                         default:
                             print("error with response status: \(status)")
                             completition(nil)
                             return
                      }
                     
                  }
    }

    
    
}
