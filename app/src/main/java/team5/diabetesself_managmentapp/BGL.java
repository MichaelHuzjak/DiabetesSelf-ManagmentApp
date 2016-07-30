package team5.diabetesself_managmentapp;

import java.util.Date;

public class BGL {
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

}
