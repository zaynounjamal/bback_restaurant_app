package zaynoun.ul.bbackapplication.ui.favourite;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Map;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.fragments.FragmentAdapter;

public class FavouriteFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
    FloatingActionButton fab;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourite, container,false);
        tabLayout=root.findViewById(R.id.tab_layout);
        viewPager2=root.findViewById(R.id.view_pager2);
        fab=root.findViewById(R.id.fab);
        String role=getRoleFromSharedPreferences();
        setFabVisibility(role);

        FragmentManager fm;
        fm = getActivity().getSupportFragmentManager();
        fragmentAdapter=new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Featured"));
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
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