package qualtech_q_smart_home_automation_2018.q_smart.SQLITE;

public class LightPojo {
    private String name;
    private int thumbnail;
    private String iconName;
    private boolean isClicked;
    private int id;

    public LightPojo(String name, int thumbnail, boolean isClicked) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }

    public LightPojo(int id,String name, int thumbnail, boolean isClicked) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
        this.id = id;
    }

    public LightPojo(int id,String name, int thumbnail, String iconName, boolean isClicked) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.iconName = iconName;
        this.isClicked = isClicked;
        this.id = id;
    }

    public LightPojo(int id) {
        this.id = id;
    }

    public LightPojo(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public LightPojo(int id, int thumbnail, boolean isClicked) {
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
