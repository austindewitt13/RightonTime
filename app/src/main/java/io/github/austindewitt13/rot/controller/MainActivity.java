package io.github.austindewitt13.rot.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import io.github.austindewitt13.rot.AlarmFragment;
import io.github.austindewitt13.rot.EventFragment;
import io.github.austindewitt13.rot.LoginActivity;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.service.GoogleSignInService;
import io.github.austindewitt13.rot.util.Utils;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean handled = true;
        if (item.getItemId() == R.id.sign_out) {
            signOut();
        } else {
            handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    private void fragmentSwitching() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    selectedFragment = EventFragment.newInstance();
                    navigation.setSelectedItemId(R.id.calendarView);
                    Log.d(TAG, "calendar nav button worked");
                    break;

                case R.id.navigation_night_mode:
                    Utils.changeToTheme(this, Utils.THEME_NIGHT);
                    navigation.setSelectedItemId(R.id.navigation_night_mode);
                    break;

                case R.id.navigation_day_mode:
                    Utils.changeToTheme(this, Utils.THEME_DAY);
                    navigation.setSelectedItemId(R.id.navigation_day_mode);
                    break;

                case R.id.navigation_set_alarms:
                    selectedFragment = AlarmFragment.newInstance();
                    navigation.setSelectedItemId(R.id.alarm_list);
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
                    Date date = new Date();
                    String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
                    TextView timeView = findViewById(R.id.time_header);
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

    private void signOut() {
        GoogleSignInService service = GoogleSignInService.getInstance();
        service.getClient().signOut().addOnCompleteListener((task) -> {
            service.setAccount(null);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}