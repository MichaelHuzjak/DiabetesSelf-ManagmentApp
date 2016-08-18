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

import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLListFragment extends Fragment {
    private BGLListAdapter adapter;
    private RecyclerView holderView;
    private View view;

    private ArrayList<BGLEntryModel> list;
    private ArrayList<String> bglID;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String BGL_CHILD = "bgl";

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        System.out.println("BGLListFragment:onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        holderView = (RecyclerView) view.findViewById(R.id.RecyclerViewBGLListHolder);

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("BGLListFragment USER ID: " + mFirebaseUser.getUid());
        System.out.println("BGLListFragment USER STRING: " + mUsername);

        list = new ArrayList<>();
        bglID = new ArrayList<>();

        ((BGLQueryActivity)getActivity()).SetListFragment(this);
    }

    public void BuildList()
    {
        System.out.println("BGLListFragment:BuildList()");

        readBglData();

        adapter = new BGLListAdapter(list, bglID, (BGLQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void BuildList(ArrayList<BGLEntryModel> list, ArrayList<String> bglID)
    {
        System.out.println("BGLListFragment:BuildList()");

        readBglData();

        adapter = new BGLListAdapter(list, bglID, (BGLQueryActivity)getActivity());

        holderView.setLayoutManager(new LinearLayoutManager(getActivity()));
        holderView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
                    System.out.println("BGLListFragment: Key: " + child.getKey() + " BGL: " + bglModel.getProgress() + " Date: " + bglModel.getDate() + " Time: " +  bglModel.getTime());
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
        System.out.println("BGLListFragment: onCreateView()");
        view = inflater.inflate(R.layout.bgllist_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(List_State, bglAdapter.getList());
    }
}
