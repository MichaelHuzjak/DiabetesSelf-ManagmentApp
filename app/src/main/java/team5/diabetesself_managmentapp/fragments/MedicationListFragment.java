package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.Diet;
import team5.diabetesself_managmentapp.DietQueryActivity;
import team5.diabetesself_managmentapp.Medication;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.adapter.DietAdapter;
import team5.diabetesself_managmentapp.adapter.MedicationAdapter;

/**
 * Created by Michael on 8/7/2016.
 */
public class MedicationListFragment extends Fragment {
    ArrayList<Medication> list;
    MedicationAdapter adapter;
    RecyclerView holderView;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MedicationQueryActivity)getActivity()).SetListFragment(this);

        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewMedListHolder);


    }
    public void BuildList(){
        list = new ArrayList<Medication>();
        for(Medication med: ((MedicationQueryActivity)getActivity()).GetList()){
            list.add(med);
        }
        adapter = new MedicationAdapter(list,((MedicationQueryActivity)getActivity()));
        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.medlist_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
