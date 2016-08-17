package team5.diabetesself_managmentapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Michael on 8/17/2016.
 */
public class NotificationHelper {
    Context context;

    public NotificationHelper(Context context){
        this.context = context;
    }
    public void CreateNotification(String notName, int delay){
        //scheduleNotification(getNotification(notName),delay);
    }
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }
    private Notification getNotification(Context context,String title,String msgText,String ticker){
        PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(msgText)
                .setSmallIcon(R.drawable.ic_launcher);;

        builder.setContentIntent(notificationIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);
        return builder.build();
        //NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //manager.notify(1,builder.build());
    }

    private void scheduleNotification(Notification notification,int id,Date date) {

        Intent notificationIntent = new Intent(context, PrescribeNotificationReceiver.class);
        final int _id = (int) System.currentTimeMillis();
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeInMillis(System.currentTimeMillis());

        notificationIntent.putExtra(PrescribeNotificationReceiver.NOTIFICATION_ID, id);
        notificationIntent.putExtra(PrescribeNotificationReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 * 60 * 24, pendingIntent);
    }
    public void EnsureNotifications(List<Prescription> prescriptions){
        for(Prescription p:prescriptions){
            System.out.println("Setting a Notification: (ID)" + p.get_id() + " (Desc) " + p.get_description() );
            scheduleNotification(getNotification(context,"Diabetes Alert",p.get_description(),p.get_description()),p.get_id(),p.GetNextDayAtTime());
        }
    }

}
