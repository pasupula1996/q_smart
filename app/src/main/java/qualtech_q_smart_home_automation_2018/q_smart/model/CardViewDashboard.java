package qualtech_q_smart_home_automation_2018.q_smart.model;

import android.graphics.Bitmap;

/**
 * Created by Qtspl _User on 3/7/2018.
 */

public class CardViewDashboard {

    public String room_name;
    public Bitmap thumbnail;

    public CardViewDashboard(String room_name, Bitmap thumbnail) {
        this.room_name = room_name;
        this.thumbnail = thumbnail;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
