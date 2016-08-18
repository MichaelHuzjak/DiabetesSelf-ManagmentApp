package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class MainMedicationFragment extends Fragment {
    private EditText etTime;
    private EditText etDate;
    private EditText etKeyword;

    private View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CreateButtons();
        SetCurrentTime();
    }

    private void CreateButtons(){
        Button medButton = (Button)getActivity().findViewById(R.id.buttonMedAll);
        medButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MedicationQueryActivity)getActivity()).ShowAll();
            }
        });
        Button beforeButton = (Button)getActivity().findViewById(R.id.buttonMedBefore);
        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBefore();
            }
        });
        Button afterButton = (Button)getActivity().findViewById(R.id.buttonMedAfter);
        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAfter();
            }
        });
        etKeyword = (EditText)getActivity().findViewById(R.id.editTextMedKeyword);
        Button keywordButton = (Button)getActivity().findViewById(R.id.buttonMedKeyword);
        keywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onKeyword();
            }
        });

        //listButton.
    }
    private void SetCurrentTime(){
        etTime = (EditText)getActivity().findViewById(R.id.editTextMedQueryTime);
        etDate = (EditText)getActivity().findViewById(R.id.editTextMedQueryDate);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
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

        view = inflater.inflate(R.layout.medicationmain_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }

    private void ShowDatePickerDialog() {

        DialogFragment newFragment = new DatePickerFragment();
        ((MedicationQueryActivity)getActivity()).SetDateEdit(etDate);
        ((MedicationQueryActivity)getActivity()).DialogHelper(newFragment,"datePicker");
    }

    /* WHEN THE USER CLICKS THE TIME BUTTON IN THE UI,
    CREATE AN INSTANCE OF A DIALOGFRAGMENT AND SHOW IT
    VIA THE FRAGMENT MANAGER
     */
    private void ShowTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        ((MedicationQueryActivity)getActivity()).SetTimeEdit(etTime);
        ((MedicationQueryActivity)getActivity()).DialogHelper(newFragment,"timePicker");
    }

    private Date GetCombinedDate(){
        String dateTime = etDate.getText() + " " + etTime.getText();
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:aa");
        try {
            return format.parse(dateTime);
        }catch(ParseException e){
            System.out.println("error with date");
            e.printStackTrace();
        }
        return null;
    }

    private void onBefore(){
        Date date = GetCombinedDate();
        ((MedicationQueryActivity)getActivity()).ShowBefore(date);
    }
    private void onAfter(){
        Date date = GetCombinedDate();
        ((MedicationQueryActivity)getActivity()).ShowAfter(date);
    }
    private void onKeyword(){
        String keyword = "" + etKeyword.getText();
        if(keyword != "") {
            ((MedicationQueryActivity) getActivity()).ShowKeyword(keyword);
        }
    }
}
