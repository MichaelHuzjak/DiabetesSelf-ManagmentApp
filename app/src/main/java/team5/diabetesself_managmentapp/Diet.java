package team5.diabetesself_managmentapp;

import java.util.Date;

public class Diet {
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
    public Diet(int id, String desc, Date date,int amount,int pres){
        _id = id;
        _prescriptionId = pres;
        _description = desc;
        _amount = amount;
        _dateTime = date;
    }

}
