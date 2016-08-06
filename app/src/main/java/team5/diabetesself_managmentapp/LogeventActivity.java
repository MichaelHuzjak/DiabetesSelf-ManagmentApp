package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import team5.diabetesself_managmentapp.adapter.LogEventAdapter;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.model.LogEventModel;
import team5.diabetesself_managmentapp.utils.LogEventConstant;

/**
 * Created by Joshua on 7/7/2016.
 * Activity class for adding diet, exercise and medication event entries.
 */
public class LogeventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

	private final String KEY_RECYCLER_STATE = "recycler_state";
	private final String KEY_LIST_STATE = "list_state";

	// PRIVATE CLASS MEMBERS FOR UI
	private RecyclerView logEventRecyclerView;
	private LogEventAdapter logEventAdapter;
	private List<LogEventModel> logEventModelList;
	private LinearLayoutManager linearLayoutManager;

	// PRIVATE CLASS MEMBERS
	private EditText etDate;
	private EditText etTime;

	// DATABASE RELATED MEMEBRS
	private DatabaseHelper myEventLogDB;

	//VIEW OBJECTS FOR LAYOUT UI REFERENCE

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logevent);

		// GET THE CONTEXT OF THIS ACTIVITY
		Context activityContext = getApplicationContext();

		// MUST SET THE CONTEXT, OTHERWISE ITS NULL WILL CRASH
		myEventLogDB = new DatabaseHelper(activityContext, null, null, 1);

		// ESTABLISH THE REFERENCES TO LIST
		logEventRecyclerView = (RecyclerView) findViewById(R.id.logEventRecyclerView);
		logEventRecyclerView.setHasFixedSize(true);

		if(savedInstanceState == null || !savedInstanceState.containsKey(KEY_LIST_STATE))
		{
			logEventModelList = new ArrayList<>();
			linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
		}
		else
		{
			logEventModelList = savedInstanceState.getParcelableArrayList(KEY_LIST_STATE);

			Parcelable listState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
			linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
			linearLayoutManager.onRestoreInstanceState(listState);

		}

		logEventRecyclerView.setLayoutManager(linearLayoutManager);
		logEventRecyclerView.setItemAnimator(new DefaultItemAnimator());

		logEventAdapter = new LogEventAdapter(LogeventActivity.this, logEventModelList);

		// ATTACH RECYCLE VIEW TO ADAPTER
		logEventRecyclerView.setAdapter(logEventAdapter);

		// ENSURE THE LOGEVENT LAYOUT ARE CENTERED IN THE RECYCLE VIEW
		logEventRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

		  @Override
		  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				  int totalWidth = parent.getWidth();
				  int layoutWidth = getResources().getDimensionPixelOffset(R.dimen.logevent_width);
			      int layoutVerticalPad = getResources().getDimensionPixelOffset(R.dimen.logevent_padding);

				  int sidePadding = (totalWidth - layoutWidth) / 2;
			      int abovePadding = layoutVerticalPad / 2;

				  sidePadding = Math.max(0, sidePadding);
			      abovePadding = Math.max(0, abovePadding);

				  outRect.set(sidePadding, abovePadding, sidePadding, abovePadding);
		  }
	  });

		// ESTABLISH THE REFERENCES TO INPUT ELEMENTS
		ImageButton fabAddDiet = (ImageButton) findViewById(R.id.fabDiet);
		ImageButton fabAddExer = (ImageButton) findViewById(R.id.fabExer);
		ImageButton fabAddMeds = (ImageButton) findViewById(R.id.fabMeds);

		fabAddDiet.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				int type = LogEventConstant.DIET;
				String content = "Diet";
				logEventRecyclerView.scrollToPosition(logEventAdapter.getItemCount()-1);
				logEventModelList.add(new LogEventModel(type , content));
				logEventAdapter.notifyDataSetChanged();
				logEventRecyclerView.scrollToPosition(logEventAdapter.getItemCount()-1);

			}
		});

		fabAddExer.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				int type = LogEventConstant.EXERCISE;
				String content = "Exercise";
				logEventModelList.add(new LogEventModel(type , content));
				logEventAdapter.notifyDataSetChanged();
				logEventRecyclerView.scrollToPosition(logEventAdapter.getItemCount()-1);

			}
		});

		fabAddMeds.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				int type = LogEventConstant.MEDICATION;
				String content = "Medication";
				logEventModelList.add(new LogEventModel(type , content));
				logEventAdapter.notifyDataSetChanged();
				logEventRecyclerView.scrollToPosition(logEventAdapter.getItemCount()-1);

			}
		});
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		/* CLOSE THE DATABASE, WILL BE REOPENED IN THE OnCreate()
		   when the activity becomes running again.
		*/
		myEventLogDB.closeDB();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_save:

				// GET THE NUMBER OF LOGEVENT LAYOUTS IN THE RECYCLE VIEW
				int itemCount = logEventAdapter.getItemCount();

				// LOCAL MEMBERS THAT WILL REFERENCE UI ELEMENTS TO GRAB DATA TO BE STORED
				View parentView;
				EditText et;

				System.out.println("Item count: " + itemCount);

				// LOOP FOR EACH ITEM IN THE RECYCLE VIEW TO COLLECT DATA
				for(int i = 0; i < itemCount; i++)
				{
					int logEventType = logEventAdapter.getItemViewType(i);
					parentView = logEventRecyclerView.getLayoutManager().findViewByPosition(i);

					if(parentView != null)
					{
						switch (logEventType) {
							case LogEventConstant.DIET:
								et = (EditText)parentView.findViewById(R.id.editTextDietDescription);
								String description = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextDietQty);
								String value = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextDateDiet);
								String date = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextTimeDiet);
								String time = et.getText().toString();
								makeDbModel(LogEventConstant.DIET, description, value, date, time);
								break;
							case LogEventConstant.EXERCISE:
								et = (EditText)parentView.findViewById(R.id.editTextExerciseDescription);
								description = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextExerciseDuration);
								value = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextDateExercise);
								date = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextTimeExercise);
								time = et.getText().toString();
								makeDbModel(LogEventConstant.EXERCISE, description, value, date, time);
								break;
							case LogEventConstant.MEDICATION:
								et = (EditText)parentView.findViewById(R.id.editTextMedicationDescription);
								description = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextMedicationQty);
								value = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextDateMeds);
								date = et.getText().toString();
								et = (EditText)parentView.findViewById(R.id.editTextTimeMeds);
								time = et.getText().toString();
								makeDbModel(LogEventConstant.MEDICATION, description, value, date, time);
								break;
							default:
								throw new IllegalArgumentException("Unexpected Type");
						}
					}
				}

				// NAVIGATE BACK THE PARENT
				NavUtils.navigateUpFromSameTask(this);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/* WHEN THE USER CLICKS THE DATE BUTTON IN THE UI,
	CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
	VIA THE FRAGMENT MANAGER
	 */
	public void showDatePickerDialog(View v) {

		etDate = (EditText)v.findViewById(v.getId());

		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	/* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
	CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
	VIA THE FRAGMENT MANAGER
	 */
	public void showTimePickerDialog(View v) {

		etTime = (EditText)v.findViewById(v.getId());

		TimePickerFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		Calendar cal = new GregorianCalendar(0, 0, 0, hourOfDay, minute, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss aa");
		etTime.setText(sdf.format(cal.getTime()));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
	{
		Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		etDate.setText(sdf.format(cal.getTime()));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelableArrayList(KEY_LIST_STATE, (ArrayList<? extends Parcelable>) logEventModelList);

		Parcelable listState = linearLayoutManager.onSaveInstanceState();
		outState.putParcelable(KEY_RECYCLER_STATE, listState);
	}

	public void makeDbModel(int type, String description, String value, String date, String time)
	{

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date convertedDate = new Date();

		System.out.println("description: " + description);
		System.out.println("value: " + value);
		System.out.println("date: " + date);
		System.out.println("time: " + time);

		try {
			convertedDate = dateFormat.parse(date + " " + time);
		} catch(ParseException pe){
			// no op
		}

		String dateTime = dateFormat.format(convertedDate);

		System.out.println("dateTime: " + dateTime);

		if(description == null)
		{
			description = "empty";
		}

		int myNum;

		if(value == null)
		{
			myNum = 0;
		}
        else {
			try {
				myNum = Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
				myNum = 0;
			}
		}
		switch(type)
		{
			case LogEventConstant.DIET:
				myEventLogDB.CreateDiet(0, description, myNum, dateTime);
				break;
			case LogEventConstant.EXERCISE:
				myEventLogDB.CreateExercise(0, description, myNum, dateTime);
				break;
			case LogEventConstant.MEDICATION:
				myEventLogDB.CreateMedication(0, description, myNum, dateTime);
				break;
			default:
				throw new IllegalArgumentException("Unexpected Type");
		}

	}
}
