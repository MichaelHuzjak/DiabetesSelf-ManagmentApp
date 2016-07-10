package team5.diabetesself_managmentapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * Created by Joshua on 7/7/2016.
 * Activity class for adding diet, exercise and medication event entries.
 */
public class AddActivity extends AppCompatActivity {

	//VIEW OBJECTS FOR LAYOUT UI REFERENCE
	private EditText DietET;
	private EditText ExerET;
	private EditText MedsET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);

		// ESTABLISH THE REFERENCES TO INPUT ELEMENTS
		DietET = (EditText) findViewById(R.id.editTextDiet);
		ExerET = (EditText) findViewById(R.id.editTextExer);
		MedsET = (EditText) findViewById(R.id.editTextMeds);

		DietET.setVisibility(View.INVISIBLE);
		ExerET.setVisibility(View.INVISIBLE);
		MedsET.setVisibility(View.INVISIBLE);
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

	public void onCheckboxClicked(View view) {
		// Is the view now checked?
		boolean checked = ((CheckBox) view).isChecked();

		// Check which checkbox was clicked
		switch(view.getId()) {
			case R.id.checkBoxDiet:
				if (checked) {
					DietET.setVisibility(View.VISIBLE);
				}
				else {
					DietET.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.checkBoxExer:
				if (checked){
					ExerET.setVisibility(View.VISIBLE);
				}
				else {
					ExerET.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.checkBoxMeds:
				if (checked){
					MedsET.setVisibility(View.VISIBLE);
				}
				else {
					MedsET.setVisibility(View.INVISIBLE);
				}
				break;

		}
	}
}
