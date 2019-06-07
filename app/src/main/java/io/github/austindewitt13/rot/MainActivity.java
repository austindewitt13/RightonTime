package io.github.austindewitt13.rot;

import static io.github.austindewitt13.rot.R.id.scroll_alarms;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private TextView mTextMessage;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_night_mode:
          return true;
        case R.id.navigation_set_alarms:
          mTextMessage.setText(R.string.title_set_alarms);
          return true;
        case R.id.navigation_calendar:
          mTextMessage.setText(R.string.title_calendar);
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

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    listView = (ListView) findViewById(scroll_alarms);

    ArrayList<String> arrayList = new ArrayList<>();

    arrayList.add ("@id/new_alarm");

    ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

    listView.setAdapter(arrayAdapter);
  }

}
