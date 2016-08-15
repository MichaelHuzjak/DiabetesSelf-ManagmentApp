package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
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
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import team5.diabetesself_managmentapp.adapter.BGLAdapter;
import team5.diabetesself_managmentapp.firebase.SignInActivity;
import team5.diabetesself_managmentapp.fragments.AddBGLFragment;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, GoogleApiClient.OnConnectionFailedListener {
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
    static TextView time;
    static TextView low;
    static TextView mid;
    static TextView norm;
    static TextView high;
    static TextView extreme;
    static TextView doc;

    private EditText etDate;
    private EditText etTime;

    private ArrayList<BGLEntryModel> bglEntryList;
    private RecyclerView BGLHolderView;
    private BGLAdapter bglAdapter;

    //private DatabaseHelper db;

    private final String BGL_FRAG_STATE = "BGL_STATE";

    // Initial User Info
    private String mUsername;
    private static final String ANONYMOUS = "anonymous";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mFirebaseDatabaseReference;
    private static final String BGL_CHILD = "bgl";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();  // Must be called first to initialize objects.

        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser == null)
        {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }
        else
        {
            mUsername = mFirebaseUser.getDisplayName();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("users/" + mFirebaseUser.getUid());

        System.out.println("USER ID: " + mFirebaseUser.getUid());
        System.out.println("USER STRING: " + mUsername);

        // Create Helper
        //db = new DatabaseHelper(this,null,null,1);

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
        ButtonsFragment = getFragmentManager().findFragmentById(R.id.FragmentButtons);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        time = (TextView) findViewById(R.id.textViewLastEnteredTime);
        TextView addBGLTextView = (TextView) findViewById(R.id.textViewAddBGL);
        EditText meanEditText = (EditText) findViewById(R.id.editTextMean);
        TextView meanTextView = (TextView) findViewById(R.id.textViewMean);
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

        switch(item.getItemId())
        {
            case R.id.action_settings:
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void saveBglData()
    {
        for(BGLEntryModel entry: AddBGLFragment.bglAdapter.getList())
        {
            System.out.println("Progress: "+entry.getProgress()+" || Date: "+entry.getDate()+" || Time: "+entry.getTime());

            mFirebaseDatabaseReference.child(BGL_CHILD).push().setValue(entry);
        }

        AddBGLFragment.bglAdapter.clearList();
        ShowHome();
    }

    public void ShowHome(){

        Fragment buttons = getFragmentManager().findFragmentById(R.id.FragmentButtons);
        Fragment addBGL = getFragmentManager().findFragmentById(R.id.FragmentBGL);

        AddBGLHelper.showFragment(getFragmentManager(),buttons);
        AddBGLHelper.hideFragment(getFragmentManager(),addBGL);

        AddBGLFragment.bglAdapter.clearList();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        System.out.println("onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
