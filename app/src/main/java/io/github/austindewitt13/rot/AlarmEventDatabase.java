package io.github.austindewitt13.rot;

import android.content.Context;
import android.os.AsyncTask;
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
                  new PopulateDbTask(INSTANCE).execute();
                }
              }).build();
        }
      }
    }
    return INSTANCE;
  }
  private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

    private final AlarmEventDatabase db;

    PopulateDbTask(AlarmEventDatabase db){
      this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      Alarm alarm = new Alarm();
      db.alarmDao().insert(alarm);
      return null;
    }
  }
}
