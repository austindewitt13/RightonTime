package io.github.austindewitt13.rot;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import java.util.List;

public class AlarmFragment extends Fragment {

    private AlarmViewModel model;
    private AlarmAdapter alarmAdapter;
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private ListView listView;
    private View view;
    private Alarm alarm = new Alarm();




    public static AlarmFragment newInstance() {
        return new AlarmFragment();
    }

    public AlarmFragment() {

    }

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.alarm_fragment, container, false);
        setupFloatingActionButton(view);
        listView = view.findViewById(R.id.alarm_list);
        model = ViewModelProviders.of(this).get(AlarmViewModel.class);
        model.getAlarmsLiveData().observe(this, (alarms) -> {
            alarmAdapter = new AlarmAdapter(context, alarms);
            listView.setAdapter(alarmAdapter);
        });

        model.removeAlarm(alarm).observe(this, (id) ->{
            removeAlarmNotification(id);
            alarmAdapter.notifyChange();
        });

        return view;
    }


//    private void setupCancelButton(View buttonView, long id) {
//        Alarm alarm = new Alarm();
//        ImageButton buttonCancelAlarm = buttonView.findViewById(R.id.cancel_alarm);
//        buttonCancelAlarm.setOnClickListener((cancelView) -> {
//            removeAlarmNotification(id);
//            model.getAlarmsLiveData().observe(this, (alarms) -> {
//                model.removeAlarm(alarm);
//            });
//        });
//    }

    private void setupFloatingActionButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.fab);

        timeSetListener = (view1, hourOfDay, minute) -> {
            Alarm alarm = new Alarm();
            alarm.setHour(hourOfDay);
            alarm.setMinute(minute);
            model.addAlarm(alarm).observe(this, (id) -> {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                startAlarm(calendar, id);
            });
        };
        fab.setOnClickListener((fabView) -> {
            Calendar calendar = Calendar.getInstance();
            timePickerDialog = new TimePickerDialog(context, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false);
            timePickerDialog.show();
        });
    }

    public void startAlarm(Calendar calendar, long id) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("event_type", 1);
        intent.putExtra("alarm_id", (int)id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)id, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }

    public void removeAlarmNotification(long id) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("event_type", 1);
        intent.putExtra("alarm_id_remove", (int)id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)id, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}