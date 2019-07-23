/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.model.Event;
import io.github.austindewitt13.rot.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment implements CalendarView.OnDateChangeListener {

    private EventViewModel thisView;
    private CalendarView scheduleCalendarView;
    private List<Event> scheduleList = new ArrayList<Event>();
    private List<Event> dayScheduleList = new ArrayList<Event>();
    private Long date;


    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        return fragment;
    }

    private Context context;

    public EventFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View thisView = inflater.inflate(R.layout.event_fragment, container, false);

        scheduleCalendarView = thisView.findViewById(R.id.calendarView);
        date = scheduleCalendarView.getDate();
        scheduleCalendarView.setOnDateChangeListener(this);

        return thisView;
    }

    public void onSelectedDayChange(CalendarView thisView, int year, int month, int dayOfMonth) {
        if (scheduleCalendarView.getDate() != date) {
            date = scheduleCalendarView.getDate();

        }
    }
}