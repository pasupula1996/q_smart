package qualtech_q_smart_home_automation_2018.q_smart.Bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import qualtech_q_smart_home_automation_2018.q_smart.Fragments.ScanFragment;
import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.Services.BluetoothServices;

public class BTActivity extends AppCompatActivity {


    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mBluetoothDevice;

    ArrayList<BluetoothDevice> devices = new ArrayList<>();
    ArrayAdapter<BluetoothDevice> arrayAdapter;

    List<String> btNames = new ArrayList<>();
    ArrayAdapter<String> btAdapter;


   // ConnectThread c;
    Intent service;

    private static final String TAG = "MY_BT";

    ListView lvPaired;
    Button BluetoothOnOff, ScanBt, pairedlist, sendButton, btDisconnect, incrementBtn, decrementBtn;
    EditText input_text;
    TextView ConnectedTo;
    boolean isConnected = false;

    Dialog Connecting;

    FragmentManager fm = getSupportFragmentManager();

    UUID BHANU_UUID = UUID.fromString("00000000-0000-1000-8000-00805f9b34fb");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        lvPaired = findViewById(R.id.btPairedLV);
        pairedlist = findViewById(R.id.btPaired);
        input_text = findViewById(R.id.user_input);
        sendButton = findViewById(R.id.btnSend);
        BluetoothOnOff = findViewById(R.id.offOn_bt);
        ScanBt = findViewById(R.id.scan_bt);
        ConnectedTo = findViewById(R.id.current_connected_bluetooth);
        btDisconnect = findViewById(R.id.bt_disconnect);


        if (!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
        }

