package qualtech_q_smart_home_automation_2018.q_smart.SimpleMenu;


import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenuActivity;
import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.Services.BluetoothServices;
import qualtech_q_smart_home_automation_2018.q_smart.Utilities.Constants;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class LobbyFragment extends Fragment {

    LinearLayout lobbyLightLayout,lobbyDimmerLayout,lobbyfanLayout,lobbyPlugLayout;
    ImageView lobbyRoomPic,lobbyPic, lobbyTvControl;
    TextView renamelobbyRoom;

    private SharedPreferences mPref;
    SharedPreferences.Editor editor;

    ImageView allLightsImg, lightOneImg,lightTwoImg, lightThreeImg, lightFourImg, lightFiveImg, lightSixImg, lightSevenImg, lightEightImg;
    TextView allLightsTxt, lightOneTxt, lightTwoTxt, lightThreeTxt, lightFourTxt, lightFiveTxt, lightSixTxt, lightSevenTxt, lightEightTxt;

    ImageView allDimmerImg, dimmerOneImg, dimmerTwoImg;
    TextView allDimmerTxt, dimmerOneTxt, dimmerTwoTxt;
    SeekBar dimmerOneSeek, dimmerTwoSeek;

    ImageView allFansImg, fanOneImg, fanTwoImg;
    TextView allFansTxt, fanOneTxt, fanTwoTxt;
    SeekBar fanOneSeek, fanTwoSeek;

    ImageView allPlugsImg, plugOneImg, plugTwoImg, plugThreeImg, plugFourImg;
    TextView allPlugsTxt, plugOneTxt, plugTwoTxt, plugThreeTxt, plugFourTxt;
    Dialog lDialogL, lDialogF,lDialogP, lDialogD;


    public LobbyFragment() {
        // Required empty public constructor
    }
    public BluetoothServices mServices;
    public boolean isbound = false;

    public ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServices = ((BluetoothServices.LocalBinder)service).getService();
            //mServices.sendData("hello android");
            isbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getContext(),"Bluetooth service disconnected",Toast.LENGTH_SHORT).show();
            mServices = null;

        }
    };

    public boolean sendDataToBt(String mess){
        boolean isTrue = false;
        if (isbound){
            if (mServices.sendData(mess)){
                isTrue = true;
            }else {
                isTrue = false;
            }
        }

        return isTrue;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent ii = new Intent(getContext(),BluetoothServices.class);
        getActivity().bindService(ii,mConnection, Context.BIND_AUTO_CREATE);
        ((SimpleMenuActivity)getActivity()).mTabLayout.setVisibility(View.GONE);
        mPref = getActivity().getSharedPreferences("LOBBY_VALUES",0);
        return inflater.inflate(R.layout.fragment_lobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lobbyLightLayout = view.findViewById(R.id.lobby_lights_layout);
        lobbyDimmerLayout = view.findViewById(R.id.lobby_dimmer_dialog_layout);
        lobbyfanLayout = view.findViewById(R.id.lobby_fan_dialog_layout);
        lobbyPlugLayout = view.findViewById(R.id.lobby_plug_layout);

        lobbyRoomPic = view.findViewById(R.id.edit_lobby_cover_btn);
        renamelobbyRoom = view.findViewById(R.id.rename_lobby_room);
        lobbyPic = view.findViewById(R.id.lobby_room);
        lobbyTvControl = view.findViewById(R.id.lobby_tv_controls);
        lobbyRoomPic.setImageResource(R.drawable.ic_edit_black);



        lobbyLightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lDialogL = new Dialog(getContext());
                lDialogL.setContentView(R.layout.light_dialog);
                TextView title = lDialogL.findViewById(R.id.light_dialog_title);
                title.setText("Lobby");

                allLightsImg = lDialogL.findViewById(R.id.light_dialog_imageView_all);
                allLightsTxt = lDialogL.findViewById(R.id.light_dialog_all_lights_txt_click);

                lightOneImg = lDialogL.findViewById(R.id.light_dialog_imageView_one);
                lightOneTxt = lDialogL.findViewById(R.id.light_dialog_light_one_txt_click);
                lightTwoImg = lDialogL.findViewById(R.id.light_dialog_imageView_two);
                lightTwoTxt = lDialogL.findViewById(R.id.light_dialog_light_two_txt_click);
                lightThreeImg = lDialogL.findViewById(R.id.light_dialog_imageView_three);
                lightThreeTxt = lDialogL.findViewById(R.id.light_dialog_light_three_txt_click);
                lightFourImg = lDialogL.findViewById(R.id.light_dialog_imageView_four);
                lightFourTxt = lDialogL.findViewById(R.id.light_dialog_light_four_txt_click);
                lightFiveImg = lDialogL.findViewById(R.id.light_dialog_imageView_five);
                lightFiveTxt = lDialogL.findViewById(R.id.light_dialog_light_five_txt_click);
                lightSixImg = lDialogL.findViewById(R.id.light_dialog_imageView_six);
                lightSixTxt = lDialogL.findViewById(R.id.light_dialog_light_six_txt_click);
                lightSevenImg = lDialogL.findViewById(R.id.light_dialog_imageView_seven);
                lightSevenTxt = lDialogL.findViewById(R.id.light_dialog_light_seven_txt_click);
                lightEightImg = lDialogL.findViewById(R.id.light_dialog_imageView_eight);
                lightEightTxt = lDialogL.findViewById(R.id.light_dialog_light_eight_txt_click);

                lDialogL.show();
                boolean tempOne = mPref.getBoolean("light_one",false);
                if (tempOne) {
                    lightOneTxt.setText("ON");
                    lightOneTxt.setTextColor(Color.GREEN);
                    lightOneImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempTwo = mPref.getBoolean("light_two",false);
                if (tempTwo) {
                    lightTwoTxt.setText("ON");
                    lightTwoTxt.setTextColor(Color.GREEN);
                    lightTwoImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempThree = mPref.getBoolean("light_three",false);
                if (tempThree) {
                    lightThreeTxt.setText("ON");
                    lightThreeTxt.setTextColor(Color.GREEN);
                    lightThreeImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempFour = mPref.getBoolean("light_four",false);
                if (tempFour) {
                    lightFourTxt.setText("ON");
                    lightFourTxt.setTextColor(Color.GREEN);
                    lightFourImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempFive = mPref.getBoolean("light_five",false);
                if (tempFive) {
                    lightFiveTxt.setText("ON");
                    lightFiveTxt.setTextColor(Color.GREEN);
                    lightFiveImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempSix = mPref.getBoolean("light_six",false);
                if (tempSix) {
                    lightSixTxt.setText("ON");
                    lightSixTxt.setTextColor(Color.GREEN);
                    lightSixImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempSeven = mPref.getBoolean("light_seven",false);
                if (tempSeven) {
                    lightSevenTxt.setText("ON");
                    lightSevenTxt.setTextColor(Color.GREEN);
                    lightSevenImg.setImageResource(R.drawable.lightbulb);
                }
                boolean tempEight = mPref.getBoolean("light_eight",false);
                if (tempEight) {
                    lightEightTxt.setText("ON");
                    lightEightTxt.setTextColor(Color.GREEN);
                    lightEightImg.setImageResource(R.drawable.lightbulb);
                }
                if (tempOne || tempTwo || tempThree || tempFour || tempFive || tempSix || tempSeven || tempEight){
                    allLightsImg.setImageResource(R.drawable.lightbulb);
                    allLightsTxt.setText("ON");

                }
                allLightsTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sendDataToBt(getString(R.string.all_lights_bt_off_code))){

                            allLightsTxt.setText("OFF");
                            allLightsImg.setImageResource(R.drawable.ic_lightbulb_off);

                            lightOneImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightOneTxt.setText("OFF");
                            lightOneTxt.setTextColor(Color.BLACK);
                            lightTwoImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightTwoTxt.setText("OFF");
                            lightTwoTxt.setTextColor(Color.BLACK);
                            lightThreeImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightThreeTxt.setText("OFF");
                            lightThreeTxt.setTextColor(Color.BLACK);
                            lightFourImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightFourTxt.setText("OFF");
                            lightFourTxt.setTextColor(Color.BLACK);
                            lightFiveImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightFiveTxt.setText("OFF");
                            lightFiveTxt.setTextColor(Color.BLACK);
                            lightSixImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightSixTxt.setText("OFF");
                            lightSixTxt.setTextColor(Color.BLACK);
                            lightSevenImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightSevenTxt.setText("OFF");
                            lightSevenTxt.setTextColor(Color.BLACK);
                            lightEightImg.setImageResource(R.drawable.ic_lightbulb_off);
                            lightEightTxt.setText("OFF");
                            lightEightTxt.setTextColor(Color.BLACK);

                            mPref.edit().putBoolean("light_one",false).apply();
                            mPref.edit().putBoolean("light_two",false).apply();
                            mPref.edit().putBoolean("light_three",false).apply();
                            mPref.edit().putBoolean("light_four",false).apply();
                            mPref.edit().putBoolean("light_five",false).apply();
                            mPref.edit().putBoolean("light_six",false).apply();
                            mPref.edit().putBoolean("light_seven",false).apply();
                            mPref.edit().putBoolean("light_eight",false).apply();

                            //off();
                        }else {
                            notConnected();
                        }


                    }
                });
                lightOneTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightOne = mPref.getBoolean("light_one",false);
                        if (!isLightOne){
                            if (sendDataToBt(getString(R.string.light1_on_bt_code))) {
                                lightOneTxt.setText("ON");
                                lightOneTxt.setTextColor(Color.GREEN);
                                lightOneImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_one", true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }


                        }else {
                            if (sendDataToBt(getString(R.string.light1_off_bt_code))) {
                                lightOneTxt.setText("OFF");
                                lightOneTxt.setTextColor(Color.BLACK);
                                lightOneImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_one",false).apply();
                                off();
                            }else {
                                notConnected();
                            }

                        }
                    }
                });
                lightTwoTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightTwo = mPref.getBoolean("light_two",false);
                        if (!isLightTwo){
                            if (sendDataToBt(getString(R.string.light2_on_bt_code))) {
                                lightTwoTxt.setText("ON");
                                lightTwoTxt.setTextColor(Color.GREEN);
                                lightTwoImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_two",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }

                        }else {
                            if (sendDataToBt(getString(R.string.light2_off_bt_code))) {

                                lightTwoTxt.setText("OFF");
                                lightTwoTxt.setTextColor(Color.BLACK);
                                lightTwoImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_two", false).apply();
                                off();
                            }
                        }
                    }
                });
                lightThreeTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightThree = mPref.getBoolean("light_three",false);
                        if (!isLightThree){
                            if (sendDataToBt(getString(R.string.light3_on_bt_code))){
                                lightThreeTxt.setText("ON");
                                lightThreeTxt.setTextColor(Color.GREEN);
                                lightThreeImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_three",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.light3_off_bt_code))){
                                lightThreeTxt.setText("OFF");
                                lightThreeTxt.setTextColor(Color.BLACK);
                                lightThreeImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_three",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                lightFourTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightFour = mPref.getBoolean("light_four",false);
                        if (!isLightFour){
                            if (sendDataToBt(getString(R.string.light4_on_bt_code))){
                                lightFourTxt.setText("ON");
                                lightFourTxt.setTextColor(Color.GREEN);
                                lightFourImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_four",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.light4_off_bt_code))){
                                lightFourTxt.setText("OFF");
                                lightFourTxt.setTextColor(Color.BLACK);
                                lightFourImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_four",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                lightFiveTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightFive = mPref.getBoolean("light_five",false);
                        if (!isLightFive){
                            if (sendDataToBt(getString(R.string.light5_on_bt_code))){
                                lightFiveTxt.setText("ON");
                                lightFiveTxt.setTextColor(Color.GREEN);
                                lightFiveImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_five",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.light5_off_bt_code))){
                                lightFiveTxt.setText("OFF");
                                lightFiveTxt.setTextColor(Color.BLACK);
                                lightFiveImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_five",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                lightSixTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightSix = mPref.getBoolean("light_six",false);
                        if (!isLightSix){
                            if (sendDataToBt(getString(R.string.light6_on_bt_code))){
                                lightSixTxt.setText("ON");
                                lightSixTxt.setTextColor(Color.GREEN);
                                lightSixImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_six",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.light6_off_bt_code))){
                                lightSixTxt.setText("OFF");
                                lightSixTxt.setTextColor(Color.BLACK);
                                lightSixImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_six",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                lightSevenTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightSeven = mPref.getBoolean("light_seven",false);
                        if (!isLightSeven){
                            if (sendDataToBt(getString(R.string.light7_on_bt_code))){
                                lightSevenTxt.setText("ON");
                                lightSevenTxt.setTextColor(Color.GREEN);
                                lightSevenImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_seven",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.light7_off_bt_code))){
                                lightSevenTxt.setText("OFF");
                                lightSevenTxt.setTextColor(Color.BLACK);
                                lightSevenImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_seven",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                lightEightTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isLightEight = mPref.getBoolean("light_eight",false);
                        if (!isLightEight){
                            if (sendDataToBt(getString(R.string.light8_on_bt_code))){
                                lightEightTxt.setText("ON");
                                lightEightTxt.setTextColor(Color.GREEN);
                                lightEightImg.setImageResource(R.drawable.lightbulb);
                                mPref.edit().putBoolean("light_eight",true).apply();

                                allLightsTxt.setText("ON");
                                allLightsImg.setImageResource(R.drawable.lightbulb);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.light8_off_bt_code))){
                                lightEightTxt.setText("OFF");
                                lightEightTxt.setTextColor(Color.BLACK);
                                lightEightImg.setImageResource(R.drawable.ic_lightbulb_off);
                                mPref.edit().putBoolean("light_eight",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
            }
        });

        lobbyfanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lDialogF = new Dialog(getContext());
                lDialogF.setContentView(R.layout.fan_dialog_layout);
                TextView title = lDialogF.findViewById(R.id.fan_dialog_title);
                title.setText("Lobby");

                allFansImg = lDialogF.findViewById(R.id.fan_dialog_img_one);
                allFansTxt = lDialogF.findViewById(R.id.fan_one_btn);

                fanOneImg = lDialogF.findViewById(R.id.fan_dialog_img_two);
                fanOneTxt = lDialogF.findViewById(R.id.fan_two_btn);
                fanTwoImg = lDialogF.findViewById(R.id.fan_dialog_img_three);
                fanTwoTxt = lDialogF.findViewById(R.id.Fan_three_btn);
                fanOneSeek = lDialogF.findViewById(R.id.fan_one_seek_bar);
                fanTwoSeek = lDialogF.findViewById(R.id.fan_two_seek_bar);
                lDialogF.show();
                boolean tempOne = mPref.getBoolean("fan_one",false);
                if (tempOne){
                    fanOneImg.setImageResource(R.drawable.fan_one);
                    fanOneTxt.setText("ON");
                    fanOneTxt.setTextColor(Color.GREEN);
                }
                boolean tempTwo = mPref.getBoolean("fan_two",false);
                if (tempTwo){
                    fanTwoImg.setImageResource(R.drawable.fan_one);
                    fanTwoTxt.setText("ON");
                    fanTwoTxt.setTextColor(Color.GREEN);
                }
                if (tempOne || tempTwo){
                    allFansImg.setImageResource(R.drawable.fan_one);
                    allFansTxt.setText("ON");
                }
                allFansTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sendDataToBt(getString(R.string.all_fan_off))){
                            allFansTxt.setText("OFF");
                            allFansImg.setImageResource(R.drawable.ic_fan_gray);

                            fanOneImg.setImageResource(R.drawable.ic_fan_gray);
                            fanOneTxt.setText("OFF");
                            fanOneTxt.setTextColor(Color.BLACK);
                            fanTwoImg.setImageResource(R.drawable.ic_fan_gray);
                            fanTwoTxt.setText("OFF");
                            fanTwoTxt.setTextColor(Color.BLACK);
                            mPref.edit().putBoolean("fan_one",false).apply();
                            mPref.edit().putBoolean("fan_two",false).apply();
                        }else {
                            notConnected();
                        }
                    }
                });
                fanOneTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isFanOne = mPref.getBoolean("fan_one",false);
                        if (!isFanOne){
                            if (sendDataToBt(getString(R.string.fan1_on_bt_code))){
                                fanOneTxt.setText("ON");
                                fanOneTxt.setTextColor(Color.GREEN);
                                fanOneImg.setImageResource(R.drawable.fan_one);
                                mPref.edit().putBoolean("fan_one",true).apply();

                                allFansTxt.setText("ON");
                                allFansImg.setImageResource(R.drawable.fan_one);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.fan1_off_bt_code))){
                                fanOneImg.setImageResource(R.drawable.ic_fan_gray);
                                fanOneTxt.setText("OFF");
                                fanOneTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("fan_one",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                fanTwoTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isFanTwo = mPref.getBoolean("fan_two",false);
                        if (!isFanTwo){
                            if (sendDataToBt(getString(R.string.fan2_on_bt_code))){
                                fanTwoTxt.setText("ON");
                                fanTwoTxt.setTextColor(Color.GREEN);
                                fanTwoImg.setImageResource(R.drawable.fan_one);
                                mPref.edit().putBoolean("fan_two",true).apply();

                                allFansTxt.setText("ON");
                                allFansImg.setImageResource(R.drawable.fan_one);
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.fan2_off_bt_code))){
                                fanTwoImg.setImageResource(R.drawable.ic_fan_gray);
                                fanTwoTxt.setText("OFF");
                                fanTwoTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("fan_two",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                fanOneSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser){
                            if (progress<20){
                                if (sendDataToBt(getString(R.string.fan_speed_1))){

                                }
                            }else if (progress<40){
                                if (sendDataToBt(getString(R.string.fan_speed_2))){

                                }
                            }else if (progress<60){
                                if (sendDataToBt(getString(R.string.fan_speed_3))){

                                }
                            }else if (progress<80){
                                if (sendDataToBt(getString(R.string.fan_speed_4))){

                                }
                            }else if (progress<100){
                                if (sendDataToBt(getString(R.string.fan_speed_5))){

                                }
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                fanTwoSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser){
                            if (progress<20){
                                if (sendDataToBt(getString(R.string.fan2_speed_1))){

                                }
                            }else if (progress<40){
                                if (sendDataToBt(getString(R.string.fan2_speed_2))){

                                }
                            }else if (progress<60){
                                if (sendDataToBt(getString(R.string.fan2_speed_3))){

                                }
                            }else if (progress<80){
                                if (sendDataToBt(getString(R.string.fan2_speed_4))){

                                }
                            }else if (progress<100){
                                if (sendDataToBt(getString(R.string.fan2_speed_5))){

                                }
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        lobbyDimmerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lDialogD = new Dialog(getContext());
                lDialogD.setContentView(R.layout.dimmer_dialog_layout);
                TextView title = lDialogD.findViewById(R.id.dimmer_dialog_title);
                title.setText("Lobby");

                allDimmerImg = lDialogD.findViewById(R.id.dimmer_dialog_img_one);
                allDimmerTxt = lDialogD.findViewById(R.id.dimmer_one_btn);
                dimmerOneImg = lDialogD.findViewById(R.id.dimmer_dialog_img_two);
                dimmerOneTxt = lDialogD.findViewById(R.id.dimmer_two_btn);
                dimmerOneSeek = lDialogD.findViewById(R.id.dimmer_one_seek_bar);

                dimmerTwoImg = lDialogD.findViewById(R.id.dimmer_dialog_img_three);
                dimmerTwoTxt = lDialogD.findViewById(R.id.dimmer_three_btn);
                dimmerTwoSeek = lDialogD.findViewById(R.id.dimmer_two_seek_bar);

                lDialogD.show();

                boolean tempOne = mPref.getBoolean("dimmer_one",false);
                if (tempOne){
                    dimmerOneImg.setImageResource(R.drawable.light_bulb_on_100);
                    dimmerOneTxt.setText("ON");
                    dimmerOneTxt.setTextColor(Color.GREEN);
                }
                boolean tempTwo = mPref.getBoolean("dimmer_two",false);
                if (tempTwo){
                    dimmerTwoImg.setImageResource(R.drawable.light_bulb_on_100);
                    dimmerTwoTxt.setText("ON");
                    dimmerTwoTxt.setTextColor(Color.GREEN);
                }
                if (tempOne || tempTwo){
                    allDimmerTxt.setText("ON");
                    allDimmerImg.setImageResource(R.drawable.light_bulb_on_100);
                }

                allDimmerTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sendDataToBt(getString(R.string.all_dimmer_off_bt_code))){
                            allDimmerTxt.setText("OFF");
                            allDimmerImg.setImageResource(R.drawable.light_bulb_off_0);

                            dimmerOneTxt.setText("OFF");
                            dimmerOneTxt.setTextColor(Color.BLACK);
                            dimmerOneImg.setImageResource(R.drawable.light_bulb_off_0);
                            dimmerTwoTxt.setText("OFF");
                            dimmerTwoTxt.setTextColor(Color.BLACK);
                            dimmerTwoImg.setImageResource(R.drawable.light_bulb_off_0);
                            dimmerTwoTxt.setText("OFF");

                            mPref.edit().putBoolean("dimmer_one",false).apply();
                            mPref.edit().putBoolean("dimmer_two",false).apply();
                        }else {
                            notConnected();
                        }
                    }
                });

                dimmerOneTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDimmerOne = mPref.getBoolean("dimmer_one",false);
                        if (!isDimmerOne){
                            if (sendDataToBt(getString(R.string.dimmer1_on_bt_code))){
                                dimmerOneTxt.setText("ON");
                                dimmerOneTxt.setTextColor(Color.GREEN);
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_on_100);
                                mPref.edit().putBoolean("dimmer_one",true).apply();

                                allDimmerImg.setImageResource(R.drawable.light_bulb_on_100);
                                allDimmerTxt.setText("ON");
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.dimmer1_off_bt_code))){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_off_0);
                                dimmerOneTxt.setText("OFF");
                                dimmerOneTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("dimmer_one",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                dimmerTwoTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDimmerTwo = mPref.getBoolean("dimmer_two",false);
                        if (!isDimmerTwo){
                            if (sendDataToBt(getString(R.string.dimmer2_on_bt_code))){
                                dimmerTwoTxt.setText("ON");
                                dimmerTwoTxt.setTextColor(Color.GREEN);
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_on_100);
                                mPref.edit().putBoolean("dimmer_two",true).apply();

                                allDimmerImg.setImageResource(R.drawable.light_bulb_on_100);
                                allDimmerTxt.setText("ON");
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.dimmer2_off_bt_code))){
                                dimmerTwoTxt.setText("OFF");
                                dimmerTwoTxt.setTextColor(Color.BLACK);
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_off_0);
                                mPref.edit().putBoolean("dimmer_two",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                dimmerOneSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //dimmerOneImg.setColorFilter(Color.YELLOW);
                        int code = seekBar.getProgress();
                        if (sendDataToBt(String.valueOf(code))){
                            if (code<=10){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_on_0);
                            }else if (code<=20){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_20);
                            }else if (code<=40 ){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_40);
                            }else if (code<=60){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_50);
                            }else if (code<=70){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_70);
                            }else if (code<=80){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_90);
                            }else if (code<=90 ){
                                dimmerOneImg.setImageResource(R.drawable.light_bulb_on_100);
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                dimmerTwoSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int code = seekBar.getProgress();
                        if (sendDataToBt(String.valueOf(code))){
                            if (code<=10){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_on_0);
                            }else if (code<=20){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_20);
                            }else if (code<=40 ){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_40);
                            }else if (code<=60){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_50);
                            }else if (code<=70){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_70);
                            }else if (code<=80 ){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_90);
                            }else if (code<=90 ){
                                dimmerTwoImg.setImageResource(R.drawable.light_bulb_on_100);
                            }
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        lobbyPlugLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lDialogP = new Dialog(getContext());
                lDialogP.setContentView(R.layout.plug_dialog_layout);
                TextView title = lDialogP.findViewById(R.id.plug_dialog_title);
                title.setText("Lobby");

                allPlugsImg = lDialogP.findViewById(R.id.plug_image_view_all);
                allPlugsTxt = lDialogP.findViewById(R.id.plug_btn_all);

                plugOneImg = lDialogP.findViewById(R.id.plug_image_view_one);
                plugOneTxt = lDialogP.findViewById(R.id.plug_btn_one);
                plugTwoImg = lDialogP.findViewById(R.id.plug_image_view_two);
                plugTwoTxt = lDialogP.findViewById(R.id.plug_btn_two);
                plugThreeImg = lDialogP.findViewById(R.id.plug_image_view_three);
                plugThreeTxt = lDialogP.findViewById(R.id.plug_btn_three);
                plugFourImg = lDialogP.findViewById(R.id.plug_image_view_four);
                plugFourTxt = lDialogP.findViewById(R.id.plug_btn_four);

                lDialogP.show();

                boolean tempOne = mPref.getBoolean("plug_one",false);
                if (tempOne){
                    plugOneTxt.setText("ON");
                    plugOneTxt.setTextColor(Color.GREEN);
                    plugOneImg.setImageResource(R.drawable.ic_plug_white);
                }
                boolean tempTwo = mPref.getBoolean("plug_two",false);
                if (tempTwo){
                    plugTwoTxt.setText("ON");
                    plugTwoTxt.setTextColor(Color.GREEN);
                    plugTwoImg.setImageResource(R.drawable.ic_plug_white);
                }
                boolean tempThree = mPref.getBoolean("plug_three",false);
                if (tempThree){
                    plugThreeTxt.setText("ON");
                    plugThreeTxt.setTextColor(Color.GREEN);
                    plugThreeImg.setImageResource(R.drawable.ic_plug_white);
                }
                boolean tempFour = mPref.getBoolean("plug_four",false);
                if (tempFour){
                    plugFourTxt.setText("ON");
                    plugFourTxt.setTextColor(Color.GREEN);
                    plugFourImg.setImageResource(R.drawable.ic_plug_white);
                }
                if (tempFour || tempThree || tempTwo || tempOne){
                    allPlugsTxt.setText("ON");
                    allPlugsImg.setImageResource(R.drawable.ic_plug_white);
                }

                allPlugsTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sendDataToBt(getString(R.string.all_plugs_bt_off_code))){
                            allPlugsTxt.setText("OFF");
                            allPlugsImg.setImageResource(R.drawable.plug_gray);

                            plugOneImg.setImageResource(R.drawable.plug_gray);
                            plugOneTxt.setText("OFF");
                            plugOneTxt.setTextColor(Color.BLACK);
                            plugTwoImg.setImageResource(R.drawable.plug_gray);
                            plugTwoTxt.setText("OFF");
                            plugTwoTxt.setTextColor(Color.BLACK);
                            plugThreeImg.setImageResource(R.drawable.plug_gray);
                            plugThreeTxt.setText("OFF");
                            plugThreeTxt.setTextColor(Color.BLACK);
                            plugFourImg.setImageResource(R.drawable.plug_gray);
                            plugFourTxt.setText("OFF");
                            plugFourTxt.setTextColor(Color.BLACK);

                            mPref.edit().putBoolean("plug_one",false).apply();
                            mPref.edit().putBoolean("plug_two",false).apply();
                            mPref.edit().putBoolean("plug_three",false).apply();
                            mPref.edit().putBoolean("plug_four",false).apply();
                        }else {
                            notConnected();
                        }
                    }
                });
                plugOneTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isPlugOne = mPref.getBoolean("plug_one",false);
                        if (!isPlugOne){
                            if (sendDataToBt(getString(R.string.plug1_on_bt_code))){
                                plugOneTxt.setText("ON");
                                plugOneTxt.setTextColor(Color.GREEN);
                                plugOneImg.setImageResource(R.drawable.ic_plug_white);
                                mPref.edit().putBoolean("plug_one",true).apply();

                                allPlugsImg.setImageResource(R.drawable.ic_plug_white);
                                allPlugsTxt.setText("ON");
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.plug1_OFF_bt_code))){
                                plugOneImg.setImageResource(R.drawable.plug_gray);
                                plugOneTxt.setText("OFF");
                                plugOneTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("plug_one",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                plugTwoTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isPlugTwo = mPref.getBoolean("plug_two",false);
                        if (!isPlugTwo){
                            if (sendDataToBt(getString(R.string.plug2_on_bt_code))){
                                plugTwoTxt.setText("ON");
                                plugTwoTxt.setTextColor(Color.GREEN);
                                plugTwoImg.setImageResource(R.drawable.ic_plug_white);
                                mPref.edit().putBoolean("plug_two",true).apply();

                                allPlugsImg.setImageResource(R.drawable.ic_plug_white);
                                allPlugsTxt.setText("ON");
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.plug2_off_bt_code))){
                                plugTwoImg.setImageResource(R.drawable.plug_gray);
                                plugTwoTxt.setText("OFF");
                                plugTwoTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("plug_two",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                plugThreeTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isPlugThree = mPref.getBoolean("plug_three",false);
                        if (!isPlugThree){
                            if (sendDataToBt(getString(R.string.plug3_on_bt_code))){
                                plugThreeTxt.setText("ON");
                                plugThreeTxt.setTextColor(Color.GREEN);
                                plugThreeImg.setImageResource(R.drawable.ic_plug_white);
                                mPref.edit().putBoolean("plug_three",true).apply();

                                allPlugsImg.setImageResource(R.drawable.ic_plug_white);
                                allPlugsTxt.setText("ON");
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.plug3_off_bt_code))){
                                plugThreeImg.setImageResource(R.drawable.plug_gray);
                                plugThreeTxt.setText("OFF");
                                plugThreeTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("plug_three",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });
                plugFourTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isPlugFour = mPref.getBoolean("plug_four",false);
                        if (!isPlugFour){
                            if (sendDataToBt(getString(R.string.plug4_on_bt_code))){
                                plugFourTxt.setText("ON");
                                plugFourTxt.setTextColor(Color.GREEN);
                                plugFourImg.setImageResource(R.drawable.ic_plug_white);
                                mPref.edit().putBoolean("plug_four",true).apply();

                                allPlugsImg.setImageResource(R.drawable.ic_plug_white);
                                allPlugsTxt.setText("ON");
                                on();
                            }else {
                                notConnected();
                            }
                        }else {
                            if (sendDataToBt(getString(R.string.plug4_off_bt_code))){
                                plugFourImg.setImageResource(R.drawable.plug_gray);
                                plugFourTxt.setText("OFF");
                                plugFourTxt.setTextColor(Color.BLACK);
                                mPref.edit().putBoolean("plug_one",false).apply();
                                off();
                            }else {
                                notConnected();
                            }
                        }
                    }
                });

            }
        });
        lobbyTvControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog tvDialog = new Dialog(getContext());
                tvDialog.setContentView(R.layout.fragment_tv_dialog);
                tvDialog.show();
            }
        });
        lobbyRoomPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog picIma = new Dialog(getContext());
                picIma.setContentView(R.layout.pick_image_layout);
                ImageView i = picIma.findViewById(R.id.gallery_image_from);
                ImageView cameraImg = picIma.findViewById(R.id.camera_image_from);

                picIma.show();
                i.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickImage();
                        picIma.dismiss();
                    }
                });
                cameraImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //checkCameraPermission();
                        captureImage();
                        picIma.dismiss();
                    }
                });
            }
        });
        renamelobbyRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog renameKitchen = new Dialog(getContext());
                renameKitchen.setContentView(R.layout.rename_bed_room);
                final EditText edtValue = renameKitchen.findViewById(R.id.bed_room_edx_rename);
                Button updateName = renameKitchen.findViewById(R.id.btn_update_bed_room_name);
                renameKitchen.show();
                updateName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        renamelobbyRoom.setText(edtValue.getText().toString());
                        mPref.edit().putString("lobbyRoom_Name",edtValue.getText().toString()).apply();
                        renameKitchen.dismiss();
                    }
                });
            }
        });


    }
    private void captureImage(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,2);
    }
    private void pickImage() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        //i.setAction(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(i, "Select picture"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        try{
            if (requestCode == 1 && null != data && data.getData() != null){
                Uri selectedImage = data.getData();
                //pick_room_image.setImageURI(selectedImage);
                String[] filePathcolumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathcolumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathcolumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp = BitmapFactory.decodeFile(picturePath);
                //pick_room_image.setBackgroundResource(0);
                lobbyPic.setImageBitmap(bmp);
                editor = mPref.edit();
                editor.putString("lobbyRoom_Picture1",picturePath).apply();
                editor.putString("lobbyRoom_Picture2",null).apply();
                //pick_room_image.setTag(bmp);
            }else if (requestCode == 2 && resultCode == RESULT_OK){
                Bitmap bmp;
                bmp = (Bitmap) data.getExtras().get("data");
                lobbyPic.setImageBitmap(bmp);

                String bitmapString = Constants.BitmapToString(bmp);
                editor = mPref.edit();
                editor.putString("lobbyRoom_Picture2",bitmapString).apply();
                editor.putString("lobbyRoom_Picture1",null).apply();

            }else {
                Toast.makeText(getContext(),"Operation cancelled by user",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Please try again...",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        /*byte[] encodeByte = Base64.decode(s, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);*/
        String s = mPref.getString("lobbyRoom_Picture1",null);
        String bytes = mPref.getString("lobbyRoom_Picture2",null);
        if (s != null){
            Bitmap bitmap = BitmapFactory.decodeFile(s);
            lobbyPic.setImageBitmap(bitmap);
        }else if (null != bytes) {
            Bitmap bmp = Constants.StringToBitmap(bytes);
            lobbyPic.setImageBitmap(bmp);
        }else {
            lobbyPic.setImageResource(R.drawable.lobby_room_back);
        }

        String re = mPref.getString("lobbyRoom_Name","Lobby");
        renamelobbyRoom.setText(re);
    }
    public void notConnected(){
        Toast.makeText(getContext(),"Bluetooth not connected",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SimpleMenuActivity)getActivity()).mTabLayout.setVisibility(View.VISIBLE);
    }

    public void on(){
        Toast.makeText(getContext(),"On",Toast.LENGTH_SHORT).show();
    }
    public void off(){
        Toast.makeText(getContext(),"off",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
