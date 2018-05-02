package qualtech_q_smart_home_automation_2018.q_smart.RecyclerViews;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LightPojo;
import qualtech_q_smart_home_automation_2018.q_smart.model.LightTitle;

/**
 * Created by Qtspl _User on 3/22/2018.
 */

public class LightRecyclerView extends RecyclerView.Adapter<LightRecyclerView.ViewHolder> {

    private ArrayList<LightPojo> LightList;
    private Context mContext;
    //private int selectedPosition = -1;

    public LightRecyclerView(Context context,ArrayList<LightPojo> mLightList) {
        LightList = mLightList;
        //clicked = clicked
        mContext = context;
        notifyItemRangeChanged(0,LightList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_light_recycler,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LightRecyclerView.ViewHolder holder, final int position) {

        LightPojo title = LightList.get(position);

        holder.lightThumbnail.setImageResource(title.getThumbnail());
        Log.d("L",title.getName());
        holder.lightTitle.setText(title.getName());

        /*holder.parent_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(,"parent layout",Toast.LENGTH_SHORT).show();
                Log.d("TAG","clicked " +LightList.get(position));
                if (!lightClicked){
                    ImageView lightPopUp = view.findViewById(R.id.row_light_thumbnail);
                    DrawableCompat.setTint(holder.lightThumbnail.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.white));
                    //toast("light on");
                    holder.lightTitle.setTextColor(Color.BLUE);
                    lightClicked = true;
                }else {
                    ImageView lightPopUp = view.findViewById(R.id.row_light_thumbnail);
                    //toast("light off");lightPopUp.getDrawable().mutate()
                    DrawableCompat.setTint(holder.lightThumbnail.getDrawable().mutate(), ContextCompat.getColor(mContext, R.color.colorAccent));
                    holder.lightTitle.setTextColor(Color.CYAN);
                    lightClicked = false;
                }
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return LightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView lightTitle;
        public ImageView lightThumbnail;
        LinearLayout parent_light;
        public ViewHolder(View itemView) {
            super(itemView);

            lightTitle = itemView.findViewById(R.id.row_light_title);
            lightThumbnail = itemView.findViewById(R.id.row_light_thumbnail);
            parent_light = itemView.findViewById(R.id.row_light_parent);

        }

        @Override
        public void onClick(View v) {

        }
    }
    public void removeAt(int position){
        LightList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,LightList.size());
    }
}
