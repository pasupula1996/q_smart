package qualtech_q_smart_home_automation_2018.q_smart.Services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class GlobalBtAccess extends Activity {

    public BluetoothServices btServices;
    public boolean isBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BluetoothServices.LocalBinder binder = ((BluetoothServices.LocalBinder)service);
            btServices = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, BluetoothServices.class);
        bindService(i,mConnection,BIND_AUTO_CREATE);
    }
}
