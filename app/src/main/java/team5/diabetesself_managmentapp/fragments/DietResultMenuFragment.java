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
import team5.diabetesself_managmentapp.DietQueryActivity;
import team5.diabetesself_managmentapp.LogeventActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class DietResultMenuFragment extends Fragment {

    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CreateButtons();
    }
    private void CreateButtons(){
        Button graphButton = (Button)getActivity().findViewById(R.id.buttonDietGraph);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DietQueryActivity)getActivity()).ShowGraph();
            }
        });
        Button buttonList = (Button)getActivity().findViewById(R.id.buttonDietList);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DietQueryActivity)getActivity()).ShowList();
            }
        });
        Button buttonStats = (Button)getActivity().findViewById(R.id.buttonDietStats);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DietQueryActivity)getActivity()).ShowStats();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dietresultmenu_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }

}
