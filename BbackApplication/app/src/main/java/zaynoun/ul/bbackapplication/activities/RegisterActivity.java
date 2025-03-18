package zaynoun.ul.bbackapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.TermsAndConditionsActivity;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private TextView nametv, emailtv, passwordtv, textViewError;
    private Button buttonSubmit;
    private ProgressBar progressBar;
    private String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Adjust padding for edge-to-edge screens
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefs = getSharedPreferences("BBackPrefs", MODE_PRIVATE);
        boolean accepted = prefs.getBoolean("AcceptedTerms", false);

        if (!accepted) {
            startActivity(new Intent(RegisterActivity.this, TermsAndConditionsActivity.class));
            finish(); // Prevent skipping terms
        }
        // Initialize views
        nametv = findViewById(R.id.name_edit_text);
        emailtv = findViewById(R.id.email_edit_text);
        passwordtv = findViewById(R.id.password_edit_text);
        textViewError = findViewById(R.id.error);
        buttonSubmit = findViewById(R.id.button_register);
        progressBar = findViewById(R.id.loading);

        buttonSubmit.setOnClickListener(v -> {
            textViewError.setVisibility(View.GONE);
            name = nametv.getText().toString().trim();
            email = emailtv.getText().toString().trim();
            password = passwordtv.getText().toString().trim();

            if (validateInput()) {
                registerUser();
            }
        });
    }

    private boolean validateInput() {
        if (name.isEmpty()) {
            textViewError.setText("Name is required");
            textViewError.setVisibility(View.VISIBLE);
            return false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textViewError.setText("Valid email is required");
            textViewError.setVisibility(View.VISIBLE);
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            textViewError.setText("Password must be at least 6 characters");
            textViewError.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void registerUser() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://172.20.10.14/Bback_application/registration.php";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "Response: " + response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intentLogin);
                            finish();
                        } else {
                            textViewError.setText(message);
                            textViewError.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON parsing error", e);
                        textViewError.setText("Unexpected response from server");
                        textViewError.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "Volley error: ", error);
                    String errorMessage = (error.getLocalizedMessage() != null)
                            ? error.getLocalizedMessage()
                            : "Timeout or network issue.";
                    textViewError.setText("Error: " + errorMessage);
                    textViewError.setVisibility(View.VISIBLE);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("name", name);
                paramV.put("email", email);
                paramV.put("password", password);
                return paramV;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, // 10 seconds timeout
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Default retry count
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT // Backoff multiplier
        ));

        queue.add(stringRequest);
    }

    public void login(View v) {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}
