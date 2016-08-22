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

import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.MedicationAdapter;
import team5.diabetesself_managmentapp.model.LogEventModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class MedicationListFragment extends Fragment {
    private MedicationAdapter adapter;
    private RecyclerView holderView;
    private View view;

    private ArrayList<LogEventModel> list;
    private ArrayList<String> medicationID;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String MEDICATION_CHILD = "medication";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("MedicationListFragment:onActivityCreated()");
        holderView = (RecyclerView)view.findViewById(R.id.RecyclerViewMedListHolder);

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("MedicationListFragment USER ID: " + mFirebaseUser.getUid());
        System.out.println("MedicationListFragment USER STRING: " + mUsername);

        list = new ArrayList<>();
        medicationID = new ArrayList<>();

        ((MedicationQueryActivity)getActivity()).SetListFragment(this);
    }

    public void BuildList()
    {
        System.out.println("MedicationListFragment:BuildList()");
        System.out.println("Medication Count: " + ((MedicationQueryActivity)getActivity()).GetList().size());

        readMedicationData();

        adapter = new MedicationAdapter(list, medicationID, (MedicationQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void BuildList(ArrayList<LogEventModel> list, ArrayList<String> medicationID)
    {
        System.out.println("MedicationListFragment:BuildList()");

        readMedicationData();

        adapter = new MedicationAdapter(list, medicationID, (MedicationQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void readMedicationData()
    {
        System.out.println("MedicationListFragment: readMedicationData()");

        DatabaseReference ref = mFirebaseDatabaseReference.child(MEDICATION_CHILD);

        // Add all polls in ref as rows
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot child : snapshot.getChildren())
                {
                    LogEventModel logEventModel = child.getValue(LogEventModel.class);
                    System.out.println("MedicationListFragment: Key: " + child.getKey() + " Medication: " + logEventModel.getDescription() + " Date: " + logEventModel.getDate() + " Time: " +  logEventModel.getTime());
                    list.add(logEventModel);
                    medicationID.add(child.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("MedicationListFragment: readMedicationData() returned.");
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
