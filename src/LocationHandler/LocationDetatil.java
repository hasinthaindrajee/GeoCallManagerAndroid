package LocationHandler;

public class LocationDetatil {
	/**
	 * default values of gps cordinates
	 */
	private static double Latitude=0;
	private static  double Longtitude=0;
	private static LocationDetatil locationDtail;
	
	/**
	 * LocaitonDetail class is singlton 
	 * @return an instance of LocationDetail Class
	 */
	
	public static LocationDetatil getInstance(){
		if (locationDtail!=null){
			return locationDtail;
		}else{
			locationDtail= new LocationDetatil();
			return locationDtail;
		}
	}
	/**
	 * Set the latitude of of LocationDetailer
	 * @param latitude Latitude of the place
	 */
	
	public void setLatitude(double latitude){
		this.Latitude=latitude;
		
	}
	/**
	 * Set the longtitude of Location Handler
	 * @param longtitude Longtitude of the place
	 */
	public void setLongtitude(double longtitude){
		this.Longtitude= longtitude;
	}
	
	/**
	 * Getter method for Latitude
	 * @return The current latitude of the user position 
	 */
	public double getLatitude(){
		return Latitude;
	}
	/**
	 * 
	 * @return The current latitude of the user postion 
	 */
	
	public double getLongtitude(){
		return Longtitude;
	}
	
	

}
