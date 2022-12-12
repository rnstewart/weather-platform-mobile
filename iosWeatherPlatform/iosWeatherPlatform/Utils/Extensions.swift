//
//  Extensions.swift
//  iosWeatherPlatform
//
//  Created by Russell Stewart on 12/12/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedWeatherPlatform

extension String {
    func localizedStringFromKey(comment: String = "") -> String {
        return NSLocalizedString(self, comment: comment)
    }
}

extension WeatherDataResponse {
    func getCurrentTempStr() -> String {
        if let temp = currentTempFahrenheit {
            return String(
                format: "temperature_x".localizedStringFromKey(),
                temp
            )
        } else {
            return ""
        }
    }
    
    func getWindStr() -> String {
        if let wind = windWithDirection {
            let speed = String(wind.first ?? "")
            let dir = String(wind.second ?? "")
            if (dir.isEmpty) {
                return String(
                    format: "wind_info".localizedStringFromKey(),
                    speed
                )
            } else {
                return String(
                    format: "wind_info_direction".localizedStringFromKey(),
                    dir,
                    speed
                )
            }
        } else {
            return ""
        }
    }
}
