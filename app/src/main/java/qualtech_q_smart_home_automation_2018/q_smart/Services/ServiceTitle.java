package qualtech_q_smart_home_automation_2018.q_smart.Services;

/**
 * Created by Qtspl _User on 3/24/2018.
 */

public class ServiceTitle {
    private String sTitle;
    private int sThumbnail;
    private boolean isClicked;

    public ServiceTitle(String sTitle, int sThumbnail, boolean isClicked) {
        this.sTitle = sTitle;
        this.sThumbnail = sThumbnail;
        this.isClicked = isClicked;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public int getsThumbnail() {
        return sThumbnail;
    }

    public void setsThumbnail(int sThumbnail) {
        this.sThumbnail = sThumbnail;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
