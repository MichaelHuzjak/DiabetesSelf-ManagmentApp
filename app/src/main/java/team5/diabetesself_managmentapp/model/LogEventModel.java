package team5.diabetesself_managmentapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class LogEventModel implements Parcelable {

    public int type;
    public String modelContent;

    public String description;
    public int value;
    public String date;
    public String time;

    public LogEventModel()
    {
    }

    public LogEventModel(int type, String modelContent, String description, int value, String date, String time)
    {
        this.type = type;
        this.modelContent = modelContent;
        this.description = description;
        this.value = value;
        this.date = date;
        this.time = time;
    }

    public LogEventModel(int type, String modelContent)
    {
        this.type = type;
        this.modelContent = modelContent;
        this.description = null;
        this.value = 0;
        this.date = null;
        this.time = null;
    }

    private LogEventModel(Parcel in)
    {
        type = in.readInt();
        modelContent = in.readString();
        description = in.readString();
        value = in.readInt();
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
        parcel.writeInt(value);
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

    public int getValue()
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

    public void setValue(int value)
    {
        this.value = value;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("description", description);
        result.put("time", time);
        result.put("value", value);

        result.put("type", type);
        result.put("modelContent", modelContent);
        return result;
    }
}
