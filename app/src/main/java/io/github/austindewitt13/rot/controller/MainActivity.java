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
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.activities.LoginActivity;
import io.github.austindewitt13.rot.fragments.AlarmFragment;
import io.github.austindewitt13.rot.fragments.DayModeFragment;
import io.github.austindewitt13.rot.fragments.EventFragment;
import io.github.austindewitt13.rot.fragments.NightModeFragment;
import io.github.austindewitt13.rot.service.GoogleSignInService;
import java.text.DateFormat;
import java.util.Date;

/**
 * Sets the main layout to activity_main, gets a time header, and switches between Alarm, Event, DayMode, and NightMode fragments.
 */
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
                    Log.d(TAG, "calendar nav button worked");
                    break;

                case R.id.navigation_set_alarms:
                    selectedFragment = AlarmFragment.newInstance();
                    Log.d(TAG, "alarm nav button worked");
                    break;

                case R.id.navigation_night_mode:
                    selectedFragment = NightModeFragment.newInstance();
                    Log.d(TAG,"night mode button worked");
                    break;

                case R.id.navigation_day_mode:
                   selectedFragment = DayModeFragment.newInstance();
                   Log.d(TAG, "day mode button worked");
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

    /**
     * onResume starts getTime which gets the Date time and puts it into a TextView.
     */
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