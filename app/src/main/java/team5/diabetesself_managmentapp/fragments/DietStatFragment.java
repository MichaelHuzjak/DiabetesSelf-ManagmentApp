package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import team5.diabetesself_managmentapp.DietQueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.LogEventModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class DietStatFragment extends Fragment {
    private EditText etMean;
    private EditText etVariance;
    private View view;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DietQueryActivity)getActivity()).SetStatsFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dietstat_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void Calculate(){

        etVariance = (EditText)getActivity().findViewById(R.id.editTextDietVariance);
        etMean = (EditText)getActivity().findViewById(R.id.editTextDietMean);
        List<LogEventModel> list = ((DietQueryActivity)getActivity()).GetList();
        float mean = 0;
        for(LogEventModel diet: list){
            mean +=(float)diet.getValue();
        }
        mean = mean / list.size();

        float variance = 0;
        for(LogEventModel diet: list){
            System.out.println(""+variance);
            float value = (float)diet.getValue() - mean;
            variance += Math.pow(value,2);

        }
        variance = variance / list.size();
        System.out.println(""+variance);

        etMean.setText(""+mean);
        etVariance.setText(""+variance);

    }

}
