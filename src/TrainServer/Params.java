package TrainServer;

public class Params {
	public String lat;
	public String lng;
	public String path;

	/**
	 * Getter for the database's path
	 * @return Database's path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Setter for the database's path
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Getter for the latitude parameter supplied
	 * @return Latitude parameter
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * Setter for the latitude parameter supplied
	 * @param lat
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * Getter for the longitude parameter supplied
	 * @return longitude parameter
	 */
	public String getLng() {
		return lng;
	}
	
	/**
	 * Setter for the longitude parameter supplied
	 * @param lat
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

}
