package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

import team5.diabetesself_managmentapp.fragments.BGLGraphFragment;
import team5.diabetesself_managmentapp.fragments.BGLListFragment;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.MainQueryFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;
import team5.diabetesself_managmentapp.firebase.SignInActivity;


public class QueryActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, GoogleApiClient.OnConnectionFailedListener {

    static final int DIALOG_ID = 0;
    boolean isStart;
    //private DatabaseHelper db;
    private BGLListFragment ListFragment;
    private MainQueryFragment MainFragment;
    private android.app.Fragment CurrentFragment;
    private EditText etDate;
    private EditText etTime;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    private static final String ANONYMOUS = "anonymous";
    private DatabaseReference mFirebaseDatabaseReference;
    private static final String BGL_CHILD = "bgl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("QueryActivity: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        //System.out.println("USER ID: " + mFirebaseUser.getUid());
        //System.out.println("USER STRING: " + mUsername);

        //db = new DatabaseHelper(this,null,null,1);
        //DisplayBGL();


        System.out.println("QueryActivity: starting ShowFragment for MainQueryFragment");
        MainFragment = (MainQueryFragment) getFragmentManager().findFragmentById(R.id.MainQueryFragment);
        CurrentFragment = MainFragment;
        ShowFragment(getFragmentManager(), MainFragment,true);

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
                //NavUtils.navigateUpFromSameTask(this);
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

    public void ListBGL(){
        ShowFragment(getFragmentManager(),ListFragment,true);
        ShowFragment(getFragmentManager(),MainFragment,false);
    }

//    public List<BGL> GetCompleteBGL(){
//        return db.GetAllBGL();
//    }

    public void ClearDatabase(){
        //db.ClearDatabase();
    }

    public void BGLListShowDatePickerDialog(View v) {

        etDate = (EditText)v.findViewById(R.id.EditTextBGLListDate);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    public void BGLListShowTimePickerDialog(View v) {

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
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        etDate.setText(sdf.format(cal.getTime()));
    }
    public void UpdateBGL(BGL bgl){
        db.UpdateBGL(bgl);
    }


    public void ShowMain(){
        ShowFragment(getFragmentManager(),MainFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        System.out.println("Here");
    }

    private void ShowFragment(FragmentManager fm, android.app.Fragment fr, boolean show){
        System.out.println("QueryActivity: ShowFragment()");
        FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        if(!show)
            ft.hide(fr);
        else
            ft.show(fr);
        if(show)
            CurrentFragment = fr;

        ft.commit();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        System.out.println("onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
