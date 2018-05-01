package TrainServer;

import java.util.HashMap;

public class Error {
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
	public static boolean checkLatLngParams(HashMap<String, String> params) {
		if(isLatAvailable(params.get("lat")) & isLngAvailable(params.get("lng"))) {
			return true;
		}

		return false;
	}
	
	public static String paramErrorMessage(HashMap<String, String> params) {
		String errorMessage = "Please supply the following parameters:";
		
		if(!isLatAvailable(params.get("lat"))) {
			errorMessage += " lat";
		}
		
		if(!isLngAvailable(params.get("lng"))) {
			errorMessage += " lng";
		}

		return errorMessage;
	}
}
