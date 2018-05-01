package TrainServer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;
import org.sqlite.*;

public class QueryDatabase {

	public static ResultSet buildData(String lat, String lng, String pathToDatabase) throws IOException {
		Connection c = null;
		ResultSet stationData = null;

		try {
			c = DriverManager.getConnection("jdbc:sqlite:" + pathToDatabase);
			System.out.println(System.getProperty("user.dir") + "/trainstations.db");
			
			PreparedStatement s = c.prepareStatement("SELECT StationName, Operator, Longitude, Latitude, ((("+ lat +" - Latitude) * ("+ lat +" - Latitude)) + (0.59 * (("+ lng +" - Longitude) * ("+ lng +" - Longitude)))) AS DistanceMetric FROM stations ORDER BY DistanceMetric LIMIT 5");

			stationData = s.executeQuery();

		}
		catch (SQLException se) {
			se.printStackTrace();
			return null;
		}
		return stationData;
		
	}
}
