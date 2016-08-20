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



import team5.diabetesself_managmentapp.Medication;
import team5.diabetesself_managmentapp.MedicationQueryActivity;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder>{
    private ArrayList<Medication> list;
    private int pos;
    private ArrayList<MedicationAdapter.ViewHolder> vh;
    Context Context;
    public MedicationAdapter(ArrayList<Medication> list,Context context){
        this.list = list;
        vh = new ArrayList<ViewHolder>();
        Context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private EditText etDate, etTime, etValue,etDesc;

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
            etDesc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).set_description(charSequence.toString());

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).set_description(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(position).set_description(editable.toString());
                }
            });
            etValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().equals(""))
                        list.get(position).set_amount(Integer.parseInt(charSequence.toString()));
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().equals(""))
                        list.get(position).set_amount(Integer.parseInt(charSequence.toString()));
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!editable.toString().equals(""))
                        list.get(position).set_amount(Integer.parseInt(editable.toString()));
                }
            });

            etDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).ChangeDate(charSequence.toString());

                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).ChangeDate(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(position).ChangeDate(editable.toString());
                }
            });

            etTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).ChangeTime(charSequence.toString());
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(position).ChangeTime(charSequence.toString());
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(position).ChangeTime(editable.toString());
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

    public void removeAt(int position) {
        if(position < 0)
            return;
        list.remove(position);
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
    public void onBindViewHolder(final MedicationAdapter.ViewHolder viewHolder, int position) {
        // If the input item is not empty, then set the editTexts and the seekbar
        // to the specified values inside the item.
        if(list.size()!=0 ){
            Medication med = list.get(position);
            viewHolder.syncEntries(med.get_description(),med.GetDateToString(),med.GetTimeToString(),med.get_amount());
        }
        viewHolder.setListeners(position);
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public ArrayList<Medication> getList(){
        return list;
    }


    public void clearList(){
        while(list.size() > 0){
            removeAt(0);
        }
        vh.removeAll(vh);
    }

    public void UpdateMedication(int position){
        final Medication med = list.get(position);
        if(Context instanceof MedicationQueryActivity) {
            ((MedicationQueryActivity) Context).UpdateMedication(med);
        }
    }
}
