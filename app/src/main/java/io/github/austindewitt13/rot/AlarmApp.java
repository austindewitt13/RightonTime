package io.github.austindewitt13.rot;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.model.Event;

public class AlarmApp extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);

    new Thread(() -> {
      Alarm alarm = new Alarm();
      alarm.setHour(2);
      AlarmDatabase.getInstance(this).alarmDao().insert(alarm);
      Event event = new Event();
      event.setEvent("Birthday");
      AlarmDatabase.getInstance(this).eventDao().insert(event);
    }).start();
  }
}
