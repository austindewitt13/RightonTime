package io.github.austindewitt13.rot.util;

import android.app.Activity;
import android.content.Intent;
import io.github.austindewitt13.rot.R;

public class Utils {

    private static int SwitchTheme;
    public final static int THEME_DAY = 0;
    public final static int THEME_NIGHT = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        SwitchTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        onActivityCreateSetTheme(activity);
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (SwitchTheme)
        {
            default:
            case THEME_DAY:
                activity.setTheme(R.style.AppTheme);
                break;

            case THEME_NIGHT:
                activity.setTheme(R.style.AppThemeDark);
                break;
        }
    }
}
