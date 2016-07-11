package team5.diabetesself_managmentapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * Created by Joshua on 7/10/2016.
 * Activity class for adding prescriptions (diet, exercise and medication) entries.
 */
public class PrescribeActivity extends AppCompatActivity {

	//VIEW OBJECTS FOR LAYOUT UI REFERENCE
	private LinearLayout prescribeDietLL;
	private LinearLayout prescribeExerLL;
	private LinearLayout prescribeMedsLL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);

		// ESTABLISH THE REFERENCES TO LAYOUT
		prescribeDietLL = (LinearLayout) findViewById(R.id.layoutPrescribeDiet);
		prescribeExerLL = (LinearLayout) findViewById(R.id.layoutPrescribeExer);
		prescribeMedsLL = (LinearLayout) findViewById(R.id.layoutPrescribeMeds);

		prescribeDietLL.setVisibility(View.INVISIBLE);
		prescribeExerLL.setVisibility(View.INVISIBLE);
		prescribeMedsLL.setVisibility(View.INVISIBLE);
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
			case R.id.checkBoxPrescribeDiet:
				if (checked) {
					prescribeDietLL.setVisibility(View.VISIBLE);
				}
				else {
					prescribeDietLL.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.checkBoxPrescribeExer:
				if (checked){
					prescribeExerLL.setVisibility(View.VISIBLE);
				}
				else {
					prescribeExerLL.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.checkBoxPrescribeMeds:
				if (checked){
					prescribeMedsLL.setVisibility(View.VISIBLE);
				}
				else {
					prescribeMedsLL.setVisibility(View.INVISIBLE);
				}
				break;

		}
	}
}
