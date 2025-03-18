package zaynoun.ul.bbackapplication.activities;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zaynoun.ul.bbackapplication.MainActivity;
import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.adapters.DetailedDailyAdapter;
import zaynoun.ul.bbackapplication.models.DetailedDailyModel;
import zaynoun.ul.bbackapplication.ui.Cart.CartFragment;
import zaynoun.ul.bbackapplication.ui.dailymeal.DailyMealFragment;

public class DetailedDailyMealActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView BackArrowImageView;
    FloatingActionButton CartButton, AddButton;
    List<DetailedDailyModel> detailedDailyModels;
    DetailedDailyAdapter detailedDailyAdapter;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed_daily_meal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CartButton=findViewById(R.id.cart);
        AddButton=findViewById(R.id.fab);
        String role=GetRoleFromSharedPreferences();
        setFabVisibility(role);
        CartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCartFragment();
            }
        });
        String type =getIntent().getStringExtra("type");
        BackArrowImageView = findViewById(R.id.backArrow);
        BackArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.detailed_rec);
        imageView = findViewById(R.id.ImageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailedDailyModels = new ArrayList<>();
        detailedDailyAdapter = new DetailedDailyAdapter(getApplicationContext(),detailedDailyModels);
        recyclerView.setAdapter(detailedDailyAdapter);
        if (type != null && type.equalsIgnoreCase("breakfast")){
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.fav1,"breakfast","4.4","description","4.0","10:00 - 23-:00"));
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.fav2,"breakfast","4.7","description","4.0","10:00 - 23-:00"));
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.fav3,"breakfast","4.0","description","4.0","10:00 - 23-:00"));
            detailedDailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("sweets")){
            imageView.setImageResource(R.drawable.sweets);
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.s1,"sweets","4.4","description","4.0","10:00 - 23-:00"));
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.s2,"sweets","4.0","description","4.0","10:00 - 23-:00"));
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.s3,"sweets","4.9","description","4.0","10:00 - 23-:00"));
            detailedDailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("coffee")){
            imageView.setImageResource(R.drawable.coffe);
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.coffe,"coffee","1.0","description","5.0","7:00 - 23-:00"));
            detailedDailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("lunch")){
            imageView.setImageResource(R.drawable.lunch);
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.lunch,"Lunch","4.4","description","4.3","10:00 - 23-:00"));
            detailedDailyAdapter.notifyDataSetChanged();
        }
        if (type != null && type.equalsIgnoreCase("Dinner")){
            imageView.setImageResource(R.drawable.dinner);
            detailedDailyModels.add(new DetailedDailyModel(R.drawable.dinner,"Dinner","4.9","description","3.0","10:00 - 23-:00"));
            detailedDailyAdapter.notifyDataSetChanged();
        }
    }
    public String GetRoleFromSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user");// Default to 'user'
        Log.d(TAG, "Role retrieved from SharedPreferences: " + role);

        // Log all SharedPreferences for debugging
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, entry.getKey() + ": " + entry.getValue());
        }

        return role;
    }
    private void setFabVisibility(String role) {
        if ("admin".equals(role)) {
            AddButton.setVisibility(View.VISIBLE); // Show FAB for admin
            Log.d(TAG, "FAB is visible for admin.");
        } else {
            AddButton.setVisibility(View.GONE); // Hide FAB for non-admin
            Log.d(TAG, "FAB is hidden for non-admin.");
        }
    }
    private void openCartFragment() {
        Fragment existingFragment = getSupportFragmentManager().findFragmentByTag("CART_FRAGMENT");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (existingFragment != null) {
            // Use the existing fragment
            transaction.replace(R.id.main, existingFragment);
        } else {
            // Otherwise, create a new one
            CartFragment cartFragment = new CartFragment();
            transaction.replace(R.id.main, cartFragment, "CART_FRAGMENT");
        }

        transaction.addToBackStack(null); // Allows back navigation
        transaction.commit();
    }
}