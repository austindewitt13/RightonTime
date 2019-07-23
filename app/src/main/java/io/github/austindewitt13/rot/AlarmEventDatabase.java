/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.github.austindewitt13.rot.dao.AlarmDao;
import io.github.austindewitt13.rot.dao.EventDao;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.model.Event;

@Database(entities = {Alarm.class, Event.class}, version = 1)
public abstract class AlarmEventDatabase extends RoomDatabase {

  public abstract AlarmDao alarmDao();
  public abstract EventDao eventDao();

  private static AlarmEventDatabase INSTANCE;

  public static AlarmEventDatabase getInstance(Context context) {
    if(INSTANCE == null) {
      synchronized (AlarmEventDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              AlarmEventDatabase.class, "database_room")
              .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                  super.onCreate(db);
                }
              }).build();
        }
      }
    }
    return INSTANCE;
  }
}
