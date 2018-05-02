package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Security;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SecurityFragment extends Fragment {

    Context mContext;
    RecyclerView mRecyclerView;
    FloatingActionButton addFab, allOffFab;

    ArrayList<SecurityPojo> data;
    LDbHelper mHelper;
    SecurityRecyclerView adapter;

    EditText edxSecurityName;
    Button btnAddSecurityName;
    int spinnerIcon;

    public SecurityFragment() {
        // Required empty public constructor

    }
    @SuppressLint("ValidFragment")
    public SecurityFragment(Context context){
        mContext = context;
        mHelper = new LDbHelper(mContext);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.security_frag_recyclerView);
        addFab = view.findViewById(R.id.fab_add_security_title);
        allOffFab = view.findViewById(R.id.fab_off_all_securities);


        data = mHelper.getSecurityData();
        adapter = new SecurityRecyclerView(data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        mRecyclerView.addOnItemTouchListener(new RecyclerTouch(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                /*int changeIcon = data.get(position).getThumbnail();

                if (changeIcon == R.drawable.ic_linked_camera_black){
                    changeIcon = R.drawable.ic_linked_camera_white;
                }else if (changeIcon == R.drawable.ic_linked_camera_white){
                    changeIcon = R.drawable.ic_view_carousel_black;
                }
                else if (changeIcon == R.drawable.ic_view_carousel_black){
                    changeIcon = R.drawable.ic_view_carousel_white;
                }else if (changeIcon == R.drawable.ic_view_carousel_white){
                    changeIcon = R.drawable.ic_view_carousel_black;
                }*/

                if (!data.get(position).isClicked){
                    ImageView icon = view.findViewById(R.id.security_thumbnail);
                    int changeIcon = data.get(position).getThumbnail();

                    if (changeIcon == R.drawable.ic_linked_camera_black){
                        changeIcon = R.drawable.ic_linked_camera_white;
                    } else if (changeIcon == R.drawable.ic_view_carousel_black){
                        changeIcon = R.drawable.ic_view_carousel_white;
                    }

                    icon.setImageResource(changeIcon);

                    data.get(position).setClicked(true);
                    SecurityPojo pojo = new SecurityPojo(data.get(position).getId(),changeIcon,true);
                    mHelper.updateSecurityState(pojo);
                    Toast toast = Toast.makeText(getContext(),data.get(position).getTitle()+" On",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    //adapter.notifyDataSetChanged();
                }else{
                    int ii = data.get(position).getThumbnail();

                    if (ii == R.drawable.ic_linked_camera_white){
                        ii = R.drawable.ic_linked_camera_black;
                    }
                    else if (ii == R.drawable.ic_view_carousel_white){
                        ii = R.drawable.ic_view_carousel_black;
                    }

                    ImageView icon = view.findViewById(R.id.security_thumbnail);
                    icon.setImageResource(ii);

                    data.get(position).setClicked(false);
                    SecurityPojo pojo = new SecurityPojo(data.get(position).getId(),ii,false);
                    mHelper.updateSecurityState(pojo);
                    Toast toast = Toast.makeText(getContext(),data.get(position).getTitle()+" Off",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,200);
                    toast.show();
                    //adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onLongClick(View view, final int position) {

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.edit_security_name);

                Button update = dialog.findViewById(R.id.btn_update_security_name);
                Button delete = dialog.findViewById(R.id.btn_delete_security_name);
                dialog.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editTitle = dialog.findViewById(R.id.edit_security_name_edx);
                        String title = editTitle.getText().toString();
                        if (title.length()>0){
                            SecurityPojo pojo = new SecurityPojo(data.get(position).getId(),title);
                            mHelper.editSecurityName(pojo);
                            data.set(position,new SecurityPojo(title,data.get(position).getThumbnail(),data.get(position).isClicked()));
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SecurityPojo pojo = new SecurityPojo(data.get(position).getId());
                        mHelper.deleteSecurity(pojo);
                        data.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });


            }
        }));
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.add_security_title);
        edxSecurityName = dialog.findViewById(R.id.edt_security_title);
        btnAddSecurityName = dialog.findViewById(R.id.btn_add_security_title);
        Spinner spinner = dialog.findViewById(R.id.spinner_security_type);
        String[] list = {"Door", "IP Cameras"};
        final ArrayAdapter<String> sadapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,list);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sadapter);
        dialog.show();
        btnAddSecurityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edxSecurityName.getText().toString();
                if (title.length()>0){
                    mHelper.insertSecurityTitle(title,spinnerIcon,false);
                    data.add(new SecurityPojo(title,spinnerIcon,false));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSpinner(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void getSpinner(String type){
        if (type.equals("Door")){
            spinnerIcon = R.drawable.ic_view_carousel_black;
        }else if (type.equals("IP Cameras")){
            spinnerIcon = R.drawable.ic_linked_camera_black;
        }
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

    class RecyclerTouch implements RecyclerView.OnItemTouchListener{
        private ClickListener mListener;
        private GestureDetector mDetector;

        public RecyclerTouch(Context context,final RecyclerView rView,final  SecurityFragment.ClickListener listener){
            mListener = listener;
            mDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = rView.findChildViewUnder(e.getX(),e.getY());
                    if (child != null && mListener != null ){
                        mListener.onLongClick(child,rView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if (child!= null && mListener != null && mDetector.onTouchEvent(e)){
                mListener.onClick(child,rv.getChildAdapterPosition(child));
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
