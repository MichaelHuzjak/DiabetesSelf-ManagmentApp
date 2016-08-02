package team5.diabetesself_managmentapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import team5.diabetesself_managmentapp.R;

/**
 * Created by Joshua on 7/28/2016.
 */
public class DatePickerFragment extends DialogFragment
{
	private Activity mActivity;
	private DatePickerDialog.OnDateSetListener mListener;

	/* THE FRAGMENT IS ATTACHED TO THE ACTIVITY THAT INVOKED THE
	FRAGMENTS'S CREATION
	 */
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mActivity = activity;

		// This error will remind you to implement an OnDateSetListener in your Activity if you forget
		try
		{
			mListener = (DatePickerDialog.OnDateSetListener)activity; // RECALL THAT ACTIVITY IMPLEMENTS THE LISTENER
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement OnDateSetListener");
		}
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// GET THE CURRENT DATE
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// CURRENT DATE IS DEFAULT DATE FOR DATEPICKERDIALOG
		// CREATE AN INSTANCE OF A DATEPICKERDIALOG AND THEN RETURN IT
		return new DatePickerDialog(mActivity, mListener, year, month, day);
	}

}
