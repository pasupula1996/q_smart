package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.entertainment;

/**
 * Created by Qtspl _User on 3/24/2018.
 */

public class EntertainmentTitle {
    private String eTitle;
    private int eThumbnail;
    private boolean isClicked;

    public EntertainmentTitle(String eTitle,int eThumbnail, boolean isClicked) {
        this.eTitle = eTitle;
        this.eThumbnail = eThumbnail;
        this.isClicked = isClicked;
    }

    public String geteTitle() {
        return eTitle;
    }

    public void seteTitle(String eTitle) {
        this.eTitle = eTitle;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int geteThumbnail() {
        return eThumbnail;
    }

    public void seteThumbnail(int eThumbnail) {
        this.eThumbnail = eThumbnail;
    }
}
