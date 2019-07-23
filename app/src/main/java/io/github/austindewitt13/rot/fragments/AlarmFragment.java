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

package io.github.austindewitt13.rot.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.manager.AlertReceiver;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.view.AlarmAdapter;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;
import java.util.Calendar;
import java.util.Objects;

/**
 * AlarmFragment creates a ListView with an AlarmAdapter,
 * and creates a FloatingActionButton to add Alarms to the ListView.
 */
public class AlarmFragment extends Fragment {

    private AlarmViewModel model;
    private AlarmAdapter alarmAdapter;
    private ListView listView;

    /**
     * @return
     * Returns a new instance of AlarmFragment.
     */
    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_fragment, container, false);
        setupFloatingActionButton(view);
        listView = view.findViewById(R.id.alarm_list);
        model = ViewModelProviders.of(this).get(AlarmViewModel.class);
        listener();
        return view;
    }

    private void listener() {
        model.getAlarmsLiveData().observe(this, (alarms) -> {
            AlarmAdapter.OnAlarmEnabledListener enabledListener = (alarm, enabled) -> {
                if (enabled) {
                    soundAlarm(alarm, alarm.getId());
                } else {
                    cancelAlarm(alarm.getId());
                }
            };
            AlarmAdapter.OnAlarmRemovedListener removedListener = this::removeAlarm;
            alarmAdapter = new AlarmAdapter(Objects.requireNonNull(getContext()), alarms, enabledListener, removedListener);
            listView.setAdapter(alarmAdapter);
        });
    }

    private void setupFloatingActionButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
            addAlarm(hourOfDay, minute);
        };
        fab.setOnClickListener((fabView) -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false)
                .show();
        });
    }

    private void addAlarm(int hourOfDay, int minute) {
        Alarm alarm = new Alarm();
        alarm.setHour(hourOfDay);
        alarm.setMinute(minute);
        model.addAlarm(alarm).observe(this, (id) -> {
            soundAlarm(alarm, id.intValue());
        });
    }

    private void removeAlarm(Alarm alarm) {
        long id = alarm.getId();
        model.removeAlarm(alarm).observe(this, (removed) -> {
            if (removed) {
                cancelAlarm((int) id);
            }
        });
    }

    private void soundAlarm(Alarm alarm, int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getContext()).getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        intent.putExtra("event_type", 1);
        intent.putExtra("alarm_id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, 0);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        assert alarmManager != null;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) Objects.requireNonNull(getContext()).getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        intent.putExtra("event_type", 2);
        intent.putExtra("alarm_id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }

}