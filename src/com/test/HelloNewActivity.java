package com.test;

import java.util.ArrayList;

import callHandler.CallListner;

import dataLayer.DatabaseHelper;

import LocationHandler.LocationDetatil;
import LocationHandler.MyLocationListner;
import SMSHandler.SMSManager;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;

import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HelloNewActivity extends Activity {

	private BroadcastReceiver receiver;
	private static final String TAG = "TestSMSApp";
	SQLiteDatabase db;
	public static DatabaseHelper DBHelper;
	public static String[] Profiles, conatacts;
	public static ArrayList<String> profileList;
	public static ContentResolver cr;
	static ArrayAdapter<String> adapter2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		final Phone phone = new Phone();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DBHelper = new DatabaseHelper(this);
		db = DBHelper.getWritableDatabase();
		

		addProfilesToDropdown(); 
		ContentResolver cr = getContentResolver();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, phone.getContactsNames(cr));
		conatacts = phone.getContactsNames(cr);
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		textView.setAdapter(adapter);
		
		Profiles = DBHelper.cursorToStringArray(DBHelper.getAllProfiles(),
				"Name"); // Get all profiles by name

		
		Log.i(TAG, " App has started up");
		System.out.println("SMS running");
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		Log.i(TAG, " Filter SMS_RECEIVED has been added");
		// Extends BroadcastReceiver
		receiver = new SMSManager();
		registerReceiver(receiver, filter);
		Log.i(TAG, " registerReceiver sorted");

		// Starting the activity of Getting GPS cordinates
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		LocationListener mlocListener = new MyLocationListner();

		if (mlocManager == null || mlocManager == null) {
			System.out.println("Manager is nulllllllll");
		}

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);

		CallListner mPhoneStateListener = new CallListner();
		mPhoneStateListener.setContext(this);

		TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

		blockCall();
		buttonOneOnClick();
		buttonTwoOnClick();
		blockHereButton();
		buttonThreeOnClick();

		buttonFourOnClick();

	}

	/**
	 * Actioin for add profile button. Gives the new UI to add a Geo Profile
	 */
	public void buttonTwoOnClick() {
		final Button button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				Intent i = new Intent(HelloNewActivity.this, AddProfile.class);
				String name = "example";
				i.putExtra("NAME", name);
				startActivityForResult(i, 0);// or startActivity,see javadoc for
												// your preferences

			}
		});
	}

	/**
	 * Action for Edit Profile button. Gives the UI to Edit a Geo Profile
	 */

	public void buttonThreeOnClick() {

		final Button button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				Intent i = new Intent(HelloNewActivity.this,
						ProfileEditor.class);
				String name = "example";
				i.putExtra("NAME", name);
				startActivityForResult(i, 0);// or startActivity,see javadoc for
												// your preferences

			}
		});
	}

	/**
	 * Action for Manage call button. Runs the Application back ground
	 */
	public void buttonFourOnClick() {
		final Button button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				moveTaskToBack(true);

			}
		});

	}

	/**
	 * Action for block contact butoon the data will be checked for validity and
	 */

	public void buttonOneOnClick() {

		final Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AutoCompleteTextView ContactBlocked = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
				AutoCompleteTextView PrfileAdded = (AutoCompleteTextView) findViewById(R.id.autocomplete_Profile);
				String contact, profile;
				contact = ContactBlocked.getText().toString();
				profile = PrfileAdded.getText().toString();

				if (contact != null && profile != null
						&& DBHelper.isThereString(Profiles, profile)
						&& DBHelper.isThereString(conatacts, contact)) {
					if (!DBHelper.addBlockContact(contact, profile)) {
						Toast.makeText(
								HelloNewActivity.this,
								"Contact already blocked under this profile"
										.toString(), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(HelloNewActivity.this,
								"Successfully Blocked the Profile".toString(),
								Toast.LENGTH_SHORT).show();
						ContactBlocked.setText("");
						PrfileAdded.setText("");
					}

				} else {
					Toast.makeText(
							HelloNewActivity.this,
							"Please select from Existing profiles and Contacts"
									.toString(), Toast.LENGTH_SHORT).show();
				}

				;// or startActivity,see javadoc for
					// your preferences

			}
		});
	}

	/**
	 * add profiles to the auto complete text box
	 */
	public void addProfilesToDropdown() {

		Profiles = DBHelper.cursorToStringArray(DBHelper.getAllProfiles(),
				"Name");

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				R.layout.list_item, Profiles);
		AutoCompleteTextView textView2 = (AutoCompleteTextView) findViewById(R.id.autocomplete_Profile);
		textView2.setAdapter(adapter2);
	}

	/**
	 * Block an incomming call 
	 */
	public void blockCall() {
		System.out.println("Block is running");
		PhoneStateListener mPhoneStateListener = new PhoneStateListener() {

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				System.out.println("incomming call from :" + incomingNumber);
				if (state == TelephonyManager.CALL_STATE_RINGING) {

					System.out
							.println("incomming call from :" + incomingNumber);
				} else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

				}

				super.onCallStateChanged(state, incomingNumber);
			}
		};
	}
