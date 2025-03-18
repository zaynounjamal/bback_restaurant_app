package zaynoun.ul.bbackapplication.RoomDatabase.Helper;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import zaynoun.ul.bbackapplication.RoomDatabase.DAO.CartDao;
import zaynoun.ul.bbackapplication.RoomDatabase.Entity.CartItemEntity;

@Database(entities = {CartItemEntity.class}, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    private static volatile CartDatabase INSTANCE;

    public abstract CartDao cartDao();

    // Singleton pattern to get the database instance
    public static CartDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CartDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CartDatabase.class, "cart_database")
                            .fallbackToDestructiveMigration() // In case of schema changes
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
