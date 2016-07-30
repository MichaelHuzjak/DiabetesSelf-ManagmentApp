package team5.diabetesself_managmentapp.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.utils.LogEventConstant;

/**
 * Created by Joshua Dixon on 7/28/2016.
 */
public class LogEventViewHolder extends RecyclerView.ViewHolder {

    public TextView multipleContent;

    private int type;

    public LogEventViewHolder(View itemView, int type) {
        super(itemView);

        if(type == LogEventConstant.DIET)
        {
            multipleContent = (TextView)itemView.findViewById(R.id.textEditDiet);
        }
        else if(type == LogEventConstant.EXERCISE)
        {
            multipleContent = (TextView)itemView.findViewById(R.id.textEditExercise);
        }
        else if(type == LogEventConstant.MEDICATION)
        {
            multipleContent = (TextView)itemView.findViewById(R.id.textEditMedication);
        }
    }
}
