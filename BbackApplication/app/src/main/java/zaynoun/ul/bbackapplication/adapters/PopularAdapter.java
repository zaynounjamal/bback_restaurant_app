package zaynoun.ul.bbackapplication.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.FeaturedVerModel;
import zaynoun.ul.bbackapplication.models.PopularVerModal;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    List<PopularVerModal> list;
    Context context;
    public PopularAdapter(Context context, List<PopularVerModal> list){
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_ver,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.rating.setText(list.get(position).getRating());
        holder.timing.setText(list.get(position).getTiming());
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user");
        if ("admin".equals(role)) {
            holder.removeImageView.setVisibility(View.VISIBLE);
            holder.EditImageView.setVisibility(View.VISIBLE);

        } else {
            holder.removeImageView.setVisibility(View.GONE);
            holder.EditImageView.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView removeImageView,EditImageView;
        TextView name,description,rating,timing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailed_img);
            name=itemView.findViewById(R.id.detailed_name);
            description=itemView.findViewById(R.id.detailed_description);
            rating=itemView.findViewById(R.id.detailed_rating);
            timing=itemView.findViewById(R.id.detailed_timing);
            removeImageView=itemView.findViewById(R.id.remove_img);
            EditImageView=itemView.findViewById(R.id.edit_img);
        }
    }
}
