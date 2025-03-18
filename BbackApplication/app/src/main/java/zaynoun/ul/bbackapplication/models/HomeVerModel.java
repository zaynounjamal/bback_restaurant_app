package zaynoun.ul.bbackapplication.models;

import java.util.Objects;

public class HomeVerModel {
    private String id;
    private String imageUrl;  // Updated: Store image URL instead of int
    private String name;
    private String timing;
    private String rating;
    private double price;
    private boolean isFavorite;  // Add this field

    // Getters and setters
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public HomeVerModel(String id, String imageUrl, String name, String timing, String rating, double price) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.timing = timing;
        this.rating = rating;
        this.price = price;
    }
    public HomeVerModel(String id, String imageUrl, String name, String timing, String rating, double price, boolean isFavorite) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.timing = timing;
        this.rating = rating;
        this.price = price;
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getImageUrl() { return imageUrl; }  // Updated
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }  // Updated

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeVerModel that = (HomeVerModel) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
