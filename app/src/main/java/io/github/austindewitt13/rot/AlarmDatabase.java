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

@Database(entities = {Alarm.class, Event.class}, version = 1,exportSchema = true)
public abstract class AlarmDatabase extends RoomDatabase {

  public abstract AlarmDao alarmDao();
  public abstract EventDao eventDao();

  private static AlarmDatabase INSTANCE;

  public static AlarmDatabase getInstance(Context context) {
    if(INSTANCE == null) {
      synchronized (AlarmDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              AlarmDatabase.class, "alarm_room")
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

    private final AlarmDatabase db;

    PopulateDbTask(AlarmDatabase db){
      this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      Alarm alarm = new Alarm();
      alarm.setHour(2);
      alarm.setMinute(30);
      db.alarmDao().insert(alarm);
      return null;
    }
  }
}
