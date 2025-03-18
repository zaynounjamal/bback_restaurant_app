package zaynoun.ul.bbackapplication.RoomDatabase.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodCategory")
public class FoodCategory {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "foodCategory_id")
    private int id;
    private String name;

    public FoodCategory(String name) {
        this.name = name;
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
}
