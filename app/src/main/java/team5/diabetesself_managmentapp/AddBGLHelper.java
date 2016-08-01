package team5.diabetesself_managmentapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Calendar;


public class AddBGLHelper extends MainActivity{
    private static int bgl_progress = 0;
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

        //The below check (textView.getVisibility() != View.VISIBLE) is
        //to make sure to only call the toggleNoticeVisibility() method ONCE in the
        //0-70 progress of the progress bar.

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

    public static void AddNewBGL(SeekBar sb) {

        sb.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        arc.setProgress(progress);
                        toggleNoticePerProgress(progress);
                        bgl_progress = progress;
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar){
                        seekBar.setProgress(bgl_progress);
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar){
                        seekBar.setProgress(bgl_progress);
                    }
                }
        );


        String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        time.setText(date);
    }


    public static void hideFragment(FragmentManager fm, Fragment fr){
        toggleFragment(fm, fr, "hide");
    }


    public static void showFragment(FragmentManager fm, Fragment fr){
        toggleFragment(fm, fr, "show");
    }

    // str: "show" or "hide"
    private static void toggleFragment(FragmentManager fm, Fragment fr, String str){

        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        if(str.equalsIgnoreCase("hide"))
            ft.hide(fr);
        else if(str.equalsIgnoreCase("show"))
            ft.show(fr);

        ft.commit();
    }
}
