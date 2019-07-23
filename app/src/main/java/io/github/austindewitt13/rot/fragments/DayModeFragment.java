package io.github.austindewitt13.rot.fragments;

import android.text.Editable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class DayModeFragment extends Fragment {

    public static DayModeFragment newInstance() {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
        return new DayModeFragment();
    }
}
