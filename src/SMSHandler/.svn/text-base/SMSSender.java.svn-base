package SMSHandler;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.util.Log;

public class SMSSender extends Activity{
	
	public void Oncreate()
	{
	  
	

	}
	
	 private void sendSMS(String phoneNumber, String message)
	    {        
	        Log.v("phoneNumber",phoneNumber);
	        Log.v("MEssage",message);
	        PendingIntent pi = PendingIntent.getActivity(this, 0,new Intent(this, SMSSender.class), 0);                
	        SmsManager sms = SmsManager.getDefault();
	        sms.sendTextMessage(phoneNumber, null, message, pi, null);        
	    }    

	

}
