package com.sp.cst142;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener
{

	//The Task Database
	private TaskDBHelper db;
	
	//get a handle on the controls
	private Spinner taskSpinner;
	private EditText taskName;
	private EditText taskDetails;
	private EditText taskId;
	//TODO Get the alarm thing
	//Buttons will have their onClick handled in the XML
	
	//List of tasks
	private List<Task> tasks;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get a handle on every control
        assignControls();
        
        this.db = new TaskDBHelper(this);
        //Get all the tasks from the database
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent intent = new Intent( this, SettingsActivity.class);
        	startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    //Assign all the controls so that they can be manipulated
    private void assignControls()
    {
    	taskSpinner = (Spinner) findViewById(R.id.taskSpinner);
    	taskName = (EditText) findViewById(R.id.txtTaskName);
    	taskDetails = (EditText) findViewById(R.id.txtTaskDetails);
    	taskId = (EditText) findViewById(R.id.txtTaskId);
    	taskId.setEnabled(false); //This is not an editable field
    }
    
    //Get DB data
    private void refreshData()
    {
    	this.tasks = db.getAllTasks();
    	taskSpinner = (Spinner) findViewById(R.id.taskSpinner);
    	
    	SpinnerAdapter adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_dropdown_item_1line, this.tasks);
    	
    	taskSpinner.setAdapter(adapter);
    }
    
    //Clear all the EditText views so that new data can be entered
    public void clearEditTextBoxes()
    {
    	taskName.setText("");
    	taskDetails.setText("");
    	taskId.setText("");
    	//TODO Add alarm stuff here
    }
    
    // save button click handler
    public void saveClicked(View actionView)
    {
    	//If ID is empty it is a new Task
    	if( taskId.getText().length() > 0 )
    	{
    		updateTask();
    	}
    	//If ID exists it is updating an existing task
    	else
    	{
    		newTask();
    	}
    }
    //new button click handler
    public void newClicked(View actionView)
    {
    	clearEditTextBoxes();
    }
    
    //delete button click handler
    public void deleteClicked(View actionView)
    {
    	if( taskId.getText().length() > 0 )
    	{
    		deleteTask();
    	}
    }
    
    //Update existing task
    private void updateTask()
    {
    	//TODO Add alarm
    	//create a new task with the same ID
    	Task task = new Task(Long.parseLong(taskId.getText().toString()), taskName.getText().toString(), taskDetails.getText().toString()); 
    	db.updateTask(task); //put updated task into DB
    	//Refresh all data
    	refreshData();
    }
    
    //Create a brand new task
    private void newTask()
    {
    	//TODO add alarm
    	Task task = new Task(taskName.getText().toString(), taskDetails.getText().toString());
    	db.createTask(task);
    	//TODO Tie notification to an alarm
    	//Create notification
    	Intent notifyIntent = new Intent(this, TaskNotification.class);
    	notifyIntent.putExtra("title", task.name); //add title name
    	notifyIntent.putExtra("id", (int) task.id); //add id
    	startActivity(notifyIntent); //create the intent
    	
    	refreshData();
    }
    
    //Delete existing task
    private void deleteTask()
    {
    	Task task = new Task(Long.parseLong(taskId.getText().toString()), taskName.getText().toString(), taskDetails.getText().toString());
    	db.deleteTask(task); //Destroy task with nuclear powered method
    	//Refresh all data
    	refreshData();
    }
    
    //TODO Maybe add onActivityResult()??

    
    ///////////// AdapterView.OnItemSelectedListener methods

    @Override
    protected void onStart()
    {
    	taskSpinner.setOnItemSelectedListener(this);
    	
    	super.onStart();
    }
    
	@Override
	public void onItemSelected(AdapterView<?> parent, View actionView, int position,
			long id) {
		//Create a task from the selected task
		Task task = this.tasks.get(position);
		//Set text boxes to selected task's properties
		taskId.setText( Long.toString(task.id) );
		taskName.setText( task.name );
		taskDetails.setText( task.details );
		//TODO Add alarm
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
		
	}
}
