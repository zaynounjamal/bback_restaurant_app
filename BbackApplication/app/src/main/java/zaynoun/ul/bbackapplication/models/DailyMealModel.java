package zaynoun.ul.bbackapplication.models;

public class DailyMealModel {
    int img;
    String name;
    String discount;
    String type;
    String description;

    public DailyMealModel(int img, String name, String discount, String type, String description) {
        this.img = img;
        this.name = name;
        this.discount = discount;
        this.type = type;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
