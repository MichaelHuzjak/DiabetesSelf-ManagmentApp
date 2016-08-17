package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team5.diabetesself_managmentapp.adapter.BGLAdapter;
import team5.diabetesself_managmentapp.fragments.AddBGLFragment;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private LinearLayoutManager linearLayoutManager;

    private GoogleApiClient client;
    private Toolbar toolbar;
    private AddBGLFragment AddBGLFragment;
    private Fragment ButtonsFragment;
    static ArcProgress arc;
    static EditText meanET, varianceET;
    static TextView meanTextView, addBGLTextView,time, low, mid, norm, high, extreme, doc;

    private EditText etDate;
    private EditText etTime;

    private ArrayList<BGLEntryModel> bglEntryList;
    private RecyclerView BGLHolderView;
    private BGLAdapter bglAdapter;

    private DatabaseHelper db;

    private int average = 0;

    private final String BGL_FRAG_STATE = "BGL_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();  // Must be called first to initialize objects.

        // Create Helper
        db = new DatabaseHelper(this,null,null,1);

        if(savedInstanceState==null)
            AddBGLHelper.hideFragment(getFragmentManager(), AddBGLFragment);
        else{
            if(savedInstanceState.getBoolean(BGL_FRAG_STATE)){
                AddBGLHelper.hideFragment(getFragmentManager(), ButtonsFragment);
                AddBGLHelper.showFragment(getFragmentManager(), AddBGLFragment);
            }else{
                AddBGLHelper.hideFragment(getFragmentManager(), AddBGLFragment);
            }
        }

        setSupportActionBar(toolbar);

        //sets the arc latest bgl entry, mean and variance.
        UpdateMainScreenValues();


        Button buttonAddBGLEvent = (Button)findViewById(R.id.ButtonShowBGLFragment);
        buttonAddBGLEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBGLHelper.hideFragment(getFragmentManager(), ButtonsFragment);
                AddBGLHelper.showFragment(getFragmentManager(), AddBGLFragment);
            }
        });


        Button buttonLogEvent = (Button) findViewById(R.id.buttonLogEvent);
        buttonLogEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LogeventActivity.class));
            }
        });

        Button buttonQuery = (Button) findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QueryActivity.class));
            }
        });

        Button buttonPrescription = (Button) findViewById(R.id.buttonPrescription);
        buttonPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PrescribeActivity.class));
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        for(Category cat:db.GetCategories()){
            System.out.println("Cat:"+cat.getId());
        }
    }

    @Override
    public void onBackPressed(){
        if(AddBGLFragment.isVisible())
            ShowHome();
    }
    public void UpdateMainScreenValues(){
        BGL latestBGL = getLatestBGL();
        int differenceSum = 0;
        int variance = 0;
        if(latestBGL!=null){
            AddBGLHelper.AddNewBGL(latestBGL.get_value());
            time.setText(latestBGL.GetFormatedDate());
        }
        String mean = ""+average+" mg/dL";

        List<BGL> bglList = db.GetAllBGL();
        int listSize = bglList.size();

        meanET.setText(mean);
        for(BGL bgl: db.GetAllBGL()){
            differenceSum += Math.pow(bgl.get_value()-average, 2);
        }
        if(listSize-1 != 0)
            variance = differenceSum/(listSize-1);

        String var = ""+variance;
        varianceET.setText(var);

    }
    private BGL getLatestBGL(){
        int sum = 0;


        BGL latestBGL = null;
        List<BGL> bglList = db.GetAllBGL();
        int listSize = bglList.size();

        for(BGL bgl: bglList){
            sum += bgl.get_value();
            if(latestBGL==null || bgl.get_date().compareTo(latestBGL.get_date())>0) {
                latestBGL = bgl;
            }
        }
        if(listSize!=0)
            average = sum/listSize;
        return latestBGL;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save the fragment's current visibility state
        savedInstanceState.putBoolean(BGL_FRAG_STATE, AddBGLFragment.isVisible());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        // Disable visibility of save menu item on the main activity
        MenuItem saveItem = menu.findItem(R.id.action_save);
        saveItem.setVisible(false);
        saveItem.setEnabled(false);

        return true;
    }

    private void initObjects(){
        AddBGLFragment = (AddBGLFragment) getFragmentManager().findFragmentById(R.id.FragmentBGL);
        ButtonsFragment = (Fragment) getFragmentManager().findFragmentById(R.id.FragmentButtons);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        time = (TextView) findViewById(R.id.textViewLastEnteredTime);
        addBGLTextView = (TextView) findViewById(R.id.textViewAddBGL);
        meanET = (EditText) findViewById(R.id.editTextMean);
        varianceET = (EditText) findViewById(R.id.editTextVariant);
        meanTextView = (TextView) findViewById(R.id.textViewMean);
        arc = (ArcProgress) findViewById(R.id.arc_progress);
        low =(TextView)findViewById(R.id.textViewLowNotice);
        mid = (TextView) findViewById(R.id.textViewMediumNotice);
        norm = (TextView) findViewById(R.id.textViewNormalNotice);
        high = (TextView) findViewById(R.id.textViewHighNotice);
        extreme = (TextView) findViewById(R.id.textViewVeryHighNotice);
        doc = (TextView) findViewById(R.id.textViewDoctorNotice);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://team5.diabetesself_managmentapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://team5.diabetesself_managmentapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /* WHEN THE USER CLICKS THE DATE BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    public void MainShowDatePickerDialog(View v) {

        etDate = (EditText)v.findViewById(R.id.EditTextBGLDate);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    public void MainShowTimePickerDialog(View v) {

        etTime = (EditText)v.findViewById(R.id.EditTextBGLTime);

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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        etDate.setText(sdf.format(cal.getTime()));
    }

    public void AddBGLtoDatabase(){
        //AddBGLFragment frag = (AddBGLFragment) getFragmentManager().findFragmentById(R.id.FragmentBGL);

        for(BGLEntryModel entry: AddBGLFragment.bglAdapter.getList()){
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String dateString = entry.getDate() + " " +  entry.getTime();
            System.out.println("Progress: "+entry.getProgress()+" || Date: "+entry.getDate()+" || Time: "+entry.getTime());
            Date date;
            try {
                db.CreateBGL(format.parse(dateString),entry.getProgress());
            }catch(ParseException e){
                e.printStackTrace();
            }
        }

        AddBGLFragment.bglAdapter.clearList();

        ShowHome();
    }

    public void ShowHome(){

        Fragment buttons = (Fragment)getFragmentManager().findFragmentById(R.id.FragmentButtons);
        Fragment addBGL = (Fragment)getFragmentManager().findFragmentById(R.id.FragmentBGL);
        Fragment bgl_frag = (Fragment)getFragmentManager().findFragmentByTag("BGL_FRAGMENT");
//                getFragmentManager().beginTransaction().remove(bgl_frag).commit();
        AddBGLHelper.hideFragment(getFragmentManager(),addBGL);
        AddBGLHelper.showFragment(getFragmentManager(),buttons);

        AddBGLFragment.bglAdapter.clearList();
    }
}
