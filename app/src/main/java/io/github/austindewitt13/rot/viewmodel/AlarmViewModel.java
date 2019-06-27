package io.github.austindewitt13.rot.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.austindewitt13.rot.AlarmDatabase;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

  private LiveData<List<Alarm>> alarms;

  public AlarmViewModel(@NonNull Application application) {
    super(application);
  /*  AlarmDatabase db = AlarmDatabase.getInstance(application);
    alarms = db.alarmDao().getAll();*/
  }

  public LiveData<List<Alarm>> getAlarmsLiveData() {
    if(alarms == null) {
      alarms = AlarmDatabase.getInstance(getApplication()).alarmDao().getAll();
    }
    return alarms;
  }

  public void addAlarm(final Alarm alarm) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        AlarmDatabase db = AlarmDatabase.getInstance(getApplication());
        db.alarmDao().insert(alarm);
      }
    }).start();
  }
  public LiveData<Alarm> getAlarm(Long id) {
    AlarmDatabase db = AlarmDatabase.getInstance((getApplication()));
  return db.alarmDao().findById(id);
  }

}
