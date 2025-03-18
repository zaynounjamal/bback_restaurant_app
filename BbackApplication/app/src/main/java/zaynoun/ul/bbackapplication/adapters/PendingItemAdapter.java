package zaynoun.ul.bbackapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zaynoun.ul.bbackapplication.AdminDashboardActivity;
import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.PendingItemModel;

public class PendingItemAdapter extends RecyclerView.Adapter<PendingItemAdapter.ViewHolder>{
    List<PendingItemModel> pending;
    Context context;
    public PendingItemAdapter(List<PendingItemModel> pending, Context context) {
        this.pending = pending;
        this.context = context;
    }
    @NonNull
    @Override
    public PendingItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_items_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingItemAdapter.ViewHolder holder, int position) {
        PendingItemModel item=pending.get(position);
         holder.personImageView.setImageResource(item.getImage());
         holder.personNameTextView.setText(item.getName());
         holder.ViewMoreButton.setOnClickListener(view -> {
             // TODO: Show order details
         });
         holder.AcceptButtonHolder.setOnClickListener(view -> {
             // TODO: Accept order
         });
         holder.RejectButtonHolder.setOnClickListener(view -> {
             // TODO: Reject order
         });
    }

    @Override
    public int getItemCount() {
        return pending.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView personImageView;
        TextView personNameTextView;
        Button ViewMoreButton,AcceptButtonHolder,RejectButtonHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            personImageView=itemView.findViewById(R.id.pending_img);
            personNameTextView=itemView.findViewById(R.id.pending_text);
            ViewMoreButton=itemView.findViewById(R.id.view_order_details);
            AcceptButtonHolder=itemView.findViewById(R.id.accept_order);
            RejectButtonHolder=itemView.findViewById(R.id.reject_order);
        }
    }
}
