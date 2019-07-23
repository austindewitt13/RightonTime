/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.github.austindewitt13.rot.AlarmEventDatabase;
import io.github.austindewitt13.rot.model.Event;

import java.util.Calendar;
import java.util.Date;

public class EventViewModel extends AndroidViewModel {

    private LiveData<Event> events;

    public EventViewModel(@NonNull Application application, LiveData<Calendar> events) {
        super(application);
    }

    public LiveData<Event> getEventsLiveData() {
        if(events == null) {
            events = AlarmEventDatabase.getInstance(getApplication()).eventDao().getAll();
        }
        return events;
    }

    public void addEvent(final Event event) {
        new Thread(() -> {
            AlarmEventDatabase db = AlarmEventDatabase.getInstance(getApplication());
            db.eventDao().insert(event);
        }).start();
    }

    public LiveData<Event> getEvent(Long id) {
        AlarmEventDatabase db = AlarmEventDatabase.getInstance((getApplication()));
        return db.eventDao().findById(id);
    }
}
