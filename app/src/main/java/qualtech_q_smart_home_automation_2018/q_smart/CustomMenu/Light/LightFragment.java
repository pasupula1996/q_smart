package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Light;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Light.LightRecyclerView;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LDbHelper;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LightPojo;


public class LightFragment extends Fragment {

    RecyclerView mRecyclerView;
    FloatingActionButton addLightTitle,allLightsOff;
    //ArrayList<LightTitle> lightTitle;
    RecyclerView.LayoutManager mLayoutManager;

    EditText edxLightName;
    Button btnLightName;

    SQLiteDatabase  db;
    LDbHelper lHelper;
    ArrayList<LightPojo> mLightPojos = new ArrayList<>();
    LightRecyclerView li;

    //private boolean lightClicked = false;
    //FragmentManager fm = getFragmentManager();
    //RecyclerView.Adapter mRecyclerViewAdapter;

    @SuppressLint("ValidFragment")
    public LightFragment(Context mContext) {
        // Required empty public constructor
        lHelper = new LDbHelper(mContext);
        //db = lHelper.getWritableDatabase();
        //db.close();
    }

    public LightFragment() {
    }
    /*private void openDb(){
        db = lHelper.getWritableDatabase();
    }
    private void closeDb(){
        lHelper.close();
    }
    private boolean add(String name, int thumbnail, boolean click){
        openDb();
        ContentValues values = new ContentValues();
        values.put(DbConstants.ROW_TITLE,name);
        values.put(DbConstants.ROW_THUMBNAIL,thumbnail);
        values.put(DbConstants.ROW_CLICKED,String.valueOf(click));
        db.insert(DbConstants.TB_NAME,DbConstants.ROW_ID,values);
        closeDb();

        return true;
    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_light, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLightPojos = lHelper.getLightData();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.light_frag);
        addLightTitle = view.findViewById(R.id.fab_add_light_title);
        allLightsOff = view.findViewById(R.id.fab_off_all_lights);

        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        //mLightPojos = lHelper.getLightData();
        mLightPojos.addAll(lHelper.getLightData());
        //addLightTitles();
        li = new LightRecyclerView(getContext(),mLightPojos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(li);
        li.notifyDataSetChanged();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),mRecyclerView,new ClickListener(){

            @Override
            public void onClick(View view, int position) {
                Log.d("BT","clicked" + mLightPojos.get(position).getName());
                //toast("clicked" + position );

                if (!mLightPojos.get(position).isClicked()){
                    ImageView lightPopUp = view.findViewById(R.id.row_light_thumbnail);
                    lightPopUp.setImageResource(R.drawable.light_on);
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(getContext(), R.color.white));
                    //toast(mLightPojos.get(position).getName() + "light on");
                    Toast toast = Toast.makeText(getContext(),mLightPojos.get(position).getName()+" On",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    Log.d("BT","clicked" + mLightPojos.get(position).isClicked());
                    mLightPojos.get(position).setClicked(true);
                    LightPojo pojo = new LightPojo(mLightPojos.get(position).getId(),R.drawable.light_on,true);
                    lHelper.upDateState(pojo);
                }else {
                    ImageView lightPopUp = view.findViewById(R.id.row_light_thumbnail);
                    //toast(mLightPojos.get(position).getName() + "light off");
                    lightPopUp.setImageResource(R.drawable.light_off);
                    Toast toast = Toast.makeText(getContext(),mLightPojos.get(position).getName()+" Off",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(getContext(), R.color.colorAccent));
                    Log.d("BT","clicked" + mLightPojos.get(position).isClicked());
                    mLightPojos.get(position).setClicked(false);
                    //lHelper.upDateState(position,R.drawable.light_off,false);
                    LightPojo pojo = new LightPojo(mLightPojos.get(position).getId(),R.drawable.light_off,false);
                    lHelper.upDateState(pojo);
                }



            }

            @Override
            public void onLongClick(View view, final int position) {
                //toast("long pressed at " + position);
                final Dialog eDialog = new Dialog(getActivity());
                eDialog.setContentView(R.layout.edit_light_name);
                //EditText editLightName = eDialog.findViewById(R.id.edit_light_name_edx);
                //final String editedLightName = editLightName.getText().toString();
                Button deleteLightName = eDialog.findViewById(R.id.btn_delete_light_name);
                Button updateLightName = eDialog.findViewById(R.id.btn_update_light_name);
                eDialog.show();
               // eDialog.setTitle("Edit light name");



                updateLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lightTitle.set(position,editLightName.getText().toString());
                        //Log.d("BT",editedLightName);
                        EditText editLightName = eDialog.findViewById(R.id.edit_light_name_edx);

                        //lightTitle.set(position,new LightTitle(editLightName.getText().toString(),false));
                        LightPojo pojo = new LightPojo(mLightPojos.get(position).getId(),editLightName.getText().toString());
                        lHelper.editName(pojo);
                        mLightPojos.get(position).setName(editLightName.getText().toString());
                        li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });

                deleteLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lightTitle.remove(position);
                        LightPojo pojo = new LightPojo(mLightPojos.get(position).getId());
                        lHelper.delete(pojo);
                        mLightPojos.remove(position);
                        li.notifyItemChanged(position);
                        //li.notifyDataSetChanged();
                        eDialog.dismiss();
                    }
                });


            }
        }));

        addLightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toast("clicked fab button");

                //LightTitleDialog titleDialog = new LightTitleDialog();
                //titleDialog.show(getActivity().getFragmentManager(),"title");
                final Dialog mDialog = new Dialog(getActivity());
                mDialog.setTitle("Add light name");
                mDialog.setContentView(R.layout.add_light_title);
                edxLightName = mDialog.findViewById(R.id.edt_light_title);
                btnLightName = mDialog.findViewById(R.id.btn_add_title);
                mDialog.show();

                btnLightName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toast("Add light");
                        if (edxLightName.getText().length() != 0){
                            String newLightName = edxLightName.getText().toString();
                            //lightTitle.add(new LightTitle(newLightName,false));
                            //add(newLightName,R.id.utilities_thumbnail,false);
                            lHelper.insertData(newLightName,R.drawable.light_off,false);
                            LightPojo pojo = new LightPojo(newLightName,R.drawable.light_off,false);
                            mLightPojos.add(pojo);
                            li.notifyDataSetChanged();
                            mDialog.dismiss();
                        }else {
                            toast("Please enter light name");
                        }
                    }
                });


            }
        });
        allLightsOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("size" + mLightPojos.size());

                        //DrawableCompat.setTint(lightPopUp.getDrawable().mutate(), ContextCompat.getColor(getContext(), R.color.white));
                       // lightTitle.get(i).setClicked(false);
                        //lightTitle.set(i,new LightTitle(lightTitle.get(i).getTitle(),false));
                        //ImageView lightPopUp = v.findViewById(R.id.row_light_thumbnail);
                        //toast(lightTitle.get(i).getTitle() + "light off");
                        //lightPopUp.setImageResource(R.drawable.light_off);
                        //li.notifyDataSetChanged();



            }
        });

       // mRecyclerViewAdapter = new RecyclerView.Adapter(getActivity());
       // mRecyclerViewAdapter = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /*private void addLightTitles() {
        lightTitle = new ArrayList<>();

        lightTitle.add(new LightTitle("bedroom",false));
        lightTitle.add(new LightTitle("kitchen",false));
        lightTitle.add(new LightTitle("hall",false));
        lightTitle.add(new LightTitle("drawing room",false));
    }*/
    public void toast(String mess){
        Toast.makeText(getActivity(),mess,Toast.LENGTH_SHORT).show();
    }
    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener mClickListener;
        private GestureDetector mGestureDetector;

        public RecyclerTouchListener(Context mContext,final RecyclerView mRecyclerView, final ClickListener clickListener) {
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

