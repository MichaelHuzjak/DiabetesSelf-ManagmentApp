package team5.diabetesself_managmentapp;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Prescription {
    private int _id;
    private int _categoryId;
    private String _category;
    private String _repeat;
    private String _description;
    private String _date;
    private String _time;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_categoryId() {
        return _categoryId;
    }

    public void set_categoryId(int _categoryId) {
        this._categoryId = _categoryId;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_repeat() {
        return _repeat;
    }

    public void set_repeat(String _repeat) {
        this._repeat = _repeat;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_date() {return _date;}

    public String get_time() {return _time;}


    public Prescription(){
        _description = "";
        _repeat = "";
        _category = "";
    }
    public Prescription(int id, int catid, String category, String desc, String repeat){
        _id = id;
        _categoryId = catid;
        _category = category;
        _description = desc;
        _repeat = repeat;
    }


    public boolean isDaily(){
        if(_repeat != null){
            String[] parts = _repeat.split(",",2);
            if(Integer.parseInt(parts[0])==0)
                return true;
        }
        return false;
    }
    public int GetDay(){
        String[] parts = _repeat.split(",",2);
        int day = Integer.parseInt(parts[0]);
        switch(day){
            default:
                return 0;
            case Calendar.SUNDAY:
                return Calendar.SUNDAY;
            case Calendar.MONDAY:
                return Calendar.MONDAY;
            case Calendar.TUESDAY:
                return Calendar.TUESDAY;
            case Calendar.WEDNESDAY:
                return Calendar.WEDNESDAY;
            case Calendar.THURSDAY:
                return Calendar.THURSDAY;
            case Calendar.FRIDAY:
                return Calendar.FRIDAY;
            case Calendar.SATURDAY:
                return Calendar.SATURDAY;
        }

    }
    public Date GetNextDayAtTime(){
        if(isDaily()){
            Calendar calendar = Calendar.getInstance();

            long mills  = calendar.getTimeInMillis();

            if (mills <= System.currentTimeMillis()) {
                Calendar c1 = calendar;
                c1.add(Calendar.DAY_OF_MONTH, 1);
                mills = c1.getTimeInMillis();
            } else {
                mills = calendar.getTimeInMillis();
            }
            return calendar.getTime();
        }else{
            Calendar cal = Calendar.getInstance();
            cal.setTime(GetCombinedCurrentDateandRepeatTime());
            long mills = cal.getTimeInMillis();
            if (mills <= System.currentTimeMillis()) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            while (cal.get(Calendar.DAY_OF_WEEK) != GetDay()) {
                cal.add(Calendar.DATE, 1);
            }
            return cal.getTime();
        }
    }
    public String GetRepeat(int day,Date time){
        Calendar cal = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        String dateTime = currentDate + " " + time;
        return day + "," + dateTime;
    }
    public String GetTime(){
        String[] parts = _repeat.split(",",2);
        return parts[1];
    }
    public Date GetCombinedCurrentDateandRepeatTime(){
        Calendar cal = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        String dateTime = currentDate + " " + GetTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:aa");
        Date date;
        try {
            date = format.parse(dateTime);
            return date;
        }catch(ParseException e){
            System.out.println("error with date");
            e.printStackTrace();
        }
        return null;
    }
    public void ChangeDay(int i){
        _repeat = i+","+get_time();
    }
    public void ChangeTime(String time){
        _repeat = GetDay()+","+time;
    }

}
