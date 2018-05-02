package qualtech_q_smart_home_automation_2018.q_smart;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import qualtech_q_smart_home_automation_2018.q_smart.Bluetooth.BTActivity;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Light.LightFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Security.SecurityFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Services.ServiceFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Utilities.UtilitiesFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.entertainment.EntertainmentFragment;
import qualtech_q_smart_home_automation_2018.q_smart.Services.BluetoothServices;
import qualtech_q_smart_home_automation_2018.q_smart.Settings.SettingsActivity;

public class HomeDeviceActivity extends AppCompatActivity {

    TextView mLightTxt, mUtilities, mEntertainment, mServices, mSecurity;
    PopupWindow mPopupWindow;
    RelativeLayout mRelativeLayout;
    ImageView lightPopUp;

    private boolean lightbulbClicked = false;
    boolean dismissPopUp = true;
    Dialog mDialog;

    FragmentManager fm = getSupportFragmentManager();
    LightFragment lightFm;

    public BluetoothServices bServices;
    public boolean isbound = false;
    Intent i;

    public static HomeDeviceActivity instance;

    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bServices = ((BluetoothServices.LocalBinder)service).getService();
            //mServices.sendData("hello android");
            isbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"Bluetooth service disconnected",Toast.LENGTH_SHORT).show();
            bServices = null;

        }
    };

    public boolean sendDataToBt(String mess){
        boolean isTrue = false;
        Log.d("widget","from home device activity");
        if (isbound){
            if (bServices.sendData(mess)){
                isTrue = true;
                Log.d("widget","send data to bluetooth service class");
            }else {
                isTrue = false;
            }
        }

        return isTrue;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_device);

        //SQLiteDatabase db;
        //LDbHelper helper = new LDbHelper(this);
        //db = helper.getWritableDatabase();
        instance = this;

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar_2)));

        i = new Intent(this, BluetoothServices.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);

        mLightTxt = findViewById(R.id.lights);
        mUtilities = findViewById(R.id.utilities);
        mEntertainment = findViewById(R.id.entertainment);
        mServices = findViewById(R.id.services);
        mSecurity = findViewById(R.id.security);
        mRelativeLayout = findViewById(R.id.popup_details);

        // lightPopUp = findViewById(R.id.light_popup);

        Context context = getApplicationContext();
        simpleMenuClickListener();


    }

    @Override
    protected void onDestroy() {
        if (!isbound){
            stopService(i);
        }
        super.onDestroy();
    }

    public void tvDetailsFragment(){
        mDialog = new Dialog(this);

        mDialog.setTitle("Add new room");
        mDialog.setContentView(R.layout.fragment_tv_dialog);
        //room_edit_txt = mDialog.findViewById(R.id.room_name_user_txt);
        //addRoomButton = mDialog.findViewById(R.id.add_room_btn);
        //pick_room_image = mDialog.findViewById(R.id.add_room_user_image)
    }
    public void simpleMenuClickListener(){
        mLightTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightFm = new LightFragment(HomeDeviceActivity.this);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.popup_details,lightFm,"Light fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();

            }
        });
        mLightTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        mUtilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilitiesFragment uFragment = new UtilitiesFragment(HomeDeviceActivity.this);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,uFragment,"Utilities fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();

            }
        });
        mUtilities.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        mEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntertainmentFragment eFragment = new EntertainmentFragment(HomeDeviceActivity.this);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,eFragment,"Entertainment fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();

            }
        });
        mEntertainment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        mServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceFragment sFragment = new ServiceFragment(HomeDeviceActivity.this);
                fm.popBackStack();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,sFragment,"Entertainment fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        });
        mServices.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        mSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecurityFragment securityFragment = new SecurityFragment(HomeDeviceActivity.this);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fm.popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,securityFragment,"Security fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        });
        mSecurity.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                return true;
            case R.id.bt_settings:
                startActivity(new Intent(getApplicationContext(),BTActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    //@Override
    /*public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }else {
            finish();
        }
    }*/
}
