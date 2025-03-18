package zaynoun.ul.bbackapplication.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import zaynoun.ul.bbackapplication.R;

public class LocationActivity extends AppCompatActivity {
    String[] locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Dropdown setup
        AutoCompleteTextView locationDropdown = findViewById(R.id.locationDropdown);
        String[] locations = {"Jaipur", "Mumbai", "Delhi", "Bangalore", "Chennai"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, locations);
        locationDropdown.setAdapter(adapter);

        // Set default value
        locationDropdown.setText("Jaipur", false);
    }
}