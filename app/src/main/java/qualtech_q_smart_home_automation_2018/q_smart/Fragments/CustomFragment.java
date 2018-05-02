package qualtech_q_smart_home_automation_2018.q_smart.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Light.LightFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Security.SecurityFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Services.ServiceFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Utilities.UtilitiesFragment;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.entertainment.EntertainmentFragment;
import qualtech_q_smart_home_automation_2018.q_smart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomFragment extends Fragment {

    TextView mLightTxt, mUtilities, mEntertainment, mServices, mSecurity;
    PopupWindow mPopupWindow;
    RelativeLayout mRelativeLayout;
    ImageView lightPopUp;

    private boolean lightbulbClicked = false;
    boolean dismissPopUp = true;
    Dialog mDialog;

    //FragmentManager fm = getActivity().getSupportFragmentManager();
    LightFragment lightFm;
    UtilitiesFragment uFragment;
    EntertainmentFragment eFragment;
    ServiceFragment sFragment;
    SecurityFragment securityFragment;

    public CustomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLightTxt = view.findViewById(R.id.lights);
        mUtilities = view.findViewById(R.id.utilities);
        mEntertainment = view.findViewById(R.id.entertainment);
        mServices = view.findViewById(R.id.services);
        mSecurity = view.findViewById(R.id.security);
        mRelativeLayout = view.findViewById(R.id.popup_details);

        // lightPopUp = findViewById(R.id.light_popup);
        //Context context = getApplicationContext();
        simpleMenuClickListener();
    }

    public void tvDetailsFragment(){
        mDialog = new Dialog(getContext());

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
                lightFm = new LightFragment(getContext());
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                getChildFragmentManager().popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.popup_details,lightFm,"Light");
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
                uFragment = new UtilitiesFragment(getContext());
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                getChildFragmentManager().popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,uFragment,"Utilities");
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
                eFragment = new EntertainmentFragment(getContext());
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                getChildFragmentManager().popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,eFragment,"Entertainment");
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
                sFragment = new ServiceFragment(getContext());
                getChildFragmentManager().popBackStack();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,sFragment,"Services");
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
                securityFragment = new SecurityFragment(getContext());
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                getChildFragmentManager().popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.popup_details,securityFragment,"Security");
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

    public boolean onBackPressed(){
        //Toast.makeText(getContext(),"from simple menu",Toast.LENGTH_SHORT).show();
        Fragment tag1 = getChildFragmentManager().findFragmentByTag("Light");
        Fragment tag2 = getChildFragmentManager().findFragmentByTag("Utilities");
        Fragment tag3 = getChildFragmentManager().findFragmentByTag("Entertainment");
        Fragment tag4 = getChildFragmentManager().findFragmentByTag("Services");
        Fragment tag5 = getChildFragmentManager().findFragmentByTag("Security");
        if (tag1 != null){
            getChildFragmentManager().beginTransaction().remove(lightFm).commit();
            return true;
        }else if (tag2 != null){
            getChildFragmentManager().beginTransaction().remove(uFragment).commit();
            return true;
        }else if (tag3 != null){
            getChildFragmentManager().beginTransaction().remove(eFragment).commit();
            return true;
        }else if (tag4 != null){
            getChildFragmentManager().beginTransaction().remove(sFragment).commit();
            return true;
        }else if (tag5 != null){
            getChildFragmentManager().beginTransaction().remove(securityFragment).commit();
            return true;
        }
        return false;
    }

}
