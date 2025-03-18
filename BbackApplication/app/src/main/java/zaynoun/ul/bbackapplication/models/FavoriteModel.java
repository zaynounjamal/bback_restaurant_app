package zaynoun.ul.bbackapplication.models;

public class FavoriteModel {
    private final String id;
    private final String imageUrl;  // Changed to String for image URL support
    private final String name;
    private final double price;
    private boolean isFavorite;


    public FavoriteModel(String id, String imageUrl, String name, double price, boolean isFavorite) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
