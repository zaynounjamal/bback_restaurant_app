package zaynoun.ul.bbackapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zaynoun.ul.bbackapplication.MainActivity;
import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.TermsAndConditionsActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String SHARED_PREFS_NAME = "MyAppName";

    private TextView emailTextView, passwordTextView, errorTextView;
    private Button loginButton;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        initializeViews();

        // SharedPreferences for storing user session data
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("BBackPrefs", MODE_PRIVATE);
        boolean accepted = prefs.getBoolean("AcceptedTerms", false);

        if (!accepted) {
            startActivity(new Intent(LoginActivity.this, TermsAndConditionsActivity.class));
            finish(); // Prevent skipping terms
        }

        // Check if the user is already logged in
        if (isUserLoggedIn()) {
            navigateToMainActivity();
        }

        // Login button click listener
        loginButton.setOnClickListener(v -> performLogin());
    }

    private void initializeViews() {
        emailTextView = findViewById(R.id.email_edit_text);
        passwordTextView = findViewById(R.id.password_edit_text);
        errorTextView = findViewById(R.id.error);
        progressBar = findViewById(R.id.loading);
        loginButton = findViewById(R.id.button_sign_in);
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void performLogin() {
        errorTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        sendLoginRequest(email, password);
    }

    private void sendLoginRequest(String email, String password) {
        String url = "http://172.20.10.14/Bback_application/login.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> handleLoginResponse(response),
                error -> handleLoginError(error)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void handleLoginResponse(String response) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "Server response: " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");

            if (status.equals("success")) {
                String name = jsonObject.getString("name");
                String email = jsonObject.getString("email");
                String apiKey = jsonObject.getString("apiKey");
                String role = jsonObject.getString("role");

                Log.d(TAG, "Role retrieved from server: " + role);
                saveUserSession(name, email, apiKey, role);
                navigateToMainActivity();
            } else {
                showError(message);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing error: ", e);
            showError("An error occurred while processing the response.");
        }
    }

    private void handleLoginError(VolleyError error) {
        progressBar.setVisibility(View.GONE);
        Log.e(TAG, "Volley error: ", error);
        showError("Unable to connect. Please try again later.");
    }

    private void saveUserSession(String name, String email, String apiKey, String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("apiKey", apiKey);
        editor.putString("role", role); // Save the role
        editor.apply();

        Log.d(TAG, "User session saved. Role: " + role);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setText(message);
        errorTextView.setVisibility(View.VISIBLE);
    }
}