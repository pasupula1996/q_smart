package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.entertainment;

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
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LightPojo;


public class EntertainmentFragment extends Fragment{

    RecyclerView eRecyclerView;
    FloatingActionButton addEntertainmentTitle,allEntertainmentOff;
    ArrayList<LightPojo> eData;
    RecyclerView.LayoutManager eLayoutManager;

    LDbHelper mHelper;

    EditText edxEntertainmentName;
    Button btnEntertainmentName;

    EntertainmentRecyclerView li;
    int add_spinner_name_icon;
    String iconName;

    Context mContext;


    @SuppressLint("ValidFragment")
    public EntertainmentFragment(Context context) {
        // Required empty public constructor
        mHelper = new LDbHelper(context);
        mContext = context;
    }

    public EntertainmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entertainment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eRecyclerView = view.findViewById(R.id.entertainment_frag_recyclerView);
        addEntertainmentTitle = view.findViewById(R.id.fab_add_entertainment_title);
        allEntertainmentOff = view.findViewById(R.id.fab_off_all_entertainment);

        eLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        //addETitles();
        eData = mHelper.getEntertainmentData();
        li = new EntertainmentRecyclerView(getContext(),eData);
        eRecyclerView.setLayoutManager(eLayoutManager);
        eRecyclerView.setAdapter(li);
        li.notifyDataSetChanged();

