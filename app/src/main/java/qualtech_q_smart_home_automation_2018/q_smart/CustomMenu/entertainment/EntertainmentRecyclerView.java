package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.entertainment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LightPojo;

/**
 * Created by Qtspl _User on 3/24/2018.
 */

public class EntertainmentRecyclerView extends RecyclerView.Adapter<EntertainmentRecyclerView.ViewHolder> {

    private ArrayList<LightPojo> mEntertainmentTitleList;
    Context mContext;

    public EntertainmentRecyclerView(Context context,ArrayList<LightPojo> entertainmentTitleList) {
        mEntertainmentTitleList = entertainmentTitleList;
        mContext = context;
    }

    @NonNull
    @Override
    public EntertainmentRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_entertainment_layout,null);

        return new EntertainmentRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntertainmentRecyclerView.ViewHolder holder, int position) {
        LightPojo e = mEntertainmentTitleList.get(position);
        holder.eThumbnail.setImageResource(e.getThumbnail());
        holder.eTitle.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return mEntertainmentTitleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView eTitle;
        public ImageView eThumbnail;
        public ViewHolder(View itemView) {
            super(itemView);

            eTitle = itemView.findViewById(R.id.row_entertainment_title);
            eThumbnail = itemView.findViewById(R.id.row_entertainment_thumbnail);
        }
    }
}
