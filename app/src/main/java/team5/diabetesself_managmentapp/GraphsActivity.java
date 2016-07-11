package team5.diabetesself_managmentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by Michael on 7/10/2016.
 */
public class GraphsActivity extends AppCompatActivity {

    private GraphView mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        // Basic graph code from GraphView, Open Source Android Graph library
        mGraph = (GraphView) findViewById(R.id.graphExample);
        DataPoint[] data = new DataPoint[30];
        for(int i = 0;i<30;i++){
            data[i] = new DataPoint(i,100 + (i*3));
        }
        BarGraphSeries<DataPoint>  series = new BarGraphSeries<DataPoint>(data);
        series.setDrawValuesOnTop(true);
        series.setSpacing(1);
        mGraph.addSeries(series);

        mGraph.setTitle("Monthly BGL Example");
        String[] labels = new String[30];
        for(int i=0;i<30;i++){
            labels[i] = "7/" + i;
        }

        // Possible Deprecated code, used for labels. Causes errors and formatting issues when using viewport's scaling.

        // StaticLabelsFormatter formatter = new StaticLabelsFormatter(mGraph);
        //formatter.setHorizontalLabels(labels);
        //mGraph.getGridLabelRenderer().setLabelFormatter(formatter);


        mGraph.getViewport().setScrollable(true);
        mGraph.getViewport().setXAxisBoundsManual(true);
        mGraph.getViewport().setMinX(0);
        mGraph.getViewport().setMaxX(5);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_save:
                //Should save data in EditText fields

                //Nav back to parent
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}