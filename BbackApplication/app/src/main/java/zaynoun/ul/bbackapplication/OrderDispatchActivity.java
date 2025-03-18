package zaynoun.ul.bbackapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zaynoun.ul.bbackapplication.adapters.DispatchOrderAdapter;
import zaynoun.ul.bbackapplication.models.DispatchOrderModel;

public class OrderDispatchActivity extends AppCompatActivity {
    ImageView backAdminButton;
    RecyclerView recyclerViewDispatch;
    ArrayList<DispatchOrderModel> orderList;
    DispatchOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_dispatch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.DispatchMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backAdminButton=findViewById(R.id.backAdmin);
        backAdminButton.setOnClickListener(v -> finish());
        recyclerViewDispatch = findViewById(R.id.recycler_dispatch_orders_item);
        orderList = new ArrayList<>();
        orderList.add(new DispatchOrderModel(R.drawable.not_delivered, "Zaynoun", "Received"));
        orderList.add(new DispatchOrderModel(R.drawable.not_delivered, "mohamad", "Not Received"));
        orderList.add(new DispatchOrderModel(R.drawable.not_delivered, "bilal", "Received"));
        adapter = new DispatchOrderAdapter(orderList, this);
        recyclerViewDispatch.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerViewDispatch.setAdapter(adapter);

    }
}