package com.sp.cst142;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "todolist.db";
	public static final int VERSION_NUMBER = 1;
	public static final String TABLE_NAME = "task";
	
	public static final String ID_COL_NAME = "_id";
	public static final String TASKNAME_COL_NAME = "name";
	public static final String DETAILS_COL_NAME = "details";
	public static final String ALARM_COL_NAME = "alarm";
	
	
	
	public TaskDBHelper(Context context) {
		super(context, DB_NAME, null, VERSION_NUMBER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Add alarm column
		String createTableSQL = "CREATE TABLE " + TABLE_NAME + 
				"(" + 
				ID_COL_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
				TASKNAME_COL_NAME + " TEXT NOT NULL, " + 
				DETAILS_COL_NAME + " TEXT NOT NULL" +
				 ");";
		db.execSQL(createTableSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Implement me later
		
	}
	
	//Create a new task in the DB
	public long createTask( Task task)
	{
		ContentValues columnValues = new ContentValues();
		
		columnValues.put(TASKNAME_COL_NAME, task.name);
		columnValues.put(DETAILS_COL_NAME, task.details);
		//TODO create alarm
		
		//Open database
		SQLiteDatabase db = this.getWritableDatabase();
		long id = db.insert(TABLE_NAME, null, columnValues);
		task.id = id;
		return id;
	}
	
	//return all tasks
	public List<Task> getAllTasks()
	{
		//TODO Add alarm
		String[] fields = {
				ID_COL_NAME,
				TASKNAME_COL_NAME,
				DETAILS_COL_NAME
		};
		
		SQLiteDatabase db = this.getReadableDatabase();
		//Get a cursor to move through the results
		Cursor results = db.query(TABLE_NAME, fields, null, null, null, null, ID_COL_NAME + " ASC");
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		//make sure there are results
		if( results != null && results.moveToFirst() )
		{
			//There is at least one record so do loop away
			do{
				Task task = new Task(
						results.getLong(0),
						results.getString(1),
						results.getString(2)
						);
				tasks.add(task);
			}while(results.moveToNext());
		}
		db.close();
		return tasks;
	}
	
	//clear DB
	public void clearDatabase()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		String dropSQL = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(dropSQL);
		this.onCreate(db);
		db.close();
	}
	//Update an existing task
	public boolean updateTask(Task task)
	{
		ContentValues values = new ContentValues();
		values.put(TASKNAME_COL_NAME, task.name);
		values.put(DETAILS_COL_NAME, task.details);
		//TODO Add alarm
		
		String[] args = {Long.toString(task.id)};
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		int recordsModified = db.update(TABLE_NAME, values, ID_COL_NAME + "=?", args);
		
		db.close();
		return recordsModified > 0;
	}
	//Delete existing task
	public boolean deleteTask( Task task)
	{
		String deleteQuery = ID_COL_NAME + " = ?";
		String[] args = {Long.toString(task.id)};
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		int recordsDeleted = db.delete(TABLE_NAME, deleteQuery, args);
		
		return recordsDeleted > 0;
	}
	

}
