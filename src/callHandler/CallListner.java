package callHandler;

import java.util.ArrayList;

import com.test.Phone;

import dataLayer.DatabaseHelper;
import LocationHandler.LocationDetatil;
import SMSHandler.SMSManager;
import SMSHandler.SMSSender;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.gsm.SmsManager;
import android.util.Log;

public class CallListner extends PhoneStateListener {
	public Context context;
	DatabaseHelper dbhelper;
	LocationDetatil locationDetail;
	Phone phone = new Phone();
	private String ringState = "ringing";

	@Override
	//triggers this action on call state change
	public void onCallStateChanged(int state, String incomingNumber) {
		System.out.println("State of the phone is "+state);
		//if the state is ringing 
		if (state == 1 && ringState.equalsIgnoreCase("ringing")) {
			
			locationDetail = LocationDetatil.getInstance(); // to get the current location detail
			dbhelper = new DatabaseHelper(context);			//to get date from database
			
			//Checks whether the incomming number is blocked according to the current postion
			if (isBlocked(
					locationDetail.getLatitude(),
					locationDetail.getLongtitude(),
					getContactName(incomingNumber, context.getContentResolver()))) {

				phone.activeSilentMode(context);
				ringState = "silent";
				sendSms(incomingNumber,
						"Hello there, I am busy right now, Please call me later");

			}

		} else if (state == 0) {
			phone.activeDefaultMode(context);
			ringState = "ringing";
		}

		super.onCallStateChanged(state, incomingNumber);
	}

	/*
	 * Set the context for this activity
	 */
	public void setContext(Context context) {
		this.context = context;
	}
	/*
	 * send an sms of given text to the given number
	 */
	private void sendSms(String resipiantNo, String text) {
		SMSManager.sendSMS(resipiantNo, text, context);
	}
	
	 
	  /**
	   * check whether the contact is blockec or not
	   * @param latitudee the latitude of the current postion	
	   * @param longtitudee the longtitiude of the current postion
	   * @param name	name of the person calling
	   * @return	whether the given name is blocked under this position or not
	   */
	public boolean isBlocked(double latitudee, double longtitudee, String name) {
		ArrayList<String> blockedConatctNames = dbhelper.getBlockedSet(dbhelper
				.getProfNameFromCordinates(latitudee, longtitudee));
		if (dbhelper.isThereString(dbhelper.listToArray(blockedConatctNames),
				name)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param phoneNumber 
	 * @param cr
	 * @return the display name of the given contact number 
	 */

	public String getContactName(final String phoneNumber, ContentResolver cr) {
		Uri uri;
		String[] projection;

		if (Build.VERSION.SDK_INT >= 5) {
			uri = Uri.parse("content://com.android.contacts/phone_lookup");
			projection = new String[] { "display_name" };

		} else {
			uri = Uri.parse("content://contacts/phones/filter");
			projection = new String[] { "name" };

		}

		uri = Uri.withAppendedPath(uri, Uri.encode(phoneNumber));

		String contactName = "";

		Cursor cursor = cr.query(uri, projection, null, null, null);

		if (cursor.moveToFirst()) {
			contactName = cursor.getString(0);
		}

		cursor.close();
		cursor = null;

		return contactName;
	}

}
