package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Services;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
import qualtech_q_smart_home_automation_2018.q_smart.model.ServiceModel;


public class ServiceFragment extends Fragment {

    RecyclerView eRecyclerView;
    FloatingActionButton addServiceTitle,allServiceOff;
    ArrayList<ServiceModel> sData;
    RecyclerView.LayoutManager eLayoutManager;

    EditText edxServiceName;
    Button btnServiceName;

    ServiceRecyclerView li;
    int add_spinner_name_icon;

    Context mContext;
    LDbHelper mHelper;


    public ServiceFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public ServiceFragment(Context context){
        mContext = context;
        mHelper = new LDbHelper(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eRecyclerView = view.findViewById(R.id.services_frag_recyclerView);
        addServiceTitle = view.findViewById(R.id.fab_add_services_title);
        allServiceOff = view.findViewById(R.id.fab_off_all_services);

        eLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        //addETitles();
        sData = mHelper.getServicesData();
        li = new ServiceRecyclerView(sData);
        eRecyclerView.setLayoutManager(eLayoutManager);
        eRecyclerView.setAdapter(li);
        li.notifyDataSetChanged();

        clickListener();

    }

    /*private void addETitles() {
        mServiceTitles = new ArrayList<>();
        mServiceTitles.add(new ServiceTitle("T.v",R.drawable.tv_one,false));
        mServiceTitles.add(new ServiceTitle("Speakers",R.drawable.ic_speaker_black,false));
        mServiceTitles.add(new ServiceTitle("Home theater",R.drawable.ic_devices_other_black,false));
        mServiceTitles.add(new ServiceTitle("XBOX",R.drawable.tv,false));

    }*/
    public void clickListener(){

        //LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter("Spinner name"));

        eRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),eRecyclerView,new ClickListener(){

            @Override
            public void onClick(View view, int position) {
                Log.d("BT","clicked" + sData.get(position).getName());
                //ImageView lightPopUp = view.findViewById(R.id.row_services_thumbnail);

                if (!sData.get(position).isClicked()){

                    //lightPopUp.setImageResource(sThumbnail);
                    ImageView lightPopUp = view.findViewById(R.id.row_services_thumbnail);

                    int sThumbnail = sData.get(position).getThumbnail();
                    //toast("clicked" + position );
                    if (sThumbnail == R.drawable.cooler_black){
                        sThumbnail = R.drawable.cooler_white;
                    }else if (sThumbnail == R.drawable.fridge){
                        sThumbnail = R.drawable.fridge_white;
                    }else if (sThumbnail == R.drawable.oven_black){
                        sThumbnail = R.drawable.oven_white;
                    }else if (sThumbnail == R.drawable.washing_machine_black){
                        sThumbnail = R.drawable.washing_machine_white;
                    }else if (sThumbnail == R.drawable.kettle_black){
                        sThumbnail = R.drawable.kettle_white;
                    }

                    lightPopUp.setImageResource(sThumbnail);
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.white));
                    //toast(sData.get(position).getName() + " on");
                    Toast toast = Toast.makeText(getContext(),sData.get(position).getName()+" On",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    Log.d("BT","clicked" + sData.get(position).isClicked());
                    sData.get(position).setClicked(true);
                    ServiceModel pojo = new ServiceModel(sData.get(position).getId(),sThumbnail,true);
                    mHelper.updateServicesState(pojo);

                }else {

                    int sThumbnail = sData.get(position).getThumbnail();
                    //toast("clicked" + position );

                    if (sThumbnail == R.drawable.cooler_white){
                        sThumbnail = R.drawable.cooler_black;
                    }else if (sThumbnail == R.drawable.fridge_white){
                        sThumbnail = R.drawable.fridge;
                    }else if (sThumbnail == R.drawable.oven_white){
                        sThumbnail = R.drawable.oven_black;
                    }else if (sThumbnail == R.drawable.washing_machine_white){
                        sThumbnail = R.drawable.washing_machine_black;
                    }else if (sThumbnail == R.drawable.kettle_white){
                        sThumbnail = R.drawable.kettle_black;
                    }

                    //lightPopUp.setImageResource(sThumbnail);
                    //toast(sData.get(position).getName() + " off");
                    Toast toast = Toast.makeText(getContext(),sData.get(position).getName()+" Off",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    ImageView lightPopUp = view.findViewById(R.id.row_services_thumbnail);
                    lightPopUp.setImageResource(sThumbnail);
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.colorAccent));
                    Log.d("BT","clicked" + sData.get(position).isClicked());
                    sData.get(position).setClicked(false);
                    ServiceModel pojo = new ServiceModel(sData.get(position).getId(),sThumbnail,false);
                    mHelper.updateServicesState(pojo);

                }



            }

