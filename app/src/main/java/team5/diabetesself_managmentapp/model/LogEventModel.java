package team5.diabetesself_managmentapp.model;

public class LogEventModel {

    public int type;
    public String modelContent;

    public LogEventModel(){}

    public LogEventModel(int type, String modelContent){
        this.type = type;
        this.modelContent = modelContent;
    }

}
