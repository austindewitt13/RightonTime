/*
Copyright (c) 2019 Austin DeWitt all rights reserved.
*/

package io.github.austindewitt13.rot;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

/**
 *NotificationHelper
 */
public class NotificationHelper extends ContextWrapper {

    private static final String channelID = "channelID";
    private static final String channelID2 = "channelID2";
    private static final String channelID3 = "channelID3";
    private static final String channelName = "Channel Name";
    private static final String channelName2 = "Channel Name";
    private static final String channelName3 = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            createChannel2();
            createChannel3();
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

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel3() {
        NotificationChannel channel3 = new NotificationChannel(channelID3, channelName3, NotificationManager.IMPORTANCE_HIGH);
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
                .setSmallIcon(R.drawable.set_alarm);

    }

    public NotificationCompat.Builder CancelAlarmNotification() {
        return new NotificationCompat.Builder(getApplicationContext(),channelID2)
                .setContentTitle("Alarm Stopped")
                .setSmallIcon(R.drawable.ic_off);
    }

    public NotificationCompat.Builder StartAlarmNotifiaction() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID3)
                .setContentTitle("Alarm Started")
                .setSmallIcon(R.drawable.ic_start);
    }
}
