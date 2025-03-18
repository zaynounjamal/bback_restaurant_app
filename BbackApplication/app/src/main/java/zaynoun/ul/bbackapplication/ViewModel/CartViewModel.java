package zaynoun.ul.bbackapplication.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import zaynoun.ul.bbackapplication.models.CartModel;

public class CartViewModel extends AndroidViewModel {
    private final MutableLiveData<List<CartModel>> cartItems = new MutableLiveData<>(new ArrayList<>());
    private final Gson gson = new Gson();
    private final SharedPreferences sharedPreferences;

    public CartViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        loadCart();
    }

    private void loadCart() {
        String json = sharedPreferences.getString("cart_items", "[]");
        Type type = new TypeToken<ArrayList<CartModel>>(){}.getType();
        List<CartModel> items = gson.fromJson(json, type);
        cartItems.setValue(items != null ? items : new ArrayList<>());
    }

    private void saveCart() {
        sharedPreferences.edit()
                .putString("cart_items", gson.toJson(cartItems.getValue()))
                .apply();
    }

    // In CartViewModel.java
    public void addItem(CartModel newItem) {
        List<CartModel> currentItems = new ArrayList<>(cartItems.getValue());

        // Check for existing item using ID only
        int existingIndex = -1;
        for (int i = 0; i < currentItems.size(); i++) {
            if (currentItems.get(i).getId().equals(newItem.getId())) {
                existingIndex = i;
                break;
            }
        }

        if (existingIndex != -1) {
            // Update quantity for existing item
            CartModel existingItem = currentItems.get(existingIndex);
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
        } else {
            // Add new item
            currentItems.add(newItem);
        }

        cartItems.setValue(new ArrayList<>(currentItems)); // Force new list reference
        saveCart();
    }

    public void removeItem(int position) {
        List<CartModel> currentItems = new ArrayList<>(cartItems.getValue());
        if (position >= 0 && position < currentItems.size()) {
            currentItems.remove(position);
            cartItems.setValue(currentItems);
        }
    }

    public LiveData<List<CartModel>> getCartItems() {
        return cartItems;
    }

    public void updateQuantity(int position, int newQuantity) {
        List<CartModel> currentItems = new ArrayList<>(cartItems.getValue());
        if (position >= 0 && position < currentItems.size()) {
            CartModel item = currentItems.get(position);
            item.setQuantity(newQuantity); // Update the quantity directly on the existing item
            currentItems.set(position, item);
            cartItems.setValue(new ArrayList<>(currentItems)); // Force new list reference
            saveCart();
        }
    }




    public void updateCart(List<CartModel> newItems) {
        cartItems.setValue(new ArrayList<>(newItems));
        saveCart();
    }
    public void clearCart() {
        List<CartModel> emptyList = new ArrayList<>();
        cartItems.setValue(emptyList);
        saveCart(); // This will persist the empty cart
    }
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (CartModel item : cartItems.getValue()) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }
}