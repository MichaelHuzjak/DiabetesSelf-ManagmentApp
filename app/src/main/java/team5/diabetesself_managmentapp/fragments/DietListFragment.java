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
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.adapter.DietAdapter;

/**
 * Created by Michael on 8/7/2016.
 */
public class DietListFragment extends Fragment {
    ArrayList<Diet> list;
    DietAdapter adapter;
    RecyclerView holderView;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((DietQueryActivity)getActivity()).SetListFragment(this);

        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewDietListHolder);


    }
    public void BuildList(){
        System.out.println("Diet Count: " + ((DietQueryActivity)getActivity()).GetList().size());
        list = new ArrayList<Diet>();
        for(Diet diet: ((DietQueryActivity)getActivity()).GetList()){
            System.out.println("Listing Diet: " + diet.get_id());
            list.add(diet);
        }
        adapter = new DietAdapter(list,((DietQueryActivity)getActivity()));
        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dietlist_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
