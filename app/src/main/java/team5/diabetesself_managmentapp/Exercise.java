package team5.diabetesself_managmentapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exercise {
    public static final Parcelable.Creator<Exercise> CREATOR
            = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in.readInt(),in.readString(),in.readString(),in.readInt(),in.readInt());
        }
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
    private int _id;
    private int _prescriptionId;
    private int _duration;
    private String _description;
    private Date _dateTime;

    public Date get_dateTime() {
        return _dateTime;
    }

    public void set_dateTime(Date _dateTime) {
        this._dateTime = _dateTime;
    }

    public int get_duration() {
        return _duration;
    }

    public void set_duration(int _duration) {
        this._duration = _duration;
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

    public int get_prescriptionId() {
        return _prescriptionId;
    }

    public void set_prescriptionId(int _prescriptionId) {
        this._prescriptionId = _prescriptionId;
    }

    public Exercise(){}
    public Exercise(int id,String desc,Date date,int duration){
        _id = id;
        _description = desc;
        _duration = duration;
        _dateTime = date;
    }
    public Exercise(int id,String desc,Date date,int duration,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _duration = duration;
        _dateTime = date;
    }
    public Exercise(int id,String desc,String date,int duration,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _duration = duration;
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
