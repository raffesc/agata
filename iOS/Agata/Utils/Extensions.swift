//
//  Extensions.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation
import UIKit

extension String {
    
    func isValidEmail() -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"

        let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailPred.evaluate(with: self)
    }
    
    func getArray() -> [String] {
        var array: [String] = []
        var stringCurr = self
        stringCurr.removeFirst(1)
        stringCurr.removeLast()
        while let index = stringCurr.firstIndex(of: "&") {
            let end = stringCurr.index(after: index)
            stringCurr.removeSubrange(index...end)
        }
        while let index = stringCurr.firstIndex(of: "q") {
            let end = stringCurr.index(after: index)
            stringCurr.removeSubrange(index...end)
        }
        while let index = stringCurr.firstIndex(of: "u") {
            let end = stringCurr.index(after: index)
            stringCurr.removeSubrange(index...end)
        }
        while let index = stringCurr.firstIndex(of: "o") {
            let end = stringCurr.index(after: index)
            stringCurr.removeSubrange(index...end)
        }
        while let index = stringCurr.firstIndex(of: "t") {
            let end = stringCurr.index(after: index)
            stringCurr.removeSubrange(index...end)
        }
        while let index = stringCurr.firstIndex(of: ";") {
            let end = stringCurr.index(after: index)
            stringCurr.removeSubrange(index...end)
        }
        let ar = stringCurr.split(separator: ",")
        for i in ar {
            array.append(String(i))
        }
        return array
    }
    
    func getUser() -> String {
        
        guard let users = ProjectSingleton.shared.listUsers else {
            return ""
        }
        
        for user in users {
            if user.id == self {
                return user.username!
            }
        }
        return ""
    }

}


extension UILabel {
    func textHeight(withWidth width: CGFloat) -> CGFloat {
        guard let text = text else {
            return 0
        }
        return text.height(withWidth: width, font: font)
    }

    func attributedTextHeight(withWidth width: CGFloat) -> CGFloat {
        guard let attributedText = attributedText else {
            return 0
        }
        return attributedText.height(withWidth: width)
    }
}

extension String {
    func height(withWidth width: CGFloat, font: UIFont) -> CGFloat {
        let maxSize = CGSize(width: width, height: CGFloat.greatestFiniteMagnitude)
        let actualSize = self.boundingRect(with: maxSize, options: [.usesLineFragmentOrigin], attributes: [.font : font], context: nil)
        return actualSize.height
    }
}

extension NSAttributedString {
    func height(withWidth width: CGFloat) -> CGFloat {
        let maxSize = CGSize(width: width, height: CGFloat.greatestFiniteMagnitude)
        let actualSize = boundingRect(with: maxSize, options: [.usesLineFragmentOrigin], context: nil)
        return actualSize.height
    }
}


public extension UIView {

    func shake(count : Float = 4,for duration : TimeInterval = 0.5,withTranslation translation : Float = 5) {

        let animation = CAKeyframeAnimation(keyPath: "transform.translation.x")
        animation.timingFunction = CAMediaTimingFunction(name: CAMediaTimingFunctionName.linear)
        animation.repeatCount = count
        animation.duration = duration/TimeInterval(animation.repeatCount)
        animation.autoreverses = true
        animation.values = [translation, -translation]
        layer.add(animation, forKey: "shake")
    }
}

extension UIView {

    func fadeIn(_ duration: TimeInterval? = 0.5, onCompletion: (() -> Void)? = nil) {
        self.alpha = 0
        self.isHidden = false
        UIView.animate(withDuration: duration!,
                       animations: { self.alpha = 1 },
                       completion: { (value: Bool) in
                          if let complete = onCompletion { complete() }
                       }
        )
    }

    func fadeOut(_ duration: TimeInterval? = 0.5, onCompletion: (() -> Void)? = nil) {
        UIView.animate(withDuration: duration!,
                       animations: { self.alpha = 0 },
                       completion: { (value: Bool) in
                           self.isHidden = true
                           if let complete = onCompletion { complete() }
                       }
        )
    }

}


extension UILabel {
    func calculateMaxLines() -> Int {
        let maxSize = CGSize(width: frame.size.width, height: CGFloat(Float.infinity))
        let charSize = font.lineHeight
        let text = (self.text ?? "") as NSString
        let textSize = text.boundingRect(with: maxSize, options: .usesLineFragmentOrigin, attributes: [NSAttributedString.Key.font: font], context: nil)
        let linesRoundedUp = Int(ceil(textSize.height/charSize))
        return linesRoundedUp
    }
}

extension Encodable {
  func asDictionary() throws -> [String: String] {
    let data = try JSONEncoder().encode(self)
    guard let dictionary = try JSONSerialization.jsonObject(with: data, options: .allowFragments) as? [String: String] else {
      throw NSError()
    }
    return dictionary
  }
}
