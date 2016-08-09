package team5.diabetesself_managmentapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BGL {
    public static final Parcelable.Creator<BGL> CREATOR
            = new Parcelable.Creator<BGL>() {
        public BGL createFromParcel(Parcel in) {
            return new BGL(in.readInt(),in.readString(),in.readInt());
        }
        public BGL[] newArray(int size) {
            return new BGL[size];
        }
    };
    private int _id;
    private Date _date;

    public int get_value() {
        return _value;
    }

    public void set_value(int _value) {
        this._value = _value;
    }

    public Date get_date() {
        return _date;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    private int _value;

    public BGL(){}

    public BGL(int id, Date date, int value){
        _id = id;
        _date = date;
        _value = value;
    }
    public BGL(int id, String date, int value){
        _id = id;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            _date = format.parse(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        _value = value;
    }
    public String GetDateToString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(_date);
    }
    public String GetTimeToString(){
        return new SimpleDateFormat("hh:mm:aa").format(_date);
    }
    public void ChangeDate(String date){
        String time = GetTimeToString();
        String dateTime = date + " " + time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            _date = format.parse(dateTime);
        }catch(ParseException e){
            System.out.println("error with date");
            e.printStackTrace();
        }
    }
    public void ChangeTime(String time){
        String dateTime = GetDateToString() + " " + time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        try {
            _date = format.parse(dateTime);
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

    public String GetFormatedDate(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:aa").format(_date);
    }

}
