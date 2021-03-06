package qualtech_q_smart_home_automation_2018.q_smart.model;

public class ServiceModel {
    private int id;
    private String name;
    private int thumbnail;
    private boolean isClicked;

    public ServiceModel(int id, String name, int thumbnail, boolean isClicked) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }

    public ServiceModel(int id, int thumbnail, boolean isClicked) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }

    public ServiceModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ServiceModel(String name, int thumbnail, boolean isClicked) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.isClicked = isClicked;
    }


    public ServiceModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}


