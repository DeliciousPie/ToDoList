package com.sp.cst142;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class SettingsActivity extends Activity{

	//Get access to the database
	private TaskDBHelper db;
	
	//Form controls
	private TextView txtPendingTasks;
	private TextView txtLongitude;
	private TextView txtLatitude;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        this.db = new TaskDBHelper(this);
        //get a handle on every control
        assignControls();
        //Update number of pending tasks
        updateTasks();

        //TODO Update location [lolz]
        //nsa = new NSA(this);
        try{
        Intent intent = new Intent( this, NSA.class);
        startActivityForResult(intent, 0);
        }
        catch(Exception e)
        {
        	Log.d("myProblem", e.getMessage());
        }
        //nsa.mGoogleApiClient.connect();
        //updateLocation();

    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	try{
    	//Get longitude back from NSA
    	String longitude = data.getStringExtra("longitude");
    	txtLongitude.setText(longitude);
    	//Get latitude back from NSA
    	String latitude = data.getStringExtra("latitude");
    	txtLatitude.setText(latitude);
    	}
    	catch(Exception e)
    	{
    		Log.d("myProblem", e.getMessage());
    	}
    }
    
 


	//Update the number of pending tasks
	private void updateTasks() {
		//Get number of tasks
		int numTasks = db.getAllTasks().size();
		//Update textView with that number
		txtPendingTasks.setText("You have " + numTasks + " pending tasks");
		
		
	}

	//Assign all controls to handles so that they can be manipulated
	private void assignControls() {
	txtPendingTasks = (TextView) findViewById(R.id.txtPendingTasks);
	txtLongitude = (TextView) findViewById(R.id.txtLongitude);
	txtLatitude = (TextView) findViewById(R.id.txtLattitude);
		
	}
}
