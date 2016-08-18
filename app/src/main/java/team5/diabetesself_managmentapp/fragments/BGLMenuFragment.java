package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLMenuFragment extends Fragment {

    private View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println("BGLMenuFragment: onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        CreateButtons();
    }
    private void CreateButtons(){
        System.out.println("BGLMenuFragment: CreateButtons()");
        Button graphButton = (Button)getActivity().findViewById(R.id.buttonBGLGraph);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("BGLMenuFragment: onClick() -> (BGLQueryActivity)getActivity()).ShowGraph()");
                ((BGLQueryActivity)getActivity()).ShowGraph();
            }
        });
        Button buttonList = (Button)getActivity().findViewById(R.id.buttonBGLList);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("BGLMenuFragment: onClick() -> (BGLQueryActivity)getActivity()).ShowList()");
                ((BGLQueryActivity)getActivity()).ShowList();
            }
        });
        Button buttonStats = (Button)getActivity().findViewById(R.id.buttonBGLStats);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("BGLMenuFragment: onClick() -> (BGLQueryActivity)getActivity()).ShowStats()");
                ((BGLQueryActivity)getActivity()).ShowStats();
            }
        });
        //listButton.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("BGLMenuFragment: onCreateView()");
        view = inflater.inflate(R.layout.bglmenu_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }

}
