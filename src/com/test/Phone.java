package com.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.provider.ContactsContract;

public class Phone extends Activity {
	
	
	public String[] getContactsNames(ContentResolver cr) {

		ArrayList<String> TempNames = new ArrayList<String>();

		String whereName = ContactsContract.Data.MIMETYPE + " = ?";
		String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE };
		
		Cursor nameCur = cr.query(ContactsContract.Data.CONTENT_URI, null,
				whereName, whereNameParams,
				ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);

		

		while (nameCur.moveToNext()) {
			String given = nameCur
					.getString(nameCur
							.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
			TempNames.add(given);

			// String family =
			// nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
			// String display =
			// nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
		}
		nameCur.close();

		return StringArrayListToArray(TempNames);
	}

	/**
	 * Converts the given List of strings to an array of String
	 * @param list 
	 * a list of Strings
	 * @return
	 * the list will be coppied to an arry and retruned
	 */
	public String[] StringArrayListToArray(ArrayList<String> list) {
		int counter = 0;
		String[] ContactNames = new String[list.size()];
		while (!list.isEmpty()) {

			ContactNames[counter] = list.remove(0);
			counter++;
		}
		return ContactNames;

	}
	
	/**
	 * Activate the silent mode
	 * @param context
	 */
	
	public void activeSilentMode(Context context){
		System.out.println("Silent mode method is running");
		AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}
	
	/**
	 * activate the default mode
	 * @param context
	 */
	public void activeDefaultMode(Context context){
		System.out.println("silent mode is running atlest trying");
		AudioManager audiomanage = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}

}
