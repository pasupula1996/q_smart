package qualtech_q_smart_home_automation_2018.q_smart.Services;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants;

public class BluetoothServices extends Service {

    private BluetoothAdapter mBluetoothAdapter;
    public static final String B_DEVICE = "MY DEVICE";
    public static final String B_UUID = "00001101-0000-1000-8000-00805f9b34fb";
    // 00000000-0000-1000-8000-00805f9b34fb

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;

    private ConnectBtThread mConnectThread;
    private static ConnectedBtThread mConnectedThread;

    private static Handler mHandler = null;
    public static int mState = STATE_NONE;
    public static String deviceName;

    public boolean isConnected;
    private RemoteViews mViews;
    private NotificationCompat.Builder builder;


    //public static BluetoothDevice sDevice = null;
    //public Vector<Byte> packData = new Vector<>(2048);

    //IBinder mIBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //mHandler = getApplication().getHandler();
        return mBinder;
    }
    public void toast(String mess){
        Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
    }
    public final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public BluetoothServices getService() {
            // Return this instance of LocalService so clients can call public methods
            return BluetoothServices.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //registerReceiver(widgetReceiver,new IntentFilter(Constants.WIDGET_ID));
       // mApp = (Common) getApplicationContext();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(messageReceiver,new IntentFilter(Constants.WIDGET_RECEIVER));

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null){
            String deviceg = intent.getStringExtra("bluetooth_device");
            //isConnected = intent.getBooleanExtra("isConnected",false);


            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            connectToDevice(deviceg);
            //showNotification();
            //new Notification().showNotification();
        }

        return START_STICKY;
    }

    private synchronized void connectToDevice(String macAddress){
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
        if (mState == STATE_CONNECTING){
            if (mConnectThread != null){
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }
        if (mConnectedThread != null){
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        mConnectThread = new ConnectBtThread(device);
        toast("connecting");
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }
    private void setState(int state){
        mState = state;
        if (mHandler != null){
           // mHandler.obtainMessage();
        }
    }
    public synchronized void stop(){
        setState(STATE_NONE);
        if (mConnectThread != null){
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread != null){
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        if (mBluetoothAdapter != null){
            mBluetoothAdapter.cancelDiscovery();
        }

        stopSelf();
    }

    public boolean sendData(String message){
        boolean isTrue = false;
        if(mConnectThread!= null){
            if (mConnectedThread!= null){
                SharedPreferences pref = getSharedPreferences("BT_STATUS",0);
                isConnected = pref.getBoolean("isConnected",false);
                if (isConnected){
                    mConnectedThread.write(message.getBytes());
                    //toast("sent data");
                    Log.d("widget","from main bluetooth service");
                    isTrue = true;
                }else {
                    //toast("Please Connect to Bluetooth First");
                    isTrue = false;
                }
            }else {
                Toast.makeText(BluetoothServices.this,"Failed to send data",Toast.LENGTH_SHORT).show();
                isTrue = false;
            }
        }
        return isTrue;
    }

    @Override
    public boolean stopService(Intent name) {
        setState(STATE_NONE);

        if (mConnectThread != null){
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null){
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mBluetoothAdapter.cancelDiscovery();
        return super.stopService(name);
    }



    private synchronized void connected(BluetoothSocket mmSocket){

        mConnectedThread = new ConnectedBtThread(mmSocket);
        mConnectedThread.start();


        setState(STATE_CONNECTED);
    }

    private class ConnectBtThread extends Thread{
        private final BluetoothSocket mSocket;
        private final BluetoothDevice mDevice;

        public ConnectBtThread(BluetoothDevice device){
            mDevice = device;
            BluetoothSocket socket = null;
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(B_UUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSocket = socket;

        }

        @Override
        public void run() {
            mBluetoothAdapter.cancelDiscovery();

            try {
                mSocket.connect();
                Log.d("service","connect thread run method (connected)");
                SharedPreferences pre = getSharedPreferences("BT_NAME",0);
                pre.edit().putString("bluetooth_connected",mDevice.getName()).apply();

            } catch (IOException e) {

                try {
                    mSocket.close();
                    Log.d("service","connect thread run method ( close function)");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            connected(mSocket);
           // mConnectedThread = new ConnectedBtThread(mSocket);
            //mConnectedThread.start();
        }

        public void cancel(){

            try {
                mSocket.close();
                Log.d("service","connect thread cancel method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectedBtThread extends Thread{
        private final BluetoothSocket cSocket;
        private final InputStream inS;
        private final OutputStream outS;

        private byte[] buffer;

        public ConnectedBtThread(BluetoothSocket socket){
            cSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inS = tmpIn;
            outS = tmpOut;
        }

        @Override
        public void run() {
            buffer = new byte[1024];
            int mByte;
            try {
                mByte= inS.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("service","connected thread run method");

        }


        public void write(byte[] buff){
            try {
                outS.write(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void cancel(){
            try {
                cSocket.close();
                Log.d("service","connected thread cancel method");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        this.stop();
        super.onDestroy();
        //unregisterReceiver(widgetReceiver);
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent){
                String extra = intent.getStringExtra("BtMessage");
                //Toast.makeText(getApplicationContext(),extra,Toast.LENGTH_SHORT).show();
                sendData(extra);
            }

        }
    };

    public void showNotification(){
        mViews = new RemoteViews(getPackageName(),R.layout.notification_layout);
        builder = new NotificationCompat.Builder(getApplicationContext())
                .setOngoing(true)
                .setContent(mViews)
                .setSmallIcon(R.drawable.logo);
        startForeground(2,builder.build());
    }


}
