package io.github.austindewitt13.rot.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;

@Entity
public class Event {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "event_id")
  private long id;
  @TypeConverters(DateConverter.class)
  private Date date;
  private String event;

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

  @NonNull
  @Override
  public String toString() {
    return event + " " + date;
  }

}

