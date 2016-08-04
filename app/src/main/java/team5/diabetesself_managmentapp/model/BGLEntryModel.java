package team5.diabetesself_managmentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;



public class BGLEntryModel implements Parcelable{

    public static final Parcelable.Creator<BGLEntryModel> CREATOR
            = new Parcelable.Creator<BGLEntryModel>() {
        public BGLEntryModel createFromParcel(Parcel in) {
            return new BGLEntryModel(in.readString(),in.readString(),in.readInt());
        }

        public BGLEntryModel[] newArray(int size) {
            return new BGLEntryModel[size];
        }
    };

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
}
