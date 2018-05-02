package qualtech_q_smart_home_automation_2018.q_smart.Utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;
import android.widget.Toast;


import qualtech_q_smart_home_automation_2018.q_smart.R;

public class MyNotification extends BroadcastReceiver {

    private Context ctx = null;
    String ns = Context.NOTIFICATION_SERVICE;
    public NotificationManager mNotificationManager = null;
    public NotificationCompat.Builder nBuilder = null;
    public RemoteViews mViews = null;
    SharedPreferences mPref;

    public MyNotification() {
    }


    public MyNotification(Context context){
        super();
        ctx = context;
       // String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) context.getSystemService(ns);

        nBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("Q-smart")
                .setSmallIcon(R.drawable.logo)
                .setOngoing(true);
        mViews = new RemoteViews(ctx.getPackageName(),R.layout.notification_layout);
       // setListeners(mViews);
        mViews.setOnClickPendingIntent(R.id.widget_light_one,getPendingSelfIntent(context,"lightOne"));
        mViews.setOnClickPendingIntent(R.id.close_notification,getPendingSelfIntent(context,"close_notification"));
        nBuilder.setContent(mViews);

        mNotificationManager.notify(2,nBuilder.build());

    }
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void cancelNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(2);
    }

    private void sendMessage(Context context,String code){
        Intent i = new Intent(Constants.WIDGET_RECEIVER);
        i.putExtra("BtMessage",code);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mViews = new RemoteViews(context.getPackageName(),R.layout.notification_layout);
        if (intent.getAction().equals("lightOne")){
            Toast.makeText(context,"lightOne clicked",Toast.LENGTH_SHORT).show();
            mViews.setImageViewResource(R.id.widget_light_one,R.drawable.light_on);
            sendMessage(context,context.getString(R.string.light1_on_bt_code));
            if (mNotificationManager != null && nBuilder != null){
                nBuilder.setContent(mViews);
                mNotificationManager.notify(2,nBuilder.build());
            }

        }else if (intent.getAction().equals("close_notification")){
            Toast.makeText(context,"Notification cancelled",Toast.LENGTH_SHORT).show();
            cancelNotification(context);
        }
        if (nBuilder!=null && mNotificationManager != null){
            nBuilder.setContent(mViews);
            mNotificationManager.notify(2,nBuilder.build());
        }
    }
}
