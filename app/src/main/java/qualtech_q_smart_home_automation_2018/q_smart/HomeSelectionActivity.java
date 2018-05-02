package qualtech_q_smart_home_automation_2018.q_smart;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

import qualtech_q_smart_home_automation_2018.q_smart.Bluetooth.BTActivity;
import qualtech_q_smart_home_automation_2018.q_smart.Fragments.CustomFragment;
import qualtech_q_smart_home_automation_2018.q_smart.Fragments.SimpleFragment;
import qualtech_q_smart_home_automation_2018.q_smart.Modules.Module_Tab;
import qualtech_q_smart_home_automation_2018.q_smart.Services.BluetoothServices;
import qualtech_q_smart_home_automation_2018.q_smart.Settings.SettingsActivity;
import qualtech_q_smart_home_automation_2018.q_smart.Utilities.MyNotification;

public class HomeSelectionActivity extends AppCompatActivity {

    BluetoothServices service;
    public static String WIDGET_ONE = "Clicked_light_one";

    private Toolbar mToolbar;
    public TabLayout mTabLayout;
    private ViewPager mViewPager;


    private SimpleFragment sf;
    private CustomFragment cf;
    private Module_Tab mModuleTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_selection);

        new MyNotification(this);


        BluetoothAdapter bp = BluetoothAdapter.getDefaultAdapter();
        if (bp!= null){
            if (!bp.isEnabled()){
                bp.enable();
            }
        }

        mToolbar = findViewById(R.id.toolbar_layout);
        mToolbar.setTitle("Q-smart");
        mToolbar.inflateMenu(R.menu.main_menu);
        
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(1);


        setupViewPager(mViewPager);
        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //mViewPager.setCurrentItem(2);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.settings){
                    startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                }else if (item.getItemId()==R.id.bt_settings){
                    startActivity(new Intent(getApplicationContext(),BTActivity.class));
                }
                return false;
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(WIDGET_ONE);
        registerReceiver(hReceiver, filter);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        sf = new SimpleFragment();
        cf = new CustomFragment();
        mModuleTab = new Module_Tab();

        adapter.addFragment(mModuleTab,"Modules");
       // adapter.addFragment(sf,"Simple menu");
        //adapter.addFragment(cf,"Custom menu");
        //adapter.addFragment(sf,"Simple menu");

        viewPager.setAdapter(adapter);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
        public void addFragment(Fragment fragment,String title){
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(hReceiver);
        super.onDestroy();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
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


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getFragments()!= null){
            for (Fragment frag: fm.getFragments()){
                if (frag.isVisible()){
                    FragmentManager chilFrag = frag.getChildFragmentManager();
                    if (chilFrag.getBackStackEntryCount()>0){
                        chilFrag.popBackStack();
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
    }

    BroadcastReceiver hReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //startBluetooth(device.getAddress());

                SharedPreferences preferences = getSharedPreferences("BT_NAME",0);
                preferences.edit().putString("bt_connected",device.getName()).apply();

                SharedPreferences pef = getSharedPreferences("BT_STATUS",0);
                pef.edit().putBoolean("isConnected",true).apply();
                pef.edit().putString("name",device.getName()).apply();
                //Toast.makeText(getApplicationContext(),"Bluetooth Connected",Toast.LENGTH_SHORT).show();


            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                SharedPreferences preferences = getSharedPreferences("BT_NAME",0);
                preferences.edit().putString("bt_connected","None").apply();

                SharedPreferences pef = getSharedPreferences("BT_STATUS",0);
                pef.edit().putBoolean("isConnected",false).apply();
                pef.edit().putString("name","None").apply();


            }

        }
    };
}
