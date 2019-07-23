/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot.fragments;

import android.app.AlarmManager;
import android.app.NotificationManager;
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

public class AlarmFragment extends Fragment {

    private AlarmViewModel model;
    private AlarmAdapter alarmAdapter;
    private ListView listView;

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
        model.getAlarmsLiveData().observe(this, (alarms) -> {
            AlarmAdapter.OnAlarmEnabledListener enabledListener = (alarm, enabled) -> {
                if (enabled) {
                    setAlarm(alarm, alarm.getId());
                } else {
                    cancelAlarm(alarm.getId());
                }
            };
            AlarmAdapter.OnAlarmRemovedListener removedListener = (alarm) -> {
                removeAlarm(alarm);
            };
            alarmAdapter = new AlarmAdapter(getContext(), alarms, enabledListener, removedListener);
            listView.setAdapter(alarmAdapter);
        });
        return view;
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
            setAlarm(alarm, id.intValue());
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

    private void setAlarm(Alarm alarm, int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        intent.putExtra("event_type", 1);
        intent.putExtra("alarm_id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, 0);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        intent.putExtra("event_type", 2);
        intent.putExtra("alarm_id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

}