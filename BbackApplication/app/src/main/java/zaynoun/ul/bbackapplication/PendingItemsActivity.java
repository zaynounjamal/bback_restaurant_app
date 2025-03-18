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
import java.util.List;

import zaynoun.ul.bbackapplication.adapters.PendingItemAdapter;
import zaynoun.ul.bbackapplication.models.PendingItemModel;

public class PendingItemsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PendingItemModel> pendingItemModels;
    ImageView backImageView;
    PendingItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pending_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pendingMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backImageView=findViewById(R.id.backAdmin);
        backImageView.setOnClickListener(view -> finish());
        recyclerView=findViewById(R.id.recycler_pending_item);
        pendingItemModels= new ArrayList<>();
        pendingItemModels.add(new PendingItemModel(R.drawable.ic_baseline_person_24,"Zaynoun"));
        pendingItemModels.add(new PendingItemModel(R.drawable.ic_baseline_person_24,"Abdelaziz"));
        pendingItemModels.add(new PendingItemModel(R.drawable.ic_baseline_person_24,"Mohammed"));
        adapter=new PendingItemAdapter(pendingItemModels,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

    }
}