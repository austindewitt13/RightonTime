/*
Copyright (c) Austin DeWitt all rights reserved.

Copyright (c) Facebook, Inc. and its affiliates.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Copyright 2019 Austin DeWitt

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package io.github.austindewitt13.rot.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Alarm entity creates an Alarm object with an hour, minute, and id field.
 */
@Entity
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    private int id;
    private int hour;
    private int minute;

    /**
     * @return
     * Returns an int hour.
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour
     * Sets int hour in AlarmEventDatabase and in the ListView of AlarmFragment.
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @return
     * Returns an int minute.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @param minute
     * Sets int minute in AlarmsEventDatabase and in the ListView of AlarmFragment.
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * @return
     * Returns an int id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     * Sets int id in AlarmEventDataBase.
     */
    public void setId(int id) { this.id = id;
    }

    /**
     * @return
     * Returns a formatted String to set the current time to standard format.
     */
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

    /**
     * @return
     * Returns a String formatted for AlarmEventDatabase.
     */
    @NonNull
    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}
