package team5.diabetesself_managmentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class BGLEntryModel implements Parcelable{

    private String date;
    private int progress;
    private String time;

    public BGLEntryModel(){
        date = "";
        progress = 0;
        time = "";
    }

    public BGLEntryModel(String date, String time, int progress){
        this.date = date;
        this.time = time;
        this.progress = progress;
    }

    public String getDate(){
        return date;
    }

    public int getProgress(){
        return progress;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(progress);
        parcel.writeString(date);
        parcel.writeString(time);
    }

    public static final Parcelable.Creator<BGLEntryModel> CREATOR
            = new Parcelable.Creator<BGLEntryModel>() {
        public BGLEntryModel createFromParcel(Parcel in) {
            return new BGLEntryModel(in.readString(),in.readString(),in.readInt());
        }

        public BGLEntryModel[] newArray(int size) {
            return new BGLEntryModel[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("progress", progress);
        result.put("time", time);
        return result;
    }
}
