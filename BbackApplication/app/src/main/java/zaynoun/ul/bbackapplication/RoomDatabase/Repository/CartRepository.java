package zaynoun.ul.bbackapplication.RoomDatabase.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import zaynoun.ul.bbackapplication.RoomDatabase.DAO.CartDao;
import zaynoun.ul.bbackapplication.RoomDatabase.Entity.CartItemEntity;
import zaynoun.ul.bbackapplication.RoomDatabase.Helper.CartDatabase;

public class CartRepository {

    private CartDao cartDao;

    public CartRepository(Application application) {
        CartDatabase db = CartDatabase.getDatabase(application);
        cartDao = db.cartDao();
    }

    public void addItemToCart(CartItemEntity item) {
        new Thread(() -> cartDao.insert(item)).start();
    }

    public LiveData<List<CartItemEntity>> getCartItems() {
        return cartDao.getAllCartItems();
    }

    public void removeItemFromCart(CartItemEntity item) {
        new Thread(() -> cartDao.delete(item)).start();
    }

    public void clearCart() {
        new Thread(() -> cartDao.clearCart()).start();
    }
}