package zaynoun.ul.bbackapplication.ui.dailymeal;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.adapters.DailyMealAdapter;
import zaynoun.ul.bbackapplication.models.DailyMealModel;

public class DailyMealFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<DailyMealModel> DailyMealModels;
    DailyMealAdapter dailyMealAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.daily_meal_fragment, container,false);
        recyclerView = root.findViewById(R.id.daily_meal_rec);
        fab = root.findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String role = getRoleFromSharedPreferences();
        setFabVisibility(role);
        DailyMealModels= new ArrayList<>();
        DailyMealModels.add(new DailyMealModel(R.drawable.breakfast,"Breakfast","30% OFF","breakfast","Description of Breakfast"));
        DailyMealModels.add(new DailyMealModel(R.drawable.lunch,"Lunch","20% OFF","lunch","Description of Lunch"));
        DailyMealModels.add(new DailyMealModel(R.drawable.dinner,"Dinner","30% OFF","dinner","Description of Dinner"));
        DailyMealModels.add(new DailyMealModel(R.drawable.sweets,"Sweets","50% OFF","sweets","Description of sweets"));
        DailyMealModels.add(new DailyMealModel(R.drawable.coffe,"Coffee","40% OFF","coffee","Description of Coffee"));
        dailyMealAdapter = new DailyMealAdapter(getContext(),DailyMealModels);
        recyclerView.setAdapter(dailyMealAdapter);
        dailyMealAdapter.notifyDataSetChanged();
        return root;
    }
    private String getRoleFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppName", requireActivity().MODE_PRIVATE);
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
            fab.setVisibility(View.VISIBLE); // Show FAB for admin
            Log.d(TAG, "FAB is visible for admin.");
        } else {
            fab.setVisibility(View.GONE); // Hide FAB for non-admin
            Log.d(TAG, "FAB is hidden for non-admin.");
        }
    }
}