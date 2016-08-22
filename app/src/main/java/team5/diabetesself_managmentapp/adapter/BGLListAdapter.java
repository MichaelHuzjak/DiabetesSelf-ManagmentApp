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

import team5.diabetesself_managmentapp.BGLQueryActivity;
import team5.diabetesself_managmentapp.BGL;
import team5.diabetesself_managmentapp.QueryActivity;
import team5.diabetesself_managmentapp.R;
import team5.diabetesself_managmentapp.fragments.BGLListFragment;
import team5.diabetesself_managmentapp.model.BGLEntryModel;

public class BGLListAdapter extends RecyclerView.Adapter<BGLListAdapter.ViewHolder>{
    private final ArrayList<BGLEntryModel> list;
    private int pos;
    private final ArrayList<BGLListAdapter.ViewHolder> vh;
    private final Context Context;
    private final ArrayList<String> bglID;

    public BGLListAdapter(ArrayList<BGLEntryModel> list, ArrayList<String> bglID, Context context){
        this.list = list;
        this.bglID = bglID;
        vh = new ArrayList<>();
        Context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final EditText etDate;
        private final EditText etTime;
        private final EditText etValue;

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

            etValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().equals(""))
                        list.get(position).setProgress(Integer.parseInt(charSequence.toString()));
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(!charSequence.toString().equals(""))
                        list.get(position).setProgress(Integer.parseInt(charSequence.toString()));
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    if(!editable.toString().equals(""))
                        list.get(position).setProgress(Integer.parseInt(editable.toString()));
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
            ImageButton updateButton = (ImageButton)itemView.findViewById(R.id.buttonUpdateBGL);
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateBGL(getAdapterPosition());
                }
            });
        }

    }

    private void removeAt(int position)
    {
        if(position < 0)
            return;

        list.remove(position);
        bglID.remove(position);

        notifyItemRemoved(position);
    }
    @Override
    public BGLListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.bgllist_entry_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        vh.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BGLListAdapter.ViewHolder viewHolder, int position) {
        // If the input item is not empty, then set the editTexts and the seekbar
        // to the specified values inside the item.
        if(list.size()!=0 )
        {
            BGLEntryModel bgl = list.get(position);
            viewHolder.syncEntries(bgl.getDate(),bgl.getTime(),bgl.getProgress());
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

    public ArrayList<BGLEntryModel> getList()
    {
        return list;
    }

    public ArrayList<String> getBglIDList()
    {
        return bglID;
    }

    public void clearList()
    {
        while(list.size() > 0)
        {
            removeAt(0);
        }

        vh.removeAll(vh);
    }

    private void UpdateBGL(int position)
    {
        System.out.println("BGLListAdapter: UpdateBGL(): Position " + position);

        final BGLEntryModel bgl = list.get(position);
        final String bglId = bglID.get(position);

        ((BGLQueryActivity) Context).updateBgl(bgl, bglId);

    }
}
