package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Utilities;

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

public class UtilitiesRecyclerView extends RecyclerView.Adapter<UtilitiesRecyclerView.ViewHolder>{

    private ArrayList<LightPojo> mUtilitiesTitles;

    public UtilitiesRecyclerView(ArrayList<LightPojo> UtilitiesTitle) {
        mUtilitiesTitles = UtilitiesTitle;
    }

    @NonNull
    @Override
    public UtilitiesRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_utilities_layout,null);

        return new UtilitiesRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilitiesRecyclerView.ViewHolder holder, int position) {
        LightPojo e = mUtilitiesTitles.get(position);
        holder.eThumbnail.setImageResource(e.getThumbnail());
        holder.eTitle.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return mUtilitiesTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView eTitle;
        public ImageView eThumbnail;
        public ViewHolder(View itemView) {
            super(itemView);

            eTitle = itemView.findViewById(R.id.row_utilities_title);
            eThumbnail = itemView.findViewById(R.id.utilities_thumbnail);
        }
    }
}
