/*
Copyright (c) Austin DeWitt all rights reserved.

Copyright (c) Facebook, Inc. and its affiliates.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Copyright 2019 Austin DeWitt

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package io.github.austindewitt13.rot.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Date;

/**
 * Event entity creates an Event Object with Date, String event, and Long id.
 */
@Entity
public class Event {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "event_id")
  private long id;
  @TypeConverters(DateConverter.class)
  private Date date;
  private String event;

  /**
   * @return
   * Returns a Long id.
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   * Sets a Long id in AlarmEventDatabase.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return
   * Returns a Date.
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date
   * Sets a Date in the AlarmEventDatabase and eventually in the EventFragment.
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @return
   * Returns a String event;
   */
  public String getEvent() {
    return event;
  }

  /**
   * @param event
   * Sets a String event in AlarmEventDatabase and eventually in the EventFragment.
   */
  public void setEvent(String event) {
    this.event = event;
  }

  /**
   * @return
   * Returns a String formatted for AlarmEventDatabase.
   */
  @NonNull
  @Override
  public String toString() {
    return event + " " + date;
  }

}

