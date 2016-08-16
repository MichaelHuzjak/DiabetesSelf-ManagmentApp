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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import team5.diabetesself_managmentapp.Medication;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;

/**
 * Created by Michael on 8/7/2016.
 */
public class MedGraphFragment extends Fragment implements OnChartValueSelectedListener{
    LineChart chart;
    LineData lineData;
    List<Entry> data;
    View view;
    LineDataSet dataSet;
    List<ILineDataSet> dataSets;
    LinearLayout ButtonAndEditTextLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MedicationQueryActivity)getActivity()).SetGraphFragment(this);
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
    public void Chart(){
        data = new ArrayList<>();

        int counter = 0;
        for(Medication med: ((MedicationQueryActivity)getActivity()).GetList()){
            Entry med_entry = new Entry(counter++,med.get_amount());
            med_entry.setData(med);
            data.add(med_entry);
        }

        chart = (LineChart)((MedicationQueryActivity)getActivity()).findViewById(R.id.graphMed);

        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDragEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setOnChartValueSelectedListener(this);

        dataSet = new LineDataSet(data,"Values");
        dataSets = new ArrayList<ILineDataSet>();

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

        ///chart.invalidate();
        // chart.setData(lineData);
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
    private void setEditLayoutFunction(final Entry e){
        final Button btn = (Button)getActivity().findViewById(R.id.ButtonSetGraphMedAmount);
        final EditText GraphEditText = (EditText)getActivity().findViewById(R.id.editTextNewMedValue);

        btn.setOnClickListener(new View.OnClickListener() {
            float new_value = 0;
            @Override
            public void onClick(View view) {

                try {

                    new_value = Float.parseFloat(GraphEditText.getText().toString());
                    e.setY(new_value);
                    Medication med = (Medication) e.getData();
                    med.set_amount((int)new_value);
                    ((MedicationQueryActivity) getActivity()).UpdateMedication(((med)));

                } catch (Exception e) {}



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
        BgingEditLayout(e,h);
        setEditLayoutFunction(e);
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