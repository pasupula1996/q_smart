package qualtech_q_smart_home_automation_2018.q_smart.Fragments;

//import android.app.DialogFragment;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import qualtech_q_smart_home_automation_2018.q_smart.Bluetooth.BTActivity;
import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.Services.BluetoothServices;

/**
 * Created by Qtspl _User on 3/21/2018.
 */

public class ScanFragment extends DialogFragment {

    ProgressBar scanProgress;
    BluetoothAdapter sBluetoothAdapter;
    ListView scanLv;
    Context mContext;
    Dialog mDialog;

    IntentFilter filter;
    Intent service;

    ArrayList<BluetoothDevice> scanArrayList = new ArrayList<>();
    ArrayAdapter<BluetoothDevice> scanAdapter;

    ArrayList<String> btNames = new ArrayList<>();
    ArrayAdapter<String> btAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_bluetooth_devices,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scanProgress = view.findViewById(R.id.scan_bt_progress);
        sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        scanLv = view.findViewById(R.id.lv_scan_bluetooth);
        if (sBluetoothAdapter == null){
            scanProgress.setVisibility(View.INVISIBLE);
        }else {
            sBluetoothAdapter.enable();
            sBluetoothAdapter.startDiscovery();
        }

        scanProgress.setVisibility(View.VISIBLE);

        scanLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sBluetoothAdapter.cancelDiscovery();
                scanProgress.setVisibility(View.INVISIBLE);
                //BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);
                BluetoothDevice device = scanArrayList.get(position);
                toast( device.getName());
                pairedDevice(device);
                /*mDialog = new Dialog(mContext);
                mDialog.setContentView(R.layout.bt_connecting);
                TextView tv = mDialog.findViewById(R.id.bt_connecting);
                tv.setText("Pairing..");*/
            }
        });

        filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

    }

    @Override
    public void onResume() {
        super.onResume();
        mContext.registerReceiver(sReceiver,filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(sReceiver);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (sBluetoothAdapter.isDiscovering()){
            sBluetoothAdapter.cancelDiscovery();
        }
    }

    BroadcastReceiver sReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        scanArrayList.add(device);
                        //toast(device.getName());
                        btNames.add(device.getName());
                        /*scanAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,scanArrayList);
                        scanLv.setAdapter(scanAdapter);
                        scanAdapter.notifyDataSetChanged();*/

                        btAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,btNames);
                        scanLv.setAdapter(btAdapter);
                        btAdapter.notifyDataSetChanged();

            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                scanProgress.setVisibility(View.INVISIBLE);
            }
        }
    };

    public void pairedDevice(BluetoothDevice device){
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
            Toast.makeText(getContext(),"Paired",Toast.LENGTH_SHORT).show();
            service = new Intent(getContext(), BluetoothServices.class);
            service.putExtra("bluetooth_device",device.getAddress());
            getActivity().startService(service);
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void toast(String mess){
        Toast.makeText(getActivity(),mess,Toast.LENGTH_SHORT).show();
    }
}
