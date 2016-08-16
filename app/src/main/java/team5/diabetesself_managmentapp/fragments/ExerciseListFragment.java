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
import team5.diabetesself_managmentapp.Exercise;
import team5.diabetesself_managmentapp.ExerciseQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.adapter.DietAdapter;
import team5.diabetesself_managmentapp.adapter.ExerciseAdapter;

/**
 * Created by Michael on 8/7/2016.
 */
public class ExerciseListFragment extends Fragment {
    ArrayList<Exercise> list;
    ExerciseAdapter adapter;
    RecyclerView holderView;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ExerciseQueryActivity)getActivity()).SetListFragment(this);

        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewExerListHolder);
        list = new ArrayList<Exercise>();

    }
    public void BuildList(){

        for(Exercise exer: ((ExerciseQueryActivity)getActivity()).GetList()){
            System.out.println("Listing Diet: " + exer.get_id());
            list.add(exer);
        }
        adapter = new ExerciseAdapter(list,((ExerciseQueryActivity)getActivity()));
        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.exerlist_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
