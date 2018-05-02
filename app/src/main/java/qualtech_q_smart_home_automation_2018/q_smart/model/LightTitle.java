package qualtech_q_smart_home_automation_2018.q_smart.model;

/**
 * Created by Qtspl _User on 3/23/2018.
 */

public class LightTitle {

    private String title;
    private boolean isClicked;

    public LightTitle(String title, boolean isClicked) {
        this.title = title;
        this.isClicked = isClicked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
}
