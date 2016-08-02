package team5.diabetesself_managmentapp.adapter;

/**
 * Created by Joshua on 7/28/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import team5.diabetesself_managmentapp.utils.LogEventConstant;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.LogEventModel;
import team5.diabetesself_managmentapp.viewHolder.LogEventViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LogEventAdapter extends RecyclerView.Adapter<LogEventViewHolder>
{
	private LayoutInflater inflater = null;
	private List<LogEventModel> logEventModelList;
	private EditText etDate;
	private EditText etTime;
	private Date cal = new GregorianCalendar().getTime();
	private SimpleDateFormat sdf;

	public LogEventAdapter(Context context, List<LogEventModel> logEventModelList)
	{
		this.logEventModelList = logEventModelList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public LogEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = null;

		if(viewType == LogEventConstant.DIET)
		{
			view = inflater.inflate(R.layout.diet_fragment, parent, false);

			// GET THE CURRENT DATE TO BE DISPLAYED INITIALLY IN THE TEXTVIEW
			etDate = (EditText)view.findViewById(R.id.editTextDateDiet);
			etTime = (EditText)view.findViewById(R.id.editTextTimeDiet);
			cal.getTime();

		}
		else if(viewType == LogEventConstant.EXERCISE)
		{
			view = inflater.inflate(R.layout.exercise_fragment, parent, false);

			// GET THE CURRENT DATE TO BE DISPLAYED INITIALLY IN THE TEXTVIEW
			etDate = (EditText)view.findViewById(R.id.editTextDateExercise);
			etTime = (EditText)view.findViewById(R.id.editTextTimeExercise);
			cal.getTime();
		}
		else if(viewType == LogEventConstant.MEDICATION)
		{
			view = inflater.inflate(R.layout.medication_fragment, parent, false);

			// GET THE CURRENT DATE TO BE DISPLAYED INITIALLY IN THE TEXTVIEW
			etDate = (EditText)view.findViewById(R.id.editTextDateMeds);
			etTime = (EditText)view.findViewById(R.id.editTextTimeMeds);
			cal.getTime();
		}

		sdf = new SimpleDateFormat("MM/dd/yyyy");
		etDate.setText(sdf.format(cal.getTime()));

		sdf = new SimpleDateFormat("hh:mm:aa");
		etTime.setText(sdf.format(cal.getTime()));

		return new LogEventViewHolder(view, viewType);
	}

	@Override
	public void onBindViewHolder(LogEventViewHolder holder, int position)
	{
		holder.multipleContent.setText(logEventModelList.get(position).modelContent);
	}

	@Override
	public int getItemCount()
	{
		return (logEventModelList != null && logEventModelList.size() > 0 ) ? logEventModelList.size() : 0;
	}

	@Override
	public int getItemViewType(int position)
	{
		LogEventModel logEventModel = logEventModelList.get(position);

		if (logEventModel != null)
			return logEventModel.type;

		return super.getItemViewType(position);
	}

	public String getItemViewString(int position)
	{
		LogEventModel logEventModel = logEventModelList.get(position);

		if (logEventModel != null)
			return logEventModel.modelContent;

		return "Empty";
	}

	public LogEventModel getItem(int position)
	{
		return logEventModelList.get(position);
	}

}
