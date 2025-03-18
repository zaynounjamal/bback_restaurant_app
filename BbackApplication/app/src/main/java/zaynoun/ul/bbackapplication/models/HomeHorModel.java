// HomeHorModel.java
package zaynoun.ul.bbackapplication.models;

public class HomeHorModel {
    private final String name;
    private final int imageResourceId;

    public HomeHorModel(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}