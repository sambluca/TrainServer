package TrainServer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;
import org.sqlite.*;

public class QueryDatabase {

	public static ResultSet buildData() throws IOException {
		String lat = Server.params.getLat();
		String lng = Server.params.getLng();
		String path = Server.params.getPath();

		Connection c = null;
		ResultSet stationData = null;

		try {
			c = DriverManager.getConnection("jdbc:sqlite:" + path);

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
