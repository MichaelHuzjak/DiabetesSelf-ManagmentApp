package team5.diabetesself_managmentapp.adapter;

/**
 * Created by Joshua on 7/28/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import team5.diabetesself_managmentapp.utils.LogEventConstant;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.LogEventModel;
import team5.diabetesself_managmentapp.viewHolder.LogEventViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LogEventAdapter extends RecyclerView.Adapter<LogEventViewHolder>
{
	private LayoutInflater inflater = null;
	private List<LogEventModel> logEventModelList;
	private EditText etDate;
	private EditText etTime;

	private EditText etDescription;
	private EditText etValue;

	private Date cal;
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

		cal = new GregorianCalendar().getTime();

		int count = logEventModelList.size()-1;

		System.out.println("class LogEventAdapter LogEventViewHolder onCreateViewHolder(). Count: " + count + " Type: " + viewType);

		if(viewType == LogEventConstant.DIET)
		{
			System.out.println("viewType == LogEventConstant.DIET");
			view = inflater.inflate(R.layout.diet_fragment, parent, false);

			// GET THE CURRENT DATE TO BE DISPLAYED INITIALLY IN THE TEXTVIEW
			etDate = (EditText)view.findViewById(R.id.editTextDateDiet);
			etTime = (EditText)view.findViewById(R.id.editTextTimeDiet);
			etDescription = (EditText)view.findViewById(R.id.editTextDietDescription);
			etValue = (EditText)view.findViewById(R.id.editTextDietQty);

			cal.getTime();

		}
		else if(viewType == LogEventConstant.EXERCISE)
		{
			System.out.println("viewType == LogEventConstant.EXERCISE");
			view = inflater.inflate(R.layout.exercise_fragment, parent, false);

			// GET THE CURRENT DATE TO BE DISPLAYED INITIALLY IN THE TEXTVIEW
			etDate = (EditText)view.findViewById(R.id.editTextDateExercise);
			etTime = (EditText)view.findViewById(R.id.editTextTimeExercise);
			etDescription = (EditText)view.findViewById(R.id.editTextExerciseDescription);
			etValue = (EditText)view.findViewById(R.id.editTextExerciseDuration);

			cal.getTime();
		}
		else if(viewType == LogEventConstant.MEDICATION)
		{
			System.out.println("viewType == LogEventConstant.MEDICATION");
			view = inflater.inflate(R.layout.medication_fragment, parent, false);

			// GET THE CURRENT DATE TO BE DISPLAYED INITIALLY IN THE TEXTVIEW
			etDate = (EditText)view.findViewById(R.id.editTextDateMeds);
			etTime = (EditText)view.findViewById(R.id.editTextTimeMeds);
			etDescription = (EditText)view.findViewById(R.id.editTextMedicationDescription);
			etValue = (EditText)view.findViewById(R.id.editTextMedicationQty);

			cal.getTime();
		}

		sdf = new SimpleDateFormat("MM/dd/yyyy");
		etDate.setText(sdf.format(cal.getTime()));
		logEventModelList.get(count).setDate(sdf.format(cal.getTime()));

		sdf = new SimpleDateFormat("hh:mm:ss aa");
		etTime.setText(sdf.format(cal.getTime()));
		logEventModelList.get(count).setTime(sdf.format(cal.getTime()));

		return new LogEventViewHolder(view, viewType,
				new MyCustomEditTextListener(LogEventConstant.DESCRIPTION),
				new MyCustomEditTextListener(LogEventConstant.VALUE),
				new MyCustomEditTextListener(LogEventConstant.DATE),
				new MyCustomEditTextListener(LogEventConstant.TIME));
	}

	@Override
	public void onBindViewHolder(LogEventViewHolder holder, int position)
	{
		System.out.println("class LogEventAdapter void onBindViewHolder(). Position: " + position + " Type: " + logEventModelList.get(position).modelContent);

		/*UPDATE THE LISTENER EVERY TIME WE BIND A NEW ITEM (A NEW ITEM WAS
		INSERTED OR THE SCREEN WAS ROTATED) SO THAT THE OBJECT OF THE ARRAY LIST IS
		UP TO DATE
		 */
		holder.descriptorEditTextListener.updatePosition(holder.getAdapterPosition());
		holder.valueEditTextListener.updatePosition(holder.getAdapterPosition());
		holder.dateEditTextListener.updatePosition(holder.getAdapterPosition());
		holder.timeEditTextListener.updatePosition(holder.getAdapterPosition());

		holder.multipleContent.setText(logEventModelList.get(position).modelContent);
		holder.description.setText(logEventModelList.get(position).description);
		holder.value.setText(logEventModelList.get(position).value);

		/* CHECK IF FOR SOME REASON, THE ONCREATEVIEW WAS SKIPPED
		 */
		if(logEventModelList.get(position).date == null || logEventModelList.get(position).time == null )
		{
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			holder.date.setText(sdf.format(cal.getTime()));

			sdf = new SimpleDateFormat("hh:mm:ss aa");
			holder.time.setText(sdf.format(cal.getTime()));
		}
		else
		{
			holder.date.setText(logEventModelList.get(position).date);
			holder.time.setText(logEventModelList.get(position).time);
		}

		System.out.println("class LogEventAdapter void onBindViewHolder(). Position: " + position + " Type: " + logEventModelList.get(position).modelContent);
		System.out.println("getDescription() " + logEventModelList.get(position).getDescription() + " " + holder.description.getText().toString());
		System.out.println("getValue()  " + logEventModelList.get(position).getValue() + " " + holder.value.getText().toString());
		System.out.println("getDate() " + logEventModelList.get(position).getDate() + " " + holder.date.getText().toString());
		System.out.println("getTime() "  +logEventModelList.get(position).getTime() + " " + holder.time.getText().toString());

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

		if(logEventModel != null)
		{
			return logEventModel.type;
		}

		return super.getItemViewType(position);
	}

	public String[] getItemViewString(int position)
	{
		LogEventModel logEventModel;

		int count = logEventModelList.size();

		String[] strings = new String[count];

		for(int i = 0; i < count; i++)
		{
			logEventModel = logEventModelList.get(position);

			if(logEventModel != null)
			{
				strings[i] = logEventModel.modelContent;
			}
			else
			{
				strings[i] = "Empty";
			}
		}

		return strings;

	}

	public LogEventModel getItem(int position)
	{
		return logEventModelList.get(position);
	}

	/* TEXTWATCHER TO BE MADE AWARE OF THE POSITION SO WHEN A NEW
	ITEM IS ATTAHED IN THE ONBINDVIEWHOLDER(), THE CURRENT POSITION WILL BE UPDATED
	IN THE VIEWHOLDER, THAT CORRESPONDS ITS MODEL IN THE ARRAY LIST AS WELL AS THE POSITION OF THE LISTENER
	SINCE ITS KEPTS BY THE VIEWHOLDER
	 */
	public class MyCustomEditTextListener implements TextWatcher
	{
		private int position;
        int type;

		public MyCustomEditTextListener(int type)
		{
			this.type = type;
		}

		public void updatePosition(int position)
		{
			this.position = position;
		}

		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
		{
			// NO OPERATION
		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
		{
			// NO OPERATION
		}

		@Override
		public void afterTextChanged(Editable editable)
		{
			System.out.println("class MyCustomEditTextListener afterTextChanged() :" + position);
			System.out.println("class MyCustomEditTextListener TextWatcher(): " +
			logEventModelList.get(position).getDescription() + " " +
			logEventModelList.get(position).getValue() + " " +
			logEventModelList.get(position).getDate() + " " +
			logEventModelList.get(position).getTime());

			if(type == LogEventConstant.DESCRIPTION)
			{
				logEventModelList.get(position).setDescription(editable.toString());
			}
			else if (type == LogEventConstant.VALUE)
			{
				logEventModelList.get(position).setValue(editable.toString());
			}
			else if (type == LogEventConstant.DATE)
			{
				logEventModelList.get(position).setDate(editable.toString());
			}
			else if (type == LogEventConstant.TIME)
			{
				logEventModelList.get(position).setTime(editable.toString());
			}

		}

	}
}
