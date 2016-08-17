package team5.diabetesself_managmentapp;


public class Prescription {
    private int _id;
    private int _categoryId;
    private String _category;
    private String _repeat;
    private String _description;

    private String _day;
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

    public void set_day(String _date) {
        this._day = _date;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_day() {return _day;}

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

}
