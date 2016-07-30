package team5.diabetesself_managmentapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import java.util.Calendar;

/**
 * Created by Joshua on 7/28/2016.
 */
public class TimePickerFragment extends DialogFragment
{
	// Notice I removed "implements OnTimeSetListener" and changed the variables

	private Activity mActivity;
	private TimePickerDialog.OnTimeSetListener mListener;

	/* THE FRAGMENT IS ATTACHED TO THE ACTIVITY THAT INVOKED THE
	FRAGMENTS'S CREATION
	 */
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mActivity = activity; // THE CALLING ACTIVITY

		// This error will remind you to implement an OnTimeSetListener in your Activity if you forget
		try
		{
			mListener = (TimePickerDialog.OnTimeSetListener) activity; // RECALL THAT ACTIVITY IMPLEMENTS THE LISTENER
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 	{

		// GET THE CURRENT TIME TO BE SET AS THE DEFAULT IN THE PICKER
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// CREATE AN INSTANE OF A TIMEPICKERDIALOG AND THEN RETURN IT
		return new TimePickerDialog(mActivity, mListener, hour, minute, DateFormat.is24HourFormat(mActivity));
	}
}