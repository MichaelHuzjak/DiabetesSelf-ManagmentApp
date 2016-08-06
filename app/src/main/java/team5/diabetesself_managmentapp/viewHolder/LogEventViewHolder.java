package team5.diabetesself_managmentapp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.utils.LogEventConstant;
import team5.diabetesself_managmentapp.adapter.LogEventAdapter;

/**
 * Created by Joshua Dixon on 7/28/2016.
 * An onject that describes the log event view and metadata about its place within the RecyclerView
 * that appears on the UI, similarly to a scrollable list / listview.
 */
public class LogEventViewHolder extends RecyclerView.ViewHolder {

    public TextView multipleContent;

    public EditText description;
    public EditText value;
    public EditText date;
    public EditText time;

    public final LogEventAdapter.MyCustomEditTextListener descriptorEditTextListener;
    public final LogEventAdapter.MyCustomEditTextListener valueEditTextListener;
    public final LogEventAdapter.MyCustomEditTextListener dateEditTextListener;
    public final LogEventAdapter.MyCustomEditTextListener timeEditTextListener;

    public LogEventViewHolder(View itemView, int type,
                              LogEventAdapter.MyCustomEditTextListener descriptorEditTextListener,
                              LogEventAdapter.MyCustomEditTextListener valueEditTextListener,
                              LogEventAdapter.MyCustomEditTextListener dateEditTextListener,
                              LogEventAdapter.MyCustomEditTextListener timeEditTextListener) {
        super(itemView);

        System.out.println("class LogEventViewHolder LogEventViewHolder(): Type: " + type);

        if(type == LogEventConstant.DIET)
        {
            this.multipleContent = (TextView)itemView.findViewById(R.id.textEditDiet);
            this.description = (EditText)itemView.findViewById(R.id.editTextDietDescription);
            this.value = (EditText)itemView.findViewById(R.id.editTextDietQty);
            this.date = (EditText)itemView.findViewById(R.id.editTextDateDiet);
            this.time = (EditText)itemView.findViewById(R.id.editTextTimeDiet);
        }
        else if(type == LogEventConstant.EXERCISE)
        {
            this.multipleContent = (TextView)itemView.findViewById(R.id.textEditExercise);
            this.description = (EditText)itemView.findViewById(R.id.editTextExerciseDescription);
            this.value = (EditText)itemView.findViewById(R.id.editTextExerciseDuration);
            this.date = (EditText)itemView.findViewById(R.id.editTextDateExercise);
            this.time = (EditText)itemView.findViewById(R.id.editTextTimeExercise);
        }
        else if(type == LogEventConstant.MEDICATION)
        {
            this.multipleContent = (TextView)itemView.findViewById(R.id.textEditMedication);
            this.description = (EditText)itemView.findViewById(R.id.editTextMedicationDescription);
            this.value = (EditText)itemView.findViewById(R.id.editTextMedicationQty);
            this.date = (EditText)itemView.findViewById(R.id.editTextDateMeds);
            this.time = (EditText)itemView.findViewById(R.id.editTextTimeMeds);
        }

        this.descriptorEditTextListener = descriptorEditTextListener;
        this.valueEditTextListener = valueEditTextListener;
        this.dateEditTextListener = dateEditTextListener;
        this.timeEditTextListener = timeEditTextListener;

        this.description.addTextChangedListener(descriptorEditTextListener);
        this.value.addTextChangedListener(valueEditTextListener);
        this.date.addTextChangedListener(dateEditTextListener);
        this.time.addTextChangedListener(timeEditTextListener);
    }
}
