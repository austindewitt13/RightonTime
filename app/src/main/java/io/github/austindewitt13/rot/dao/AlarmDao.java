package io.github.austindewitt13.rot.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.austindewitt13.rot.model.Alarm;
import java.util.List;

@Dao
public interface AlarmDao {

  @Insert
  long insert(Alarm alarm);

  @Query("SELECT * FROM alarm")
  LiveData<List<Alarm>> getAll();

}
