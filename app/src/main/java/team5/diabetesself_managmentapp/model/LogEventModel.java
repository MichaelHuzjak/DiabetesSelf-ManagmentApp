package team5.diabetesself_managmentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LogEventModel implements Parcelable {

    public int type;
    public String modelContent;

    public LogEventModel(){}

    public LogEventModel(int type, String modelContent){
        this.type = type;
        this.modelContent = modelContent;
    }

    protected LogEventModel(Parcel in) {
        type = in.readInt();
        modelContent = in.readString();
    }

    public static final Creator<LogEventModel> CREATOR = new Creator<LogEventModel>() {
        @Override
        public LogEventModel createFromParcel(Parcel in) {
            return new LogEventModel(in);
        }

        @Override
        public LogEventModel[] newArray(int size) {
            return new LogEventModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(modelContent);
    }
}
