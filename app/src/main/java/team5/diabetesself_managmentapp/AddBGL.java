package team5.diabetesself_managmentapp;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


import java.text.DateFormat;
import java.util.Calendar;


public class AddBGL extends MainActivity{

    // This method traverses each present notice and toggles their visibility.
    // Only one is visible at a time.
    // int visibility: either View.VISIBLE or View.INVISIBLE
    // TextView t: the textView to toggle on or off.
    private static void toggleNoticeVisibility(int visibility, TextView t){

        TextView[] notices = {low,mid,norm,high,extreme,doc};

        int new_visibility = 0;

        if(visibility == View.VISIBLE)
            new_visibility = View.INVISIBLE;
        else
            new_visibility = View.VISIBLE;

        t.setVisibility(visibility);

        for (TextView notice : notices) {
            if (!notice.equals(t))
                notice.setVisibility(new_visibility);
        }

    }

    // Checks the progress of the SeekBar which is passed through OnSeekBarChangeListener
    // and calls toggleNoticeVisibility() to toggle the visibility of the notices.
    private static void toggleNoticePerProgress(int progress) {

        if (progress <= 70) {
            if(low.getVisibility() != View.VISIBLE)
                toggleNoticeVisibility(View.VISIBLE, low);
        } else if (progress >= 70 && progress <= 90) {
            if(mid.getVisibility() != View.VISIBLE)
                toggleNoticeVisibility(View.VISIBLE, mid);
        } else if (progress >= 90 && progress <= 160) {
            if(norm.getVisibility() != View.VISIBLE)
                toggleNoticeVisibility(View.VISIBLE, norm);
        } else if (progress >= 160 && progress <= 240) {
            if(high.getVisibility() != View.VISIBLE)
                toggleNoticeVisibility(View.VISIBLE, high);
        } else if (progress >= 240 && progress <= 300) {
            if(extreme.getVisibility() != View.VISIBLE)
                toggleNoticeVisibility(View.VISIBLE, extreme);
        } else {
            if(doc.getVisibility() != View.VISIBLE)
                toggleNoticeVisibility(View.VISIBLE, doc);
        }

    }

    public static void AddNewBGL(View v) {
        if (bglSelector.getVisibility() == View.INVISIBLE) {

            addBGLTextView.setText(R.string.setNewBglString);
            meanEditText.setVisibility(View.INVISIBLE);
            meanTextView.setVisibility(View.INVISIBLE);
            bglSelector.setVisibility(View.VISIBLE);
            bglSelector.setProgress(0);
            arc.setProgress(0);
            toggleNoticePerProgress(0);

            bglSelector.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            arc.setProgress(progress);
                            toggleNoticePerProgress(progress);
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar){}
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar){}
                    }
            );


        } else {

            String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            addBGLTextView.setText(R.string.addNewBglString);
            arc.setProgress(bglSelector.getProgress());
            bglSelector.setVisibility(View.INVISIBLE);
            meanEditText.setVisibility(View.VISIBLE);
            meanTextView.setVisibility(View.VISIBLE);
            time.setText(date);
        }
    }

}
