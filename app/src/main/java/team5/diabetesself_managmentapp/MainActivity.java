package team5.diabetesself_managmentapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.Calendar;



public class MainActivity extends AppCompatActivity {
    ArcProgress arc;
    int seekBarProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void toggleNoticeVisibility(int progress){
        TextView low = (TextView)findViewById(R.id.textViewLowNotice);
        TextView mid = (TextView)findViewById(R.id.textViewMediumNotice);
        TextView norm = (TextView)findViewById(R.id.textViewNormalNotice);
        TextView high = (TextView)findViewById(R.id.textViewHighNotice);
        TextView extreme = (TextView)findViewById(R.id.textViewVeryHighNotice);
        TextView doc = (TextView)findViewById(R.id.textViewDoctorNotice);
        if(progress<=70){
            low.setVisibility(low.VISIBLE);
            doc.setVisibility(doc.INVISIBLE);
            mid.setVisibility(mid.INVISIBLE);
            norm.setVisibility(norm.INVISIBLE);
            high.setVisibility(high.INVISIBLE);
            extreme.setVisibility(extreme.INVISIBLE);

        }else if(progress >= 70 && progress <= 90){
            low.setVisibility(low.INVISIBLE);
            doc.setVisibility(doc.INVISIBLE);
            mid.setVisibility(mid.VISIBLE);
            norm.setVisibility(norm.INVISIBLE);
            high.setVisibility(high.INVISIBLE);
            extreme.setVisibility(extreme.INVISIBLE);
        }else if(progress >= 90 && progress <= 160){
            low.setVisibility(low.INVISIBLE);
            doc.setVisibility(doc.INVISIBLE);
            mid.setVisibility(mid.INVISIBLE);
            norm.setVisibility(norm.VISIBLE);
            high.setVisibility(high.INVISIBLE);
            extreme.setVisibility(extreme.INVISIBLE);
        }else if(progress >= 160 && progress <= 240){
            low.setVisibility(low.INVISIBLE);
            doc.setVisibility(doc.INVISIBLE);
            mid.setVisibility(mid.INVISIBLE);
            norm.setVisibility(norm.INVISIBLE);
            high.setVisibility(high.VISIBLE);
            extreme.setVisibility(extreme.INVISIBLE);
        }else if(progress >= 240 && progress <= 300){
            low.setVisibility(low.INVISIBLE);
            doc.setVisibility(doc.INVISIBLE);
            mid.setVisibility(mid.INVISIBLE);
            norm.setVisibility(norm.INVISIBLE);
            high.setVisibility(high.INVISIBLE);
            extreme.setVisibility(extreme.VISIBLE);
        }else{
            low.setVisibility(low.INVISIBLE);
            doc.setVisibility(doc.VISIBLE);
            mid.setVisibility(mid.INVISIBLE);
            norm.setVisibility(norm.INVISIBLE);
            high.setVisibility(high.INVISIBLE);
            extreme.setVisibility(extreme.INVISIBLE);

        }
    }
    public void AddNewBGL(View v){

        SeekBar bglSelector = (SeekBar)findViewById(R.id.seekBar);
        TextView txt = (TextView)findViewById(R.id.textView9);

        if(bglSelector.getVisibility()==v.INVISIBLE) {
            bglSelector.setVisibility(v.VISIBLE);
            bglSelector.setProgress(0);
            txt.setText("Set New BGL");
            arc =(ArcProgress)findViewById(R.id.arc_progress);
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



        }
        else {
            TextView time = (TextView)findViewById(R.id.textViewLastEnteredTime);
            txt.setText("Add New BGL");
            arc.setProgress(bglSelector.getProgress());
            bglSelector.setVisibility(v.INVISIBLE);

            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
            time.setText(mydate);
        }
    }
}
