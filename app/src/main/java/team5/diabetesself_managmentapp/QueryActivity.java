package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class QueryActivity extends AppCompatActivity {
    private Date mStartDate;
    private Date mEndDate;
    static final int DIALOG_ID = 0;
    boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        Calendar cal = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        // end date
        isStart = false;
        mEndDate = c.getTime();
        c.setTime(mEndDate);
        ChangeDate(c.getTime());
        // start date
        isStart = true;
        c.add(Calendar.DATE, -7);
        ChangeDate(c.getTime());
        showDialogueDatePicker();

        // Basic graph code from GraphView, Open Source Android Graph library
        GraphView mGraph = (GraphView) findViewById(R.id.graphExample);
        DataPoint[] data = new DataPoint[30];
        for(int i = 0;i<30;i++){
            data[i] = new DataPoint(i,100 + (i*3));
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(data);
        series.setDrawValuesOnTop(true);
        series.setSpacing(1);
        mGraph.addSeries(series);

        mGraph.setTitle("Monthly BGL Example");
        String[] labels = new String[30];
        for(int i=0;i<30;i++){
            labels[i] = "7/" + i;
        }

        // Possible Deprecated code, used for labels. Causes errors and formatting issues when using viewport's scaling.

        // StaticLabelsFormatter formatter = new StaticLabelsFormatter(mGraph);
        //formatter.setHorizontalLabels(labels);
        //mGraph.getGridLabelRenderer().setLabelFormatter(formatter);


        mGraph.getViewport().setScrollable(true);
        mGraph.getViewport().setXAxisBoundsManual(true);
        mGraph.getViewport().setMinX(0);
        mGraph.getViewport().setMaxX(5);




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

    void showDialogueDatePicker(){
        Button buttonStart = (Button) findViewById(R.id.buttonStartDate);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = true;
                showDialog(DIALOG_ID);
            }
        });
        Button buttonEnd = (Button) findViewById(R.id.buttonEndDate);
        buttonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = false;
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    public Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            Calendar c = Calendar.getInstance();
            if(isStart){
                c.setTime(mStartDate);
            }else{
                c.setTime(mEndDate);
            }
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            return new DatePickerDialog(this,datepickerlistener,year,month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datepickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year,month,day);
            ChangeDate(c.getTime());
        }
    };

    private void ChangeDate(Date date){

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH)+1;
        int year = c.get(Calendar.YEAR);
        String dateString = month + "/" + day + "/" + year;
        if(isStart){
            mStartDate = date;
            Button buttonStart = (Button) findViewById(R.id.buttonStartDate);
            buttonStart.setText(dateString);
        }else{
            mEndDate = date;
            Button buttonEnd = (Button) findViewById(R.id.buttonEndDate);
            buttonEnd.setText(dateString);
        }
    }


}