        clickListener();


    }

    /*private void addETitles() {
        mEntertainmentTitles = new ArrayList<>();
        mEntertainmentTitles.add(new EntertainmentTitle("T.v",R.drawable.tv_one,false));
        mEntertainmentTitles.add(new EntertainmentTitle("Speakers",R.drawable.ic_speaker_black,false));
        mEntertainmentTitles.add(new EntertainmentTitle("Home theater",R.drawable.ic_devices_other_black,false));
        mEntertainmentTitles.add(new EntertainmentTitle("XBOX",R.drawable.tv,false));

    }*/
    public void clickListener(){

        //LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, new IntentFilter("Spinner name"));

        eRecyclerView.addOnItemTouchListener(new EntertainmentFragment.RecyclerTouchListener(getActivity(),eRecyclerView,new ClickListener(){

            @Override
            public void onClick(View view, int position) {
                Log.d("BT","clicked" + eData.get(position).getName());
                //ImageView lightPopUp = view.findViewById(R.id.row_entertainment_thumbnail);
                int selectedThumbnail = eData.get(position).getThumbnail();
                if (selectedThumbnail == R.drawable.tv_one){
                    selectedThumbnail = R.drawable.tv_one_white;
                }else if (selectedThumbnail == R.drawable.tv_one_white){
                    selectedThumbnail = R.drawable.tv_one;
                }else if (selectedThumbnail == R.drawable.ic_speaker_black){
                    selectedThumbnail = R.drawable.ic_speaker_white;
                }else if (selectedThumbnail == R.drawable.ic_speaker_white){
                    selectedThumbnail = R.drawable.ic_speaker_black;
                }else if (selectedThumbnail == R.drawable.ic_devices_other_black){
                    selectedThumbnail = R.drawable.ic_devices_other_white;
                }else if (selectedThumbnail == R.drawable.ic_devices_other_white){
                    selectedThumbnail = R.drawable.ic_devices_other_black;
                }else if (selectedThumbnail == R.drawable.laptop_mac_black){
                    selectedThumbnail = R.drawable.ic_laptop_mac_white;
                }else if (selectedThumbnail == R.drawable.ic_laptop_mac_white){
                    selectedThumbnail = R.drawable.laptop_mac_black;
                }else {
                    selectedThumbnail = eData.get(position).getThumbnail();
                }

                if (!eData.get(position).isClicked()){

                    //lightPopUp.setImageResource(selectedThumbnail);
                    ///setIcon(eData.get(position).getThumbnail());

                    ImageView lightPopUp = view.findViewById(R.id.row_entertainment_thumbnail);
                    lightPopUp.setImageResource(selectedThumbnail);
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.white));
                    //toast(eData.get(position).getName() + " on");
                    Toast toast = Toast.makeText(getContext(),eData.get(position).getName()+" On",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    Log.d("click","clicked :" + eData.get(position).isClicked());

                    LightPojo pojo = new LightPojo(eData.get(position).getId(),selectedThumbnail,true);
                    eData.get(position).setThumbnail(selectedThumbnail);

                    mHelper.updateEntertainmentState(pojo);
                    eData.get(position).setClicked(true);


                }else {

                    //lightPopUp.setImageResource(selectedThumbnail);));
                    ImageView lightPopUp = view.findViewById(R.id.row_entertainment_thumbnail);
                    //toast(eData.get(position).getName() + " off");
                    Toast toast = Toast.makeText(getContext(),eData.get(position).getName()+" Off",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    lightPopUp.setImageResource(selectedThumbnail);
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.colorAccent));
                    Log.d("click","clicked :" + eData.get(position).isClicked());

                    eData.get(position).setThumbnail(selectedThumbnail);

                    LightPojo pojo = new LightPojo(eData.get(position).getId(),selectedThumbnail,false);
                    mHelper.updateEntertainmentState(pojo);
                    eData.get(position).setClicked(false);

                }



            }

            @Override
            public void onLongClick(View view, final int position) {
                //toast("long pressed at " + position);
                final Dialog eDialog = new Dialog(mContext);
                eDialog.setContentView(R.layout.edit_entertainment_name);
                //EditText editLightName = eDialog.findViewById(R.id.edit_light_name_edx);
                //final String editedLightName = editLightName.getText().toString();
                Button deleteLightName = eDialog.findViewById(R.id.btn_delete_entertainment_name);
                Button updateLightName = eDialog.findViewById(R.id.btn_update_entertainment_name);
                eDialog.show();
                // eDialog.setTitle("Edit light name");



                updateLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lightTitle.set(position,editLightName.getText().toString());
                        //Log.d("BT",editedLightName);
                        EditText editLightName = eDialog.findViewById(R.id.edit_entertainment_name_edx);
                        LightPojo pojo = new LightPojo(eData.get(position).getId(),editLightName.getText().toString());
                        mHelper.editEntertainmentName(pojo);
                        eData.set(position,new LightPojo(editLightName.getText().toString(),eData.get(position).getThumbnail(),false));
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });

                deleteLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LightPojo pojo = new LightPojo(eData.get(position).getId());
                        mHelper.deleteEntertainment(pojo);
                        eData.remove(position);
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });


            }
        }));
        addEntertainmentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog eDialog = new Dialog(mContext);
                //eDialog.setTitle("Add Entertainment name");
                eDialog.setContentView(R.layout.add_entertainment_name);
                edxEntertainmentName = eDialog.findViewById(R.id.edt_entertainment_title);
                btnEntertainmentName = eDialog.findViewById(R.id.btn_add_entertainment_title);
                Spinner listSpinner = eDialog.findViewById(R.id.spinner_entertainment_type);
                String[] list_names = {"T.V","Speakers","Home Theater","Computer"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
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


                btnEntertainmentName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toast("Add light");
                        if (edxEntertainmentName.getText().length() != 0){
                            eData.add(new LightPojo(edxEntertainmentName.getText().toString(),add_spinner_name_icon,false));
                            //LightPojo pojo = new LightPojo(edxEntertainmentName.getText().toString(),add_spinner_name_icon,false);
                            mHelper.insertEntertainmentTitle(edxEntertainmentName.getText().toString(),add_spinner_name_icon,iconName,false);
                            li.notifyDataSetChanged();
                            eDialog.dismiss();
                        }else {
                            toast("Please enter a name");
                        }
                    }
                });
            }

        });
        allEntertainmentOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("all off");
            }
        });
    }

    public void getSpinner(String TYPE){
        //String TYPE = parent.getSelectedItem().toString();

        if (TYPE.equals("T.V")){
            //toast("2");
            add_spinner_name_icon = R.drawable.tv_one;
            iconName = "T.V";

        }else if (TYPE.equals("Speakers")){
            //toast("3");
            add_spinner_name_icon = R.drawable.ic_speaker_black;
            iconName = "Speakers";

        }else if (TYPE.equals("Home Theater")){
            //toast("4");
            add_spinner_name_icon = R.drawable.ic_devices_other_black;
            iconName = "Home Theater";

        }else if (TYPE.equals("Computer")){
            //toast("5");
            add_spinner_name_icon = R.drawable.laptop_mac_black;
            iconName = "Computer";

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

        private EntertainmentFragment.ClickListener mClickListener;
        private GestureDetector mGestureDetector;

        private RecyclerTouchListener(Context mContext,final RecyclerView mRecyclerView, final EntertainmentFragment.ClickListener clickListener) {
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
