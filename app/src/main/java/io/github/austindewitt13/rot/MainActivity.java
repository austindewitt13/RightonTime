package io.github.austindewitt13.rot;

import static io.github.austindewitt13.rot.R.id.alarm_list;
import static io.github.austindewitt13.rot.R.id.beginning;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.ArrayList;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = MainActivity.class.getSimpleName();
  private Button alarmButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Fragment fragment = AlarmFragment.newInstance();
    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
    assert fragment != null;
    transaction1.replace(R.id.frame_layout, fragment);
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
}