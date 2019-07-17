//package io.github.austindewitt13.rot.view;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Switch;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import io.github.austindewitt13.rot.R;
//import io.github.austindewitt13.rot.model.Alarm;

//import java.util.List;
//
//public class AlarmAdapter extends ArrayAdapter<Alarm> {
//
//    private int hour;
//    private int minute;
//
//    public AlarmAdapter(@NonNull Context context, @NonNull List<Alarm> objects) {
//        super(context, R.layout.alarm_fragment, objects);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//                View rowView = View.inflate(R.layout.alarm_fragment);
//        Switch switchAlarm = layout.findViewById(R.id.switch1);
//
//        if(position != 0) {
//            switchAlarm.setVisibility(View.GONE);
//        }
//        return
//    }
//}
