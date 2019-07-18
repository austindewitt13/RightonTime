package io.github.austindewitt13.rot;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.dao.AlarmDao;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.view.AlarmAdapter;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


public class AlarmFragment extends Fragment {

    private AlarmAdapter alarmAdapter;
    private AlarmViewModel model;
    private FloatingActionButton fab;
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private List<Alarm> alarmList;
    private Switch alarmSwitch;

    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        return fragment;
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
        alarmAdapter = new AlarmAdapter(context, alarmList);
        alarmList = new LinkedList<>();

        final View view = inflater.inflate(R.layout.alarm_fragment, container, false);
        setupFloatingActionButton(view);
        final Switch alarmSwitch = view.findViewById(R.id.alarm_switch);
        alarmSwitch.setChecked(false);
        final ListView listView = view.findViewById(R.id.alarm_list);
        listView.setAdapter(alarmAdapter);

        return view;
    }

    private void setupFloatingActionButton(View view) {
        Calendar calendar = Calendar.getInstance();
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener((fabView) -> {
            timeSetListener = (view1, hourOfDay, minute) -> {
                Alarm alarm = new Alarm();
                alarm.setHour(hourOfDay);
                alarm.setMinute(minute);
                model.addAlarm(alarm);
                alarmList.add(alarm);
                alarmAdapter.notifyDataSetChanged();
            };
            timePickerDialog = new TimePickerDialog(context, timeSetListener, calendar.get(Calendar.MILLISECOND),
                    calendar.get(Calendar.MILLISECOND), false);
            timePickerDialog.show();
        });
    }
}





