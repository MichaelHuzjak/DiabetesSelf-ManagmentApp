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
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLListFragment extends Fragment {
    BGLListAdapter adapter;
    RecyclerView holderView;
    View view;

    private ArrayList<BGLEntryModel> list;
    private ArrayList<String> bglID;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String BGL_CHILD = "bgl";

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        System.out.println("BGLListFragment:onActivityCreated()");
        RecyclerView holderView = (RecyclerView) view.findViewById(R.id.RecyclerViewBGLListHolder);

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("USER ID: " + mFirebaseUser.getUid());
        System.out.println("USER STRING: " + mUsername);

        list = new ArrayList<>();
        bglID = new ArrayList<>();

        readBglData();

        adapter = new BGLListAdapter(list, bglID, getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ((BGLQueryActivity)getActivity()).SetListFragment(this);

    }

    private void readBglData()
    {
        System.out.println("BGLListFragment: readBglData()");

        DatabaseReference ref = mFirebaseDatabaseReference.child(BGL_CHILD);

        // Add all polls in ref as rows
        ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {

                for(DataSnapshot child : snapshot.getChildren())
                {
                    BGLEntryModel bglModel = child.getValue(BGLEntryModel.class);
                    System.out.println("Key: " + child.getKey() + " BGL: " + bglModel.getProgress() + " Date: " + bglModel.getDate() + " Time: " +  bglModel.getTime());
                    list.add(bglModel);
                    bglID.add(child.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("BGLListFragment: readBglData() returned.");
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
