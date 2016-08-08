package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class MainQueryFragment extends Fragment {
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CreateButtons();
    }
    private void CreateButtons(){
        Button listButton = (Button)getActivity().findViewById(R.id.buttonListBGL);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((QueryActivity)getActivity()).ListBGL();
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
