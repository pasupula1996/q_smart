package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Utilities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LDbHelper;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LightPojo;


public class UtilitiesFragment extends Fragment {


    RecyclerView mRecyclerView;
    FloatingActionButton addUtilitiesTitle,allUtilitiesOff;
    ArrayList<LightPojo> mData;
    RecyclerView.LayoutManager mLayoutManager;
    UtilitiesRecyclerView li;

    EditText edxUtilitiesName;
    Button btnUtilitiesName;

    int add_spinner_name_icon;

    LDbHelper mHelper;
    Context mContext;


    @SuppressLint("ValidFragment")
    public UtilitiesFragment(Context context){
        mHelper = new LDbHelper(context);
        mContext = context;
    }

    public UtilitiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.utilities_frag_recyclerView);
        addUtilitiesTitle = view.findViewById(R.id.fab_add_utilities_title);
        allUtilitiesOff = view.findViewById(R.id.fab_off_all_utilities);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //addLightTitles();
        mData = mHelper.getUtilitiesData();
        li = new UtilitiesRecyclerView(mData);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(li);
        li.notifyDataSetChanged();

        clickListener();

    }

    @Override
    public void onDetach() {
       // startActivity(new Intent(getContext(),TabActivity.class));
        super.onDetach();

    }

    public void clickListener(){
        mRecyclerView.addOnItemTouchListener(new UtilitiesFragment.RecyclerTouchListener(getActivity(),mRecyclerView,new UtilitiesFragment.ClickListener(){

            @Override
            public void onClick(View view, int position) {
                Log.d("BT","clicked " + mData.get(position).getName()+mData.get(position).getThumbnail());
                //toast("clicked" + position );

                if (!mData.get(position).isClicked()){

                    //lightPopUp.setImageResource(sThumbnail);
                    ImageView lightPopUp = view.findViewById(R.id.utilities_thumbnail);
                    int thumb = mData.get(position).getThumbnail();
                    if (thumb == R.drawable.electricity_off){
                        thumb = R.drawable.electricity_on;
                    }else if (thumb == R.drawable.cable_off){
                        thumb = R.drawable.cable_on;
                    }else if (thumb == R.drawable.gas_black){
                        thumb = R.drawable.gas_white;
                    }else if (thumb == R.drawable.wireless_black){
                        thumb = R.drawable.wireless_white;
                    }else if (thumb == R.drawable.heater_off){
                        thumb = R.drawable.heater_on;
                    }
                    lightPopUp.setImageResource(thumb);
                    Log.d("BT","thumb "+thumb );
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.white));
                    //toast(mData.get(position).getName() + " on");
                    Toast toast = Toast.makeText(getContext(),mData.get(position).getName()+" On",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    Log.d("BT","clicked" + mData.get(position).isClicked()+ mData.get(position).getThumbnail());
                    mData.get(position).setClicked(true);
                    UtilModel pojo = new UtilModel(mData.get(position).getId(),thumb,true);
                    mHelper.updateUtilitiesState(pojo);

                }else {

                    int thumb2 = mData.get(position).getThumbnail();
                    if (thumb2 == R.drawable.electricity_on){
                        thumb2 = R.drawable.electricity_off;
                    }else if (thumb2 == R.drawable.cable_on){
                        thumb2 = R.drawable.cable_off;
                    }else if (thumb2 == R.drawable.gas_white){
                        thumb2 = R.drawable.gas_black;
                    }else if (thumb2 == R.drawable.wireless_white){
                        thumb2 = R.drawable.wireless_black;
                    }else if (thumb2 == R.drawable.heater_on){
                        thumb2 = R.drawable.heater_off;
                    }

                    //lightPopUp.setImageResource(sThumbnail);
                    //toast(mData.get(position).getName() + " off");
                    ImageView lightPopUp = view.findViewById(R.id.utilities_thumbnail);
                    lightPopUp.setImageResource(thumb2);
                    Log.d("BT","thumb "+thumb2 );
                    Toast toast = Toast.makeText(getContext(),mData.get(position).getName()+" Off",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.colorAccent));
                    Log.d("BT","clicked" + mData.get(position).isClicked()+mData.get(position).getThumbnail());
                    mData.get(position).setClicked(false);
                    UtilModel pojo = new UtilModel(mData.get(position).getId(),thumb2,false);
                    mHelper.updateUtilitiesState(pojo);

                }



            }

            @Override
            public void onLongClick(View view, final int position) {
                //toast("long pressed at " + position);
                final Dialog eDialog = new Dialog(mContext);
                eDialog.setContentView(R.layout.edit_utilites_name);
                //EditText editLightName = eDialog.findViewById(R.id.edit_light_name_edx);
                //final String editedLightName = editLightName.getText().toString();
                Button deleteLightName = eDialog.findViewById(R.id.btn_delete_utilities_name);
                Button updateLightName = eDialog.findViewById(R.id.btn_update_utilities_name);
                eDialog.show();
                // eDialog.setTitle("Edit light name");



                updateLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lightTitle.set(position,editLightName.getText().toString());
                        //Log.d("BT",editedLightName);
                        EditText editLightName = eDialog.findViewById(R.id.edit_utilities_name_edx);

                        UtilModel pojo = new UtilModel(mData.get(position).getId(),editLightName.getText().toString());
                        mHelper.editUtilitiesName(pojo);
                        mData.set(position,new LightPojo(editLightName.getText().toString(),add_spinner_name_icon,false));
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });

                deleteLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UtilModel pojo = new UtilModel(mData.get(position).getId());
                        mHelper.deleteUtility(pojo);
                        mData.remove(position);
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });


            }
        }));

        addUtilitiesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast("clicked fab button");

                //LightTitleDialog titleDialog = new LightTitleDialog();
                //titleDialog.show(getActivity().getFragmentManager(),"title");
                final Dialog mDialog = new Dialog(mContext);
                mDialog.setTitle("Add name");
                mDialog.setContentView(R.layout.add_utilities_name);
                edxUtilitiesName = mDialog.findViewById(R.id.edt_utilities_title);
                btnUtilitiesName = mDialog.findViewById(R.id.btn_add_utilities_title);
                Spinner listSpinner = mDialog.findViewById(R.id.spinner_utilities_type);
                String[] list_names = {"Electricity","Cable","Gas","Wireless","Heater"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_spinner_item, list_names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listSpinner.setAdapter(adapter);
                mDialog.show();

                btnUtilitiesName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toast("Add light");
                        if (edxUtilitiesName.getText().length() != 0){
                            //LightPojo pojo = new LightPojo();
                            mHelper.insertUtilitiesTitle(edxUtilitiesName.getText().toString(),add_spinner_name_icon,false);
                            mData.add(new LightPojo(edxUtilitiesName.getText().toString(),add_spinner_name_icon,false));
                            li.notifyDataSetChanged();
                            mDialog.dismiss();
                        }else {
                            toast("Please enter name");
                        }
                    }
                });

                listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //toast(parent.getSelectedItem().toString());
                        getSpinner(parent.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        });
        allUtilitiesOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("size" + mData.size());

            }
        });


    }
    public void getSpinner(String TYPE){
        //String TYPE = parent.getSelectedItem().toString();
        if (TYPE.equals("Electricity")){
            //toast("1");
            add_spinner_name_icon = R.drawable.electricity_off;

        }else if (TYPE.equals("Cable")){
            //toast("2");
            add_spinner_name_icon = R.drawable.cable_off;

        }else if (TYPE.equals("Gas")){
            //toast("3");
            add_spinner_name_icon = R.drawable.gas_black;

        }else if (TYPE.equals("Wireless")){
            //toast("4");
            add_spinner_name_icon = R.drawable.wireless_black;

        }else if (TYPE.equals("Heater")){
            //toast("5");
            add_spinner_name_icon = R.drawable.heater_off;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /*private void addLightTitles() {
        utilitiesTitleList = new ArrayList<>();

        utilitiesTitleList.add(new UtilitiesTitle("bedroom",R.drawable.tv,false));
        utilitiesTitleList.add(new UtilitiesTitle("kitchen",R.drawable.ic_speaker_black,false));
        utilitiesTitleList.add(new UtilitiesTitle("hall",R.drawable.ic_devices_other_black,false));
        utilitiesTitleList.add(new UtilitiesTitle("drawing room",R.drawable.tv_one,false));
    }*/
    public void toast(String mess){
        Toast.makeText(getActivity(),mess,Toast.LENGTH_SHORT).show();
    }
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private UtilitiesFragment.ClickListener uClickListener;
        private GestureDetector uGestureDetector;

        private RecyclerTouchListener(Context mContext,final RecyclerView mRecyclerView, final UtilitiesFragment.ClickListener clickListener) {
            uClickListener = clickListener;
            uGestureDetector = new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child != null && uClickListener != null){
                        uClickListener.onLongClick(child,mRecyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child != null && uClickListener != null && uGestureDetector.onTouchEvent(e)){
                uClickListener.onClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
