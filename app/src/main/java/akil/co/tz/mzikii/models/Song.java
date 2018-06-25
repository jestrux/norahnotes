package akil.co.tz.mzikii.models;

public class Song {
    String id;
    String title;
    String description;

    public Song() {
    }

    public Song(String id, String title, String details) {
        this.id = id;
        this.title = title;
        this.description = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
