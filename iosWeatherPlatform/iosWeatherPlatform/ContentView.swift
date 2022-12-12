import SwiftUI
import sharedWeatherPlatform

struct ContentView: View {
    @State var weatherRepository: WeatherRepository? = nil
    @StateObject var locationManager = LocationManager()
    @State var searchQuery = ""
    @State var isLoading: Bool = false
    
	var body: some View {
        VStack {
            HStack {
                TextField(
                    "",
                    text: $searchQuery
                ).textFieldStyle(.roundedBorder)
                
                if (isLoading) {
                    ProgressView()
                        .padding(6)
                } else {
                    Image("IconLocation")
                        .padding(6)
                        .onTapGesture {
                            locationManager.updateLocation()
                        }
                }
            }
            
            if let data = weatherRepository?.data.data {
                VStack {
                    HStack {
                        Spacer()
                        Text(data.name ?? "")
                            .font(.system(size: 24))
                        Spacer()
                    }
                    Spacer()
                }.padding(8)
            }
            Spacer()
        }.padding(8).onAppear {
            weatherRepository = Repositories().weatherRepository
            locationManager.setLocationCallback { location in
                if let location = location {
                    weatherRepository?.searchWeather(
                        query: "",
                        latitude: KotlinDouble(value: location.coordinate.latitude),
                        longitude: KotlinDouble(value: location.coordinate.longitude)
                    ) { updatedRepository, error in
                        self.weatherRepository = updatedRepository
                    }
                }
            }
        }
    }
    
    func getLocation() {
        
    }
}
