package io.github.austindewitt13.rot;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private AlarmViewModel model;
    private AlarmFragment alarmFragment;
    private FloatingActionButton fab;
    private TimePickerDialog timePickerDialog;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(AlarmViewModel.class);

        alarmFragment = AlarmFragment.newInstance();
        setupFloatingActionButton();
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        assert alarmFragment != null;
        transaction1.replace(R.id.frame_layout, alarmFragment);
        transaction1.commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    selectedFragment = EventFragment.newInstance();
                    Log.d(TAG, "calendar nav button worked");
                    break;

                case R.id.navigation_night_mode:
                    return true;

                case R.id.navigation_set_alarms:
                    selectedFragment = AlarmFragment.newInstance();
                    Log.d(TAG, "alarm nav button worked");
                    break;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            assert selectedFragment != null;
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        });
    }

    private void setupFloatingActionButton() {
        Calendar calendar = Calendar.getInstance();
        fab = findViewById(R.id.fab);

        fab.setOnClickListener((view) -> {
                        timeSetListener = (view1, hourOfDay, minute) -> {
                            Alarm alarm = new Alarm();
                            alarm.setHour(hourOfDay);
                            alarm.setMinute(minute);
                            model.addAlarm(alarm);
                        };
                timePickerDialog = new TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        });
    }

}

