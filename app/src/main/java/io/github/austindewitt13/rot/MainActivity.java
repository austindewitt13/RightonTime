package io.github.austindewitt13.rot;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTime();
        fragmentSwitching();
    }

    private void fragmentSwitching() {

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

    private void getTime() {

        Thread timeThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Log.d(TAG, "D/T thread started");
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        update();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "D/T thread interrupted");
                    }
                }
            }

            private void update() {
                runOnUiThread(() -> {
                    Date d = new Date();
                    String time = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(d);
                    TextView timeView = findViewById(R.id.edit_time);
                    timeView.setText(time);
                });
            }
        });

        timeThread.start();
    }

    protected void onResume() {
        super.onResume();
        getTime();
    }
}