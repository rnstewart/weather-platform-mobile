import SwiftUI
import sharedWeatherPlatform

struct ContentView: View {
    @State var api: OpenWeatherService? = nil
    
	var body: some View {
        VStack {
        }.onAppear {
            let apiKeys = Bundle.main.decode(APIKeysCodable.self, from: "apiKeys.json")
            api = OpenWeatherService.Companion().instance(apiKeys: apiKeys.toApiKeys())
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
