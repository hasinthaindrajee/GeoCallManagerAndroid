package dataLayer;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class Test extends Activity{
	SQLiteDatabase db;
	DatabaseHelper DBHelper;
	
	//@test
	
	public void testDBAdding(){
		
		DBHelper = new DatabaseHelper(this);
		db = DBHelper.getWritableDatabase();
		if(DBHelper.addBlockContact("Amila", "Home")){
			
			System.out.println("Successfully added");
		}else  {
			System.out.println("Error while adding");
		}
		
if(DBHelper.addBlockContact("Amila", "Home")){
			
			System.out.println("Successfully added");
		}else  {
			System.out.println("Error while adding");
		}
		System.out.println(DBHelper.addBlockContact("Amila", "Home"));
		DBHelper.addBlockContact("Amila", "Home");
		DBHelper.addBlockContact("Amila", "Colombo");
		DBHelper.addBlockContact("Hasintha", "Galle");
		DBHelper.addBlockContact("Amila", "Colombo");
		DBHelper.addGeoProfile("Moratuwa", 1000.252538, 500.25);
		DBHelper.addGeoProfile("University of Colombo", 1000.252538, 500.25);
		DBHelper.addGeoProfile("Moratuwa", 1000.252538, 500.25);
		DBHelper.addGeoProfile("Galle", 1000.252538, 500.25);
	}

}
