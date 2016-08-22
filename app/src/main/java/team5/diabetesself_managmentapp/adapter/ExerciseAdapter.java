package team5.diabetesself_managmentapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.ArrayList;


import team5.diabetesself_managmentapp.ExerciseQueryActivity;
import team5.diabetesself_managmentapp.model.LogEventModel;
import team5.diabetesself_managmentapp.R;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{
    private final ArrayList<LogEventModel> list;
    private int pos;
    private final ArrayList<ExerciseAdapter.ViewHolder> vh;
    private final Context Context;
    private final ArrayList<String> exerciseID;

    public ExerciseAdapter(ArrayList<LogEventModel> list, ArrayList<String> exerciseID, Context context){
        this.list = list;
        this.exerciseID = exerciseID;
        vh = new ArrayList<>();
        Context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final EditText etDate, etTime, etValue,etDesc;

        public ViewHolder(View itemView){
            super(itemView);
            etDesc = (EditText)itemView.findViewById(R.id.EditTextExerDescription);
            etDate = (EditText)itemView.findViewById(R.id.EditTextExerListDate);
            etTime = (EditText)itemView.findViewById(R.id.EditTextExerListTime);
            etValue = (EditText)itemView.findViewById(R.id.EditTextExerValue);
            setUpdateButton();
            //setRemoveFunction();
        }
        private void syncEntries(String desc,String date, String time, int value){
            etDesc.setText(desc);
            etValue.setText(""+value);
            etDate.setText(date);
            etTime.setText(time);
        }

        private void setListeners(final int position){

            etValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().equals(""))
                        list.get(position).setValue(Integer.parseInt(charSequence.toString()));
                    else
                        list.get(position).setValue(0);
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().equals(""))
                        list.get(position).setValue(Integer.parseInt(charSequence.toString()));
                    else
                        list.get(position).setValue(0);
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!editable.toString().equals(""))
                        list.get(position).setValue(Integer.parseInt(editable.toString()));
                    else
                        list.get(position).setValue(0);
                }
            });

            etDesc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setDescription(charSequence.toString());

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setDescription(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(position).setDescription(editable.toString());
                }
            });

            etDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setDate(charSequence.toString());

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setDate(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(position).setDate(editable.toString());
                }
            });

            etTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setTime(charSequence.toString());
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).setTime(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(position).setTime(editable.toString());
                }
            });
        }
        private void setUpdateButton(){
            ImageButton updateButton = (ImageButton)itemView.findViewById(R.id.buttonUpdateExer);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateExercise(getAdapterPosition());
                }
            });
        }

    }

    private void removeAt(int position)
    {
        if(position < 0)
            return;

        list.remove(position);
        exerciseID.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exerlist_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        vh.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ExerciseAdapter.ViewHolder viewHolder, int position) {
        // If the input item is not empty, then set the editTexts and the seekbar
        // to the specified values inside the item.
        if(list.size()!=0 )
        {
            LogEventModel exer = list.get(position);
            viewHolder.syncEntries(exer.getDescription(),exer.getDate(),exer.getTime(), exer.getValue());
        }

        viewHolder.setListeners(position);
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public ArrayList<LogEventModel> getList()
    {
        return list;
    }


    public void clearList()
    {
        while(list.size() > 0)
        {
            removeAt(0);
        }

        vh.removeAll(vh);
    }

    private void UpdateExercise(int position)
    {
        System.out.println("DietAdapter: UpdateExercise()");

        final LogEventModel exer = list.get(position);
        final String exerciseId = exerciseID.get(position);

        ((ExerciseQueryActivity) Context).UpdateExercise(exer, exerciseId);

    }

}
