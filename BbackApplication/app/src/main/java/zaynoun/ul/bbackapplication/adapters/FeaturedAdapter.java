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
import zaynoun.ul.bbackapplication.models.FeaturedModel;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {
    List<FeaturedModel> list;
    Context context;
    public FeaturedAdapter(Context context,List<FeaturedModel> list ) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_hor_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.ViewHolder holder, int position) {
         holder.image.setImageResource(list.get(position).getImage());
         holder.name.setText(list.get(position).getName());
         holder.desc.setText(list.get(position).getDescription());
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
        ImageView image;
        ImageView removeImageView,EditImageView;
        TextView name,desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.featured_img);
            name = itemView.findViewById(R.id.featured_name);
            desc = itemView.findViewById(R.id.featured_des);
            removeImageView=itemView.findViewById(R.id.remove_img);
            EditImageView=itemView.findViewById(R.id.edit_img);
        }
    }
}
