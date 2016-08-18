package team5.diabetesself_managmentapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Medication {
    public static final Parcelable.Creator<Medication> CREATOR
            = new Parcelable.Creator<Medication>() {
        public Medication createFromParcel(Parcel in) {
            return new Medication(in.readInt(),in.readString(),in.readString(),in.readInt(),in.readInt());
        }
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };
    private int _id;
    private int _amount;
    private String _description;
    private Date _datetime;
    private int _prescriptionId;

    public int get_prescriptionId() {
        return _prescriptionId;
    }

    public void set_prescriptionId(int _prescriptionId) {
        this._prescriptionId = _prescriptionId;
    }

    public Date get_datetime() {
        return _datetime;
    }

    public void set_datetime(Date _datetime) {
        this._datetime = _datetime;
    }

    public int get_amount() {
        return _amount;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Medication(){}
    public Medication(int id,String desc,Date date, int amount,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _amount = amount;
        _datetime =date;
    }
    public Medication(int id,String desc,Date date, int amount){
        _id = id;
        _description = desc;
        _amount = amount;
        _datetime =date;
    }
    private Medication(int id, String desc, String date, int amount, int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _amount = amount;
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:aa");
        try {
            _datetime = format.parse(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
    private String GetDateToString(){
        return new SimpleDateFormat("MM-dd-yyyy").format(_datetime);
    }
    private String GetTimeToString(){
        return new SimpleDateFormat("hh:mm:aa").format(_datetime);
    }
    public void ChangeDate(String date){
        String time = GetTimeToString();
        String dateTime = date + " " + time;
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:aa");
        try {
            _datetime = format.parse(dateTime);
        }catch(ParseException e){
            System.out.println("error with date");
            e.printStackTrace();
        }
    }
    public void ChangeTime(String time){
        String dateTime = GetDateToString() + " " + time;
        DateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:aa");
        try {
            _datetime = format.parse(dateTime);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public String GetFormatedDate(){
        return new SimpleDateFormat("MM-dd-yyyy hh:mm:aa").format(_datetime);
    }


}
