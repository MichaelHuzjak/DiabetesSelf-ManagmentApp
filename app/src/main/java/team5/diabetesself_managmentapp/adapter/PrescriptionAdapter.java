package team5.diabetesself_managmentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import team5.diabetesself_managmentapp.AddBGLHelper;
import team5.diabetesself_managmentapp.Prescription;
import team5.diabetesself_managmentapp.R;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder>{
    private ArrayList<Prescription> list;
    private int pos;
    private ArrayList<PrescriptionAdapter.ViewHolder> vh;
    private LayoutInflater inflater = null;
    private SimpleDateFormat formatter;
    private Date cal;
    private Button setPresc;
    Context context;

    public PrescriptionAdapter(Context c, ArrayList<Prescription> list){
        this.list = list;
        vh = new ArrayList<ViewHolder>();
        cal = new GregorianCalendar().getTime();
        context = c;
        inflater = LayoutInflater.from(c);

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public EditText description;
        private EditText etDate, etTime;
        private ImageButton remove;
        private RadioGroup cat, repeat;
        private Spinner days_spinner;
        private ArrayAdapter<CharSequence> adapter;

        public ViewHolder(View itemView){
            super(itemView);
            description = (EditText)itemView.findViewById(R.id.editTextPrescDescription);
            etTime = (EditText)itemView.findViewById(R.id.editTextPrescTime);
            remove = (ImageButton)itemView.findViewById(R.id.imageButtonRemovePresc);
            cat = (RadioGroup)itemView.findViewById(R.id.radioGroupCatType);
            repeat = (RadioGroup)itemView.findViewById(R.id.radioGroupRepeat);

            days_spinner = (Spinner)itemView.findViewById(R.id.spinnerDayPicker);
            adapter = ArrayAdapter.createFromResource(context, R.array.weak_days, R.layout.spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            days_spinner.setAdapter(adapter);

            setRemoveFunction();

        }
        public void setListeners(){

            list.get(getAdapterPosition()).set_time(etTime.getText().toString());
            list.get(getAdapterPosition()).set_day(days_spinner.getSelectedItem().toString());
            list.get(getAdapterPosition()).set_repeat("Daily"); // Selected by default

            days_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    list.get(getAdapterPosition()).set_day(days_spinner.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            // Listens for category change.
            cat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    String new_catagory = "None";
                    switch(radioGroup.getCheckedRadioButtonId()){
                        case R.id.radioButtonDiet:
                            new_catagory="Diet";
                            break;
                        case R.id.radioButtonExer:
                            new_catagory="Exercise";
                            break;
                        case R.id.radioButtonMed:
                            new_catagory="Medication";
                            break;
                        default:
                            break;
                    }
                    list.get(getAdapterPosition()).set_category(new_catagory);
                }
            });

            repeat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    String new_repeat = "None";
                    switch(radioGroup.getCheckedRadioButtonId()){
                        case R.id.radioButtonDaily:
                            new_repeat="Daily";
                            days_spinner.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.radioButtonWeekly:
                            new_repeat="Weekly";
                            days_spinner.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                    list.get(getAdapterPosition()).set_repeat(new_repeat);
                }
            });

            // Listens for description change and sets the corresponding model variables.
            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(getAdapterPosition()).set_description(editable.toString());
                }
            });
//            etDate.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    list.get(getAdapterPosition()).set_date(charSequence.toString());
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    list.get(getAdapterPosition()).set_date(editable.toString());
//                }
//            });
            etTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    list.get(getAdapterPosition()).set_time(charSequence.toString());
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(getAdapterPosition()).set_time(editable.toString());
                }
            });
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
    public PrescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.prescription_entry_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


//        formatter = new SimpleDateFormat("MM/dd/yyyy");
//        viewHolder.etDate.setText(formatter.format(cal.getTime()));

        formatter = new SimpleDateFormat("hh:mm:aa");
        viewHolder.etTime.setText(formatter.format(cal.getTime()));

        vh.add(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final PrescriptionAdapter.ViewHolder viewHolder, int position) {

        viewHolder.setListeners();

    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    public ArrayList<Prescription> getList(){
        return list;
    }



    public void clearList(){
        while(list.size() > 0){
            removeAt(0);
        }
        vh.removeAll(vh);
    }
}
