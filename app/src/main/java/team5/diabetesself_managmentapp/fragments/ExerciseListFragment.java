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
import team5.diabetesself_managmentapp.Exercise;
import team5.diabetesself_managmentapp.ExerciseQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.adapter.DietAdapter;
import team5.diabetesself_managmentapp.adapter.ExerciseAdapter;
import team5.diabetesself_managmentapp.model.LogEventModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class ExerciseListFragment extends Fragment {
    private ExerciseAdapter adapter;
    private RecyclerView holderView;
    private View view;

    private ArrayList<LogEventModel> list;
    private ArrayList<String> exerciseID;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String EXERCISE_CHILD = "exercise";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("ExerciseListFragment:onActivityCreated()");
        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewExerListHolder);

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("ExerciseListFragment USER ID: " + mFirebaseUser.getUid());
        System.out.println("ExerciseListFragment USER STRING: " + mUsername);

        list = new ArrayList<>();
        exerciseID = new ArrayList<>();

        ((ExerciseQueryActivity)getActivity()).SetListFragment(this);
    }

    public void BuildList()
    {
        System.out.println("ExerciseListFragment:BuildList()");
        System.out.println("Exercise Count: " + ((ExerciseQueryActivity)getActivity()).GetList().size());

        readExerciseData();

        adapter = new ExerciseAdapter(list, exerciseID, (ExerciseQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void BuildList(ArrayList<LogEventModel> list, ArrayList<String> exerciseID)
    {
        System.out.println("BGLListFragment:BuildList()");

        readExerciseData();

        adapter = new ExerciseAdapter(list, exerciseID, (ExerciseQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void readExerciseData()
    {
        System.out.println("ExerciseListFragment: readExerciseData()");

        DatabaseReference ref = mFirebaseDatabaseReference.child(EXERCISE_CHILD);

        // Add all polls in ref as rows
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot child : snapshot.getChildren())
                {
                    LogEventModel logEventModel = child.getValue(LogEventModel.class);
                    System.out.println("ExerciseListFragment: Key: " + child.getKey() + " Exercise: " + logEventModel.getDescription() + " Date: " + logEventModel.getDate() + " Time: " +  logEventModel.getTime());
                    list.add(logEventModel);
                    exerciseID.add(child.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("ExerciseListFragment: readExerciseData() returned.");
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
