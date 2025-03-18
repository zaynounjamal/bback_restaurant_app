package zaynoun.ul.bbackapplication.models;

public class FeaturedVerModel {
    int image;
    String name;
    String rating;
    String description;
    String timing;

    public FeaturedVerModel(int image, String name, String rating, String description, String timing) {
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.timing = timing;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
