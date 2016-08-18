package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.DietQueryActivity;
import team5.diabetesself_managmentapp.ExerciseQueryActivity;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class MainQueryFragment extends Fragment {
    private View view;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println("MainQueryFragment: MainQueryFragment()");
        super.onActivityCreated(savedInstanceState);
        CreateButtons();
    }

    private void CreateButtons(){
        System.out.println("MainQueryFragment: CreateButtons()");

        Button bglButton = (Button)getActivity().findViewById(R.id.buttonBGLQuery);
        bglButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainQueryFragment: onClick() -> (QueryActivity)getActivity()).getApplicationContext(), BGLQueryActivity.class)");
                startActivity(new Intent(((QueryActivity)getActivity()).getApplicationContext(), BGLQueryActivity.class));
            }
        });
        Button buttonDiet = (Button)getActivity().findViewById(R.id.buttonQueryDiet);
        buttonDiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainQueryFragment: onClick() -> (QueryActivity)getActivity()).getApplicationContext(), DietQueryActivity.class)");
                startActivity(new Intent(((QueryActivity)getActivity()).getApplicationContext(), DietQueryActivity.class));
            }
        });
        Button buttonExercise = (Button)getActivity().findViewById(R.id.buttonQueryExercise);
        buttonExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(((QueryActivity)getActivity()).getApplicationContext(), ExerciseQueryActivity.class));
            }
        });
        Button buttonMedication = (Button)getActivity().findViewById(R.id.buttonQueryMed);
        buttonMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(((QueryActivity)getActivity()).getApplicationContext(), MedicationQueryActivity.class));
            }
        });
        Button clearButton = (Button)getActivity().findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((QueryActivity)getActivity()).ClearDatabase();
            }
        });
        //listButton.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.query_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
