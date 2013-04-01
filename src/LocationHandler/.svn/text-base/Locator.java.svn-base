package LocationHandler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.test.R;


public class Locator extends MapActivity{
	
	double LATITUDE = 37.42233;
	double LONGITUDE = -122.083;

	final int maxResult =5;
	String addressList[] = new String[maxResult];
	private ArrayAdapter<String> adapter;


	Geocoder geocoder;

	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.main);
	     getPlace();
	    
	      
	  }



	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void getPlace(){
		geocoder = new Geocoder(this, Locale.ENGLISH);

	      try {
	  List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, maxResult);
	 
	  if(addresses != null) {
	  
	   for (int j=0; j<maxResult; j++){
	    Address returnedAddress = addresses.get(j);
	    StringBuilder strReturnedAddress = new StringBuilder();
	    for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
	     strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
	    }
	    addressList[j] = strReturnedAddress.toString();
	   }
	  
	   adapter = new ArrayAdapter<String>(this,
	     android.R.layout.simple_spinner_item, addressList);
	   adapter.setDropDownViewResource(android.R.layout.
	     simple_spinner_dropdown_item);
	  
	  
	   
	  }
	  else{
	   
	  }
	 } catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	
	 }
	}
}
