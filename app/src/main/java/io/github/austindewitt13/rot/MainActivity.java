package io.github.austindewitt13.rot;

import static io.github.austindewitt13.rot.R.id.scroll_alarms;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_night_mode:
          return true;
        case R.id.navigation_set_alarms:
          return true;
        case R.id.navigation_calendar:
          return true;
      }
      return false;
    }
  };
  //TODO add never ending list of alarms and edit them
  ListView listView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    listView = findViewById(scroll_alarms);

    ArrayList<Class<Alarm>> arrayList = new ArrayList<>();

    arrayList.add(Alarm.class);

    ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList);

    listView.setAdapter(arrayAdapter);
  }

}
