package zaynoun.ul.bbackapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.FavoriteModel;
import zaynoun.ul.bbackapplication.models.HomeVerModel;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<HomeVerModel> favoriteItems = new ArrayList<>();
    public void updateFavorites(List<HomeVerModel> allItems) {
        favoriteItems.clear();
        for (HomeVerModel item : allItems) {
            if (item.isFavorite()) {
                favoriteItems.add(item);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_favourite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        HomeVerModel item = favoriteItems.get(position);
        // Use Glide to load the image if the image is a URL
        Glide.with(holder.image.getContext())
                .load(item.getImageUrl())  // Change to getImageUrl() for URL support
                .into(holder.image);
        holder.name.setText(item.getName());
        holder.price.setText(String.format("%.2f", item.getPrice()));

    }

    @Override
    public int getItemCount() {
        return favoriteItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image,favimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.detailed_img);
            name = itemView.findViewById(R.id.detailed_name);
            price = itemView.findViewById(R.id.item_price);
            favimage = itemView.findViewById(R.id.fav_img);

        }
    }
}
