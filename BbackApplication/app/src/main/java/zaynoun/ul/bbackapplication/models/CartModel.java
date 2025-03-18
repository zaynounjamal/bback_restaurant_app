package zaynoun.ul.bbackapplication.models;

import java.util.Objects;

public class CartModel {
    private final String id;
    private final String imageUrl;  // Changed to String for image URL support
    private final String name;
    private final double price;
    private int quantity;

    public CartModel(String id, String imageUrl, String name, double price, int quantity) {
        this.id = id;
        this.imageUrl = imageUrl;  // Changed to URL
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getId() { return id; }
    public String getImageUrl() { return imageUrl; }  // Getter for the image URL
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Setter
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartModel cartModel = (CartModel) o;
        return Objects.equals(id, cartModel.id); // Only compare ID for uniqueness
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Optional: To String method for easy debugging and printing
    @Override
    public String toString() {
        return "CartModel{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
