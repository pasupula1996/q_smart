package qualtech_q_smart_home_automation_2018.q_smart.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.Services.BluetoothServices;
import qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants;

import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_BUTTON;
import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_FAN_BUTTON;
import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_LIGHT_BUTTON;
import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_TV_BUTTON;

/**
 * Implementation of App Widget functionality.
 */
public class CommonWidget extends AppWidgetProvider {

    public ImageView lightOne, tvOne, fanOne,lightTwo;
    SharedPreferences mPref;
    Context mContext;
    PendingIntent pi;
    Context mainContext;

   // private Common mApp;




    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

    }
    protected void initWidgets(Context context,int[] appWidgetIds){
        mContext = context;

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        context.startService(new Intent(context,BluetoothServices.class));
        RemoteViews views;
        ComponentName widgetName;
        mContext = context;
        //mApp = (Common) context.getApplicationContext();

        views = new RemoteViews(context.getPackageName(), R.layout.common_widget);
        widgetName = new ComponentName(mContext,CommonWidget.class);


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        //Intent i = new Intent(context, BluetoothServices.class);
        //i.setAction(WIDGET_BUTTON);
       // pi = PendingIntent.getService(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
       // views.setOnClickPendingIntent(R.id.widget_light_one,pi);


        views.setOnClickPendingIntent(R.id.widget_light_one,getSelfPendingIntent(context,WIDGET_BUTTON));
        views.setOnClickPendingIntent(R.id.widget_tv_one,getSelfPendingIntent(context,WIDGET_TV_BUTTON));
        //PendingIntent.getService(context,0,new Intent(context,BluetoothServices.class),0);
        views.setOnClickPendingIntent(R.id.widget_fan_one,getSelfPendingIntent(context,WIDGET_FAN_BUTTON));
        views.setOnClickPendingIntent(R.id.widget_light_two,getSelfPendingIntent(context,WIDGET_LIGHT_BUTTON));
        appWidgetManager.updateAppWidget(widgetName, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
       // mApp = (Common) context.getApplicationContext();
        mPref = context.getSharedPreferences("Common_widget_values",0);
        //context.getApplicationContext().bindService(intent,mConnection)

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews remoteViews;
        ComponentName watchWidget;

        //Toast.makeText(context,"light clicked",Toast.LENGTH_SHORT).show();
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.common_widget);
        watchWidget = new ComponentName(context, CommonWidget.class);
        //frag = new HomeFragment();

        if (WIDGET_BUTTON.equals(intent.getAction())){
            boolean  bulbOneClick = mPref.getBoolean("widget_bulb_one",true);
            //context.startService(pi);
                if (bulbOneClick){

                        remoteViews.setImageViewResource(R.id.widget_light_one,R.drawable.light_on);
                        Toast.makeText(context,"light on",Toast.LENGTH_SHORT).show();
                        mPref.edit().putBoolean("widget_bulb_one",false).apply();
                        sendMessage(mContext.getString(R.string.light1_on_bt_code));
                }else {

                        remoteViews.setImageViewResource(R.id.widget_light_one,R.drawable.light_off);
                        Toast.makeText(context,"light off",Toast.LENGTH_SHORT).show();
                        mPref.edit().putBoolean("widget_bulb_one",true).apply();
                    sendMessage(mContext.getString(R.string.light1_off_bt_code));

                }
            }
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        if (WIDGET_TV_BUTTON.equals(intent.getAction())){
            boolean isTvClick = mPref.getBoolean("widget_tv_one",true);
            if (isTvClick){
                remoteViews.setImageViewResource(R.id.widget_tv_one,R.drawable.tv_one_white);
                Toast.makeText(context,"Tv on",Toast.LENGTH_SHORT).show();
                mPref.edit().putBoolean("widget_tv_one",false).apply();
                sendMessage("sent message to  tv on");
            }else {
                remoteViews.setImageViewResource(R.id.widget_tv_one,R.drawable.tv_one);
                Toast.makeText(context,"Tv off",Toast.LENGTH_SHORT).show();
                mPref.edit().putBoolean("widget_tv_one",true).apply();
                sendMessage("sent message to tv off");
            }
            appWidgetManager.updateAppWidget(watchWidget,remoteViews);
        }
        if (WIDGET_FAN_BUTTON.equals(intent.getAction())){
            boolean isFanClick = mPref.getBoolean("widget_fan_one",true);


            if (isFanClick){
                //new MainActivity().sendDataToBt("on");
                remoteViews.setImageViewResource(R.id.widget_fan_one,R.drawable.fan_one);
                Toast.makeText(context,"Fan on",Toast.LENGTH_SHORT).show();
                mPref.edit().putBoolean("widget_fan_one",false).apply();
                sendMessage(mContext.getString(R.string.fan1_on_bt_code));
                //MainActivity activity = new MainActivity();

            }else {
                //new MainActivity().sendDataToBt("on");
                remoteViews.setImageViewResource(R.id.widget_fan_one,R.drawable.fan_black);
                Toast.makeText(context,"Fan off",Toast.LENGTH_SHORT).show();
                mPref.edit().putBoolean("widget_fan_one",true).apply();
                sendMessage(mContext.getString(R.string.fan2_off_bt_code));
            }
            appWidgetManager.updateAppWidget(watchWidget,remoteViews);
        }
        if (WIDGET_LIGHT_BUTTON.equals(intent.getAction())){
            boolean isLightTwoClick = mPref.getBoolean("widget_light_two",true);
            if (isLightTwoClick){
                remoteViews.setImageViewResource(R.id.widget_light_two,R.drawable.light_on);
                Toast.makeText(context,"light on",Toast.LENGTH_SHORT).show();
                mPref.edit().putBoolean("widget_light_two",false).apply();
                sendMessage(mContext.getString(R.string.light2_on_bt_code));
            }else {
                remoteViews.setImageViewResource(R.id.widget_light_two,R.drawable.light_off);
                Toast.makeText(context,"light off",Toast.LENGTH_SHORT).show();
                mPref.edit().putBoolean("widget_light_two",true).apply();
                sendMessage(mContext.getString(R.string.light2_off_bt_code));
            }
            appWidgetManager.updateAppWidget(watchWidget,remoteViews);
        }



    }
    protected PendingIntent getSelfPendingIntent(Context c,String action){
        Intent i = new Intent(c, CommonWidget.class);
        //Intent i = new Intent(Constants.WIDGET_ID);
        i.setAction(action);
        //return
        //i.putExtra("click",true);
        return PendingIntent.getBroadcast(c,0,i,0);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void sendMessage(String code){
        Intent i = new Intent(Constants.WIDGET_RECEIVER);
        i.putExtra("BtMessage",code);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
    }
}