/**
 * block the porvided contact on the location where the user is currently in
 */
	public void blockHereButton() {
		final Button saveButton = (Button) findViewById(R.id.blockHereButton);

		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LocationDetatil locator = LocationDetatil.getInstance();
				double longtitude = locator.getLongtitude(); // getting
																// references to
																// Edit Text
																// Boxes
				double latitiude = locator.getLatitude();
				AutoCompleteTextView ContactBlocked = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
				AutoCompleteTextView PrfileAdded = (AutoCompleteTextView) findViewById(R.id.autocomplete_Profile);
				String contact, profile;
				contact = ContactBlocked.getText().toString();
				profile = PrfileAdded.getText().toString();

				try {

					if (contact != null && profile != null
							&& DBHelper.isThereString(conatacts, contact)
							&& !contact.equalsIgnoreCase("")
							&& !profile.equalsIgnoreCase("")) {

						if (!DBHelper.addGeoProfile(profile, latitiude,
								longtitude)) {

							Toast.makeText(
									HelloNewActivity.this,
									"Profile Already Exists, Try Different Name"
											.toString(), Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(HelloNewActivity.this,
									"Profile Created Successfully".toString(),
									Toast.LENGTH_SHORT).show(); // Sowing
																// success
							BlockContact(); // message

						}

					} else {
						Toast.makeText(
								HelloNewActivity.this,
								"Please select an existing contact and fill both fields"
										.toString(), Toast.LENGTH_SHORT).show();
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(HelloNewActivity.this,
							"Error while creating profile".toString(),
							Toast.LENGTH_SHORT).show(); // Showing Error Message
				}

			}
		});

	}

	/**
	 * Block the given contact on current positon 
	 */
	public void BlockContact() {
		AutoCompleteTextView ContactBlocked = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		AutoCompleteTextView PrfileAdded = (AutoCompleteTextView) findViewById(R.id.autocomplete_Profile);
		String contact, profile;
		contact = ContactBlocked.getText().toString();
		profile = PrfileAdded.getText().toString();

		if (contact != null && profile != null
				&& DBHelper.isThereString(conatacts, contact)) {
			if (!DBHelper.addBlockContact(contact, profile)) {
				Toast.makeText(
						HelloNewActivity.this,
						"Contact already blocked under this profile".toString(),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(HelloNewActivity.this,
						"Successfully Blocked the Profile".toString(),
						Toast.LENGTH_SHORT).show();
				ContactBlocked.setText("");
				PrfileAdded.setText("");
			}

		} else {
			Toast.makeText(
					HelloNewActivity.this,
					"Please select from Existing profiles and Contacts"
							.toString(), Toast.LENGTH_SHORT).show();
		}

		

	}

}