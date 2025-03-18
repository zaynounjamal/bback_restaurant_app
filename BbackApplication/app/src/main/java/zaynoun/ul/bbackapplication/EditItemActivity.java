package zaynoun.ul.bbackapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditItemActivity extends AppCompatActivity {

    private ImageView backArrow;
    private Button updateButton;
    private EditText etName, etDescription, etPrice, etRating, etTiming,etImageUrl;
    private Uri newImageUri; // Optional, if user wants to change the image
    // Assume the item id is passed via Intent extras
    private String itemId;
    private static final String UPDATE_URL = "http://172.20.10.14/Bback_application/update_item.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        // Initialize views (reuse the same layout as AddItemActivity if desired)
        backArrow = findViewById(R.id.editBackArrow);
        updateButton = findViewById(R.id.saveButton);
        etName = findViewById(R.id.editName);
        etDescription = findViewById(R.id.editDescription);
        etPrice = findViewById(R.id.editPrice);
        etRating = findViewById(R.id.editRating);
        etTiming = findViewById(R.id.editTiming);
        etImageUrl = findViewById(R.id.edit_image_url);

        // Retrieve item data from Intent extras
        Intent intent = getIntent();
        itemId = intent.getStringExtra("id");
        etName.setText(intent.getStringExtra("name"));
        etDescription.setText(intent.getStringExtra("description"));
        etPrice.setText(intent.getStringExtra("price"));
        etRating.setText(intent.getStringExtra("rating"));
        etTiming.setText(intent.getStringExtra("timing"));
        etImageUrl.setText(intent.getStringExtra("image_url"));

        backArrow.setOnClickListener(v -> finish());


        updateButton.setOnClickListener(v -> {
            // Get input values
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String rating = etRating.getText().toString().trim();
            String timing = etTiming.getText().toString().trim();
            String newImageUrl = etImageUrl.getText().toString().trim();
            String category = "Pizza"; // Replace with the selected category from the UI

            // Validate inputs
            if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty()
                    || rating.isEmpty() || timing.isEmpty() || newImageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!URLUtil.isValidUrl(newImageUrl)) {
                etImageUrl.setError("Invalid URL format");
                return;
            }

            try {
                // Convert price to double
                double price = Double.parseDouble(priceStr);

                // Create request parameters
                Map<String, String> params = new HashMap<>();
                params.put("id", itemId);
                params.put("name", name);
                params.put("description", description);
                params.put("price", priceStr);
                params.put("rating", rating);
                params.put("timing", timing);
                params.put("image_url", newImageUrl);
                params.put("category", category); // Add category

                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                        response -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (jsonResponse.getString("status").equals("success")) {
                                    handleUpdateSuccess();
                                } else {
                                    Toast.makeText(this, jsonResponse.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                handleUpdateError(new VolleyError("Invalid server response"));
                            }
                        },
                        error -> handleUpdateError(error)) {
                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }
                };

                Volley.newRequestQueue(this).add(stringRequest);

            } catch (NumberFormatException e) {
                etPrice.setError("Invalid price format");
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void handleUpdateSuccess() {
        // Show success message
        Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show();

        // Set result to refresh parent activity
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);

        // Close the activity
        finish();
    }

    private void handleUpdateError(VolleyError error) {
        // Handle different error types
        if (error instanceof NetworkError) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }

        // Log the error
        Log.e("UpdateError", "Error: " + error.getMessage());
    }
}