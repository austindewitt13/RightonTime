package io.github.austindewitt13.rot.fragments;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class NightModeFragment extends Fragment {

    public static NightModeFragment newInstance() {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        return new NightModeFragment();
    }
}
