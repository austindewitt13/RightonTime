package io.github.austindewitt13.rot.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity
public class Event {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "event_id")
  private long id;

  private String event;
  @TypeConverters(DateConverter.class)
  private Date date;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }



}

