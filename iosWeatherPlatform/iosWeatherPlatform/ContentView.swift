import SwiftUI
import sharedWeatherPlatform

struct ContentView: View {
    @State var api: OpenWeatherService = OpenWeatherService.Companion().instance()
    
	var body: some View {
        VStack {
            Text("Hello")
        }.onAppear {
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
