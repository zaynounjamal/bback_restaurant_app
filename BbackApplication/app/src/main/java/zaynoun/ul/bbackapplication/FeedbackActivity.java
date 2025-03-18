package zaynoun.ul.bbackapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import zaynoun.ul.bbackapplication.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feedbackInput;
    private Button submitButton;
    private ImageView feedBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackInput = findViewById(R.id.feedback_input);
        submitButton = findViewById(R.id.submit_feedback_button);
        feedBackArrow = findViewById(R.id.feedbackArrow);
        feedBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the keyboard for feedback input
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackInput.getText().toString();
                if (feedback.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please provide feedback", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle feedback submission (e.g., store it in a database, send it via email, etc.)
                    Toast.makeText(FeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                }
            }
        });
    }
}
