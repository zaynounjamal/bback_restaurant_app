package zaynoun.ul.bbackapplication.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import zaynoun.ul.bbackapplication.NotificationActivity;
import zaynoun.ul.bbackapplication.ProfileActivity;
import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.UploadHomeItemsActivity;
import zaynoun.ul.bbackapplication.adapters.HomeHorAdapter;
import zaynoun.ul.bbackapplication.adapters.HomeVerAdapter;
import zaynoun.ul.bbackapplication.adapters.UpdateVerticalRec;
import zaynoun.ul.bbackapplication.models.HomeHorModel;
import zaynoun.ul.bbackapplication.models.HomeVerModel;
import zaynoun.ul.bbackapplication.ViewModel.CartViewModel;

public class HomeFragment extends Fragment implements UpdateVerticalRec {

    private FloatingActionButton fab;
    private TextView nameTextView;
    private ImageView profileIcon, notificationIcon;
    private RecyclerView homeHorizontalRec, homeVerticalRec;

    private ArrayList<HomeHorModel> homeHorModelList;
    private HomeHorAdapter homeHorAdapter;
    private ArrayList<HomeVerModel> homeVerticalModelList;
    private HomeVerAdapter homeVerAdapter;
    private CartViewModel cartViewModel;
    String GET_ITEMS_URL="http://172.20.10.14/Bback_application/get_items.php";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ViewModel
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        initializeViews(root);
        setupUserInfo();
        setupFAB();
        setupRecyclerViews();

        return root;
    }

    private void initializeViews(View root) {
        fab = root.findViewById(R.id.fab);
        profileIcon = root.findViewById(R.id.profile_icon);
        notificationIcon = root.findViewById(R.id.notification_icon);
        nameTextView = root.findViewById(R.id.hello_user);
        homeHorizontalRec = root.findViewById(R.id.home_hor_rec);
        homeVerticalRec = root.findViewById(R.id.home_ver_rec);
    }

    private void setupUserInfo() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "User");
        nameTextView.setText("Hello " + name);

        profileIcon.setOnClickListener(v -> startActivity(new Intent(requireActivity(), ProfileActivity.class)));
        notificationIcon.setOnClickListener(v -> startActivity(new Intent(requireActivity(), NotificationActivity.class)));
    }

    private void setupFAB() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        String role = prefs.getString("role", "user");

        if ("admin".equalsIgnoreCase(role)) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> startActivity(new Intent(getActivity(), UploadHomeItemsActivity.class)));
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerViews() {
        // Horizontal RecyclerView
        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel("Pizza", R.drawable.pizza));
        homeHorModelList.add(new HomeHorModel("HamBurger", R.drawable.hamburger));
        homeHorModelList.add(new HomeHorModel("Fries", R.drawable.fried_potatoes));
        homeHorModelList.add(new HomeHorModel("Sandwich", R.drawable.sandwich));
        homeHorModelList.add(new HomeHorModel("Mashawi", R.drawable.sandwich1));

        // Corrected adapter initialization
        homeHorAdapter = new HomeHorAdapter(homeHorModelList, this, requireActivity());
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeHorizontalRec.setAdapter(homeHorAdapter);

        // Vertical RecyclerView
        homeVerticalModelList = new ArrayList<>();
        homeVerAdapter = new HomeVerAdapter(requireActivity(), homeVerticalModelList, cartViewModel);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homeVerticalRec.setAdapter(homeVerAdapter);

    // Vertical RecyclerView
        homeVerticalModelList = new ArrayList<>();
        homeVerAdapter = new HomeVerAdapter(getActivity(), homeVerticalModelList, cartViewModel);
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        homeVerticalRec.setAdapter(homeVerAdapter);
        fetchItems("all");
    }
    @Override
    public void callBack(int position, String category) {
        fetchItems(category.toLowerCase());
    }
    @Override
    public void callBack(int position, ArrayList<HomeVerModel> list) {
        homeVerAdapter.updateList(list);
        homeVerticalRec.scheduleLayoutAnimation();
        homeVerAdapter.notifyDataSetChanged();
    }
    private void fetchItems(String category) {
        String url = GET_ITEMS_URL + "?category=" + category;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getString("status").equals("success")) {
                            JSONArray data = jsonResponse.getJSONArray("data");
                            List<HomeVerModel> items = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                items.add(new HomeVerModel(
                                        obj.getString("id"),
                                        obj.getString("image_url"),
                                        obj.getString("name"),
                                        obj.getString("timing"),
                                        obj.getString("rating"),
                                        obj.getDouble("price")
                                ));
                            }

                            // Clear existing data and update
                            homeVerticalModelList.clear();
                            homeVerticalModelList.addAll(items);
                            homeVerAdapter.notifyDataSetChanged();
                            homeVerticalRec.scheduleLayoutAnimation();

                        } else {
                            Toast.makeText(getActivity(), "Error: " + jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("FetchError", "JSON error: " + e.getMessage());
                        Toast.makeText(getActivity(), "Data format error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("FetchError", "Volley error: " + error.toString());
                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(getActivity()).add(request);
    }
    // Replace existing onActivityResult with:
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            refreshCurrentCategory();
        }
    }

    private void refreshCurrentCategory() {
        if (homeHorAdapter != null) {
            int pos = homeHorAdapter.getSelectedPosition();
            String category = pos == 0 ? "all" : homeHorModelList.get(pos).getName().toLowerCase();
            fetchItems(category);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        if (homeHorAdapter != null) {
            int currentCategoryPosition = homeHorAdapter.getSelectedPosition();
            if (currentCategoryPosition >= 0 && currentCategoryPosition < homeHorModelList.size()) {
                String category = homeHorModelList.get(currentCategoryPosition).getName().toLowerCase();
                fetchItems(category);
            }
        }
    }

}
