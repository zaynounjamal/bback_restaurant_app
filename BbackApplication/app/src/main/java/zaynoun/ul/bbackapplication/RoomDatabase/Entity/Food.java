package zaynoun.ul.bbackapplication.RoomDatabase.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Food_table", foreignKeys = {
        @ForeignKey(entity = FoodCategory.class, parentColumns = "foodCategory_id",
                childColumns = "food_category_id",
                onUpdate = ForeignKey.CASCADE,
                onDelete =ForeignKey.CASCADE )

})
public class Food {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    private int id;
    @NonNull
    @ColumnInfo(name = "food_category_id")
    private int foodCategoryId;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "price")
    private Double price;
    @NonNull
    @ColumnInfo(name = "rating")
    private float rating;
    @NonNull
    @ColumnInfo(name = "timing")
    private String timing;
    public Food(){

    }

    public Food(String name, String description, Double price, float rating, String timing,int foodCategoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.timing = timing;
        this.foodCategoryId = foodCategoryId;
    }

    public int getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(int foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }
}
