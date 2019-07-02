package io.github.austindewitt13.rot.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.austindewitt13.rot.model.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Dao
public interface EventDao {

  @Insert
  Long insert(Event event);

  @Query("SELECT * FROM event")
  LiveData<Event> getAll();

  @Query("SELECT * FROM event WHERE event_id = :id")
  LiveData<Event> findById(Long id);
}
