package zaynoun.ul.bbackapplication.adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.DetailedDailyModel;

public class DetailedDailyAdapter extends RecyclerView.Adapter<DetailedDailyAdapter.ViewHolder> {
    List<DetailedDailyModel> list;
    Context context;
    public DetailedDailyAdapter(Context context,List<DetailedDailyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_daily_meal_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        holder.description.setText(list.get(position).getDescription());
        holder.rating.setText(list.get(position).getRating());
        holder.timing.setText(list.get(position).getTiming());
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user");
        if ("admin".equals(role)) {
            holder.removeImageView.setVisibility(View.VISIBLE);
            holder.EditImageView.setVisibility(View.VISIBLE);
            Log.d(TAG, "FAB is visible for admin.");
        } else {
            holder.removeImageView.setVisibility(View.GONE);
            holder.EditImageView.setVisibility(View.GONE);
            Log.d(TAG, "FAB is hidden for non-admin.");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView removeImageView,EditImageView;
        TextView name,price,description,rating,timing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.detailed_img);
            name = itemView.findViewById(R.id.detailed_name);
            price = itemView.findViewById(R.id.detailed_price);
            description = itemView.findViewById(R.id.detailed_description);
            rating = itemView.findViewById(R.id.detailed_rating);
            timing = itemView.findViewById(R.id.detailed_timing);
            removeImageView=itemView.findViewById(R.id.remove_img);
            EditImageView=itemView.findViewById(R.id.edit_img);
        }
    }
}
