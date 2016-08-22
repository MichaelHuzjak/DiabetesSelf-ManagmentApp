package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLGraphFragment extends Fragment implements OnChartValueSelectedListener{
    private LineChart chart;
    private LineData lineData;
    private static List<Entry> data;
    View view;
    private LineDataSet dataSet;
    private List<ILineDataSet> dataSets;
    private LinearLayout ButtonAndEditTextLayout;

    private ArrayList<BGLEntryModel> list;
    private ArrayList<String> bglID;

    private int counter;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String BGL_CHILD = "bgl";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println("BGLGraphFragment: onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        // Initialize Firebase Auth
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String mUsername = mFirebaseUser != null ? mFirebaseUser.getDisplayName() : null;

        //mDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("/users/" + mFirebaseUser.getUid());

        System.out.println("BGLGraphFragment USER ID: " + mFirebaseUser.getUid());
        System.out.println("BGLGraphFragment USER STRING: " + mUsername);

        counter = 0;

        list = new ArrayList<>();
        bglID = new ArrayList<>();
        data = new ArrayList<>();

        readBglData();

        ((BGLQueryActivity)getActivity()).SetGraphFragment(this);
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
        System.out.println("BGLGraphFragment: onCreateView()");
        View view = inflater.inflate(R.layout.bglgraph_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    public void Chart()
    {
        System.out.println("BGLGraphFragment:Chart()");

        chart = (LineChart) getActivity().findViewById(R.id.graphBGL);

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
        System.out.println("BGLGraphFragment: BgingEditLayout()");
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
        System.out.println("BGLGraphFragment: setEditLayoutFunction()");
        final Button btn = (Button)getActivity().findViewById(R.id.ButtonSetGraphBGL);
        final EditText bglGraphEditText = (EditText)getActivity().findViewById(R.id.editTextNewBGLValue);

        btn.setOnClickListener(new View.OnClickListener()
        {
            float new_value = 0;

            @Override
            public void onClick(View view)
            {
                System.out.println("BGLGraphFragment: onClick()");
                try{
                    new_value = Float.parseFloat(bglGraphEditText.getText().toString());

                    e.setY(new_value);

                    BGLEntryModel bgl = (BGLEntryModel)e.getData();

                    bgl.setProgress((int)new_value);

                    // Retrieve the BGL ID from the list of unique BGL ID's per index
                    final String bglId = bglID.get(index);

                    updateBgl(bgl, bglId);

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

    /* Update the BGL values on the UI resulting in a update
 * of the entry in the firebase database
 */
    private void updateBgl(BGLEntryModel bglModel, String bglID)
    {
        System.out.println("BGLGraphFragment: updateBgl()");

        Map<String, Object> updatedValues = bglModel.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/" + BGL_CHILD + "/" + bglID, updatedValues);

        mFirebaseDatabaseReference.updateChildren(childUpdates);
    }

    public static void setData(List<Entry> setTo)
    {
        data = setTo;
    }
}
