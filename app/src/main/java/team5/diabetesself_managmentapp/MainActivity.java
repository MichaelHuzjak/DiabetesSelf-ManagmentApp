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

public class MainActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Toolbar toolbar;

    static ArcProgress arc;
    static SeekBar bglSelector;
    static TextView addBGLTextView;
    static EditText meanEditText;
    static TextView meanTextView;
    static TextView time;
    static TextView low, mid, norm, high, extreme, doc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();

        setSupportActionBar(toolbar);

        Button buttonAddBGLEvent = (Button) findViewById(R.id.ButtonAddBGL);
        buttonAddBGLEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBGL.AddNewBGL(view);
            }
        });

        Button buttonLogEvent = (Button) findViewById(R.id.buttonLogEvent);
        buttonLogEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
        Button buttonGraphs = (Button) findViewById(R.id.bGraph);
        buttonGraphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GraphsActivity.class));
            }
        });

        Button buttonQuery = (Button) findViewById(R.id.buttonQuery);
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QueryActivity.class));
            }
        });

        Button buttonPrescription = (Button) findViewById(R.id.buttonPrescription);
        buttonPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PrescribeActivity.class));
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initObjects(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        time = (TextView) findViewById(R.id.textViewLastEnteredTime);
        addBGLTextView = (TextView) findViewById(R.id.textViewAddBGL);
        bglSelector = (SeekBar) findViewById(R.id.seekBar);
        meanEditText = (EditText) findViewById(R.id.editTextMean);
        meanTextView = (TextView) findViewById(R.id.textViewMean);
        arc = (ArcProgress) findViewById(R.id.arc_progress);
        low =(TextView)findViewById(R.id.textViewLowNotice);
        mid = (TextView) findViewById(R.id.textViewMediumNotice);
        norm = (TextView) findViewById(R.id.textViewNormalNotice);
        high = (TextView) findViewById(R.id.textViewHighNotice);
        extreme = (TextView) findViewById(R.id.textViewVeryHighNotice);
        doc = (TextView) findViewById(R.id.textViewDoctorNotice);
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

}
