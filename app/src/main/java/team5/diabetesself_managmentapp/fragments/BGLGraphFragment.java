package team5.diabetesself_managmentapp.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.List;

import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.adapter.BGLListAdapter;

/**
 * Created by Michael on 8/7/2016.
 */
public class BGLGraphFragment extends Fragment {

    View view;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Graph();
        Chart();

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
        // Get ALL BGL List
        ArrayList<BGL> list = new ArrayList<BGL>();

        for(BGL bgl: ((QueryActivity)getActivity()).GetCompleteBGL()){
            System.out.println("ID: " + bgl.get_id());
            list.add(bgl);
        }


        LineChart chart = (LineChart)((QueryActivity)getActivity()).findViewById(R.id.graphExample);

        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDragEnabled(true);
        chart.setDrawGridBackground(false);
        //chart.setVisibleXRange(0,5);

        List<Entry> data = new ArrayList<>();
        ArrayList<String> xdata = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            data.add(new Entry(i,list.get(i).get_value()));
        }

        LineDataSet dataSet = new LineDataSet(data,"Values");

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSet.setDrawValues(true);
        dataSet.setCircleRadius(15);
        dataSet.setValueTextSize(20);

        dataSets.add(dataSet);
        XAxis xaxis = chart.getXAxis();
        xaxis.disableGridDashedLine();

        YAxis yaxis = chart.getAxisLeft();
        yaxis.setDrawGridLines(true);

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        ///chart.invalidate();
        // chart.setData(lineData);
    }
    public void Graph(){

        // Get ALL BGL List
        ArrayList<BGL> list = new ArrayList<BGL>();

        for(BGL bgl: ((QueryActivity)getActivity()).GetCompleteBGL()){
            System.out.println("ID: " + bgl.get_id());
            list.add(bgl);
        }
        DataPoint[] bglDataPoints = new DataPoint[list.size()];
        for(int i=0;i<bglDataPoints.length;i++){
            bglDataPoints[i] = new DataPoint(i,list.get(i).get_value());
        }

        // Basic graph code from GraphView, Open Source Android Graph library
        GraphView mGraph = (GraphView)((QueryActivity)getActivity()).findViewById(R.id.graphExample);
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
}
