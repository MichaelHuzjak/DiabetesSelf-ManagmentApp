package team5.diabetesself_managmentapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import java.util.ArrayList;
import java.util.List;

import team5.diabetesself_managmentapp.AddBGLHelper;
import team5.diabetesself_managmentapp.MainActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLAdapter;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

public class AddBGLFragment extends Fragment{
    private View view;

    private ArrayList<BGLEntryModel> bglEntryList;
    private RecyclerView BGLHolderView;
    public BGLAdapter bglAdapter;
    private LinearLayoutManager linearLayoutManager;

    private String List_State = "List_State";
    private String Recycler_State = "Recycler_State";

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        final Fragment addBGL = (Fragment)getActivity().getFragmentManager().findFragmentById(R.id.FragmentBGL);
        final Fragment buttons = (Fragment)getActivity().getFragmentManager().findFragmentById(R.id.FragmentButtons);

        if(savedInstanceState != null)
        {
            bglEntryList = savedInstanceState.getParcelableArrayList(List_State);
            for(BGLEntryModel l: bglEntryList){
                System.out.println("Progress: "+l.getProgress()+" || Date: "+l.getDate()+" || Time: "+l.getTime());
            }

        }else{
            bglEntryList = new ArrayList<BGLEntryModel>();
        }

        BGLHolderView = (RecyclerView)view.findViewById(R.id.RecyclerViewBGLHolder);
        bglAdapter = new BGLAdapter(bglEntryList);
        BGLHolderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BGLHolderView.setAdapter(bglAdapter);
        bglAdapter.notifyDataSetChanged();


        ImageButton addBglEntry = (ImageButton)view.findViewById(R.id.ButtonAddBGL);
        addBglEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BGLHolderView.post(new Runnable() {
                    @Override
                    public void run() {
                        // smooth scroll
                        BGLHolderView.smoothScrollToPosition(bglAdapter.getItemCount());
                    }
                });
                bglEntryList.add(new BGLEntryModel());
                bglAdapter.notifyItemInserted(bglAdapter.getItemCount());
            }
        });


        //Currently prints to STDOUT
        ImageButton setValues = (ImageButton)view.findViewById(R.id.ImageButtonSetBGL);
        setValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).AddBGLtoDatabase();
            }
        });

        ImageButton home = (ImageButton)view.findViewById(R.id.ImageButtonHomePage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).ShowHome();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bgl_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
