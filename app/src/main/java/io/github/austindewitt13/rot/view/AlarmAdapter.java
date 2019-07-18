package io.github.austindewitt13.rot.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.github.austindewitt13.rot.R;
import io.github.austindewitt13.rot.model.Alarm;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    private List<Alarm> alarmContents;
    private OnInputChangedListener listener;

    public AlarmAdapter(@NonNull Context context, List<Alarm> alarmContents) {
        super(context, R.layout.alarm_fragment);
        this.alarmContents = alarmContents;
    }

    public void notifyChange() {
        if(listener != null) {
            listener.onChange(alarmContents);
        }
    }

    public void setOnChangeListener(OnInputChangedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View layout = convertView == null ?
                LayoutInflater.from(getContext()).inflate(R.layout.alarm_fragment,parent,false)
                : convertView;
        Alarm item = getItem(position);

        Switch alarmSwitch = layout.findViewById(R.id.alarm_switch);

        alarmSwitch.setOnClickListener(
                v -> {
                    alarmContents.remove(item);
                    AlarmAdapter.this.notifyDataSetInvalidated();
                    AlarmAdapter.this.notifyChange();
                }
        );
        return layout;
    }
    public interface OnInputChangedListener {
        void onChange(List<Alarm> alarmContents);
    }
}
