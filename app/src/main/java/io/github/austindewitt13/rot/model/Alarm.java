package io.github.austindewitt13.rot.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    private long id;

    private int hour;

    private int minute;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Alarm " +
                hour + ":" +
                minute;
    }
}
