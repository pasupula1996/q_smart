package qualtech_q_smart_home_automation_2018.q_smart.Fragments;


import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;


import qualtech_q_smart_home_automation_2018.q_smart.Activities.GeneralActivity;
import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenu.HomeFragment;
import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenu.BedRoomFragment;
import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenu.KitchenFragment;
import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenu.LobbyFragment;
import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenu.StudyFragment;
import qualtech_q_smart_home_automation_2018.q_smart.Widgets.CommonWidget;

import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_BUTTON;
import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_FAN_BUTTON;
import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_LIGHT_BUTTON;
import static qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants.WIDGET_TV_BUTTON;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment  {

    LinearLayout home,layout,lobby,kitchenFrag, studyRoom, singleMenu;
    Fragment fm1,fm2,fm3,fm4,fm5;


    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().registerReceiver(widgetReceiver,new IntentFilter(WIDGET_ID));

        FloatingActionButton fabButton = view.findViewById(R.id.fab_bottom);
        layout = view.findViewById(R.id.bed_room_card_view);
        lobby = view.findViewById(R.id.lobby_fragment);
        kitchenFrag = view.findViewById(R.id.kitchen_click_fragment);
        studyRoom = view.findViewById(R.id.study_room_click_fragment);
        singleMenu = view.findViewById(R.id.single_menu_cardview);
        home = view.findViewById(R.id.bed_room_click);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent i = new Intent(getContext(),CustomMenuActivity.class);
                //startActivity(i);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bedRoomMethod();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"you clicked bed room",Toast.LENGTH_SHORT).show();
                bedRoomMethod2();
            }
        });
        lobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lobbyFragment();
            }
        });
        kitchenFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kitchenClick();
            }
        });
        studyRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyRoomClick();
            }
        });
        singleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GeneralActivity.class));
            }
        });

    }

    public void bedRoomMethod() {

            //startActivity(new Intent(getApplicationContext(),BedRoomActivity.class));
            //sFragmentManager.beginTransaction().add(sBedRoomFragment,"sBedRoomFragmetn").commit();
            FragmentTransaction fm =getChildFragmentManager().beginTransaction();
            fm1 = new HomeFragment();
            fm.replace(R.id.activity_main,fm1,"home");
            fm.addToBackStack(null);
            //fm.addToBackStack("home");
            fm.commit();

    }
    public void bedRoomMethod2(){

        //Toast.makeText(getApplicationContext(),"again you clicked bedroom",Toast.LENGTH_SHORT).show();
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm2 = new BedRoomFragment();
        fm.addToBackStack(null);
        fm.replace(R.id.activity_main,fm2,"bedroom2");

       // getChildFragmentManager().addToBackStack("layout");
        fm.commit();
    }
    public void lobbyFragment(){
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm3 = new LobbyFragment();
        fm.replace(R.id.activity_main,fm3,"lobby");
        fm.addToBackStack(null);
        //getChildFragmentManager().popBackStackImmediate();
        //fm.addToBackStack("lobby");
        fm.commit();
    }
    public void kitchenClick(){
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm4 = new KitchenFragment();
        fm.add(R.id.activity_main,fm4,"kitchen");
        //fm.addToBackStack(null);
        fm.addToBackStack(null);
        fm.commit();
    }
    public void studyRoomClick(){
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm5 = new StudyFragment();
        fm.replace(R.id.activity_main,fm5,"study");
        fm.addToBackStack(null);
        //fm.addToBackStack(null);
        fm.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
       //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // getActivity().getSupportFragmentManager().popBackStack();
       // getActivity().unregisterReceiver(widgetReceiver);
    }


    private BroadcastReceiver widgetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // mApp = (Common) context.getApplicationContext();
            Toast.makeText(context,"light on",Toast.LENGTH_SHORT).show();

            SharedPreferences mPref;
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



                }else {

                    remoteViews.setImageViewResource(R.id.widget_light_one,R.drawable.light_off);
                    Toast.makeText(context,"light off",Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("widget_bulb_one",true).apply();

                }
            }
            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

            if (WIDGET_TV_BUTTON.equals(intent.getAction())){
                boolean isTvClick = mPref.getBoolean("widget_tv_one",true);
                if (isTvClick){
                    remoteViews.setImageViewResource(R.id.widget_tv_one,R.drawable.tv_one_white);
                    Toast.makeText(context,"Tv on",Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("widget_tv_one",false).apply();
                }else {
                    remoteViews.setImageViewResource(R.id.widget_tv_one,R.drawable.tv_one);
                    Toast.makeText(context,"Tv off",Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("widget_tv_one",true).apply();
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
                    //MainActivity activity = new MainActivity();

                }else {
                    //new MainActivity().sendDataToBt("on");
                    remoteViews.setImageViewResource(R.id.widget_fan_one,R.drawable.fan_black);
                    Toast.makeText(context,"Fan off",Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("widget_fan_one",true).apply();
                }
                appWidgetManager.updateAppWidget(watchWidget,remoteViews);
            }
            if (WIDGET_LIGHT_BUTTON.equals(intent.getAction())){
                boolean isLightTwoClick = mPref.getBoolean("widget_light_two",true);
                if (isLightTwoClick){
                    remoteViews.setImageViewResource(R.id.widget_light_two,R.drawable.light_on);
                    Toast.makeText(context,"light on",Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("widget_light_two",false).apply();
                }else {
                    remoteViews.setImageViewResource(R.id.widget_light_two,R.drawable.light_off);
                    Toast.makeText(context,"light off",Toast.LENGTH_SHORT).show();
                    mPref.edit().putBoolean("widget_light_two",true).apply();
                }
                appWidgetManager.updateAppWidget(watchWidget,remoteViews);
            }

        }
    };



}
