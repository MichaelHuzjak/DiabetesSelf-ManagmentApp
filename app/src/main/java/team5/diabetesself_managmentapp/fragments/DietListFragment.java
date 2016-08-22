package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import team5.diabetesself_managmentapp.model.LogEventModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class DietListFragment extends Fragment {
    private DietAdapter adapter;
    private RecyclerView holderView;
    private View view;

    private ArrayList<LogEventModel> list;
    private ArrayList<String> dietID;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String DIET_CHILD = "diet";

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        System.out.println("DietListFragment:onActivityCreated()");
        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewDietListHolder);

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("DietListFragment USER ID: " + mFirebaseUser.getUid());
        System.out.println("DietListFragment USER STRING: " + mUsername);

        list = new ArrayList<>();
        dietID = new ArrayList<>();

        ((DietQueryActivity)getActivity()).SetListFragment(this);
    }

    public void BuildList()
    {
        System.out.println("DietListFragment:BuildList()");
        System.out.println("Diet Count: " + ((DietQueryActivity)getActivity()).GetList().size());

        readDietData();

        adapter = new DietAdapter(list, dietID, (DietQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void BuildList(ArrayList<LogEventModel> list, ArrayList<String> dietID)
    {
        System.out.println("DietListFragment:BuildList()");

        readDietData();

        adapter = new DietAdapter(list, dietID, (DietQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void readDietData()
    {
        System.out.println("DietListFragment: readDietData()");

        DatabaseReference ref = mFirebaseDatabaseReference.child(DIET_CHILD);

        // Add all polls in ref as rows
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot child : snapshot.getChildren())
                {
                    LogEventModel logEventModel = child.getValue(LogEventModel.class);
                    System.out.println("DietListFragment Key: " + child.getKey() + " Diet: " + logEventModel.getDescription() + " Date: " + logEventModel.getDate() + " Time: " +  logEventModel.getTime());
                    list.add(logEventModel);
                    dietID.add(child.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("DietListFragment: readDietData() returned.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("DietListFragment: onCreateView()");
        view = inflater.inflate(R.layout.dietlist_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
