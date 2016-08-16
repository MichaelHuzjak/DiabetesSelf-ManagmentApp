package team5.diabetesself_managmentapp.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.LogeventActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class MainBGLFragment extends Fragment {
    EditText etTime;
    EditText etDate;

    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CreateButtons();
        SetCurrentTime();
    }
    private void CreateButtons(){
        Button bglButton = (Button)getActivity().findViewById(R.id.buttonBGLAll);
        bglButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((BGLQueryActivity)getActivity()).ShowAll();
            }
        });
        Button beforeButton = (Button)getActivity().findViewById(R.id.buttonBGLQueryBefore);
        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBefore();
            }
        });
        Button afterButton = (Button)getActivity().findViewById(R.id.buttonBGLQueryAfter);
        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAfter();
            }
        });

        //listButton.
    }
    private void SetCurrentTime(){
        etTime = (EditText)getActivity().findViewById(R.id.editTextBGLQueryTime);
        etDate = (EditText)getActivity().findViewById(R.id.editTextBGLQueryDate);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        etDate.setText(sdf.format(cal.getTime()));
        sdf = new SimpleDateFormat("hh:mm:aa");
        etTime.setText(sdf.format(cal.getTime()));

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePickerDialog();
            }
        });
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTimePickerDialog();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bglquerymain_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }

    public void ShowDatePickerDialog() {

        DialogFragment newFragment = new DatePickerFragment();
        ((BGLQueryActivity)getActivity()).SetDateEdit(etDate);
        ((BGLQueryActivity)getActivity()).DialogHelper(newFragment,"datePicker");
    }

    /* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    public void ShowTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        ((BGLQueryActivity)getActivity()).SetTimeEdit(etTime);
        ((BGLQueryActivity)getActivity()).DialogHelper(newFragment,"timePicker");
    }

    public Date GetCombinedDate(){
        String dateTime = etDate.getText() + " " + etTime.getText();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            return format.parse(dateTime);
        }catch(ParseException e){
            System.out.println("error with date");
            e.printStackTrace();
        }
        return null;
    }

    public void onBefore(){
        Date date = GetCombinedDate();
        ((BGLQueryActivity)getActivity()).ShowBefore(date);
    }
    public void onAfter(){
        Date date = GetCombinedDate();
        ((BGLQueryActivity)getActivity()).ShowAfter(date);
    }
}
