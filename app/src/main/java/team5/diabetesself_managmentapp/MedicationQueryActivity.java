package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.MainMedicationFragment;
import team5.diabetesself_managmentapp.fragments.MedGraphFragment;
import team5.diabetesself_managmentapp.fragments.MedStatFragment;
import team5.diabetesself_managmentapp.fragments.MedicationListFragment;
import team5.diabetesself_managmentapp.fragments.MedicationResultFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;
import team5.diabetesself_managmentapp.model.LogEventModel;


public class MedicationQueryActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, GoogleApiClient.OnConnectionFailedListener {

    static final int DIALOG_ID = 0;
    boolean isStart;
    private DatabaseHelper db;
    private MedicationListFragment ListFragment;
    private MainMedicationFragment MainFragment;
    private MedGraphFragment GraphFragment;
    private MedicationResultFragment ResultFragment;
    private MedStatFragment StatsFragment;
    private android.app.Fragment CurrentFragment;
    private EditText etDate;
    private EditText etTime;
    private ArrayList<LogEventModel> currentList;
    private ArrayList<String> listID;

    private int counter;
    private boolean changeFlag;
    private Date changeDate;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private static final String ANONYMOUS = "anonymous";
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mFirebaseDatabaseReference;
    private static final String MEDICATION_CHILD = "medication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("BGLQueryActivity: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicationquery);
        //db = new DatabaseHelper(this,null,null,1);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("MedicationQueryActivity USER ID: " + mFirebaseUser.getUid());
        System.out.println("MedicationQueryActivity USER STRING: " + mUsername);

        currentList = new ArrayList<>();
        listID = new ArrayList<>();

        counter = 0;
        changeFlag = false;
        changeDate = new Date();

        readMedicationData();

        System.out.println("MedicationQueryActivity: starting ShowFragment for MainMedicationFragment");
        MainFragment = (MainMedicationFragment) getFragmentManager().findFragmentById(R.id.MainMedicationFragment);
        CurrentFragment = MainFragment;
        ShowFragment(getFragmentManager(), MainFragment,true);

        System.out.println("MedicationQueryActivity: starting ShowFragment for MedicationResultFragment");
        ResultFragment = (MedicationResultFragment) getFragmentManager().findFragmentById(R.id.MedicationResultFragment);
        ShowFragment(getFragmentManager(), ResultFragment,false);

    }

    private void readMedicationData()
    {
        System.out.println("MedicationQueryActivity: readMedicationData()");

        DatabaseReference ref = mFirebaseDatabaseReference.child(MEDICATION_CHILD);

        // Add all polls in ref as rows
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot child : snapshot.getChildren())
                {
                    LogEventModel logEventModel = child.getValue(LogEventModel.class);
                    System.out.println("MedicationQueryActivity: Key: " + child.getKey() + " Diet: " + logEventModel.getDescription() + " Date: " + logEventModel.getDate() + " Time: " +  logEventModel.getTime());

                    currentList.add(logEventModel);
                    listID.add(child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("MedicationQueryActivity: readMedicationData() returned.");
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
//            case R.id.sign_out_menu:
//                mFirebaseAuth.signOut();
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                mUsername = ANONYMOUS;
//                startActivity(new Intent(this, SignInActivity.class));
//                return true;
            case R.id.action_save:
                //Should save data in EditText fields

                //Nav back to parent
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ListDiet(){
        ShowFragment(getFragmentManager(),ListFragment,true);
        ShowFragment(getFragmentManager(),MainFragment,false);
    }
    public List<LogEventModel> GetList()
    {
        System.out.println("MedicationQueryActivity: GetList()");
        return currentList;
    }

    private void GetCompleteMedication()
    {
        System.out.println("MedicationQueryActivity: GetCompleteMedication()");
        //return db.GetAllBGL();
        //currentList = db.GetAllMedication();
    }

    private void GetBefore(Date date)
    {
        System.out.println("MedicationQueryActivity: GetBefore(): date " + date);
        //currentList = db.GetAllMedicationBeforeDate(date);

        ArrayList<LogEventModel> before = new ArrayList<>();
        ArrayList<String> beforelist = new ArrayList<>();
        List<Entry> dataToSet = new ArrayList<>();

        for(int i = 0; i < currentList.size(); i++)
        {
            if(ConvertToDate(currentList.get(i).getDate()).before(date))
            {
                beforelist.add(listID.get(i));
                before.add(currentList.get(i));

                Entry log_entry = new Entry(counter++, currentList.get(i).getValue());

                // There is a data "Object" inside every Entry that is null by default.
                // I utilize it by setting it to be a BGL object, then I retrieve the BGL object
                // when the set button inside the graph is clicked to update the SQL!
                log_entry.setData(currentList.get(i));

                dataToSet.add(log_entry);
            }
        }

        //currentList = db.GetAllBGLBeforeDate(date);
        currentList = before;
        listID = beforelist;

        MedGraphFragment.setData(dataToSet);
    }

    private void GetAfter(Date date)
    {
        System.out.println("MedicationQueryActivity: GetAfter(): date " + date);
        //currentList = db.GetAllMedicationAfterDate(date);

        ArrayList<LogEventModel> before = new ArrayList<>();
        ArrayList<String> afterlist = new ArrayList<>();
        List<Entry> dataToSet = new ArrayList<>();

        for(int i = 0; i < currentList.size(); i++)
        {
            if(ConvertToDate(currentList.get(i).getDate()).after(date))
            {
                afterlist.add(listID.get(i));
                before.add(currentList.get(i));

                Entry log_entry = new Entry(counter++, currentList.get(i).getValue());

                // There is a data "Object" inside every Entry that is null by default.
                // I utilize it by setting it to be a BGL object, then I retrieve the BGL object
                // when the set button inside the graph is clicked to update the SQL!
                log_entry.setData(currentList.get(i));

                dataToSet.add(log_entry);
            }
        }

        //currentList = db.GetAllBGLAfterDate(date);
        currentList = before;
        listID = afterlist;

        MedGraphFragment.setData(dataToSet);
    }

    private void GetKeyword(String keyword)
    {
        System.out.println("MedicationQueryActivity: GetKeyword(): keyword: " + keyword);
        //currentList = db.GetMedByKeyword(keyword);

        ArrayList<LogEventModel> before = new ArrayList<>();
        ArrayList<String> afterlist = new ArrayList<>();
        List<Entry> dataToSet = new ArrayList<>();

        for(int i = 0; i < currentList.size(); i++)
        {
            if(currentList.get(i).getDescription().toLowerCase().contains(keyword.toLowerCase()))
            {
                afterlist.add(listID.get(i));
                before.add(currentList.get(i));

                Entry log_entry = new Entry(counter++, currentList.get(i).getValue());

                // There is a data "Object" inside every Entry that is null by default.
                // I utilize it by setting it to be a BGL object, then I retrieve the BGL object
                // when the set button inside the graph is clicked to update the SQL!
                log_entry.setData(currentList.get(i));

                dataToSet.add(log_entry);
            }
        }

        currentList = before;
        listID = afterlist;

        MedGraphFragment.setData(dataToSet);
    }

    public void ClearDatabase(){
        db.ClearDatabase();
    }

    public void BGLListShowDatePickerDialog(View v)
    {
        etDate = (EditText)v.findViewById(R.id.EditTextBGLListDate);

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    public void BGLListShowTimePickerDialog(View v)
    {
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
    public void UpdateMedication(Medication med){
        db.UpdateMedication(med);
    }

    public void ShowGraph(){
        System.out.println("MedicationQueryActivity: ShowGraph()");
        ShowFragment(getFragmentManager(),GraphFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
        CurrentFragment=GraphFragment;
    }
    public void ShowList(){
        System.out.println("MedicationQueryActivity: ShowList()");
        ShowFragment(getFragmentManager(),ListFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
        CurrentFragment=ListFragment;
    }
    public void ShowStats(){
        System.out.println("MedicationQueryActivity: ShowStats()");
        ShowFragment(getFragmentManager(),StatsFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
        CurrentFragment=StatsFragment;
    }
    public void ShowMain(){
        System.out.println("MedicationQueryActivity: ShowMain()");
        ShowFragment(getFragmentManager(),MainFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
    }

    private void ShowFragment(FragmentManager fm, android.app.Fragment fr, boolean show){

        FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        if(!show)
            ft.hide(fr);
        else
            ft.show(fr);

        ft.commit();
    }

    public void DialogHelper(DialogFragment frag, String name){
        frag.show(getSupportFragmentManager(), name);
    }
    public void SetDateEdit(EditText et){
        etDate = et;
    }
    public void SetTimeEdit(EditText et){
        etTime = et;
    }

    public void ShowAll(){
        System.out.println("MedicationQueryActivity: ShowAll()");
        GetCompleteMedication();
        GraphFragment.Chart();
        ListFragment.BuildList();
        StatsFragment.Calculate();
        ShowResult();
    }
    public void ShowBefore(Date date){
        System.out.println("MedicationQueryActivity:: ShowBefore(): date: " + date);
        GetBefore(date);
        GraphFragment.Chart();
        ListFragment.BuildList(currentList, listID);
        StatsFragment.Calculate();
        ShowResult();
    }
    public void ShowAfter(Date date){
        System.out.println("MedicationQueryActivity: ShowResult(): date: " + date);
        GetAfter(date);
        GraphFragment.Chart();
        ListFragment.BuildList(currentList, listID);
        StatsFragment.Calculate();
        ShowResult();
    }
    public void ShowKeyword(String keyword)
    {
        System.out.println("MedicationQueryActivity: ShowKeyword(): keyword: " + keyword);
        GetKeyword(keyword);
        GraphFragment.Chart();
        ListFragment.BuildList(currentList, listID);
        StatsFragment.Calculate();
        ShowResult();
    }

    private void ShowResult()
    {
        System.out.println("MedicationQueryActivity: ShowResult()");
        ShowFragment(getFragmentManager(), ResultFragment,true);
        ShowFragment(getFragmentManager(),GraphFragment,false);
        ShowFragment(getFragmentManager(),StatsFragment,false);
        ShowList();
        ShowFragment(getFragmentManager(),MainFragment,false);
    }

    public void SetGraphFragment(Fragment frag){
        GraphFragment = (MedGraphFragment) frag;
    }
    public void SetListFragment(Fragment frag){
        ListFragment = (MedicationListFragment) frag;
    }
    public void SetStatsFragment(Fragment frag){
        StatsFragment = (MedStatFragment) frag;
    }

    public void UpdateMedication(LogEventModel medication, String medicationID)
    {
        Map<String, Object> updatedValues = medication.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/" + MEDICATION_CHILD + "/" + medicationID, updatedValues);

        mFirebaseDatabaseReference.updateChildren(childUpdates);

        if(!changeFlag)
        {
            GetBefore(changeDate);
        }
        else
        {
            GetAfter(changeDate);
        }

        GraphFragment.Chart();
        ListFragment.BuildList(currentList, listID);
        StatsFragment.Calculate();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        System.out.println("onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private Date ConvertToDate(String dateString)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date convertedDate = new Date();

        try
        {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("convertedDate: "  + convertedDate);
        return convertedDate;
    }
}
