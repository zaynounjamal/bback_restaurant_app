package zaynoun.ul.bbackapplication.RoomDatabase.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import zaynoun.ul.bbackapplication.RoomDatabase.Entity.Food;
import zaynoun.ul.bbackapplication.RoomDatabase.Entity.FoodCategory;

@Dao
public interface FoodCategoryDAO {
    @Insert
    void FoodCategoryInsert(FoodCategory...foodCategories);
    @Update
    void FoodCategoryUpdate(FoodCategory...foodCategories);
    @Delete
    void FoodCategoryDelete(FoodCategory foodCategories);
    @Query("SELECT * FROM foodCategory order by name asc")
    LiveData<List<FoodCategory>> getAllFoodCategory();
    @Query("SELECT * FROM foodCategory where name=:name")
    LiveData<FoodCategory> getFoodCategoryByName(String name);
}
