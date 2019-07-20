package io.github.austindewitt13.rot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.manager.AlertReceiver;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.view.AlarmAdapter;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class AlarmFragment extends Fragment {

    private AlarmViewModel model;
    private AlarmAdapter alarmAdapter;
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private List<Alarm> alarmList;

    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    private Context context;

    public AlarmFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        model = ViewModelProviders.of(this).get(AlarmViewModel.class);
        alarmList = new LinkedList<>();
        alarmAdapter = new AlarmAdapter(context, alarmList);
        final View buttonView = inflater.inflate(R.layout.alarm_switch,container, false);
        final View view = inflater.inflate(R.layout.alarm_fragment, container, false);
        setupFloatingActionButton(view);
        final ListView listView = view.findViewById(R.id.alarm_list);
        listView.setAdapter(alarmAdapter);

        Button buttonCancelAlarm = buttonView.findViewById(R.id.cancel_alarm);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        return view;
    }

//    private void setupCancelButton(View view) {
//        Button buttonCancelAlarm = findViewById(R.id.cancel_alarm);
//        buttonCancelAlarm.setOnClickListener(view1 -> cancelAlarm());
//    }

    private void setupFloatingActionButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        Calendar calendar = Calendar.getInstance();
        fab.setOnClickListener((fabView) -> {
            timeSetListener = (view1, hourOfDay, minute) -> {
                Alarm alarm = new Alarm();
                alarm.setHour(hourOfDay);
                alarm.setMinute(minute);
                model.addAlarm(alarm);
                alarmList.add(alarm);
                alarmAdapter.notifyDataSetChanged();
                alarmAdapter.notifyChange();

                startAlarm(alarm);

            };
            timePickerDialog = new TimePickerDialog(context, timeSetListener, calendar.get(Calendar.MILLISECOND),
                    calendar.get(Calendar.MILLISECOND), false);
            timePickerDialog.show();
        });
    }

    public void startAlarm(Alarm alarm) {

        Calendar calendar = Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public AlarmFragment cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        return null;
    }
}