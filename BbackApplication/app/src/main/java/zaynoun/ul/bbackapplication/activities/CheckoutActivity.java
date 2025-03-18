package zaynoun.ul.bbackapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import zaynoun.ul.bbackapplication.MainActivity;
import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.ViewModel.CartViewModel;

public class CheckoutActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;
    ImageView backArrowImageView;
    private TextInputEditText etName, etEmail, etAddress, etCardNumber, etExpiry, etCvv,etPhone;
    private TextView tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);


        // Get total price from intent
        double total = getIntent().getDoubleExtra("total", 0.00);
        tvTotalPrice = findViewById(R.id.total_price);
        tvTotalPrice.setText(String.format("Total: $%.2f", total));
        SharedPreferences sharedPreferences= getSharedPreferences("MyAppName",MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        initializeViews();
        etEmail.setText(email);
        backArrowImageView = findViewById(R.id.CheckOutBackArrowBack);
        backArrowImageView.setOnClickListener(v -> finish());
        setupCheckoutButton();
    }

    private void initializeViews() {
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etCardNumber = findViewById(R.id.et_card_number);
        etExpiry = findViewById(R.id.et_expiry);
        etCvv = findViewById(R.id.et_cvv);
        etPhone=findViewById(R.id.et_phone);
    }

    private void setupCheckoutButton() {
        Button btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(v ->
                processPayment());
    }


    private boolean validateFields() {
        boolean isValid = true;

        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Name is required");
            isValid = false;
        }

        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Email is required");
            isValid = false;
        }
        if (etPhone.getText().toString().trim().isEmpty()) {
            etEmail.setError("Phone is required");
            isValid = false;
        }

        if (etAddress.getText().toString().trim().isEmpty()) {
            etAddress.setError("Address is required");
            isValid = false;
        }

        if (etCardNumber.getText().toString().trim().length() != 16) {
            etCardNumber.setError("Invalid card number");
            isValid = false;
        }

        if (etExpiry.getText().toString().trim().length() != 5) {
            etExpiry.setError("MM/YY format required");
            isValid = false;
        }

        if (etCvv.getText().toString().trim().length() != 3) {
            etCvv.setError("3 digits required");
            isValid = false;
        }

        return isValid;
    }

    private void processPayment() {
        // Simulate payment processing
        Toast.makeText(this, "Payment processed successfully!", Toast.LENGTH_SHORT).show();
        insertData();

        // Clear the cart
        cartViewModel.clearCart();

        // Navigate back to main screen
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
            private void insertData() {
                String url = "http://172.20.10.14/Bback_application/insert_location.php";

                if (validateFields()) {
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    response = response.trim(); // Trim any extra whitespace or newlines

                                    // Log the response for debugging
                                    Log.d("Volley Response", response);

                                    // Check the response from the server
                                    if (response.equalsIgnoreCase("Data inserted successfully")) {
                                        Toast.makeText(CheckoutActivity.this, "Thanks for Ordering!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CheckoutActivity.this, "Failed to insert data: " + response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error response
                                    String errorMessage = error.networkResponse != null
                                            ? new String(error.networkResponse.data) // Parse network error if available
                                            : error.toString(); // General error message
                                    Log.e("Volley Error", errorMessage); // Log the error
                                    Toast.makeText(CheckoutActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("FullName", etName.getText().toString().trim());
                            params.put("Email", etEmail.getText().toString().trim());
                            params.put("Phone", etPhone.getText().toString().trim());
                            params.put("Location", etAddress.getText().toString().trim());
                            return params;
                        }
                    };

                    // Add retry policy to handle server timeouts
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            5000, // Timeout in ms
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    // Add the request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(CheckoutActivity.this);
                    requestQueue.add(request);
                }
            }
        }