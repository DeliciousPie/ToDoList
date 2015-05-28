package com.sp.cst142;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class TaskNotification extends Activity{

	//Title of the notification (the Task's title(
	private String title;
	//Unique notification ID (will match it's Task's id)
	private int notificationId;
	
//	public TaskNotification(String title, int id)
//	{
//		this.title = title;
//		notificationId = id;
//	}
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		title = i.getStringExtra("title");
		notificationId = i.getIntExtra("id", -1);
		createNotification();
		
	};
	
	//Create the notification
	protected void createNotification()
	{
		//Get NotificationManager
		NotificationManagerCompat notifyMgr = NotificationManagerCompat.from(this); 
		//Build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		//Set icon
		builder.setSmallIcon(R.drawable.ic_launcher);
		//Set ticker (just the title again. This is a very simple notification)
		builder.setTicker(title);
		//Set time event occured
		builder.setWhen(System.currentTimeMillis());
		//Set title
		builder.setContentTitle(title);
		//Set text
    	//builder.setContentText(title);
		
		//Create a pending intent. For now it will just go back to the main activity. Future releases will open up actual task
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		//Add pending intent to the notification
		builder.setContentIntent( pIntent );
		
		//get actual instance of notification
		Notification n = builder.build();
		
		//Send notification to the manager
		notifyMgr.notify( notificationId, n);	
		
		//Close activity ////There is no setResult call because nothing is coming back
		finish();
	}
}
	

