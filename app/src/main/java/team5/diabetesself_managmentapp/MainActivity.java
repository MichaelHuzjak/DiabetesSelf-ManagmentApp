package team5.diabetesself_managmentapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ArcProgress arc;
    private int seekBarProgress = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button buttonLogEvent = (Button) findViewById(R.id.buttonLogEvent);
        buttonLogEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void toggleNoticeVisibility(int progress) {
        TextView low = (TextView) findViewById(R.id.textViewLowNotice);
        TextView mid = (TextView) findViewById(R.id.textViewMediumNotice);
        TextView norm = (TextView) findViewById(R.id.textViewNormalNotice);
        TextView high = (TextView) findViewById(R.id.textViewHighNotice);
        TextView extreme = (TextView) findViewById(R.id.textViewVeryHighNotice);
        TextView doc = (TextView) findViewById(R.id.textViewDoctorNotice);
        if (progress <= 70) {
            low.setVisibility(View.VISIBLE);
            doc.setVisibility(View.INVISIBLE);
            mid.setVisibility(View.INVISIBLE);
            norm.setVisibility(View.INVISIBLE);
            high.setVisibility(View.INVISIBLE);
            extreme.setVisibility(View.INVISIBLE);

        } else if (progress >= 70 && progress <= 90) {
            low.setVisibility(View.INVISIBLE);
            doc.setVisibility(View.INVISIBLE);
            mid.setVisibility(View.VISIBLE);
            norm.setVisibility(View.INVISIBLE);
            high.setVisibility(View.INVISIBLE);
            extreme.setVisibility(View.INVISIBLE);
        } else if (progress >= 90 && progress <= 160) {
            low.setVisibility(View.INVISIBLE);
            doc.setVisibility(View.INVISIBLE);
            mid.setVisibility(View.INVISIBLE);
            norm.setVisibility(View.VISIBLE);
            high.setVisibility(View.INVISIBLE);
            extreme.setVisibility(View.INVISIBLE);
        } else if (progress >= 160 && progress <= 240) {
            low.setVisibility(View.INVISIBLE);
            doc.setVisibility(View.INVISIBLE);
            mid.setVisibility(View.INVISIBLE);
            norm.setVisibility(View.INVISIBLE);
            high.setVisibility(View.VISIBLE);
            extreme.setVisibility(View.INVISIBLE);
        } else if (progress >= 240 && progress <= 300) {
            low.setVisibility(View.INVISIBLE);
            doc.setVisibility(View.INVISIBLE);
            mid.setVisibility(View.INVISIBLE);
            norm.setVisibility(View.INVISIBLE);
            high.setVisibility(View.INVISIBLE);
            extreme.setVisibility(View.VISIBLE);
        } else {
            low.setVisibility(View.INVISIBLE);
            doc.setVisibility(View.VISIBLE);
            mid.setVisibility(View.INVISIBLE);
            norm.setVisibility(View.INVISIBLE);
            high.setVisibility(View.INVISIBLE);
            extreme.setVisibility(View.INVISIBLE);

        }
    }

    public void AddNewBGL(View v) {

        SeekBar bglSelector = (SeekBar) findViewById(R.id.seekBar);
        TextView addBGLTextView = (TextView) findViewById(R.id.textViewAddBGL);
        EditText meanEditText = (EditText) findViewById(R.id.editTextMean);
        TextView meanTextView = (TextView) findViewById(R.id.textViewMean);

        if (bglSelector.getVisibility() == View.INVISIBLE) {
            arc = (ArcProgress) findViewById(R.id.arc_progress);


            addBGLTextView.setText(R.string.setNewBglString);
            meanEditText.setVisibility(View.INVISIBLE);
            meanTextView.setVisibility(View.INVISIBLE);
            bglSelector.setVisibility(View.VISIBLE);
            bglSelector.setProgress(0);
            arc.setProgress(0);

            bglSelector.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            arc.setProgress(progress);
                            seekBarProgress = progress;
                            toggleNoticeVisibility(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );


        } else {
            TextView time = (TextView) findViewById(R.id.textViewLastEnteredTime);
            String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            addBGLTextView.setText(R.string.addNewBglString);
            arc.setProgress(bglSelector.getProgress());
            bglSelector.setVisibility(View.INVISIBLE);
            meanEditText.setVisibility(View.VISIBLE);
            meanTextView.setVisibility(View.VISIBLE);
            time.setText(date);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        // Disable visibility of save menu item on the main activity
        MenuItem saveItem = menu.findItem(R.id.action_save);
        saveItem.setVisible(false);
        saveItem.setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://team5.diabetesself_managmentapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://team5.diabetesself_managmentapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    void onGraphClick(View v){
        if(v.getId() == R.id.bGraph){
            Intent i = new Intent(MainActivity.this, Graphs.class);
            startActivity(i);
        }
    }
}
