package team5.diabetesself_managmentapp.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;


import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.fragments.BGLListFragment;

public class BGLListAdapter extends RecyclerView.Adapter<BGLListAdapter.ViewHolder>{
    private ArrayList<BGL> list;
    private int pos;
    private ArrayList<BGLListAdapter.ViewHolder> vh;
    Context Context;
    public BGLListAdapter(ArrayList<BGL> list,Context context){
        this.list = list;
        vh = new ArrayList<ViewHolder>();
        Context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private EditText etDate, etTime, etValue;

        public ViewHolder(View itemView){
            super(itemView);
            etDate = (EditText)itemView.findViewById(R.id.EditTextBGLListDate);
            etTime = (EditText)itemView.findViewById(R.id.EditTextBGLListTime);
            etValue = (EditText)itemView.findViewById(R.id.textBGLListValue);
            setUpdateButton();
            //setRemoveFunction();
        }
        private void syncEntries(String date, String time, int value){
            etValue.setText(""+value);
            etDate.setText(date);
            etTime.setText(time);
        }
        private void setListeners(final int position){

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
            ImageButton updateButton = (ImageButton)itemView.findViewById(R.id.buttonUpdateBGL);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateBGL(getAdapterPosition());
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
    public BGLListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bgllist_entry_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        //Date cal = new GregorianCalendar().getTime();
        //SimpleDateFormat formatter;

        //formatter = new SimpleDateFormat("MM/dd/yyyy");
        //viewHolder.etDate.setText(formatter.format(cal.getTime()));

        //formatter = new SimpleDateFormat("hh:mm:aa");
        //viewHolder.etTime.setText(formatter.format(cal.getTime()));

        vh.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BGLListAdapter.ViewHolder viewHolder, int position) {
        // If the input item is not empty, then set the editTexts and the seekbar
        // to the specified values inside the item.
        if(list.size()!=0 ){
            BGL bgl = list.get(position);
            viewHolder.syncEntries(bgl.GetDateToString(),bgl.GetTimeToString(),bgl.get_value());
        }
        viewHolder.setListeners(position);
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public ArrayList<BGL> getList(){
        return list;
    }


    public void clearList(){
        while(list.size() > 0){
            removeAt(0);
        }
        vh.removeAll(vh);
    }

    public void UpdateBGL(int position){
        final BGL bgl = list.get(position);
        if(Context instanceof QueryActivity) {
            ((QueryActivity) Context).UpdateBGL(bgl);
        }
    }
}
