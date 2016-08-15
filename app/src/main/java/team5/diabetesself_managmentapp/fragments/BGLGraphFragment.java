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

import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLGraphFragment extends Fragment implements OnChartValueSelectedListener{
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
        ((BGLQueryActivity)getActivity()).SetGraphFragment(this);
        //Graph();
        //Chart();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.bglgraph_fragment, container, false);
        return view;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
    public void Chart(){
        System.out.println("Chart");
        // Get ALL BGL List
//        ArrayList<BGL> list = new ArrayList<BGL>();
        data = new ArrayList<>();

        int counter = 0;
        for(BGL bgl: ((BGLQueryActivity)getActivity()).GetList()){
            System.out.println("ID: " + bgl.get_id());
//            list.add(bgl);
            Entry bgl_entry = new Entry(counter++,bgl.get_value());

            // There is a data "Object" inside every Entry that is null by default.
            // I utilize it by setting it to be a BGL object, then I retrieve the BGL object
            // when the set button inside the graph is clicked to update the SQL!
            bgl_entry.setData(bgl);

            data.add(bgl_entry);

        }

        chart = (LineChart)((BGLQueryActivity)getActivity()).findViewById(R.id.graphBGL);

        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDragEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setOnChartValueSelectedListener(this);
        //chart.setVisibleXRange(0,5);

//        ArrayList<String> xdata = new ArrayList<>();

//        for(int i=0;i<list.size();i++){
//            Entry bgl_entry = new Entry(i,list.get(i).get_value());
//            data.add(new Entry(i,list.get(i).get_value()));
//        }

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
    public void Graph(){

        // Get ALL BGL List
        ArrayList<BGL> list = new ArrayList<BGL>();

        for(BGL bgl: ((BGLQueryActivity)getActivity()).GetList()){
            System.out.println("ID: " + bgl.get_id());
            list.add(bgl);
        }
        DataPoint[] bglDataPoints = new DataPoint[list.size()];
        for(int i=0;i<bglDataPoints.length;i++){
            bglDataPoints[i] = new DataPoint(i,list.get(i).get_value());
        }

        // Basic graph code from GraphView, Open Source Android Graph library
        GraphView mGraph = (GraphView)((BGLQueryActivity)getActivity()).findViewById(R.id.graphBGL);
        DataPoint[] data = new DataPoint[30];
        for(int i = 0;i<30;i++){
            data[i] = new DataPoint(i,100 + (i*3));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(bglDataPoints);
        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>(bglDataPoints);
        series2.setColor(Color.RED);
        //series.setDrawValuesOnTop(true);
        //series.setSpacing(1);

        mGraph.addSeries(series);
        mGraph.addSeries(series2);

        Viewport vp = mGraph.getViewport();
        vp.setScalable(true);
        vp.setScrollable(true);
        vp.setXAxisBoundsManual(true);
        vp.setMaxX(bglDataPoints.length);
        vp.setMinX(1);
        vp.setMinY(1);
        vp.setMaxY(20);
        vp.calcCompleteRange();
        vp.computeScroll();

        mGraph.setTitle("Monthly BGL Example");
        String[] labels = new String[30];
        for(int i=0;i<30;i++){
            labels[i] = "7/" + i;
        }
    }


    //Brings up the editTexts layout when the user clicks on a point.
    //It also shifts the view based on the location of the point that was clicked.
    private void BgingEditLayout(Entry e, Highlight h){
        EditText graphDateTime = (EditText)getActivity().findViewById(R.id.EditTextGraphDateTime);
        ButtonAndEditTextLayout = (LinearLayout)getActivity().findViewById(R.id.LinearLayoutUpdateBGLQuery);

        ButtonAndEditTextLayout.setVisibility(View.VISIBLE);
        graphDateTime.setText(((BGL)e.getData()).GetFormatedDate());

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
        final Button btn = (Button)getActivity().findViewById(R.id.ButtonSetGraphBGL);
        final EditText bglGraphEditText = (EditText)getActivity().findViewById(R.id.editTextNewBGLValue);

        btn.setOnClickListener(new View.OnClickListener() {
            float new_value = 0;
            @Override
            public void onClick(View view) {

                try {

                    new_value = Float.parseFloat(bglGraphEditText.getText().toString());
                    e.setY(new_value);
                    BGL bgl = (BGL)e.getData();
                    bgl.set_value((int)new_value);
                    ((BGLQueryActivity) getActivity()).UpdateBGL(((bgl)));

                } catch (Exception e) {}



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
    public void Test(){
        System.out.println("Test!");
    }
}
