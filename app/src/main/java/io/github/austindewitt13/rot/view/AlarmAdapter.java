/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.model.Alarm;

import java.util.List;

/**
 * This class creates an adapter to use with the AlarmFragment class
 * to inflate and make changes to the Alarm ListView.
 */
public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private List<Alarm> alarms;
    private final OnAlarmEnabledListener enabledListener;
    private final OnAlarmRemovedListener removedListener;


    public AlarmAdapter(@NonNull Context context, @NonNull List<Alarm> alarms, OnAlarmEnabledListener enabledListener, OnAlarmRemovedListener removedListener) {
        super(context, R.layout.alarm_fragment, alarms);
        this.alarms = alarms;
        this.enabledListener = enabledListener;
        this.removedListener = removedListener;
    }

    /**
     * Inflates the alarm fragment view and
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Alarm item = getItem(position);
        View layout = convertView == null ?
                LayoutInflater.from(getContext()).inflate(R.layout.alarm_switch, parent, false)
                : convertView;
        TextView timeText = layout.findViewById(R.id.alarm_list_time);
        timeText.setText(item.toStandardTime());
        layout.findViewById(R.id.alarm_on).setOnClickListener((v) -> enabledListener.enable(item, true));
        layout.findViewById(R.id.alarm_off).setOnClickListener((v) -> enabledListener.enable(item, false));
        layout.findViewById(R.id.cancel_alarm).setOnClickListener((v) -> removedListener.remove(item));
        return layout;

    }

    public interface OnAlarmEnabledListener {

        void enable(Alarm alarm, boolean enabled);

    }

    public interface OnAlarmRemovedListener {

        void remove(Alarm alarm);

    }

}

