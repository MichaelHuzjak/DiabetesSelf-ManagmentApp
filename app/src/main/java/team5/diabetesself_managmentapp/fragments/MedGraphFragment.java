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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team5.diabetesself_managmentapp.Medication;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.LogEventModel;

/**
 * Created by Michael on 8/7/2016.
 */
public class MedGraphFragment extends Fragment implements OnChartValueSelectedListener{
    private LineChart chart;
    private LineData lineData;
    private static List<Entry> data;
    private View view;
    private LineDataSet dataSet;
    private List<ILineDataSet> dataSets;
    private LinearLayout ButtonAndEditTextLayout;

    private ArrayList<LogEventModel> list;
    private ArrayList<String> medicationID;

    private int counter;

    private DatabaseReference mFirebaseDatabaseReference;
    private static final String MEDICATION_CHILD = "medication";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("MedGraphFragment: onActivityCreated()");

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
        medicationID = new ArrayList<>();
        data = new ArrayList<>();

        readMedicationData();

        ((MedicationQueryActivity)getActivity()).SetGraphFragment(this);
    }

    private void readMedicationData()
    {
        System.out.println("MedGraphFragment: readMedicationData()");

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
                    System.out.println("BGLGraphFragment: Key: " + child.getKey() + " Diet: " + logEventModel.getDescription() + " Date: " + logEventModel.getDate() + " Time: " +  logEventModel.getTime());
                    list.add(logEventModel);
                    medicationID.add(child.getKey());

                    Entry bgl_entry = new Entry(counter++, logEventModel.getValue());
                    // There is a data "Object" inside every Entry that is null by default.
                    // I utilize it by setting it to be a BGL object, then I retrieve the BGL object
                    // when the set button inside the graph is clicked to update the SQL!
                    bgl_entry.setData(logEventModel);

                    data.add(bgl_entry);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        System.out.println("MedGraphFragment: readMedicationData() returned.");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.medgraph_fragment, container, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void Chart()
    {
        System.out.println("MedGraphFragment:Chart()");

        chart = (LineChart)((MedicationQueryActivity)getActivity()).findViewById(R.id.graphMed);

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
        EditText graphDateTime = (EditText)getActivity().findViewById(R.id.EditTextMedGraphDateTime);
        ButtonAndEditTextLayout = (LinearLayout)getActivity().findViewById(R.id.LinearLayoutUpdateMedQuery);

        ButtonAndEditTextLayout.setVisibility(View.VISIBLE);
        graphDateTime.setText(((Medication)e.getData()).GetFormatedDate());

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
    private void setEditLayoutFunction(final Entry e, final int index){
        final Button btn = (Button)getActivity().findViewById(R.id.ButtonSetGraphMedAmount);
        final EditText GraphEditText = (EditText)getActivity().findViewById(R.id.editTextNewMedValue);

        btn.setOnClickListener(new View.OnClickListener()
        {
            float new_value = 0;

            @Override
            public void onClick(View view) {

                try {

                    new_value = Float.parseFloat(GraphEditText.getText().toString());

                    e.setY(new_value);

                    LogEventModel med = (LogEventModel) e.getData();

                    med.setValue((int)(new_value));

                    final String medicationId = medicationID.get(index);

                    updateMedication(med, medicationId);

                } catch (Exception e) {
                    return;
                }

                // Update the sql table with the new value.

                ButtonAndEditTextLayout.setVisibility(View.INVISIBLE);
                GraphEditText.setText("");
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

    private void updateMedication(LogEventModel medication, String medicationID)
    {
        Map<String, Object> updatedValues = medication.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/" + MEDICATION_CHILD + "/" + medicationID, updatedValues);

        mFirebaseDatabaseReference.updateChildren(childUpdates);
    }

    public static void setData(List<Entry> setTo)
    {
        data = setTo;
    }
}