package qualtech_q_smart_home_automation_2018.q_smart.CustomMenu.Security;

public class SecurityPojo {
    public int id;
    public String title;
    public int thumbnail;
    public boolean isClicked;

    public SecurityPojo(int id, String title, int thumbnail, boolean isClicked) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }

    public SecurityPojo(String title, int thumbnail, boolean isClicked) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }

    public SecurityPojo(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public SecurityPojo(int id, int thumbnail, boolean isClicked) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }

    public SecurityPojo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
