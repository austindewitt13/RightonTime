package io.github.austindewitt13.rot;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "channelID";
    public static final String channelID2 = "channelID2";
    public static final String channelName = "Channel Name";
    public static final String channelName2 = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            createChannel2();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel2() {
        NotificationChannel channel2 = new NotificationChannel(channelID2, channelName2, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel2);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder SetAlarmNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Wakey, Wakey, Eggs, and Bakey!")
                .setSmallIcon(R.drawable.ic_add);
    }

    public NotificationCompat.Builder CancelAlarmNotification() {
        return new NotificationCompat.Builder(getApplicationContext(),channelID2)
                .setContentTitle("Alarm Removed!")
                .setSmallIcon(R.drawable.ic_remove);
    }
}