        onClickListener();
        pairedList();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter);

        if (!mBluetoothAdapter.isEnabled()){

            ConnectedTo.setText("none");
            btDisconnect.setVisibility(View.INVISIBLE);
        }

        SharedPreferences pref = getSharedPreferences("BT_STATUS",0);
        boolean isC = pref.getBoolean("isConnected",false);
        String bt_name = pref.getString("name","None");
        if (isC){
            btDisconnect.setVisibility(View.VISIBLE);
            ConnectedTo.setText(bt_name);
        }

    }
    public void onClickListener(){

        pairedlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayAdapter != null){
                    arrayAdapter.clear();
                }
                if (btAdapter != null){
                    btAdapter.clear();
                }
                pairedList();
            }
        });

        lvPaired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConnectedTo.setText("none");
                btDisconnect.setVisibility(View.INVISIBLE);

                //BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);
                //c = new ConnectThread(device, true);
                //c.start();
                BluetoothDevice device = devices.get(position);

                service = new Intent(BTActivity.this, BluetoothServices.class);
                service.putExtra("bluetooth_device",device.getAddress());
                service.putExtra("isConnected",isConnected);
                startService(service);
                Connecting = new Dialog(BTActivity.this);
                Connecting.setContentView(R.layout.bt_connecting);
                Connecting.show();
            }
        });
        lvPaired.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog renameBt = new Dialog(BTActivity.this);
                renameBt.setContentView(R.layout.rename_bed_room);
                final EditText rename = renameBt.findViewById(R.id.bed_room_edx_rename);
                Button bt = renameBt.findViewById(R.id.btn_update_bed_room_name);
                rename.setText(btNames.get(position));
                renameBt.show();
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String name = rename.getText().toString();
                       BluetoothDevice de = devices.get(position);

                        try {
                            Method mv = de.getClass().getMethod("Bluetooth",String.class);
                            if (mv != null){
                                mv.invoke(de,name);
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        renameBt.dismiss();
                        if (btAdapter != null){
                            btAdapter.clear();
                        }
                        pairedList();
                    }
                });
                return true;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input_text.getText().toString();
                //c.sendData(text);
                input_text.setText("");

            }
        });
        BluetoothOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()){
                    mBluetoothAdapter.enable();
                    BluetoothOnOff.setText("ON");
                    pairedList();
                }else {
                    mBluetoothAdapter.disable();
                    BluetoothOnOff.setText("OFF");
                    ConnectedTo.setText("none");
                    btDisconnect.setVisibility(View.INVISIBLE);

                    SharedPreferences preferences = getSharedPreferences("BT_NAME",0);
                    preferences.edit().putString("bt_connected","None").apply();
                    //ConnectedTo.setText("None");
                    SharedPreferences pef = getSharedPreferences("BT_STATUS",0);
                    pef.edit().putBoolean("isConnected",false).apply();
                    pef.edit().putString("name","None").apply();
                }
                //stopService(service);
                //toast("service stopped");
            }
        });
        ScanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanFragment scanFragment = new ScanFragment();

                scanFragment.show(fm,"ScanBT");
                //ScanFragment scanFragment = new ScanFragment();
                //scanFragment.show(fm,"Scan Fragment");
            }
        });
        btDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // c.cancel();
            }
        });


    }


    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void pairedList() {
        //arrayAdapter.clear();
        Set<BluetoothDevice> device = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device1 : device){
            devices.add(device1);
            btNames.add(device1.getName());
            //deviceAddress.add(device1.getName());
        }

       /*arrayAdapter = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1,android.R.id.text1, devices);
        lvPaired.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();*/

        btAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,btNames);
        lvPaired.setAdapter(btAdapter);
        btAdapter.notifyDataSetChanged();

    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                toast("connected");
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                ConnectedTo.setText(device.getName());

                SharedPreferences preferences = getSharedPreferences("BT_NAME",0);
                preferences.edit().putString("bt_connected",device.getName()).apply();

                SharedPreferences pef = getSharedPreferences("BT_STATUS",0);
                pef.edit().putBoolean("isConnected",true).apply();
                pef.edit().putString("name",device.getName()).apply();

                btDisconnect.setVisibility(View.VISIBLE);
                if (Connecting != null){
                    Connecting.dismiss();
                }
                //getActionBar().setSubtitle(device.getName());
                Log.d("BT", "updated to true");
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                toast("disconnected");
                //stopService(service);
                SharedPreferences preferences = getSharedPreferences("BT_NAME",0);
                preferences.edit().putString("bt_connected","None").apply();
                ConnectedTo.setText("None");
                SharedPreferences pef = getSharedPreferences("BT_STATUS",0);
                pef.edit().putBoolean("isConnected",false).apply();
                pef.edit().putString("name","None").apply();
                btDisconnect.setVisibility(View.INVISIBLE);
               // getActionBar().setSubtitle("");
                // boolean shared =  pef.getBoolean("isConnected",false);
                //Toast.makeText(getApplicationContext(), String.valueOf(shared),Toast.LENGTH_SHORT).show();
                Log.d("BT","disconnected");
            }

        }
    };


    /*public class ConnectThread extends Thread {
        BluetoothDevice cDevice;
        BluetoothSocket socket;
        ConnectedThread ct;

        ConnectThread(BluetoothDevice device, boolean insecureConnection) {
            cDevice = device;
            try {
                if (insecureConnection) {
                    socket = device.createInsecureRfcommSocketToServiceRecord(BHANU_UUID);
                } else {
                    socket = device.createRfcommSocketToServiceRecord(BHANU_UUID);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

        public void run() {
            mBluetoothAdapter.cancelDiscovery();

            try {
                Log.d("BT", "Socket ready to connect");
                socket.connect();
                Log.d("BT", "Socket connected");
                // out = socket.getOutputStream();
                // input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            } catch (final IOException e) {
                e.getMessage();
            }

            ct = new ConnectedThread(socket);

            //ct.write("Q-smart".getBytes());
                /*try {
                    socket.close();
                } catch (final IOException closeException) {
                    closeException.getMessage();
                }*/
       /* }
        public void sendData(String message){
            Log.d(TAG,message);
            if (socket != null){
                ct.write(message.getBytes());
            }else {
                toast("Please connect to bluetooth first");
            }
        }*/

      //  public void cancel() {
         //   try {
             //   socket.close();
        ////    } catch (IOException e) {
        //        e.printStackTrace();
      //      }
      //  }
   // }

  //  public class ConnectedThread extends Thread {
       // private final BluetoothSocket mmSocket;
       // private final InputStream mmInStream;
       // private final OutputStream mmOutStream;
     //   private byte[] mmBuffer; // mmBuffer store for the stream

       // public ConnectedThread(BluetoothSocket socket) {
           // mmSocket = socket;
           // InputStream tmpIn = null;
           // OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
           // try {
               // tmpIn = socket.getInputStream();
          //  } catch (IOException e) {
          //      Log.e(TAG, "Error occurred when creating input stream", e);
         //   }
         //   try {
           //     tmpOut = socket.getOutputStream();
          //  } catch (IOException e) {
           //     Log.e(TAG, "Error occurred when creating output stream", e);
           // }

          //  mmInStream = tmpIn;
          //  mmOutStream = tmpOut;
       // }

      //  public void run() {
           //mmBuffer = new byte[1024];
           // int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
           // while (true) {
               // try {
                    // Read from the InputStream.
                    //numBytes = mmInStream.read(mmBuffer);
                    // Send the obtained bytes to the UI activity.
                   /* Message readMsg = mHandler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();*/
                /*} catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }*/

        // Call this from the main activity to send data to the remote device.
        /*public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);*/

                // Share the sent message with the UI activity.
               /* Message writtenMsg = mHandler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();*/
           /* } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);*/

                // Send a failure message back to the activity.
               /* Message writeErrorMsg =
                        mHandler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                mHandler.sendMessage(writeErrorMsg);*/
     /*       }
        }


    }*/
}
