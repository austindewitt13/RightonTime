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

package io.github.austindewitt13.rot.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.austindewitt13.rot.AlarmEventDatabase;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.List;

/**
 * This initializes a LiveData List of Alarms, adds an Alarm, and removes an Alarm from the AlarmEventDatabase.
 */
public class AlarmViewModel extends AndroidViewModel {

    private static LiveData<List<Alarm>> alarms;
    private MutableLiveData<Long> newAlarmId;
    private MutableLiveData<Boolean> alarmRemoved;

    /**
     * @param application
     * Takes in the application of a class.
     */
    public AlarmViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * @return
     * Returns Livedata List of Alarm.
     */
    public LiveData<List<Alarm>> getAlarmsLiveData() {
        if (alarms == null) {
            alarms = AlarmEventDatabase.getInstance(getApplication()).alarmDao().getAll();
        }
        return alarms;
    }

    /**
     * @param alarm
     * Takes in an Alarm and puts it in the Database.
     * @return
     * Returns the id of the Alarm that was put into the Database.
     */
    public LiveData<Long> addAlarm(final Alarm alarm) {

        if (newAlarmId == null) {
            newAlarmId = new MutableLiveData<>();
        }
        new Thread(() ->
                newAlarmId.postValue(AlarmEventDatabase.getInstance(getApplication()).alarmDao().insert(alarm))).start();
        return newAlarmId;
    }

    /**
     * @param alarm
     * Takes in an Alarm and removes it from the Database.
     * @return
     * Returns an empty row where the Alarm was removed.
     */
    public LiveData<Boolean> removeAlarm(Alarm alarm) {
        if (alarmRemoved == null) {
            alarmRemoved = new MutableLiveData<>();
        }
        new Thread(() -> {
            alarmRemoved.postValue(AlarmEventDatabase.getInstance(getApplication()).alarmDao().delete(alarm) > 0);
        }).start();
        return alarmRemoved;
    }
}

