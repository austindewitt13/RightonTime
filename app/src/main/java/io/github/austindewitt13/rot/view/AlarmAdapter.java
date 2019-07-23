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

    /**
     * @param context
     * Takes in context of class.
     * @param alarms
     * Takes in a List of Alarms.
     * @param enabledListener
     * Listener for the on button to turn on an Alarm.
     * @param removedListener
     * Listener for the off button to turn off an Alarm.
     */
    public AlarmAdapter(@NonNull Context context, @NonNull List<Alarm> alarms, OnAlarmEnabledListener enabledListener, OnAlarmRemovedListener removedListener) {
        super(context, R.layout.alarm_fragment, alarms);
        this.alarms = alarms;
        this.enabledListener = enabledListener;
        this.removedListener = removedListener;
    }

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

    /**
     * Listener to turn on an Alarm.
     */
    public interface OnAlarmEnabledListener {

        void enable(Alarm alarm, boolean enabled);
    }

    /**
     * Listener to turn off an Alarm.
     */
    public interface OnAlarmRemovedListener {

        void remove(Alarm alarm);
    }

}

