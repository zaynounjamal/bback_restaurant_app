package zaynoun.ul.bbackapplication.adapters;

import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.activities.DetailedDailyMealActivity;
import zaynoun.ul.bbackapplication.models.DailyMealModel;

public class DailyMealAdapter extends RecyclerView.Adapter<DailyMealAdapter.ViewHolder>{
    List<DailyMealModel> list;
    Context context;
    public DailyMealAdapter(Context context,List<DailyMealModel> list ) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public DailyMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_meal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMealAdapter.ViewHolder holder, int position) {
            holder.imageView.setImageResource(list.get(position).getImg());
            holder.name.setText(list.get(position).getName());
            holder.description.setText(list.get(position).getDescription());
            holder.discount.setText(list.get(position).getDiscount());
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "user");
        if ("admin".equals(role)) {
            holder.removeImageView.setVisibility(View.VISIBLE);
            holder.editImageView.setVisibility(View.VISIBLE);

        } else {
            holder.removeImageView.setVisibility(View.GONE);
            holder.editImageView.setVisibility(View.GONE);
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailedDailyMealActivity.class);
                    intent.putExtra("type",list.get(position).getType());
                    try{
                        context.startActivity(intent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(context, "Page Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView removeImageView,editImageView;
        TextView name,description,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDailyMeal);
            name = itemView.findViewById(R.id.Dinner_text_view);
            description = itemView.findViewById(R.id.description_text_view);
            discount = itemView.findViewById(R.id.discount_text_view);
            removeImageView = itemView.findViewById(R.id.remove_img);
            editImageView = itemView.findViewById(R.id.edit_img);

        }
    }
}
