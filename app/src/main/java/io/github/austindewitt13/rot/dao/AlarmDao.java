package io.github.austindewitt13.rot.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.List;

/**
 *This interface populates the AlarmEventDatabase with
 * an Alarm's hour, minute, and id.
 */

@Dao
public interface AlarmDao {

  @Insert
  long insert(Alarm alarm);

  @Query("SELECT * FROM alarm")
  LiveData<List<Alarm>> getAll();

  @Query("SELECT * FROM alarm WHERE alarm_id = :id")
  LiveData<Alarm> findById(Long id);
}
