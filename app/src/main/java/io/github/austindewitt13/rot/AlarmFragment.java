package io.github.austindewitt13.rot;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.viewmodel.AlarmViewModel;
import java.util.List;
import java.util.Observer;


public class AlarmFragment extends Fragment {

  private FloatingActionButton fab;

  public FloatingActionButton getFab() {
    return fab;
  }

  public static AlarmFragment newInstance() {
    AlarmFragment fragment = new AlarmFragment();
    return fragment;
  }

  private Context context;

  public AlarmFragment() {

  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.alarm_fragment, container, false);
    fab = view.findViewById(R.id.fab);
    final AlarmViewModel viewModel = ViewModelProviders.of(getActivity()).get(AlarmViewModel.class);

    viewModel.getAlarmsLiveData().observe(this, alarmList -> {
      final ArrayAdapter<Alarm> adapter =
          new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, alarmList);
      final ListView alarmListView = view.findViewById(R.id.alarm_list);
      alarmListView.setAdapter(adapter);

      });

  return view;
  }
 }


