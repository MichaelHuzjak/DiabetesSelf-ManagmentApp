package team5.diabetesself_managmentapp;


public class Category {

    private int _id;
    private String _name;

    public int getId(){return _id;}
    public void setId(int id){_id = id;}
    public String getName(){return _name;}
    public void setName(String name){_name = name;}

    public Category()
    {
        _id = 0;
        _name = "";
    }

    public Category(int id, String name)
    {
        _id = id;
        _name = name;
    }
}
