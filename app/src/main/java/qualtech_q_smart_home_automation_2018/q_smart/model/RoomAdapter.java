package qualtech_q_smart_home_automation_2018.q_smart.model;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import qualtech_q_smart_home_automation_2018.q_smart.R;

/**
 * Created by Qtspl _User on 3/7/2018.
 */

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyRoomHolder> {

    private Context mContext;
    private List<CardViewDashboard> mCardViewDashboard;
    @Override
    public MyRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_card,parent,false);
        return new MyRoomHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyRoomHolder holder, int position) {
        CardViewDashboard items = mCardViewDashboard.get(position);
        holder.Room_name.setText(items.getRoom_name());

        //holder.Room_thumbnail.setImageResource(items.getThumbnail());
        //Glide.with(mContext).load(items.getThumbnail()).into(holder.Room_thumbnail);
        holder.Room_thumbnail.setImageBitmap(items.getThumbnail());
        holder.Room_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRoomDetails(holder.Room_thumbnail);
            }
        });

        holder.Overflow_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOverFlowMenu(holder.Overflow_menu);
            }
        });

    }

    private void showRoomDetails(ImageView room_thumbnail) {
        Toast.makeText(mContext,"you clicked" + room_thumbnail,Toast.LENGTH_SHORT).show();

    }

    private void showOverFlowMenu(ImageView overflow_menu) {
        PopupMenu popupMenu = new PopupMenu(mContext,overflow_menu);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_room_album,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyOverFLowMenuClick());
        popupMenu.show();
    }

    public RoomAdapter(Context context, List<CardViewDashboard> cardViewDashboard) {
        mContext = context;
        mCardViewDashboard = cardViewDashboard;
    }

    @Override
    public int getItemCount() {
        return mCardViewDashboard.size();
    }

    public class MyRoomHolder extends RecyclerView.ViewHolder{

        public TextView Room_name;
        public ImageView Room_thumbnail, Overflow_menu;
        public MyRoomHolder(View itemView) {
            super(itemView);

            Room_name = itemView.findViewById(R.id.room_name);
            Room_thumbnail = itemView.findViewById(R.id.room_thumbnail_image);
            Overflow_menu = itemView.findViewById(R.id.overflow_menu);

        }

    }

    private class MyOverFLowMenuClick implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()){
                case R.id.action_add_favourite:
                    Toast.makeText(mContext,"Added to favorites",Toast.LENGTH_SHORT).show();
                    return  true;
                case R.id.action_edit:
                    Toast.makeText(mContext,"You clicked edit button",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_delete:
                    Toast.makeText(mContext,"Room deleted",Toast.LENGTH_SHORT).show();
                    return true;
                    default:

            }
            return false;

        }
    }
}
