package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import team5.diabetesself_managmentapp.fragments.BGLListFragment;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.MainQueryFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;


public class QueryActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    static final int DIALOG_ID = 0;
    boolean isStart;
    private DatabaseHelper db;
    private BGLListFragment ListFragment;
    private MainQueryFragment MainFragment;

    EditText etDate;
    EditText etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        db = new DatabaseHelper(this,null,null,1);
        //DisplayBGL();

         MainFragment = (MainQueryFragment) getFragmentManager().findFragmentById(R.id.MainQueryFragment);
        QueryHelper.ShowFragment(getFragmentManager(), MainFragment,true);

        ListFragment = (BGLListFragment) getFragmentManager().findFragmentById(R.id.BGLListFragment);
        QueryHelper.ShowFragment(getFragmentManager(), ListFragment,false);


    }
    public void old(){
        Calendar cal = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        // end date
        isStart = false;
        //mEndDate = c.getTime();
        TextView etDate;
        TextView etTime;

        // Basic graph code from GraphView, Open Source Android Graph library
        GraphView mGraph = (GraphView) findViewById(R.id.graphExample);
        DataPoint[] data = new DataPoint[30];
        for(int i = 0;i<30;i++){
            data[i] = new DataPoint(i,100 + (i*3));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);
        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>(data);

        //series.setDrawValuesOnTop(true);
        //series.setSpacing(1);

        mGraph.addSeries(series);
        mGraph.addSeries(series2);


        mGraph.setTitle("Monthly BGL Example");
        String[] labels = new String[30];
        for(int i=0;i<30;i++){
            labels[i] = "7/" + i;
        }

        // Possible Deprecated code, used for labels. Causes errors and formatting issues when using viewport's scaling.


        mGraph.getViewport().setScrollable(true);
        mGraph.getViewport().setXAxisBoundsManual(true);
        //mGraph.getViewport().setMinX(0);
        //mGraph.getViewport().setMaxX(5);

//        <RelativeLayout
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content">
//
//        <com.jjoe64.graphview.GraphView
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        android:id="@+id/graphExample" />
//
//        </RelativeLayout>
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

    public void DisplayBGL(){
        String s = "";
        for(BGL b: db.GetAllBGL()){
            s+= b.get_id() + " " + b.get_date().toString() + " - " + b.get_value() + "\n";
        }
        //TextView text = (TextView)findViewById(R.id.bglDisplay);
        //text.setText(s);
    }

    public void ListBGL(){
        QueryHelper.ShowFragment(getFragmentManager(),ListFragment,true);
        QueryHelper.ShowFragment(getFragmentManager(),MainFragment,false);
    }
    public List<BGL> GetCompleteBGL(){
        return db.GetAllBGL();
    }
    public void ClearDatabase(){
        db.ClearDatabase();
    }
    public void BGLListShowDatePickerDialog(View v) {

        //etDate = (EditText)v.findViewById(R.id.EditTextBGLDate);
        etDate = (EditText)v.findViewById(R.id.EditTextBGLListDate);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    public void BGLListShowTimePickerDialog(View v) {

        //etTime = (EditText)v.findViewById(R.id.EditTextBGLTime);
        etTime = (EditText)v.findViewById(R.id.EditTextBGLListTime);

        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        Calendar cal = new GregorianCalendar(0, 0, 0, hourOfDay, minute, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:aa");
        etTime.setText(sdf.format(cal.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {

        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        etDate.setText(sdf.format(cal.getTime()));
    }
    public void UpdateBGL(BGL bgl){
        db.UpdateBGL(bgl);
    }



}
