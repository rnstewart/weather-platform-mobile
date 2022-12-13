import SwiftUI
import sharedWeatherPlatform
import Kingfisher

struct ContentView: View {
    @State var weatherRepository: WeatherRepository? = nil
    @State var googleMapsRepository: GoogleMapsRepository? = nil
    @StateObject var locationManager = LocationManager()
    @State var searchQuery = ""
    @State var isLoading: Bool = false
    
	var body: some View {
        VStack {
            HStack {
                TextField(
                    "",
                    text: $searchQuery
                ).padding(.bottom, 8)
                    .padding(.top, 16)
                    .textFieldStyle(.roundedBorder)
                
                if (isLoading) {
                    ProgressView()
                        .padding(6)
                } else if (!searchQuery.isEmpty) {
                    Image("IconUpdate")
                        .padding(6)
                        .onTapGesture {
                            searchLocation(query: searchQuery)
                        }
                } else {
                    Image("IconLocation")
                        .padding(6)
                        .onTapGesture {
                            locationManager.updateLocation()
                        }
                }
            }
            
            if let autocompletePredictions = googleMapsRepository?.data.autocompletePredictions,
               !autocompletePredictions.isEmpty {
                List {
                    ForEach(autocompletePredictions, id: \.self) { prediction in
                        HStack {
                            Text(prediction.name)
                                .font(.system(size: 16))
                            
                            Spacer()
                        }.padding(8).onTapGesture {
                            if let weatherRepository = weatherRepository {
                                googleMapsRepository?.autocompleteResultSelected(
                                    location: prediction,
                                    weatherRepository: weatherRepository
                                ) { updatedRepo, error in
                                    self.weatherRepository = updatedRepo
                                    self.googleMapsRepository = self.googleMapsRepository?.clear()
                                }
                            }
                        }
                    }
                }
            } else if let data = weatherRepository?.data.data {
                HStack {
                    Spacer()
                    Text(data.name ?? "")
                        .font(.system(size: 24))
                    Spacer()
                }
                
                HStack(alignment: .center) {
                    VStack(alignment: .leading) {
                        Text(data.getCurrentTempStr())
                            .font(.system(size: 20))
                        
                        if let currentWeatherCondition = data.currentWeatherCondition {
                            Text(currentWeatherCondition)
                                .font(.system(size: 18))
                        }
                    }
                    
                    let urlString = (data.getIconUrl(density: 2) ?? "")
                    if let iconUrl = URL(string: urlString) {
                        KFImage.url(iconUrl)
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 40, height: 40)
                    }
                    Spacer()
                }.padding(.top, 16)
            }
            Spacer()
        }.padding(8).onAppear {
            let repositories = Repositories()
            weatherRepository = repositories.weatherRepository
            googleMapsRepository = repositories.googleMapsRepository
            locationManager.setLocationCallback { location in
                if let location = location {
                    weatherRepository?.searchWeather(
                        query: "",
                        latitude: KotlinDouble(value: location.coordinate.latitude),
                        longitude: KotlinDouble(value: location.coordinate.longitude)
                    ) { updatedRepository, error in
                        self.googleMapsRepository = self.googleMapsRepository?.clear()
                        self.weatherRepository = updatedRepository
                    }
                }
            }
        }
    }
    
    func searchLocation(query: String) {
        UIApplication.shared.endEditing()
        googleMapsRepository?.placesAutoComplete(input: query, latitude: nil, longitude: nil) { updatedRepo, error in
            self.googleMapsRepository = updatedRepo
        }
    }
}
