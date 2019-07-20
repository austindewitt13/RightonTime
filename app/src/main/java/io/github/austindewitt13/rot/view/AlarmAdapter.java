package io.github.austindewitt13.rot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.austindewitt13.rot.AlarmFragment;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.model.Alarm;

import java.util.Calendar;
import java.util.List;

/**
 *This class creates an adapter to use with the AlarmFragment class
 *  to inflate and make changes to the Alarm ListView.
 */
public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private List<Alarm> alarmContents;
    private OnInputChangedListener listener;
    private AlarmFragment alarmFragment;
    private ToggleButton alarmOn;
    private ToggleButton alarmOff;
    private Button buttonDeleteAlarm;

    public AlarmAdapter(@NonNull Context context, @NonNull List<Alarm> objects) {
        super(context, R.layout.alarm_fragment, objects);
        alarmContents = objects;
    }

    /**
     * Notifies the listener that the contents of the list have changed.
     */
    public void notifyChange() {
        if(listener != null) {
            listener.onChange(alarmContents);
        }
    }

    /**
     * Listens for any changes made by the user in the listview.
     * @param listener
     */
    public void setOnChangeListener(OnInputChangedListener listener) {
        this.listener = listener;
    }

    /**
     * Inflates the alarm fragment view and
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Calendar calendar = Calendar.getInstance();

        View layout = convertView == null ?
                LayoutInflater.from(getContext()).inflate(R.layout.alarm_switch,parent,false)
                : convertView;

        TextView timeText = layout.findViewById(R.id.alarm_list_time);
        Alarm item = getItem(position);
        timeText.setText(item.toStandardTime());
//        alarmOn.findViewById(R.id.alarm_on);
//        alarmOff.findViewById(R.id.alarm_off);
//        alarmFragment.cancelAlarm();
////        buttonDeleteAlarm = new Button(this, ).findViewById(R.id._alarm);
////        buttonDeleteAlarm.setOnClickListener(v -> {
////            AlarmFragment cancel = new AlarmFragment().cancelAlarm();
////        });
//
//
//
//
//        alarmOn.setOnClickListener(v -> alarmFragment.startAlarm(calendar));
//
//        alarmOff.setOnClickListener(v -> alarmFragment.cancelAlarm());

        return layout;
    }

    /**
     *
     */
    public interface OnInputChangedListener {
        void onChange(List<Alarm> alarmContents);
    }
}
