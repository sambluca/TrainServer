package TrainServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Error {	
	private static boolean isValidLatLng(String value) {
		  try {
			  Double.parseDouble(value);
			  return true;
		  } catch(Exception e) {
			  return false;
		  }
	}

	private static boolean isLatAvailable(String lat) {
		if(lat != null) {
			return true;
		}
		return false;
	}

	private static boolean isLngAvailable(String lng) {
		if(lng != null) {
			return true;
		}
		return false;
	}

	public static boolean checkLatLngParams() {
		String lat = Server.params.getLat();
		String lng = Server.params.getLng();

		if(isLatAvailable(lat) & isLngAvailable(lng) & isValidLatLng(lat) & isValidLatLng(lng)) {
			return true;
		}

		return false;
	}
	
	public static ArrayList<String> paramErrorMessages() {
		ArrayList<String> errors = new ArrayList<String>();
		String lat = Server.params.getLat();
		String lng = Server.params.getLng();

		if(!isLatAvailable(lat)) {
			errors.add("Please supply a latitude parameter.");
		}

		if(!isLngAvailable(lng)) {
			errors.add("Please supply a longitude parameter.");
		}

		if(!isValidLatLng(lat) & isLatAvailable(lat)) {
			errors.add("That isn't a valid value for latitude.");
		}
		
		if(!isValidLatLng(lng) & isLngAvailable(lng)) {
			errors.add("That isn't a valid value for longitude.");
		}

		return errors;
	}
}
