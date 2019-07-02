package io.github.austindewitt13.rot;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.austindewitt13.rot.model.Event;
import io.github.austindewitt13.rot.viewmodel.EventViewModel;

import java.util.ConcurrentModificationException;

public class EventFragment extends Fragment {


    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        return fragment;
    }

//    private Context context;
//
//    public EventFragment() {
//
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        this.context = context;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.event_fragment, container, false);
//        final EventViewModel viewModel = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
//
//        viewModel.getEventsLiveData().observe(this, event -> {
//            final View<Event> adapter =
//                    new ArrayAdapter(context, android.R.layout., event);
//
//        });
//    }
}