            @Override
            public void onLongClick(View view, final int position) {
                //toast("long pressed at " + position);
                final Dialog eDialog = new Dialog(mContext);
                eDialog.setContentView(R.layout.edit_services_name);
                //EditText editLightName = eDialog.findViewById(R.id.edit_light_name_edx);
                //final String editedLightName = editLightName.getText().toString();
                Button deleteLightName = eDialog.findViewById(R.id.btn_delete_services_name);
                Button updateLightName = eDialog.findViewById(R.id.btn_update_services_name);
                eDialog.show();
                // eDialog.setTitle("Edit light name");



                updateLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lightTitle.set(position,editLightName.getText().toString());
                        //Log.d("BT",editedLightName);
                        EditText editLightName = eDialog.findViewById(R.id.edit_services_name_edx);

                        ServiceModel pojo = new ServiceModel(sData.get(position).getId(),editLightName.getText().toString());
                        mHelper.editServicesName(pojo);
                        sData.set(position,new ServiceModel(editLightName.getText().toString(),sData.get(position).getThumbnail(),sData.get(position).isClicked()));
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });

                deleteLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServiceModel pojo = new ServiceModel(sData.get(position).getId());
                        mHelper.deleteService(pojo);
                        sData.remove(position);
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });


            }
        }));
        addServiceTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog eDialog = new Dialog(mContext);
                //eDialog.setTitle("Add Entertainment name");
                eDialog.setContentView(R.layout.add_services_name);
                edxServiceName = eDialog.findViewById(R.id.edt_service_title);
                btnServiceName = eDialog.findViewById(R.id.btn_add_services_title);
                Spinner listSpinner = eDialog.findViewById(R.id.spinner_service_type);
                String[] list_names = {"Cooler","Fridge","Oven","Washing machine","Kettle"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item, list_names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listSpinner.setAdapter(adapter);
                eDialog.show();


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


                btnServiceName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toast("Add light");
                        if (edxServiceName.getText().length() != 0){
                            mHelper.insertServicesTitle(edxServiceName.getText().toString(),add_spinner_name_icon,false);
                            sData.add(new ServiceModel(edxServiceName.getText().toString(),add_spinner_name_icon,false));
                            li.notifyDataSetChanged();
                            eDialog.dismiss();
                        }else {
                            toast("Please enter a name");
                        }
                    }
                });
            }

        });
        allServiceOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("all off");
            }
        });
    }
    public void getSpinner(String TYPE){
        //String TYPE = parent.getSelectedItem().toString();
        if (TYPE.equals("Cooler")){
            //toast("1");
            add_spinner_name_icon = R.drawable.cooler_black;

        }else if (TYPE.equals("Fridge")){
            //toast("2");
            add_spinner_name_icon = R.drawable.fridge;

        }else if (TYPE.equals("Oven")){
            //toast("3");
            add_spinner_name_icon = R.drawable.oven_black;

        }else if (TYPE.equals("Washing machine")){
            //toast("4");
            add_spinner_name_icon = R.drawable.washing_machine_black;

        }else if (TYPE.equals("Kettle")){
            //toast("5");
            add_spinner_name_icon = R.drawable.kettle_black;

        }
    }
    public void toast(String mess){
        Toast.makeText(getContext(),mess,Toast.LENGTH_SHORT).show();
    }


    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener mClickListener;
        private GestureDetector mGestureDetector;

        public RecyclerTouchListener(Context mContext, final RecyclerView mRecyclerView, final ClickListener clickListener) {
            mClickListener = clickListener;
            mGestureDetector = new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
                    if (child != null && mClickListener != null){
                        mClickListener.onLongClick(child,mRecyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)){
                mClickListener.onClick(child,rv.getChildAdapterPosition(child));
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

