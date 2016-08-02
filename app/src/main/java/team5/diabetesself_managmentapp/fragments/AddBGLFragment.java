package team5.diabetesself_managmentapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import java.util.ArrayList;

import team5.diabetesself_managmentapp.AddBGLHelper;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLAdapter;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

public class AddBGLFragment extends Fragment{
    private View view;

    private ArrayList<BGLAdapter.ViewHolder> bglEntryList;
    private RecyclerView BGLHolderView;
    private BGLAdapter bglAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        final Fragment addBGL = (Fragment)getActivity().getFragmentManager().findFragmentById(R.id.FragmentBGL);
        final Fragment buttons = (Fragment)getActivity().getFragmentManager().findFragmentById(R.id.FragmentButtons);


        bglEntryList = new ArrayList<BGLAdapter.ViewHolder>();
        BGLHolderView = (RecyclerView)view.findViewById(R.id.RecyclerViewBGLHolder);
        bglAdapter = new BGLAdapter(bglEntryList);
        BGLHolderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BGLHolderView.setAdapter(bglAdapter);


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
                bglEntryList.add(null);
                bglAdapter.notifyItemInserted(bglAdapter.getItemCount());
            }
        });


        //Currently prints to STDOUT
        ImageButton setValues = (ImageButton)view.findViewById(R.id.ImageButtonSetBGL);
        setValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bglAdapter.printList();
            }
        });

        ImageButton home = (ImageButton)view.findViewById(R.id.ImageButtonHomePage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBGLHelper.hideFragment(getFragmentManager(),addBGL);
                AddBGLHelper.showFragment(getFragmentManager(),buttons);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bgl_fragment, container, false);
        return view;
    }



}
