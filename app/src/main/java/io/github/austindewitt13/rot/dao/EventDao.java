package io.github.austindewitt13.rot.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.austindewitt13.rot.model.Event;
import java.util.List;

@Dao
public interface EventDao {

  @Insert
  Long insert(Event event);

  @Query("SELECT * FROM event")
  LiveData<List<Event>> getAll();

}
