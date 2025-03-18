package zaynoun.ul.bbackapplication.RoomDatabase.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import zaynoun.ul.bbackapplication.RoomDatabase.Entity.Food;

@Dao
public interface FoodDAO {
    @Insert
    void FoodInsert(Food...food);
    @Update
    void FoodUpdate(Food...food);
    @Delete
    void FoodDelete(Food food);
    @Query("SELECT * FROM Food_table order by name asc")
    LiveData<List<Food>> getAllFood();
    @Query("SELECT * FROM Food_table where name=:name")
    LiveData<Food> getFoodByName(String name);
    @Query("SELECT * FROM Food_table where name LIKE '%' || :query || '%'")
    LiveData<List<Food>> getFoodBySearchQuery(String query);
}
