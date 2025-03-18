package zaynoun.ul.bbackapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import zaynoun.ul.bbackapplication.CustomerService;
import zaynoun.ul.bbackapplication.ProfileActivity;
import zaynoun.ul.bbackapplication.activities.WelcomeActivity;
import zaynoun.ul.bbackapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ProgressBar progressBar;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding the layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences prefs = getSharedPreferences("BBackPrefs", MODE_PRIVATE);
        boolean accepted = prefs.getBoolean("AcceptedTerms", false);

        if (!accepted) {
            startActivity(new Intent(MainActivity.this, TermsAndConditionsActivity.class));
            finish(); // Prevent skipping terms
        }
        // Set up the toolbar
        setSupportActionBar(binding.appBarMain.toolbar);

        // Drawer and Navigation View setup
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Initialize ProgressBar and Logout Button
        progressBar = findViewById(R.id.loading2);
        buttonLogout = findViewById(R.id.log_out);

        // Configure AppBarConfiguration with navigation destinations
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_daily_meal, R.id.nav_favourite, R.id.nav_my_cart,R.id.nav_my_favourites)
                .setOpenableLayout(drawer)
                .build();

        // Set up Navigation Controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Set Logout Button click listener
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Show the progress bar
                                progressBar.setVisibility(View.VISIBLE);

                                // Clear SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear(); // Clears all stored preferences
                                editor.apply();

                                // Redirect to WelcomeActivity
                                Intent logoutIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                                startActivity(logoutIntent);
                                finish(); // Close the MainActivity
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                // Show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if present
        getMenuInflater().inflate(R.menu.main, menu);
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user"); // Default to "user"

        // Find the adminDashboard menu item
        MenuItem adminMenuItem = menu.findItem(R.id.adminDashBoard);

        // Show or hide the adminDashboard based on the role
        if ("admin".equals(role)) {
            adminMenuItem.setVisible(true);
        } else {
            adminMenuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Handle Action Bar item clicks
        if (id == R.id.action_profile) {
            Intent profileIntent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(profileIntent);
        } else if (id == R.id.action_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Confirm Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Clear SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear(); // Clears all stored preferences
                            editor.apply();

                            // Redirect to WelcomeActivity
                            Intent logoutIntent = new Intent(MainActivity.this, WelcomeActivity.class);
                            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                            startActivity(logoutIntent);
                            finish(); // Close the MainActivity
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    });

            // Show the AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (id == R.id.action_customer_service) {
            Intent serviceIntent = new Intent(MainActivity.this, CustomerService.class);
            startActivity(serviceIntent);
        } else if (id == R.id.adminDashBoard) {
            // Redirect to AdminDashboardActivity
            Intent adminDashboardIntent = new Intent(MainActivity.this, AdminDashboardActivity.class);
            startActivity(adminDashboardIntent);

        }else if(id == R.id.settings){
            //Redirect to SettingsActivity
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }else if (id == R.id.send_your_feedback){
            //Redirect to SendFeedbackActivity
            Intent sendFeedbackIntent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(sendFeedbackIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true); // Minimize the app instead of going back to LoginActivity
    }
}
