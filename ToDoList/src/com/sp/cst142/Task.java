package com.sp.cst142;

public class Task {

	//ID for specific task
	public long id;
	
	//Data fields for a task
	public String name; //name of task; what will appear in the notification
	public String details; //details of the task
	//TODO Implement alarm
	//	There needs to be a variable to hold the time for the alarm to go off and create the notification
	
	//No id constructor
	public Task( String name, String details)
	{
		id = -1;
		this.name = name;
		this.details = details;
	}
	
	//ID constructor
	public Task(long id, String name, String details)
	{
		this(name, details);
		this.id = id;
	}
	
	//Notification maker
	
	//Maybe create getters and setters?
}
