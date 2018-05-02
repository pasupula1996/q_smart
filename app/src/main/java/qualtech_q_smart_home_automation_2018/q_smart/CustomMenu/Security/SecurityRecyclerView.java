package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Security;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import qualtech_q_smart_home_automation_2018.q_smart.R;

public class SecurityRecyclerView extends RecyclerView.Adapter<SecurityRecyclerView.ViewHolder> {

    private ArrayList<SecurityPojo> data;

    public SecurityRecyclerView(ArrayList<SecurityPojo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SecurityRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_security_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecurityRecyclerView.ViewHolder holder, int position) {
        SecurityPojo pojo = data.get(position);
        holder.seTitle.setText(pojo.getTitle());
        holder.seThumbnail.setImageResource(pojo.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView seTitle;
        private ImageView seThumbnail;
        public ViewHolder(View itemView) {
            super(itemView);

            seTitle = itemView.findViewById(R.id.security_title);
            seThumbnail = itemView.findViewById(R.id.security_thumbnail);
        }
    }
}
