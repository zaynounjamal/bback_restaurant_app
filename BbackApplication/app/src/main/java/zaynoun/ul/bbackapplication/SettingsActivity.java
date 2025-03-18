package zaynoun.ul.bbackapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.File;

import zaynoun.ul.bbackapplication.activities.WelcomeActivity;
public class SettingsActivity extends AppCompatActivity {

    private Switch switchTheme, switchNotifications;
    private Button btnTermsAndConditions, btnClearCache, btnLogout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // Ensure to use the correct layout

        // Initialize views
        switchTheme = findViewById(R.id.switchTheme);
        switchNotifications = findViewById(R.id.switchNotifications);
        btnTermsAndConditions = findViewById(R.id.btnTermsAndConditions);
        btnClearCache = findViewById(R.id.btnClearCache);
        btnLogout = findViewById(R.id.btnLogout_Settings);

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        // Set initial states of switches based on saved preferences
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        switchTheme.setChecked(isDarkMode);
        AppCompatDelegate.setDefaultNightMode(isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        switchNotifications.setChecked(sharedPreferences.getBoolean("notifications", true)); // true = Notifications enabled

        // Back Button Logic (Finish Activity)
        findViewById(R.id.SettingsBackArrow).setOnClickListener(v -> finish());

        // Toggle theme mode based on switch
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();
        });

        // Handle Notifications Switch
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("notifications", isChecked);
            editor.apply();
            String message = isChecked ? "Notifications enabled" : "Notifications disabled";
            Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        // Handle Terms and Conditions Button
        btnTermsAndConditions.setOnClickListener(v -> {
            // Navigate to the Terms and Conditions screen (you can create a new activity or display a dialog)
            Toast.makeText(SettingsActivity.this, "Opening Terms and Conditions", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsActivity.this, TermsAndConditionsSettingsActivity.class);
            startActivity(intent);
        });

        // Handle Clear Cache Button
        btnClearCache.setOnClickListener(v -> clearCache());

        // Handle Logout Button with AlertDialog
        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        logout();
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss())
                    .show();
        });
    }

    // Method to clear cache
    private void clearCache() {
        try {
            // Clear application cache
            File cacheDir = getCacheDir();
            deleteDir(cacheDir);
            Toast.makeText(SettingsActivity.this, "Cache cleared", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SettingsActivity.this, "Error clearing cache", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to delete files recursively
    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // Method for logout
    private void logout() {
        // Clear user session or any data
        editor.clear();  // Clears all the data
        editor.apply();
        Toast.makeText(SettingsActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Optionally, navigate to Welcome activity
        Intent intent = new Intent(SettingsActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish(); // Close the current activity so the user cannot go back to it
    }
}
