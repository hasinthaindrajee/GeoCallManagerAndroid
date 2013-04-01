package LocationHandler;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListner extends Activity implements LocationListener 

{
	public LocationDetatil locaitonDetails;

@Override
/**
 *  gets the Lattitude and longtitude when the postion of the user is changing
 */
public void onLocationChanged(Location loc){
locaitonDetails = LocationDetatil.getInstance();

locaitonDetails.setLatitude(loc.getLatitude());
locaitonDetails.setLongtitude(loc.getLongitude());
String Text = "My current location is: " +
"Latitud = " + locaitonDetails.getLatitude() +
"Longitud = " + locaitonDetails.getLongtitude();
System.out.println(Text);

}


@Override

public void onProviderDisabled(String provider){

Toast.makeText( getApplicationContext(),"Gps Disabled",Toast.LENGTH_SHORT ).show();

}


@Override

public void onProviderEnabled(String provider){

Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();

}


@Override

public void onStatusChanged(String provider, int status, Bundle extras)

{

}

}/* End of Class MyLocationListener */
