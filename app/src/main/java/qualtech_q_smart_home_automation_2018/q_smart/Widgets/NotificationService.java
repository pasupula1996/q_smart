package qualtech_q_smart_home_automation_2018.q_smart.Widgets;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants;

public class NotificationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.START_NOTIFICATION_SERVICE)){
            showNotification();
        }else if (intent.getAction().equals(Constants.LIGHT1_NOTIFICATION_SERVICE)){

        }else if (intent.getAction().equals(Constants.LIGHT2_NOTIFICATION_SERVICE)){

        }else if (intent.getAction().equals(Constants.FAN1_NOTIFICATION_SERVICE)){

        }else if (intent.getAction().equals(Constants.TV1_NOTIFICATION_SERVICE)){

        }

        return START_STICKY;
    }

    private void showNotification() {
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.notification_layout);

    }
}
