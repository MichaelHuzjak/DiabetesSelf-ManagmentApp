package team5.diabetesself_managmentapp.model;

/**
 * Created by Sein on 7/31/2016.
 */
public class BGLEntryModel {
    private String date, time;
    private int progress;

    public BGLEntryModel(){
        date = "";
        time = "";
        progress = 0;
    }

    public BGLEntryModel(String date, String time, int progress){
        this.date = date;
        this.time = time;
        this.progress = progress;
    }
    public int getProgress(){
        return progress;
    }
    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setProgress(int progress){
        this.progress = progress;
    }
}
