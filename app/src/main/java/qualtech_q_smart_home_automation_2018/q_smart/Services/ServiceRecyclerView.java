package qualtech_q_smart_home_automation_2018.q_smart.Services;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qualtech_q_smart_home_automation_2018.q_smart.R;
import qualtech_q_smart_home_automation_2018.q_smart.SQLITE.LightPojo;
import qualtech_q_smart_home_automation_2018.q_smart.model.ServiceModel;

/**
 * Created by Qtspl _User on 3/24/2018.
 */

public class ServiceRecyclerView extends RecyclerView.Adapter<ServiceRecyclerView.ViewHolder> {

    private ArrayList<ServiceModel> serviceTitleList;

    public ServiceRecyclerView(ArrayList<ServiceModel> entertainmentTitleList) {
        serviceTitleList = entertainmentTitleList;
    }

    @NonNull
    @Override
    public ServiceRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_services_layout,null);

        return new ServiceRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceRecyclerView.ViewHolder holder, int position) {
        ServiceModel e = serviceTitleList.get(position);
        holder.eThumbnail.setImageResource(e.getThumbnail());
        holder.eTitle.setText(e.getName());
    }

    @Override
    public int getItemCount() {
        return serviceTitleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView eTitle;
        public ImageView eThumbnail;
        public ViewHolder(View itemView) {
            super(itemView);

            eTitle = itemView.findViewById(R.id.row_services_title);
            eThumbnail = itemView.findViewById(R.id.row_services_thumbnail);
        }
    }
}
