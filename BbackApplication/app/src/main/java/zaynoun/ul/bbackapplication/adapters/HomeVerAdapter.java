package zaynoun.ul.bbackapplication.adapters;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zaynoun.ul.bbackapplication.EditItemActivity;
import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.ViewModel.CartViewModel;
import zaynoun.ul.bbackapplication.models.CartModel;
import zaynoun.ul.bbackapplication.models.HomeVerModel;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {

    private final Context context;
    private final List<HomeVerModel> list;
    private final CartViewModel cartViewModel;
    String DELETE_URL="http://172.20.10.14/Bback_application/delete_item.php";

    public HomeVerAdapter(Context context, List<HomeVerModel> list, CartViewModel cartViewModel) {
        this.context = context;
        this.list = list;
        this.cartViewModel = cartViewModel;
    }

    public void updateList(List<HomeVerModel> newList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new HomeDiffCallback(this.list, newList));
        this.list.clear();
        this.list.addAll(newList);
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.home_vertical_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeVerModel item = list.get(position);

        // Load image from URL using Glide
        Glide.with(context)
                .load(item.getImageUrl()) // Updated to load from URL
                .placeholder(R.drawable.placeholder_image) // Add a placeholder image
                .error(R.drawable.error_image) // Add an error image
                .into(holder.imageView);

        holder.name.setText(item.getName());
        holder.timing.setText(item.getTiming());
        holder.rating.setText(item.getRating());
        holder.price.setText(String.format("%.2f", item.getPrice()));

        holder.itemView.setOnClickListener(v -> showBottomSheet(item));
    }

    private void showBottomSheet(HomeVerModel item) {
        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetTheme);
        View sheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null);

        // Initialize views
        ImageView itemImage = sheetView.findViewById(R.id.bottom_img);
        TextView itemName = sheetView.findViewById(R.id.bottom_name);
        TextView itemPrice = sheetView.findViewById(R.id.bottom_price);
        TextView itemTiming = sheetView.findViewById(R.id.bottom_timing);
        TextView itemRating = sheetView.findViewById(R.id.bottom_rating);
        ImageView fav_img=sheetView.findViewById(R.id.fav_icon);
        ImageView editButton, deleteButton;
        editButton = sheetView.findViewById(R.id.edit_img);
        deleteButton = sheetView.findViewById(R.id.remove_img);

        // Load image from URL in bottom sheet
        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(itemImage);

        itemName.setText(item.getName());
        itemPrice.setText(String.format("%.2f", item.getPrice()));
        itemTiming.setText(item.getTiming());
        itemRating.setText(item.getRating());
        // Set initial favorite icon state
        fav_img.setImageResource(item.isFavorite()
                ? R.drawable.baseline_favorite_24
                : R.drawable.baseline_favorite_border_24);

        fav_img.setOnClickListener(v -> {
            boolean newFavoriteState = !item.isFavorite();
            item.setFavorite(newFavoriteState);
            fav_img.setImageResource(newFavoriteState
                    ? R.drawable.baseline_favorite_24
                    : R.drawable.baseline_favorite_border_24);

            toggleFavoriteOnServer(item.getId(), newFavoriteState);
        });

        // Show/hide edit and delete buttons based on user role
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user");

        if ("admin".equals(role)) {
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            Log.d(TAG, "Buttons visible for admin.");
        } else {
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            Log.d(TAG, "Buttons hidden for non-admin.");
        }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditItemActivity.class);

                // Pass all item data to the edit activity
                intent.putExtra("id", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("price", String.valueOf(item.getPrice()));
                intent.putExtra("rating", item.getRating());
                intent.putExtra("timing", item.getTiming());
                intent.putExtra("image_url", item.getImageUrl());

                // Start activity for result
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 100);
                } else {
                    context.startActivity(intent);
                }

                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(v -> {
            deleteItem(item.getId());
            list.remove(item);
            notifyDataSetChanged();
            dialog.dismiss();
            Toast.makeText(context, "Delete function will be implemented", Toast.LENGTH_SHORT).show();
        });

        // Add to Cart functionality
        sheetView.findViewById(R.id.add_to_cart).setOnClickListener(v -> {
            CartModel cartItem = new CartModel(
                    item.getId(),
                    item.getImageUrl(), // Updated to use image URL
                    item.getName(),
                    item.getPrice(),
                    1
            );

            if (cartViewModel != null) {
                cartViewModel.addItem(cartItem);
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error adding to cart", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialog.setContentView(sheetView);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price, rating, timing, name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.dinner_id);
            price = itemView.findViewById(R.id.price);
            rating = itemView.findViewById(R.id.rating_text_id);
            timing = itemView.findViewById(R.id.timing);
            name = itemView.findViewById(R.id.name);
            
        }
    }

    private static class HomeDiffCallback extends DiffUtil.Callback {
        private final List<HomeVerModel> oldList, newList;

        public HomeDiffCallback(List<HomeVerModel> oldList, List<HomeVerModel> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override public int getOldListSize() { return oldList.size(); }
        @Override public int getNewListSize() { return newList.size(); }
        @Override public boolean areItemsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).getId().equals(newList.get(newPos).getId());
        }
        @Override public boolean areContentsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).equals(newList.get(newPos));
        }
    }
    private void deleteItem(String itemId) {
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_URL,
                response -> Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", itemId);
                return params;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }
    private void toggleFavoriteOnServer(String itemId, boolean isFavorite) {
        String TOGGLE_FAV_URL = "http://172.20.10.14/Bback_application/toggle_favorite.php";
        String userId = getUserIdFromPrefs();

        StringRequest request = new StringRequest(Request.Method.POST, TOGGLE_FAV_URL,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getBoolean("success")) {
                            notifyDataSetChanged();
                            Toast.makeText(context, isFavorite
                                    ? "Added to favorites"
                                    : "Removed from favorites", Toast.LENGTH_SHORT).show();
                        } else {
                            revertFavoriteState(itemId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        revertFavoriteState(itemId);
                    }
                },
                error -> revertFavoriteState(itemId)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("item_id", itemId);
                params.put("user_id", userId);
                params.put("is_favorite", isFavorite ? "1" : "0");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(request);
    }

    private void revertFavoriteState(String itemId) {
        for (HomeVerModel item : list) {
            if (item.getId().equals(itemId)) {
                item.setFavorite(!item.isFavorite());
                break;
            }
        }
        notifyDataSetChanged();
        Toast.makeText(context, "Failed to update favorite", Toast.LENGTH_SHORT).show();
    }

    private String getUserIdFromPrefs() {
        SharedPreferences prefs = context.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        return prefs.getString("user_id", "");
    }
}
