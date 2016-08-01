package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	// PRIVATE CLASS MEMBERS
	private RecyclerView logEventRecyclerView;
	private LogEventAdapter logEventAdapter;
	private List<LogEventModel> logEventModelList = new ArrayList<>();

	// PRIVATE CLASS MEMBERS
	private EditText etDate;
	private EditText etTime;

	//VIEW OBJECTS FOR LAYOUT UI REFERENCE

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logevent);

		// ESTABLISH THE REFERENCES TO LIST
		logEventRecyclerView = (RecyclerView) findViewById(R.id.logEventRecyclerView);
		logEventRecyclerView.setHasFixedSize(true);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
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
		FloatingActionButton fabAddDiet = (FloatingActionButton) findViewById(R.id.fabDiet);
		FloatingActionButton fabAddExer = (FloatingActionButton) findViewById(R.id.fabExer);
		FloatingActionButton fabAddMeds = (FloatingActionButton) findViewById(R.id.fabMeds);

		fabAddDiet.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				int type = LogEventConstant.DIET;
				String content = "Diet";

				logEventModelList.add(new LogEventModel(type , content));
				logEventAdapter.notifyDataSetChanged();
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
			}
		});
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
								et = (EditText)parentView.findViewById(R.id.editTextDietQty);
								et = (EditText)parentView.findViewById(R.id.editTextDateDiet);
								et = (EditText)parentView.findViewById(R.id.editTextTimeDiet);
								continue;
							case LogEventConstant.EXERCISE:
								et = (EditText)parentView.findViewById(R.id.editTextExerciseDescription);
								et = (EditText)parentView.findViewById(R.id.editTextExerciseDuration);
								et = (EditText)parentView.findViewById(R.id.editTextDateExercise);
								et = (EditText)parentView.findViewById(R.id.editTextTimeExercise);
								continue;
							case LogEventConstant.MEDICATION:
								et = (EditText)parentView.findViewById(R.id.editTextMedicationDescription);
								et = (EditText)parentView.findViewById(R.id.editTextMedicationQty);
								et = (EditText)parentView.findViewById(R.id.editTextDateMeds);
								et = (EditText)parentView.findViewById(R.id.editTextTimeMeds);
								continue;
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
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		etTime.setText(sdf.format(cal.getTime()));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
	{
		Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		etDate.setText(sdf.format(cal.getTime()));
	}

}
