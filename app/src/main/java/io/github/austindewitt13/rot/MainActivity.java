package io.github.austindewitt13.rot;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;

public class MainActivity extends AppCompatActivity {

  private AlarmFragment alarmFragment;
  private AlarmViewModel model;
  private FloatingActionButton fab;
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
    fab = findViewById(R.id.fab);
    fab.setOnClickListener((view)-> model.addAlarm(new Alarm()));
  }
}

