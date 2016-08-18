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


import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.LogEventModel;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder>{
    private final ArrayList<LogEventModel> list;
    private int pos;
    private final ArrayList<MedicationAdapter.ViewHolder> vh;
    private final Context Context;
    private final ArrayList<String> medicationID;

    public MedicationAdapter(ArrayList<LogEventModel> list, ArrayList<String> medicationID,  Context context)
    {
        this.list = list;
        this.medicationID = medicationID;
        vh = new ArrayList<ViewHolder>();
        Context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final EditText etDate, etTime, etValue,etDesc;

        public ViewHolder(View itemView){
            super(itemView);
            etDesc = (EditText)itemView.findViewById(R.id.EditTextMedDescription);
            etDate = (EditText)itemView.findViewById(R.id.EditTextMedListDate);
            etTime = (EditText)itemView.findViewById(R.id.EditTextMedListTime);
            etValue = (EditText)itemView.findViewById(R.id.textMedtListValue);
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
            ImageButton updateButton = (ImageButton)itemView.findViewById(R.id.buttonUpdateMed);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateMedication(getAdapterPosition());
                }
            });
        }

    }

    private void removeAt(int position)
    {
        if(position < 0)
            return;

        list.remove(position);
        medicationID.remove(position);

        notifyItemRemoved(position);
    }

    @Override
    public MedicationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.medlist_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        vh.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MedicationAdapter.ViewHolder viewHolder, int position)
    {
        // If the input item is not empty, then set the editTexts and the seekbar
        // to the specified values inside the item.
        if(list.size()!=0 )
        {
            LogEventModel med = list.get(position);
            viewHolder.syncEntries(med.getDescription(),med.getDate(),med.getTime(),med.getValue());
        }

        viewHolder.setListeners(position);
    }

    @Override
    public int getItemCount()
    {
        if(list==null)
            return 0;

        return list.size();
    }

    public ArrayList<LogEventModel> getList()
    {
        return list;
    }

    public ArrayList<String> getDietIDList()
    {
        return medicationID;
    }

    public void clearList()
    {
        while(list.size() > 0)
        {
            removeAt(0);
        }

        vh.removeAll(vh);
    }

    private void UpdateMedication(int position)
    {
        System.out.println("MedicationAdapter: UpdateMedication()");

        final LogEventModel med = list.get(position);
        final String medicationId = medicationID.get(position);

        ((MedicationQueryActivity) Context).UpdateMedication(med, medicationId);
    }
}
