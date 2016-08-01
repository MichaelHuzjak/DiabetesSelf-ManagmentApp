package team5.diabetesself_managmentapp.adapter;

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
    private ArrayList<BGLAdapter.ViewHolder> list;
    private int pos;


    public BGLAdapter(ArrayList<BGLAdapter.ViewHolder> list){
        this.list = list;
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

        private void setRemoveFunction(){
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeAt(getAdapterPosition());
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


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BGLAdapter.ViewHolder viewHolder, int position) {
        viewHolder.seek.setProgress(0);
        AddBGLHelper.AddNewBGL(viewHolder.seek);
        list.set(getItemCount()-1,viewHolder);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    //For Debugging purposes
    public void printList(){
        for(ViewHolder l: list)
            System.out.println("Progress: "+l.seek.getProgress()+" || Date: "+l.etDate.getText()+" || Time: "+l.etTime.getText());
    }
}
