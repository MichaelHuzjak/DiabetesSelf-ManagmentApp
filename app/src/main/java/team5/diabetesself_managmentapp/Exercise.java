package team5.diabetesself_managmentapp;

import java.util.Date;

public class Exercise {
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
    public Exercise(int id,String desc,Date date,int duration,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _duration = duration;
        _dateTime = date;
    }
}
