package zaynoun.ul.bbackapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import zaynoun.ul.bbackapplication.activities.WelcomeActivity;

public class TermsAndConditionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        TextView tvTerms = findViewById(R.id.tvTerms);
        Button btnAccept = findViewById(R.id.btnAccept);

        // Set Terms and Conditions Text
        String termsText = "TERMS AND CONDITIONS\n\n"
                + "Last Updated: [31/1/2025]\n\n"
                + "Welcome to **B.Back Restaurant App**! By using this app, you agree to the following terms and conditions. If you do not agree, please refrain from using the app.\n\n"
                + "**1. Acceptance of Terms**\n"
                + "By downloading and using the **B.Back Restaurant App**, you accept these Terms and Conditions. If you disagree with any part of these terms, do not proceed with using the app.\n\n"
                + "**2. Use of the App**\n"
                + "The app allows you to place food orders, make reservations, receive promotions, and contact customer service. It is your responsibility to use the app for lawful purposes and to avoid unauthorized access or misuse of the app’s features.\n\n"
                + "**3. User Accounts**\n"
                + "To use certain features, you may need to create an account. You are responsible for maintaining the confidentiality of your login credentials and any activity that occurs under your account. If you suspect any unauthorized use, please contact us immediately.\n\n"
                + "**4. Orders & Reservations**\n"
                + "All orders and reservations made via the app are subject to availability. Once an order is placed, it is final and cannot be changed unless the restaurant is notified within a reasonable time. We reserve the right to cancel or modify orders at our discretion.\n\n"
                + "**5. Payments & Refunds**\n"
                + "Payments can be made using **cash on delivery**, **credit/debit cards**, or **online payment gateways**. Refunds will only be processed if the order is incorrect or not delivered as agreed. In such cases, please contact **B.Back Customer Support** within **24 hours** of receiving your order.\n\n"
                + "**6. Delivery & Pickup**\n"
                + "Delivery times are estimates, and while we strive for timely deliveries, they may vary based on traffic and order volume. For pickup orders, please arrive at the restaurant within 15 minutes of the specified pickup time to avoid any delays.\n\n"
                + "**7. Promotions & Offers**\n"
                + "We may offer discounts, special deals, or loyalty programs through the app. These offers are subject to availability and may change without notice. Ensure you check the validity of each offer before use.\n\n"
                + "**8. Privacy and Data Protection**\n"
                + "We value your privacy and are committed to protecting your personal data. Please review our **Privacy Policy** to learn how we collect, store, and process your personal information.\n\n"
                + "**9. Liability Limitations**\n"
                + "While we take every precaution to ensure the app’s functionality, **B.Back Restaurant** is not liable for any direct, indirect, or consequential damages arising from the use of the app or any failure in the app’s services. We also do not guarantee that the app will be free from interruptions or errors.\n\n"
                + "**10. Termination**\n"
                + "We reserve the right to suspend or terminate your access to the app if you violate any of these terms. We may also update these terms from time to time, and your continued use of the app constitutes acceptance of those changes.\n\n"
                + "**11. Governing Law**\n"
                + "These terms shall be governed by and construed in accordance with the laws of Lebanon. Any disputes arising from these terms will be resolved in the courts of Lebanon.\n\n"
                + "**12. Contact Information**\n"
                + "If you have any questions or concerns about these terms, please reach out to us via:\n"
                + "📧 Email: bbackrestaurant@gmail.com\n"
                + "📞 Phone: +961 76 433 009\n\n";

        tvTerms.setText(termsText);

        // Handle Accept Button Click
        btnAccept.setOnClickListener(view -> {
            // Save agreement to SharedPreferences
            SharedPreferences prefs = getSharedPreferences("BBackPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("AcceptedTerms", true);
            editor.apply();

            // Redirect to Main Activity
            Intent intent = new Intent(TermsAndConditionsActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
