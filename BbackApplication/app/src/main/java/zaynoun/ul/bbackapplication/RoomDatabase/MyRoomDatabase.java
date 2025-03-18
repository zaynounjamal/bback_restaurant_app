package zaynoun.ul.bbackapplication.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zaynoun.ul.bbackapplication.RoomDatabase.DAO.FoodCategoryDAO;
import zaynoun.ul.bbackapplication.RoomDatabase.DAO.FoodDAO;
import zaynoun.ul.bbackapplication.RoomDatabase.Entity.Food;
import zaynoun.ul.bbackapplication.RoomDatabase.Entity.FoodCategory;

@Database(entities = {FoodCategory.class, Food.class},version = 1,exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract FoodDAO foodDao();
    public abstract FoodCategoryDAO foodCategoryDao();

}
