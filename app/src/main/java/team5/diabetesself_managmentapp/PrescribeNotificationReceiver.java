package team5.diabetesself_managmentapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Michael on 8/17/2016.
 */
public class PrescribeNotificationReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
        //createNotification(context,"Times up","5 seconds has passed","Alert");
    }

//    public void createNotification(Context context,String title,String msgText, String ticker){
//        PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setContentTitle(title)
//                .setContentText(msgText)
//                .setTicker(ticker)
//        .setSmallIcon(R.drawable.ic_launcher);;
//
//        builder.setContentIntent(notificationIntent);
//        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
//        builder.setAutoCancel(true);
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(1,builder.build());
//    }
}
