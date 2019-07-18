package io.github.austindewitt13.rot;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;

import java.util.Calendar;


public class AlarmFragment extends Fragment {

    private AlarmViewModel model;
    private FloatingActionButton fab;
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

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

        final View view = inflater.inflate(R.layout.alarm_fragment, container, false);
        setupFloatingActionButton(view);


        final AlarmViewModel viewModel = ViewModelProviders.of(getActivity()).get(AlarmViewModel.class);
        viewModel.getAlarmsLiveData().observe(this, alarmList -> {
            final ArrayAdapter<Alarm> adapter =
                    new ArrayAdapter<>(context, android.R.layout.simple_list_item_activated_1, alarmList);
            final ListView alarmListView = view.findViewById(R.id.alarm_list);
            alarmListView.setAdapter(adapter);
        });
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
            };
            timePickerDialog = new TimePickerDialog(context, timeSetListener, calendar.get(Calendar.MILLISECOND),
                    calendar.get(Calendar.MILLISECOND), false);
            timePickerDialog.show();
        });
    }
}





