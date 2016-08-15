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
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLStatsFragment extends Fragment {
    EditText etMean;
    EditText etVariance;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BGLQueryActivity)getActivity()).SetStatsFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bglstats_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void Calculate(){

        etVariance = (EditText)getActivity().findViewById(R.id.editTextBGLVariant);
        etMean = (EditText)getActivity().findViewById(R.id.editTextBGLMean);
        List<BGL> list = ((BGLQueryActivity)getActivity()).GetList();
        float mean = 0;
        for(BGL bgl: list){
            mean += bgl.get_value();
        }
        mean = mean / list.size();

        float variance = 0;
        for(BGL bgl: list){
            System.out.println(""+variance);
            float value = bgl.get_value() - mean;
            variance += Math.pow(value,2);

        }
        variance = variance / list.size();
        System.out.println(""+variance);

        etMean.setText(""+mean);
        etVariance.setText(""+variance);

    }

}

