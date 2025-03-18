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

import zaynoun.ul.bbackapplication.adapters.CompletedOrderItemsAdapter;
import zaynoun.ul.bbackapplication.models.CompletedOrderItemsModel;

public class CompletedOrdersActivity extends AppCompatActivity {
    ArrayList<CompletedOrderItemsModel> completedOrderItemsModels;
    RecyclerView recyclerView;
    CompletedOrderItemsAdapter completedOrdersAdapter;
    ImageView backAdminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_completed_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CompletedMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backAdminButton=findViewById(R.id.backAdmin);
        backAdminButton.setOnClickListener(v -> finish());
        recyclerView=findViewById(R.id.recycler_completed_orders_item);
        completedOrderItemsModels=new ArrayList<>();
        completedOrderItemsModels.add(new CompletedOrderItemsModel(R.drawable.delivered,"Zaynoun","Received"));
        completedOrderItemsModels.add(new CompletedOrderItemsModel(R.drawable.delivered,"Mahmoud","Not Received"));
        completedOrderItemsModels.add(new CompletedOrderItemsModel(R.drawable.delivered,"Jad","Received"));
        completedOrdersAdapter=new CompletedOrderItemsAdapter(completedOrderItemsModels,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(completedOrdersAdapter);
    }
}