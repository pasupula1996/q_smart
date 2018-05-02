package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Utilities;

/**
 * Created by Qtspl _User on 3/24/2018.
 */

public class UtilitiesTitle {
    private String uTitle;
    private int uThumbnails;
    private boolean isClicked;

    public UtilitiesTitle(String uTitle, int uThumbnails, boolean isClicked) {
        this.uTitle = uTitle;
        this.uThumbnails = uThumbnails;
        this.isClicked = isClicked;
    }

    public String getuTitle() {
        return uTitle;
    }

    public void setuTitle(String uTitle) {
        this.uTitle = uTitle;
    }

    public int getuThumbnails() {
        return uThumbnails;
    }

    public void setuThumbnails(int uThumbnails) {
        this.uThumbnails = uThumbnails;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
