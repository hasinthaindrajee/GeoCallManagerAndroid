package com.test;

import com.test.R;
import com.test.R.id;
import com.test.R.layout;

import dataLayer.DatabaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProfile extends Activity {
	private EditText Longtitude;	//to get the input of Longtitude of Geo Profile
	private EditText Latitude;		//to get the input of Latitude of Geo Profile
	private EditText ProfName;
	private DatabaseHelper DBHelper = new DatabaseHelper(this);

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profileadder);

		saveButtonAction(this);		//calling button actions on AddProfile UI
		backButtonAction(this);

	}

	/**
	 * Action on Back button on AddProfile UI
	 * finishes the current activity and goes back to main activity
	 */

	public void backButtonAction(final Context context) {

		final Button backButton = (Button) findViewById(R.id.backButton);

		// profName.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
				Intent i = new Intent(context,
						HelloNewActivity.class);
				String name = "example";
				i.putExtra("NAME", name);
				startActivityForResult(i, 0);
			}
		});

	}
	/**
	 * Action on Save button on 
	 * Adds the geo profile info to the database, if sucesses give a successfull message
	 * if there already exists a profile with the same name an error window will be shown.
	 */

	public void saveButtonAction(final Context context) {
		final Button saveButton = (Button) findViewById(R.id.saveButton);

		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//getting inputs from input fields
				Double LongtitudeValue, LatitudeValue;
				Longtitude = (EditText) findViewById(R.id.Longtitude); //getting references to Edit Text Boxes
				Latitude = (EditText) findViewById(R.id.Lattitude);
				ProfName = (EditText) findViewById(R.id.ProfName);
				
				//converting data in input fields to data types
				try {
					LongtitudeValue = Double.valueOf(Longtitude.getText()	//converting Strings to Double values
							.toString());
					LatitudeValue = Double.valueOf(Latitude.getText()
							.toString());
					if (!DBHelper.addGeoProfile(ProfName.getText().toString(),
							LatitudeValue, LongtitudeValue)) {
						
						Toast.makeText(
								AddProfile.this,
								"Profile Already Exists, Try Different Name"
										.toString(), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(AddProfile.this,
								"Profile Created Successfully".toString(),
								Toast.LENGTH_SHORT).show();					//Sowing success message
						finish();
						Intent i = new Intent(context,
								HelloNewActivity.class);
						String name = "example";
						i.putExtra("NAME", name);
						startActivityForResult(i, 0);
					}

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(
							AddProfile.this,
							"Invalied input in Lattitude or Longtitude"
									.toString(), Toast.LENGTH_SHORT).show();  //Showing Error Message
				}


			}
		});

	}
	
	
	

}
