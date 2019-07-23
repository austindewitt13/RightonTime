/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id;
    }

    public String toStandardTime() {
        int hourStandard = getHour();
        boolean afternoon = false;
        hourStandard = getHour() % 12;
        if (getHour() >= 12) {
            afternoon = true;
        }
        if (hourStandard == 0) {
            hourStandard = 12;
        }
        return String.format("%d : %02d %s", hourStandard, getMinute(), afternoon ? "PM" : "AM");
    }

    @NonNull
    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}
