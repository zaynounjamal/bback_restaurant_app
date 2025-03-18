package zaynoun.ul.bbackapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.CompletedOrderItemsModel;

public class CompletedOrderItemsAdapter extends RecyclerView.Adapter<CompletedOrderItemsAdapter.ViewHolder> {
    List<CompletedOrderItemsModel> list;
    Context context;

    public CompletedOrderItemsAdapter(List<CompletedOrderItemsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CompletedOrderItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_order_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedOrderItemsAdapter.ViewHolder holder, int position) {
      CompletedOrderItemsModel item=list.get(position);
      holder.NameTextView.setText(item.getName());
      holder.PaymentTextView.setText(item.getPayment());
      holder.DeliveredImageView.setImageResource(item.getDeliveredImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView NameTextView,PaymentTextView;
        ImageView DeliveredImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTextView=itemView.findViewById(R.id.customer_complete_name);
            PaymentTextView=itemView.findViewById(R.id.payment_status);
            DeliveredImageView=itemView.findViewById(R.id.delivered_icon);
        }
    }
}
