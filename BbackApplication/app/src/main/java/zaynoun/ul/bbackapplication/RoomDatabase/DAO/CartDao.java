package zaynoun.ul.bbackapplication.RoomDatabase.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import zaynoun.ul.bbackapplication.RoomDatabase.Entity.CartItemEntity;

@Dao
public interface CartDao {
    @Insert
    void insert(CartItemEntity cartItem);

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItemEntity>> getAllCartItems();

    @Delete
    void delete(CartItemEntity cartItem);

    @Query("DELETE FROM cart_items")
    void clearCart();
}

