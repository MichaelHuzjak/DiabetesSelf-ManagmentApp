package team5.diabetesself_managmentapp.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import team5.diabetesself_managmentapp.AddBGLHelper;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

public class BGLAdapter extends RecyclerView.Adapter<BGLAdapter.ViewHolder>{
    private ArrayList<BGLEntryModel> list;
    private int pos;
    private ArrayList<BGLAdapter.ViewHolder> vh;

    public BGLAdapter(ArrayList<BGLEntryModel> list){
        this.list = list;
        vh = new ArrayList<ViewHolder>();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public SeekBar seek;
        public ImageButton remove;
        private EditText etDate, etTime;

        public ViewHolder(View itemView){
            super(itemView);
            seek = (SeekBar)itemView.findViewById(R.id.seekBarForBGL);
            remove = (ImageButton)itemView.findViewById(R.id.imageButtonRemoveBGLEntry);
            etDate = (EditText)itemView.findViewById(R.id.EditTextBGLDate);
            etTime = (EditText)itemView.findViewById(R.id.EditTextBGLTime);
            setRemoveFunction();
        }
        private void syncEntries(String date, String time, int progress){
            seek.setProgress(progress);
            etDate.setText(date);
            etTime.setText(time);
        }
        private void setListeners(final int position){

            list.get(position).setTime(etTime.getText().toString());
            list.get(position).setDate(etDate.getText().toString());

            seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    AddBGLHelper.AddNewBGL(i);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    list.get(position).setProgress(seekBar.getProgress());
                    AddBGLHelper.AddNewBGL(seekBar.getProgress());
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    list.get(position).setProgress(seekBar.getProgress());
                    AddBGLHelper.AddNewBGL(seekBar.getProgress());
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
        private void setRemoveFunction(){
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(getAdapterPosition());
                    seek.setProgress(0);
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
    public BGLAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bgl_entry_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        Date cal = new GregorianCalendar().getTime();
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("MM/dd/yyyy");
        viewHolder.etDate.setText(formatter.format(cal.getTime()));

        formatter = new SimpleDateFormat("hh:mm:aa");
        viewHolder.etTime.setText(formatter.format(cal.getTime()));

        vh.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BGLAdapter.ViewHolder viewHolder, int position) {
        // If the input item is not empty, then set the editTexts and the seekbar
        // to the specified values inside the item.

        if(list.size()!=0 && !list.get(position).getTime().equals("")){
            BGLEntryModel bml = list.get(position);
            viewHolder.syncEntries(bml.getDate(),bml.getTime(),bml.getProgress());
        }else{
            // Set the seek bar to zero.
            viewHolder.seek.setProgress(0);
        }
        viewHolder.setListeners(position);
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public ArrayList<BGLEntryModel> getList(){
        return list;
    }
    //For Debugging purposes
    public void printList(){
        for(BGLEntryModel l: list){
            System.out.println("Progress: "+l.getProgress()+" || Date: "+l.getDate()+" || Time: "+l.getTime());

        }
    }

    public void clearList(){
        while(list.size() > 0){
            removeAt(0);
        }
        vh.removeAll(vh);
    }
}
