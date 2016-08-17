package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import team5.diabetesself_managmentapp.adapter.PrescriptionAdapter;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;

/**
 * Created by Joshua on 7/10/2016.
 * Activity class for adding prescriptions (diet, exercise and medication) entries.
 */
public class PrescribeActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
	DatabaseHelper db;
	NotificationHelper nh;
	private EditText etDate, etTime;
	private String catType;

	RecyclerView prescRecyclerView;
	PrescriptionAdapter prescAdapter;
	ArrayList<Prescription> prescList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prescribe_activity);

		prescRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewPresc);
		//etDate = (EditText)findViewById(R.id.editTextPrescDate);
		etTime = (EditText)findViewById(R.id.editTextPrescTime);
		prescList = new ArrayList<>();
		prescAdapter = new PrescriptionAdapter(this, prescList);

		prescRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		prescRecyclerView.setAdapter(prescAdapter);
		prescAdapter.notifyDataSetChanged();
		prescRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewPresc);
		db = new DatabaseHelper(this,null,null,1);
		nh = new NotificationHelper(this);
	}

	public void onAddPrescClicked(View v){
		prescList.add(new Prescription());
		prescAdapter.notifyItemInserted(prescAdapter.getItemCount());
	}
	public void onSetPrescClicked(View v){
		for(Prescription p: prescList){
			if(p!=null)

				System.out.println("Category: " + p.get_categoryId());
			System.out.println("Description: " + p.get_description());
			System.out.println("Repeat: " + p.get_repeat());
			System.out.println("----------------------------");
			db.CreatePrescription(p.get_categoryId(),p.get_description(),p.get_repeat());
		}
		nh.EnsureNotifications(db.GetAllPrescriptions());
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
				//Should save data in EditText fields

				//Nav back to parent
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

}
