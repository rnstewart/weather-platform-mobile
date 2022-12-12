import SwiftUI
import sharedWeatherPlatform
import Kingfisher

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
                ).padding(.bottom, 8).textFieldStyle(.roundedBorder)
                
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
