package team5.diabetesself_managmentapp.adapter;

import android.content.Context;
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
import android.widget.Spinner;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private Context context;

    public PrescriptionAdapter(Context c, ArrayList<Prescription> list){
        this.list = list;
        vh = new ArrayList<ViewHolder>();
        cal = new GregorianCalendar().getTime();
        inflater = LayoutInflater.from(c);
        context = c;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public EditText description;
        private EditText etDate, etTime;
        private ImageButton remove;
        //private RadioGroup cat, repeat;
        private Spinner day,cat;
        String[] arraySpinner;
        String[] catArray;

        public ViewHolder(View itemView){
            super(itemView);
            description = (EditText)itemView.findViewById(R.id.editTextPrescDescription);
            // etDate = (EditText)itemView.findViewById(R.id.editTextPrescDate);
            etTime = (EditText)itemView.findViewById(R.id.editTextPrescTime);
            remove = (ImageButton)itemView.findViewById(R.id.imageButtonRemovePresc);
            //cat = (RadioGroup)itemView.findViewById(R.id.radioGroupCatType);
            //repeat = (RadioGroup)itemView.findViewById(R.id.radioGroupRepeat);
            this.arraySpinner = new String[] {
                    "Daily", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday","Friday","Saturday"
            };
            day = (Spinner)itemView.findViewById(R.id.spinnerPrescription);
            this.catArray = new String[] {
                    "Diet", "Exercise", "Medication", "BGL"
            };
            cat = (Spinner)itemView.findViewById(R.id.spinnerPrescriptionCat);

            ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, arraySpinner);

            ArrayAdapter<String> catAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, catArray);
            day.setAdapter(dayAdapter);
            cat.setAdapter(catAdapter);
            setRemoveFunction();

        }
        private void syncEntries(String desc,int catid, int day,String time){
            description.setText(desc);
            //cat.setSelection(catid);

            etTime.setText(time);
        }
        public void setListeners(){

            //Set the date and time of the model initially in case the user doesn't edit any of them
            //and wants to leave them as default.

//            list.get(getAdapterPosition()).set_date(etDate.getText().toString());
            list.get(getAdapterPosition()).set_time(etTime.getText().toString());

            cat.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    System.out.println("Changing!");
                    list.get(getAdapterPosition()).set_categoryId(position+1);
                    list.get(getAdapterPosition()).set_category(catArray[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here

                }

            });
            day.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code here
                    System.out.println("Changing!");

                    list.get(getAdapterPosition()).ChangeDay(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            // Listens for category change.
//            cat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                    String new_catagory = "None";
//                    switch(radioGroup.getCheckedRadioButtonId()){
//                        case R.id.radioButtonDiet:
//                            new_catagory="Diet";
//                            break;
//                        case R.id.radioButtonExer:
//                            new_catagory="Exercise";
//                            break;
//                        case R.id.radioButtonMed:
//                            new_catagory="Medication";
//                            break;
//                        default:
//                            break;
//                    }
//                    list.get(getAdapterPosition()).set_category(new_catagory);
//                }
//            });

//            repeat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                    String new_repeat = "None";
//                    switch(radioGroup.getCheckedRadioButtonId()){
//                        case R.id.radioButtonDaily:
//                            new_repeat="Daily";
//                            break;
//                        case R.id.radioButtonWeekly:
//                            new_repeat="Weekly";
//                            break;
//                        default:
//                            break;
//                    }
//                    list.get(getAdapterPosition()).set_repeat(new_repeat);
//                }
//            });

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
                    list.get(getAdapterPosition()).ChangeTime(charSequence.toString());
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    list.get(getAdapterPosition()).ChangeTime(editable.toString());
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
        if(position < 0 || position >= list.size())
            return;
        list.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public PrescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.prescription_entry_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        formatter = new SimpleDateFormat("MM/dd/yyyy");
//        viewHolder.etDate.setText(formatter.format(cal.getTime()));

        formatter = new SimpleDateFormat("hh:mm:aa");
        viewHolder.etTime.setText(formatter.format(cal.getTime()));

        vh.add(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final PrescriptionAdapter.ViewHolder viewHolder, int position) {

        if(list.size()!=0 ){
            Prescription p = list.get(position);
            if(p.get_description() != null && p.get_repeat() != null && p.get_repeat().length() > 0 && p.GetTime() != null){
                viewHolder.syncEntries(p.get_description(),p.get_categoryId(),0,p.GetTime());
            }
        }
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
