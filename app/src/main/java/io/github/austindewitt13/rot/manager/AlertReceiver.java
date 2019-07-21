package io.github.austindewitt13.rot.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import io.github.austindewitt13.rot.NotificationHelper;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notificationBuilder;
        int eventType = intent.getIntExtra("event_type", 1);
        int alarmId = intent.getIntExtra("alarm_id", 0);
        int alarmIdRemove = intent.getIntExtra("alarm_id_remove", 2);

        switch (eventType) {
            case 1:
                notificationBuilder = notificationHelper.SetAlarmNotification();
                notificationHelper.getManager().notify(alarmId, notificationBuilder.build());
                break;

            case 2:
                notificationBuilder = notificationHelper.CancelAlarmNotification();
                notificationHelper.getManager().notify(alarmIdRemove, notificationBuilder.build());
                break;



        }

    }
}
