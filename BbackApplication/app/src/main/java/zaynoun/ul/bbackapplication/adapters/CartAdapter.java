package zaynoun.ul.bbackapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.CartModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartModel> items = new ArrayList<>();
    private final CartListener listener;

    public CartAdapter(CartListener listener) {
        this.listener = listener;
    }

    public interface CartListener {
        void onUpdateQuantity(int position, int newQuantity);
        void onRemoveItem(int position);
    }

    public void updateItems(List<CartModel> newItems) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(items, newItems));
        items = new ArrayList<>(newItems);
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel item = items.get(position);

        // Use Glide to load the image if the image is a URL
        Glide.with(holder.Images.getContext())
                .load(item.getImageUrl())  // Change to getImageUrl() for URL support
                .into(holder.Images);

        holder.name.setText(item.getName());
        holder.price.setText(String.format("%.2f", item.getPrice()));
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        // Button click listeners for increase, decrease, and remove
        holder.increase.setOnClickListener(v -> listener.onUpdateQuantity(position, item.getQuantity() + 1));
        holder.decrease.setOnClickListener(v -> listener.onUpdateQuantity(position, item.getQuantity() - 1));
        holder.remove.setOnClickListener(v -> listener.onRemoveItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView increase, decrease, remove, Images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Images = itemView.findViewById(R.id.detailed_img);
            name = itemView.findViewById(R.id.detailed_name);
            price = itemView.findViewById(R.id.item_price);
            quantity = itemView.findViewById(R.id.quantity_item);
            increase = itemView.findViewById(R.id.add_item_count);
            decrease = itemView.findViewById(R.id.min_item_count);
            remove = itemView.findViewById(R.id.remove_from_cart);
        }
    }

    private static class DiffCallback extends DiffUtil.Callback {
        private final List<CartModel> oldList, newList;

        public DiffCallback(List<CartModel> oldList, List<CartModel> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override public int getOldListSize() { return oldList.size(); }
        @Override public int getNewListSize() { return newList.size(); }
        @Override public boolean areItemsTheSame(int oldPos, int newPos) {
            return oldList.get(oldPos).getId().equals(newList.get(newPos).getId());
        }
        @Override
        public boolean areContentsTheSame(int oldPos, int newPos) {
            CartModel oldItem = oldList.get(oldPos);
            CartModel newItem = newList.get(newPos);

            return oldItem.getQuantity() == newItem.getQuantity()
                    && oldItem.getImageUrl().equals(newItem.getImageUrl()) // Compare image URLs
                    && oldItem.getName().equals(newItem.getName())
                    && oldItem.getPrice() == newItem.getPrice();
        }
    }
}
