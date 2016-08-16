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
import team5.diabetesself_managmentapp.Medication;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class MedStatFragment extends Fragment {
    EditText etMean;
    EditText etVariance;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MedicationQueryActivity)getActivity()).SetStatsFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.medstat_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void Calculate(){

        etVariance = (EditText)getActivity().findViewById(R.id.editTextMedVariance);
        etMean = (EditText)getActivity().findViewById(R.id.editTextMedMean);
        List<Medication> list = ((MedicationQueryActivity)getActivity()).GetList();
        float mean = 0;
        for(Medication med: list){
            mean += med.get_amount();
        }
        mean = mean / list.size();

        float variance = 0;
        for(Medication med: list){
            System.out.println(""+variance);
            float value = med.get_amount() - mean;
            variance += Math.pow(value,2);

        }
        variance = variance / list.size();
        System.out.println(""+variance);

        etMean.setText(""+mean);
        etVariance.setText(""+variance);

    }

}
