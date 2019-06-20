package io.github.austindewitt13.rot;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import io.github.austindewitt13.rot.dao.AlarmDao;
import io.github.austindewitt13.rot.dao.EventDao;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.model.Event;

@Database(entities = {Alarm.class, Event.class}, version = 1)
public abstract class AlarmDatabase extends RoomDatabase {

  public abstract AlarmDao alarmDao();
  public abstract EventDao eventDao();

  private static AlarmDatabase INSTANCE;

  public static AlarmDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
          AlarmDatabase.class,"alarm_room").build();
    }
    return INSTANCE;
  }

}
