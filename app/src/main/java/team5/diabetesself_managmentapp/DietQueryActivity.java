package team5.diabetesself_managmentapp;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.media.MediaBrowserServiceCompat;
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

import team5.diabetesself_managmentapp.fragments.BGLGraphFragment;
import team5.diabetesself_managmentapp.fragments.BGLListFragment;
import team5.diabetesself_managmentapp.fragments.BGLResultFragment;
import team5.diabetesself_managmentapp.fragments.BGLStatsFragment;
import team5.diabetesself_managmentapp.fragments.DatePickerFragment;
import team5.diabetesself_managmentapp.fragments.DietGraphFragment;
import team5.diabetesself_managmentapp.fragments.DietListFragment;
import team5.diabetesself_managmentapp.fragments.DietResultFragment;
import team5.diabetesself_managmentapp.fragments.DietResultMenuFragment;
import team5.diabetesself_managmentapp.fragments.DietStatFragment;
import team5.diabetesself_managmentapp.fragments.MainBGLFragment;
import team5.diabetesself_managmentapp.fragments.MainDietFragment;
import team5.diabetesself_managmentapp.fragments.MainQueryFragment;
import team5.diabetesself_managmentapp.fragments.TimePickerFragment;


public class DietQueryActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    static final int DIALOG_ID = 0;
    private DatabaseHelper db;
    private DietListFragment ListFragment;
    private MainDietFragment MainFragment;
    private DietGraphFragment GraphFragment;
    private DietResultFragment ResultFragment;
    private DietStatFragment StatsFragment;
    public android.app.Fragment CurrentFragment;
    EditText etDate;
    EditText etTime;
    List<Diet> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietquery);
        db = new DatabaseHelper(this,null,null,1);

        MainFragment = (MainDietFragment) getFragmentManager().findFragmentById(R.id.MainDietFragment);
        CurrentFragment = MainFragment;
        ShowFragment(getFragmentManager(), MainFragment,true);

        ResultFragment = (DietResultFragment) getFragmentManager().findFragmentById(R.id.DietResultFragment);
        ShowFragment(getFragmentManager(), ResultFragment,false);

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

    public void ListDiet(){
        ShowFragment(getFragmentManager(),ListFragment,true);
        ShowFragment(getFragmentManager(),MainFragment,false);
    }
    public List<Diet> GetList(){
        return currentList;
    }
    public void GetCompleteDiet(){
        //return db.GetAllBGL();
        currentList = db.GetAllDiet();
    }
    public void GetBefore(Date date){
        currentList = db.GetAllDiet();
    }
    public void Getafter(Date date){
        currentList =db.GetAllDiet();
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
    public void UpdateDiet(Diet diet){
        db.UpdateDiet(diet);
    }

    public void ShowGraph(){
        GraphFragment.Chart();
        ShowFragment(getFragmentManager(),GraphFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
        CurrentFragment=GraphFragment;
    }
    public void ShowList(){
        ListFragment.BuildList();
        ShowFragment(getFragmentManager(),ListFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
        CurrentFragment=ListFragment;
    }
    public void ShowStats(){
        StatsFragment.Calculate();
        ShowFragment(getFragmentManager(),StatsFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
        CurrentFragment=StatsFragment;
    }
    public void ShowMain(){
        ShowFragment(getFragmentManager(),MainFragment,true);
        ShowFragment(getFragmentManager(),CurrentFragment,false);
    }

    public void ShowFragment(FragmentManager fm, android.app.Fragment fr, boolean show){

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
        GetCompleteDiet();
        GraphFragment.Chart();
        ListFragment.BuildList();
        StatsFragment.Calculate();
        ShowResult();
    }
    public void ShowBefore(Date date){
        GetBefore(date);
        GraphFragment.Chart();
        ListFragment.BuildList();
        StatsFragment.Calculate();
        ShowResult();
    }
    public void ShowAfter(Date date){
        Getafter(date);
        GraphFragment.Chart();
        ListFragment.BuildList();
        StatsFragment.Calculate();
        ShowResult();
    }
    public void ShowKeyword(String keyword){
        currentList = db.GetDietByKeyword(keyword);
        GraphFragment.Chart();
        ListFragment.BuildList();
        StatsFragment.Calculate();
        ShowResult();
    }

    public void ShowResult(){
        ShowFragment(getFragmentManager(), ResultFragment,true);
        ShowFragment(getFragmentManager(),GraphFragment,false);
        ShowFragment(getFragmentManager(),StatsFragment,false);
        ShowList();
        ShowFragment(getFragmentManager(),MainFragment,false);
    }

    public void SetGraphFragment(Fragment frag){
        GraphFragment = (DietGraphFragment) frag;
    }
    public void SetListFragment(Fragment frag){
        ListFragment = (DietListFragment) frag;
    }
    public void SetStatsFragment(Fragment frag){
        StatsFragment = (DietStatFragment) frag;
    }



}
