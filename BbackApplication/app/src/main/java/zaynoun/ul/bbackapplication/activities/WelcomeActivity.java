package zaynoun.ul.bbackapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.TermsAndConditionsActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefs = getSharedPreferences("BBackPrefs", MODE_PRIVATE);
        boolean accepted = prefs.getBoolean("AcceptedTerms", false);

        if (!accepted) {
            startActivity(new Intent(WelcomeActivity.this, TermsAndConditionsActivity.class));
            finish(); // Prevent skipping terms
        }
    }
    public void Register(View v){
        Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    public void login(View v){
        Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}