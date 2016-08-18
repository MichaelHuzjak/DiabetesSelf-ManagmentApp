package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLStatsFragment extends Fragment {
    private EditText etMean;
    private EditText etVariance;
    private View view;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println("BGLStatsFragment:onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        ((BGLQueryActivity)getActivity()).SetStatsFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("BGLStatsFragment:onCreateView()");
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
        List<BGLEntryModel> list = ((BGLQueryActivity)getActivity()).GetList();
        float mean = 0;
        for(BGLEntryModel bgl: list){
            mean += bgl.getProgress();
        }
        mean = mean / list.size();

        float variance = 0;
        for(BGLEntryModel bgl: list){
            System.out.println(""+variance);
            float value = bgl.getProgress() - mean;
            variance += Math.pow(value,2);

        }
        variance = variance / list.size();
        System.out.println(""+variance);

        etMean.setText(""+mean);
        etVariance.setText(""+variance);

    }

}

