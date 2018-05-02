package TrainServer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.*;

public class Server {
	public static Params params = new Params();

	private static String convertToJson(ResultSet stationsData) throws JSONException, SQLException {
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

	private static void buildParams(String query) {
		HashMap<String, String> paramsHashmap = new HashMap<String, String>();
		String[] valuesPassed = query.split("&");
		System.out.println(valuesPassed[0]);
		String path = System.getProperty("user.dir") + "/trainstations.db";

		for(int i=0; i<valuesPassed.length; i++) {
			String[] values = valuesPassed[i].split("=");
			if(values.length < 2) {continue;}
			paramsHashmap.put(values[0], values[1]);
		}
		
		params.setLng(paramsHashmap.get("lng"));
		params.setLat(paramsHashmap.get("lat"));

		if(paramsHashmap.get("path") != null) {
			path = paramsHashmap.get("path");
		}
		params.setPath(path);
	}

	private static void createServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
			
			server.createContext("/stations", new HttpHandler() {

				@Override
				public void handle(HttpExchange he) throws IOException {
					buildParams(he.getRequestURI().getQuery());
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
					
					if(Error.checkLatLngParams()) {
						ResultSet Stations = QueryDatabase.buildData();
						he.sendResponseHeaders(200, 0);

						try {
							bw.write(convertToJson(Stations));
						} catch (JSONException | SQLException e) {
							e.printStackTrace();
						}
					} else {
						he.sendResponseHeaders(404, 0);
						ArrayList<String> errors = Error.paramErrorMessages();
			            bw.write("Errors: \n");
				        for (int i = 0; i < errors.size(); i++){
				            String error = errors.get(i);
				            bw.write(error + "\n");
				        }

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
