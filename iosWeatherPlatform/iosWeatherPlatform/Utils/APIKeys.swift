//
//  APIKeys.swift
//  iosWeatherPlatform
//
//  Created by Russell Stewart on 3/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import sharedWeatherPlatform

struct APIKeysCodable: Codable {
    var openWeatherMap: OpenWeatherMap? = nil
    var googleMaps: GoogleMaps? = nil
    
    struct OpenWeatherMap: Codable {
        var apiKey: String = ""
        var apiHost: String = ""
    }
    
    struct GoogleMaps: Codable {
        var apiKey: String = ""
    }
    
    func toApiKeys() -> APIKeys {
        return APIKeys(
            openWeatherMap: APIKeys.OpenWeatherMap(
                apiKey: openWeatherMap?.apiKey ?? "",
                apiHost: openWeatherMap?.apiHost ?? ""
            ),
            googleMaps: APIKeys.GoogleMaps(
                apiKey: googleMaps?.apiKey ?? ""
            )
        )
    }
}
