package com.sp.cst142;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
/**
 * 
 * @author Wes Purpose: The NSA object is responsible for determining and
 *         reporting the location of the user
 */
 
public class NSA extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

	public GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		buildGoogleApiClient();
		
	}
	
	
	protected synchronized void buildGoogleApiClient() {
	   try{
		mGoogleApiClient = new GoogleApiClient.Builder(this) //changed from 'this'
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(LocationServices.API)
	        .build();
	    mGoogleApiClient.connect();
	   }
	   catch(Exception e)
	   {
		   Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		   Log.d("problem", e.getMessage());
	   }
	}


	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
        	i = getIntent();
        	i.putExtra("latitude", String.valueOf(mLastLocation.getLatitude()));
        	i.putExtra("longitude", String.valueOf(mLastLocation.getLongitude()));
        	setResult(RESULT_OK, i);
        	finish();
        }
        else
        {
        	Toast.makeText(this, "Last location unknown", Toast.LENGTH_LONG).show();
        }
	}
	


	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		finish();
	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Location connection failed!", Toast.LENGTH_SHORT).show();
       //	i.putExtra("latitude", "Connection Error");
    	//i.putExtra("longitude", "Connection Error");
    	finish();
	}
}
