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
import zaynoun.ul.bbackapplication.models.DispatchOrderModel;

public class DispatchOrderAdapter extends RecyclerView.Adapter<DispatchOrderAdapter.ViewHolder> {
    List<DispatchOrderModel> dispatchOrderModels;
    Context context;
    public DispatchOrderAdapter(List<DispatchOrderModel> dispatchOrderModels, Context context) {
        this.dispatchOrderModels = dispatchOrderModels;
        this.context = context;
    }
    @NonNull
    @Override
    public DispatchOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dispatch_order_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DispatchOrderAdapter.ViewHolder holder, int position) {
      DispatchOrderModel item=dispatchOrderModels.get(position);
      holder.DeliveredImageView.setImageResource(item.getImage());
      holder.Namedispatchorder.setText(item.getName());
      holder.PaymentDispatchOrder.setText(item.getPayment());
    }

    @Override
    public int getItemCount() {
        return dispatchOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Namedispatchorder,PaymentDispatchOrder;
        ImageView DeliveredImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Namedispatchorder = itemView.findViewById(R.id.customer_dispatch_name);
            PaymentDispatchOrder = itemView.findViewById(R.id.payment_status);
            DeliveredImageView = itemView.findViewById(R.id.delivered_icon);
        }
    }
}
