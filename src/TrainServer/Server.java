package TrainServer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.*;

public class Server {
	public static String convertToJson(ResultSet stationsData) throws JSONException, SQLException {
		JSONArray convertedStationsData = new JSONArray();

		while(stationsData.next()) {			
			JSONObject stationData = new JSONObject();
			stationData.put("Latitide", stationsData.getString("Latitude"));
			stationData.put("Longitude", stationsData.getString("Longitude"));
			stationData.put("StationName", stationsData.getString("StationName"));
			
			convertedStationsData.put(stationData);
		}

		return convertedStationsData.toString();
		
	}

	public static HashMap<String, String> buildParams(String query) {
		HashMap<String, String> params = new HashMap<String, String>();
		String[] pairs = query.split("&");
		
		for(int i=0; i<pairs.length; i++) {
			String[] halves = pairs[i].split("=");
			if(halves.length < 2) {continue;}
			params.put(halves[0], halves[1]);
		}
		return params;
	}

	public static void createServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
			
			server.createContext("/stations", new HttpHandler() {

				@Override
				public void handle(HttpExchange he) throws IOException {
					HashMap<String, String> params = buildParams(he.getRequestURI().getQuery());
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
					String path = System.getProperty("user.dir") + "/trainstations.db";
					
					if(Error.checkLatLngParams(params)) {
						if(params.get("path") != null) {
							path = params.get("path");
						}
						String lat = params.get("lat");
						String lng = params.get("lng");


						ResultSet Stations = QueryDatabase.buildData(lat, lng, path);
						he.sendResponseHeaders(200, 0);

						try {
							bw.write(convertToJson(Stations));
						} catch (JSONException | SQLException e) {
							e.printStackTrace();
						}
					} else {
						he.sendResponseHeaders(404, 0);
						bw.write(Error.paramErrorMessage(params));
					}

					bw.close();
				}
				
			});
			server.start();			
			System.out.println("Started");

		} catch (IOException e) {
			System.err.println("ERRORS" + e);

			e.printStackTrace();
		}
		
	}

	public static void main(String args[]) {
		createServer();
	}
}
