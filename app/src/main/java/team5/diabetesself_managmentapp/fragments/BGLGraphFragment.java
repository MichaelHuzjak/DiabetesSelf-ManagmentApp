package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLGraphFragment extends Fragment implements OnChartValueSelectedListener{
    private LineChart chart;
    private LineData lineData;
    private List<Entry> data;
    private LineDataSet dataSet;
    private List<ILineDataSet> dataSets;
    private LinearLayout ButtonAndEditTextLayout;

    private ArrayList<BGLEntryModel> list;
    private ArrayList<String> bglID;

    private int counter;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String BGL_CHILD = "bgl";
    //private FirebaseDatabase mDatabase;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("BGLGraphFragment: onActivityCreated()");

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("USER ID: " + mFirebaseUser.getUid());
        System.out.println("USER STRING: " + mUsername);

        counter = 0;

        list = new ArrayList<>();
        bglID = new ArrayList<>();

        readBglData();

        Chart();

    }

    private void readBglData()
    {
        System.out.println("BGLGraphFragment: readBglData()");

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
                    System.out.println("BGLGraphFragment: Key: " + child.getKey() + " BGL: " + bglModel.getProgress() + " Date: " + bglModel.getDate() + " Time: " +  bglModel.getTime());
                    list.add(bglModel);
                    bglID.add(child.getKey());

                    Entry bgl_entry = new Entry(counter++, bglModel.getProgress());

                    // There is a data "Object" inside every Entry that is null by default.
                    // I utilize it by setting it to be a BGL object, then I retrieve the BGL object
                    // when the set button inside the graph is clicked to update the SQL!
                    bgl_entry.setData(bglModel);

                    data.add(bgl_entry);
                }

                dataSet = new LineDataSet(data,"Values");
                //dataSets = new ArrayList<ILineDataSet>();

                dataSet.setDrawValues(true);
                dataSet.setCircleRadius(15);
                dataSet.setValueTextSize(20);
                dataSet.setHighlightEnabled(true);
                dataSet.setDrawHighlightIndicators(true);
                dataSet.setHighLightColor(Color.RED);

                dataSets.add(dataSet);

                lineData = new LineData(dataSets);
                chart.setData(lineData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("BGLGraphFragment: readBglData() returned.");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.bglgraph_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    private void Chart()
    {
        data = new ArrayList<>();

        System.out.println("BGLGraphFragment:Chart()");

        chart = (LineChart) getActivity().findViewById(R.id.graphExample);

        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDragEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setOnChartValueSelectedListener(this);

        dataSet = new LineDataSet(data,"Values");
        dataSets = new ArrayList<>();

        dataSet.setDrawValues(true);
        dataSet.setCircleRadius(15);
        dataSet.setValueTextSize(20);
        dataSet.setHighlightEnabled(true);
        dataSet.setDrawHighlightIndicators(true);
        dataSet.setHighLightColor(Color.RED);

        dataSets.add(dataSet);

        XAxis xaxis = chart.getXAxis();
        xaxis.disableGridDashedLine();

        YAxis yaxis = chart.getAxisLeft();
        yaxis.setDrawGridLines(true);

        lineData = new LineData(dataSets);

        chart.setData(lineData);
    }

    //Brings up the editTexts layout when the user clicks on a point.
    //It also shifts the view based on the location of the point that was clicked.
    private void BgingEditLayout(Entry e, Highlight h){
        EditText graphDateTime = (EditText)getActivity().findViewById(R.id.EditTextGraphDateTime);
        ButtonAndEditTextLayout = (LinearLayout)getActivity().findViewById(R.id.LinearLayoutUpdateBGLQuery);

        ButtonAndEditTextLayout.setVisibility(View.VISIBLE);
        graphDateTime.setText(((BGLEntryModel)e.getData()).getDate());

        /////////////////////////////////////////////////////////
        // If the point is on the edge of the screen (left or top)
        // shift the layout of the edittext and button so
        // it wont disappear from view.
        if(h.getXPx() <= 320)
            ButtonAndEditTextLayout.setX(h.getXPx());
        else
            ButtonAndEditTextLayout.setX(h.getXPx()-320);

        if(h.getYPx() <= 300)
            ButtonAndEditTextLayout.setY(h.getYPx()+50);
        else
            ButtonAndEditTextLayout.setY(h.getYPx()-300);
        /////////////////////////////////////////////////////////
    }

    //Sets the functionality of the check button.
    private void setEditLayoutFunction(final Entry e, final int index)
    {
        final Button btn = (Button)getActivity().findViewById(R.id.ButtonSetGraphBGL);
        final EditText bglGraphEditText = (EditText)getActivity().findViewById(R.id.editTextNewBGLValue);

        btn.setOnClickListener(new View.OnClickListener()
        {
            float new_value = 0;

            @Override
            public void onClick(View view)
            {

                try{
                    new_value = Float.parseFloat(bglGraphEditText.getText().toString());

                    e.setY(new_value);

                    BGLEntryModel bgl = (BGLEntryModel)e.getData();

                    bgl.setProgress((int)new_value);

                    // Retrieve the BGL ID from the list of unique BGL ID's per index
                    final String bglId = bglID.get(index);

                    ((QueryActivity) getActivity()).UpdateBGL(bgl, bglId);

                }catch (Exception e) {
                    return;
                }

                // Update the sql table with the new value.

                ButtonAndEditTextLayout.setVisibility(View.INVISIBLE);
                bglGraphEditText.setText("");
                chart.setAlpha(1f);
                dataSet.notifyDataSetChanged();
                lineData.notifyDataChanged();
                chart.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        // get the position of this Entry object.
        int index = (int)e.getX();

        BgingEditLayout(e,h);

        setEditLayoutFunction(e, index);

        chart.setAlpha(0.3f); //Changes the opacity of the graph
    }

    @Override
    public void onNothingSelected() {
        //When the user touchs somewhere else, set the opacity of the graph back to 1 and make
        //the edit view invisible.
        if(chart!=null) chart.setAlpha(1f);
        if(ButtonAndEditTextLayout!=null) ButtonAndEditTextLayout.setVisibility(View.INVISIBLE);

    }
}
