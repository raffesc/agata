//
//  TabNavigationMenu.swift
//  Agata
//
//  Created by Raffaele on 03/08/2020.
//  Copyright © 2020 Raffaele Ascione. All rights reserved.
//

import Foundation

import UIKit

class TabNavigationMenu: ShadowView {
    
    var itemTapped: ((_ tab: Int) -> Void)?
    var activeItem: Int = 0
    var itemsInEachView: [(UIImageView,UILabel)] = []
    var items: [TabItem] = [.progetti,.esplora,.profilo]
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    convenience init(menuItems: [TabItem], frame: CGRect) {
        self.init(frame: frame)
        self.layer.backgroundColor = UIColor.white.cgColor
        
        for i in 0 ..< menuItems.count {
            let itemWidth = self.frame.width / CGFloat(menuItems.count)
            let leadingAnchor = itemWidth * CGFloat(i)
            
            let itemView = self.createTabItem(item: menuItems[i])
            itemView.tag = i
            
            self.addSubview(itemView)
            
            itemView.translatesAutoresizingMaskIntoConstraints = false
          //  itemView.clipsToBounds = true
            
            NSLayoutConstraint.activate([
                itemView.heightAnchor.constraint(equalTo: self.heightAnchor),
                itemView.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: leadingAnchor),
                itemView.topAnchor.constraint(equalTo: self.topAnchor),
            ])
        }
        self.setNeedsLayout()
        self.layoutIfNeeded()
        self.activateTab(tab: 0)
    }
    
    func createTabItem(item: TabItem) -> UIView {
        let tabBarItem = UIView(frame: CGRect.zero)
        let itemTitleLabel = UILabel(frame: CGRect.zero)
        let itemIconView = UIImageView(frame: CGRect.zero)
        
        itemTitleLabel.text = item.displayTitle
        itemTitleLabel.font = UIFont.systemFont(ofSize: 14)
        itemTitleLabel.textAlignment = .center
        itemTitleLabel.textColor = .gray
        itemTitleLabel.translatesAutoresizingMaskIntoConstraints = false
        itemTitleLabel.tag = 0
        //itemTitleLabel.clipsToBounds = true
        
        itemIconView.image = item.iconDeactive
        itemIconView.tag = 1
        itemIconView.contentMode = .scaleAspectFit
        itemIconView.translatesAutoresizingMaskIntoConstraints = false
        itemIconView.clipsToBounds = true
        //tabBarItem.layer.backgroundColor = UIColor.white.cgColor
        tabBarItem.addSubview(itemIconView)
        tabBarItem.addSubview(itemTitleLabel)
        tabBarItem.translatesAutoresizingMaskIntoConstraints = false
        tabBarItem.clipsToBounds = true
        itemsInEachView.append((itemIconView,itemTitleLabel))
        NSLayoutConstraint.activate([
            itemIconView.heightAnchor.constraint(equalToConstant: 30), // Fixed height for our tab item(25pts)
            itemIconView.widthAnchor.constraint(equalToConstant: 25), // Fixed width for our tab item icon
            itemIconView.centerXAnchor.constraint(equalTo: tabBarItem.centerXAnchor),
            itemIconView.topAnchor.constraint(equalTo: tabBarItem.topAnchor, constant: 8), // Position menu item icon 8pts from the top of it's parent view
            itemIconView.leadingAnchor.constraint(equalTo: tabBarItem.leadingAnchor, constant: 35),
            itemTitleLabel.heightAnchor.constraint(equalToConstant: 17), // Fixed height for title label
            itemTitleLabel.widthAnchor.constraint(equalTo: tabBarItem.widthAnchor), // Position label full width across tab bar item
            itemTitleLabel.topAnchor.constraint(equalTo: itemIconView.bottomAnchor, constant: 4), // Position title label 4pts below item icon
            ])
        tabBarItem.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.handleTap))) // Each item should be able to trigger and action on tap
        return tabBarItem
    }
    
    @objc func handleTap(_ sender: UIGestureRecognizer) {
        self.switchTab(from: self.activeItem, to: sender.view!.tag)
    }
    
    func switchTab(from: Int, to: Int) {
        self.deactivateTab(tab: from)
        self.activateTab(tab: to)
    }
    
    func activateTab(tab: Int) {
        
        let tabToActivate = self.subviews[tab]
        let  titleImageView = self.itemsInEachView[tab].0
        let titleTextView = self.itemsInEachView[tab].1
        titleTextView.textColor = Utils.defaultColor.color
        titleImageView.image = self.items[tab].iconActive
        let borderWidth = tabToActivate.frame.size.width - 20
        let borderLayer = CALayer()
        borderLayer.backgroundColor = Utils.defaultColor.color.cgColor
        borderLayer.name = "active border"
        borderLayer.frame = CGRect(x: 10, y: 0, width: borderWidth, height: 2)
        DispatchQueue.main.async {
            UIView.animate(withDuration: 0.8, delay: 0.0, options: [.curveEaseIn, .allowUserInteraction], animations: {
                tabToActivate.layer.addSublayer(borderLayer)
                tabToActivate.setNeedsLayout()
                tabToActivate.layoutIfNeeded()
            })
            self.itemTapped?(tab)
        }
        self.activeItem = tab
    }
    
    func deactivateTab(tab: Int) {
        
        let inactiveTab = self.subviews[tab]
        let layersToRemove = inactiveTab.layer.sublayers!.filter({ $0.name == "active border" })
        let  titleImageView = self.itemsInEachView[tab].0
        let titleTextView = self.itemsInEachView[tab].1
        titleTextView.textColor = .gray
        titleImageView.image = self.items[tab].iconDeactive
        DispatchQueue.main.async {
            UIView.animate(withDuration: 0.4, delay: 0.0, options: [.curveEaseIn, .allowUserInteraction], animations: {
                layersToRemove.forEach({ $0.removeFromSuperlayer() })
                inactiveTab.setNeedsLayout()
                inactiveTab.layoutIfNeeded()
            })
        }
        
    }
}

