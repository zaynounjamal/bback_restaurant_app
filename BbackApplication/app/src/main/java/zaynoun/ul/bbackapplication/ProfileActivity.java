package zaynoun.ul.bbackapplication;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

import zaynoun.ul.bbackapplication.activities.WelcomeActivity;

public class ProfileActivity extends AppCompatActivity {

    Button logoutButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backButton=findViewById(R.id.backProfile);
        logoutButton=findViewById(R.id.LogOutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
                                Intent logoutIntent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                                startActivity(logoutIntent);
                                finish(); // Close the ProfileActivity
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
        String name=getNameFromSharedPreferences();
        String email=getEmailFromSharedPreferences();
        setEmail(email);
        setName(name);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String getNameFromSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "User");// Default to 'User'
        Log.d(TAG, "Name retrieved from SharedPreferences: " + name);

        // Log all SharedPreferences for debugging
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d(TAG, entry.getKey() + ": " + entry.getValue());
        }

        return name;
    }
    private String getEmailFromSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "User@example.com");// Default to 'User@example.com'
        Log.d(TAG, "Email retrieved from SharedPreferences: " + email);

        return email;
    }
    private void setEmail(String email) {
        TextView emailTv = findViewById(R.id.ProfileEmail);
        emailTv.setText(email);
    }
    private void setName(String name) {
        TextView nameTv = findViewById(R.id.ProfileName);
        nameTv.setText(name);
    }
}