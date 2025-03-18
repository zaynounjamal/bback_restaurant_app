package zaynoun.ul.bbackapplication;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;


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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import zaynoun.ul.bbackapplication.RoomDatabase.Entity.Food;
import zaynoun.ul.bbackapplication.RoomDatabase.MyRoomDatabase;
import zaynoun.ul.bbackapplication.ui.home.HomeFragment;

public class UploadHomeItemsActivity extends AppCompatActivity {
    private ImageView backArrow;
    private Button saveButton;
    private EditText etName, etDescription, etPrice, etRating, etTiming,etImageUrl;
    private Uri imageUri;
    private static final String UPLOAD_URL = "http://172.20.10.14/Bback_application/upload_item.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_home_items);
        // Initialize views
        backArrow = findViewById(R.id.uploadBackArrow);
        saveButton = findViewById(R.id.saveButton);
        etName = findViewById(R.id.uploadName);
        etDescription = findViewById(R.id.uploadDescription);
        etPrice = findViewById(R.id.uploadPrice);
        etRating = findViewById(R.id.uploadRating);
        etTiming = findViewById(R.id.uploadTiming);
        etImageUrl = findViewById(R.id.upload_image_url);
        backArrow.setOnClickListener(v -> finish());


        saveButton.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String rating = etRating.getText().toString().trim();
            String timing = etTiming.getText().toString().trim();
            String imageUrl = etImageUrl.getText().toString().trim();
            String category = "Pizza"; // Replace with the selected category from the UI

            if (name.isEmpty() || description.isEmpty() || price.isEmpty() ||
                    rating.isEmpty() || timing.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(UploadHomeItemsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!URLUtil.isValidUrl(imageUrl)) {
                etImageUrl.setError("Invalid URL format");
                return;
            }
            try {
                Double.parseDouble(price); // Validate numeric price
            } catch (NumberFormatException e) {
                etPrice.setError("Invalid price format");
                return;
            }

            // Create params map
            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("description", description);
            params.put("price", price);
            params.put("rating", rating);
            params.put("timing", timing);
            params.put("image_url", imageUrl);
            params.put("category", category); // Add category

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("success")) {
                                handleSuccess();
                            } else {
                                handleError(new VolleyError(jsonResponse.getString("message")));
                            }
                        } catch (JSONException e) {
                            handleError(new VolleyError("Invalid server response"));
                        }
                    },
                    this::handleError) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);
        });
    }
    private void handleSuccess() {
        // Clear input fields
        etName.setText("");
        etDescription.setText("");
        etPrice.setText("");
        etRating.setText("");
        etTiming.setText("");
        etImageUrl.setText("");

        // Show success message
        Toast.makeText(this, "Item uploaded successfully", Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void handleError(VolleyError error) {
        // Handle different error types
        if (error instanceof NetworkError) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show();
        }

        // Log the error
        Log.e("UploadError", "Error: " + error.getMessage());
    }
}