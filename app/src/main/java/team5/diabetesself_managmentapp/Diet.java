package team5.diabetesself_managmentapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Diet {
    public static final Parcelable.Creator<Diet> CREATOR
            = new Parcelable.Creator<Diet>() {
        public Diet createFromParcel(Parcel in) {
            return new Diet(in.readInt(),in.readString(),in.readString(),in.readInt(),in.readInt());
        }
        public Diet[] newArray(int size) {
            return new Diet[size];
        }
    };
    private int _id;
    private int _amount;
    private String _description;
    private Date _dateTime;
    private int _prescriptionId;

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_prescriptionId() {
        return _prescriptionId;
    }

    public void set_prescriptionId(int _prescriptionId) {
        this._prescriptionId = _prescriptionId;
    }

    public Date get_date() {
        return _dateTime;
    }

    public void set_date(Date _date) {
        this._dateTime = _date;
    }

    public int get_amount() {
        return _amount;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public Diet(){}
    public Diet(int id, String desc, Date date,int amount){
        _id = id;
        _description = desc;
        _amount = amount;
        _dateTime = date;
    }
    public Diet(int id, String desc, Date date,int amount,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _amount = amount;
        _dateTime = date;
    }
    public Diet(int id, String desc, String date,int amount,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _amount = amount;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            _dateTime = format.parse(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public String GetDateToString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(_dateTime);
    }
    public String GetTimeToString(){
        return new SimpleDateFormat("hh:mm:aa").format(_dateTime);
    }
    public void ChangeDate(String date){
        String time = GetTimeToString();
        String dateTime = date + " " + time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            _dateTime = format.parse(dateTime);
        }catch(ParseException e){
            System.out.println("error with date");
            e.printStackTrace();
        }
    }
    public void ChangeTime(String time){
        String dateTime = GetDateToString() + " " + time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            _dateTime = format.parse(dateTime);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public String GetFormatedDate(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:aa").format(_dateTime);
    }
}
