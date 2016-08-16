package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.Diet;
import team5.diabetesself_managmentapp.DietQueryActivity;
import team5.diabetesself_managmentapp.Exercise;
import team5.diabetesself_managmentapp.ExerciseQueryActivity;
import team5.diabetesself_managmentapp.Medication;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class ExerStatFragment extends Fragment {
    EditText etMean;
    EditText etVariance;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ExerciseQueryActivity)getActivity()).SetStatsFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.exerstat_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void Calculate(){

        etVariance = (EditText)getActivity().findViewById(R.id.editTextExerVariance);
        etMean = (EditText)getActivity().findViewById(R.id.editTextExerMean);
        List<Exercise> list = ((ExerciseQueryActivity)getActivity()).GetList();
        float mean = 0;
        for(Exercise exer: list){
            mean += exer.get_duration();
        }
        mean = mean / list.size();

        float variance = 0;
        for(Exercise exer: list){
            System.out.println(""+variance);
            float value = exer.get_duration() - mean;
            variance += Math.pow(value,2);

        }
        variance = variance / list.size();
        System.out.println(""+variance);

        etMean.setText(""+mean);
        etVariance.setText(""+variance);

    }

}
