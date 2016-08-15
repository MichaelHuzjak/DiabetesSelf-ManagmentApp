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
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLListFragment extends Fragment {
    ArrayList<BGL> list;
    BGLListAdapter adapter;
    RecyclerView holderView;
    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BGLQueryActivity)getActivity()).SetListFragment(this);

        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewBGLListHolder);
        list = new ArrayList<BGL>();

    }
    public void BuildList(){
        for(BGL bgl: ((BGLQueryActivity)getActivity()).GetList()){
            System.out.println("ID: " + bgl.get_id());
            list.add(bgl);
        }
        adapter = new BGLListAdapter(list,((BGLQueryActivity)getActivity()));
        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bgllist_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
