package SMSHandler;

import java.util.ArrayList;

import com.test.HelloNewActivity;

import dataLayer.DatabaseHelper;

import LocationHandler.LocationDetatil;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.gsm.SmsManager;
import android.util.Log;

public class SMSManager extends BroadcastReceiver {
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static final String TAG = "TestSMSApp";
	public DatabaseHelper dbhelper;
	LocationDetatil locationDetail;

	@Override
	public void onReceive(Context arg0, Intent intent) {

		// TODO Auto-generated method stub
		locationDetail = LocationDetatil.getInstance();
		Log.i(TAG, "Intent recieved: " + intent.getAction());
		dbhelper = new DatabaseHelper(arg0);

		if (intent.getAction().equalsIgnoreCase(SMS_RECEIVED)) {
			System.out.println("sms recaving yahoo");

			// ///////////////////////////////////
			Bundle myBundle = intent.getExtras();
			SmsMessage[] messages = null;
			String strMessage = "";

			if (myBundle != null) {
				Object[] pdus = (Object[]) myBundle.get("pdus");
				messages = new SmsMessage[pdus.length];

				for (int i = 0; i < messages.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
					strMessage += "SMS From: "
							+ messages[i].getOriginatingAddress();
					strMessage += " : ";
					strMessage += messages[i].getMessageBody();
					strMessage += "\n";

					System.out.println();
					try {
						if (isBlocked(
								locationDetail.getLatitude(),
								locationDetail.getLongtitude(),
								getContactName(
										messages[i].getOriginatingAddress(),
										arg0.getContentResolver()))) {

							/*
							 * PendingIntent pi =
							 * PendingIntent.getActivity(arg0, 0,new
							 * Intent(arg0, Object.class), 0); SmsManager sms =
							 * SmsManager.getDefault();
							 * sms.sendTextMessage("15555215556", null,
							 * "kkkkkk", pi, null);
							 */
							sendSMS(messages[i].getOriginatingAddress(),
									"Hello there I am busy.please contact me later",
									arg0);

							System.out
									.println("this profile is being blocked at this place...wow!!!!");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

		}

	}
	/**
	 * to get the display name of a given telephone number
	 * @param phoneNumber phone number of  the contact					
	 * @param cr A content resolver	
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
	
	/**
	 * to check whether a given contact is blocked on given geo cordinates
	 * @param latitudee  the latitiude of the geo place	
	 * @param longtitudee	the longtitude of the geo place	
	 * @param name	name of the contact
	 * @return true if the contact is blocked on given place, return false if the given contact is not blocked
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
	 * sends an SMS		
	 * @param phoneNumber the number of the recipiant 
	 * @param message the body of the message
	 * @param con	context which is being used
	 */

	public static void sendSMS(String phoneNumber, String message, Context con) {
		System.out.println("phone number to is " + phoneNumber);
		PendingIntent pi = PendingIntent.getActivity(con, 0, new Intent(con,
				Object.class), 0);
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, pi, null);

	}

}
