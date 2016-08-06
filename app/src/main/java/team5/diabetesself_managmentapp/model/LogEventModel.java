package team5.diabetesself_managmentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LogEventModel implements Parcelable {

    public final int type;

    public final String modelContent;

    public String description;
    public String value;
    public String date;
    public String time;

    public LogEventModel(int type, String modelContent)
    {
        this.type = type;
        this.modelContent = modelContent;
        this.description = null;
        this.value = null;
        this.date = null;
        this.time = null;
    }

    private LogEventModel(Parcel in)
    {
        type = in.readInt();
        modelContent = in.readString();
        description = in.readString();
        value = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<LogEventModel> CREATOR = new Creator<LogEventModel>()
    {
        @Override
        public LogEventModel createFromParcel(Parcel in)
        {
            return new LogEventModel(in);
        }

        @Override
        public LogEventModel[] newArray(int size)
        {
            return new LogEventModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(type);
        parcel.writeString(modelContent);
        parcel.writeString(description);
        parcel.writeString(value);
        parcel.writeString(date);
        parcel.writeString(time);
    }

    public String getTime()
    {
        return time;
    }

    public String getDate()
    {
        return date;
    }

    public String getValue()
    {
        return value;
    }

    public String getDescription()
    {
        return description;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
