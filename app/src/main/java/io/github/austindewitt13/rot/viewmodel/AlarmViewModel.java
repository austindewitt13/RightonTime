package io.github.austindewitt13.rot.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.github.austindewitt13.rot.AlarmEventDatabase;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

  private LiveData<List<Alarm>> alarms;
  private MutableLiveData<Long> newAlarmId;
  private MutableLiveData<Long> AlarmId;

  public AlarmViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<List<Alarm>> getAlarmsLiveData() {
    if(alarms == null) {
      alarms = AlarmEventDatabase.getInstance(getApplication()).alarmDao().getAll();
    }
    return alarms;
  }

  public LiveData<Long> addAlarm(final Alarm alarm) {

    if (newAlarmId == null) {
      newAlarmId = new MutableLiveData<>();
    }
    new Thread(() ->
            newAlarmId.postValue(AlarmEventDatabase.getInstance(getApplication()).alarmDao().insert(alarm))).start();
    return newAlarmId;
  }

  public LiveData<Alarm> getAlarm(Long id) {
    AlarmEventDatabase db = AlarmEventDatabase.getInstance((getApplication()));
  return db.alarmDao().findById(id);
  }

  public LiveData<Long> removeAlarm(final Alarm alarm) {

    if (AlarmId != null) {
    AlarmId = new MutableLiveData<>();
  }
    new Thread(() ->
            AlarmId.postValue((long) AlarmEventDatabase.getInstance(getApplication()).alarmDao().delete(alarm))).start();
  return AlarmId;
  }
}
