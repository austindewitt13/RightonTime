package io.github.austindewitt13.rot;

import android.app.Application;
import androidx.room.Database;
import com.facebook.stetho.Stetho;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.model.Event;

public class AlarmApp extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);

   /* new Thread(() -> {
      Alarm alarm = new Alarm();
      AlarmDatabase.getInstance(this).alarmDao().insert(alarm);
      alarm.setMinute(30);
      Event event = new Event();
      AlarmDatabase.getInstance(this).eventDao().insert(event);
     event.setEvent("birthday");
    }).start();*/
  }

}
