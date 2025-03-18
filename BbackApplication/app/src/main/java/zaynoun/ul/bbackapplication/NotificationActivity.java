package zaynoun.ul.bbackapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.adapters.NotificationsAdapter;

public class NotificationActivity extends AppCompatActivity {

    private ListView notificationsListView;
    private ImageView NotificationBackArrow;
    private NotificationsAdapter notificationsAdapter;
    private ArrayList<String> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationsListView = findViewById(R.id.notifications_list);
        NotificationBackArrow = findViewById(R.id.notificationBackArrow);
        NotificationBackArrow.setOnClickListener(view -> finish());

        // For demonstration, add some sample notifications
        notifications = new ArrayList<>();
        notifications.add("New order received!");
        notifications.add("Your meal is on the way.");
        notifications.add("Your table is ready!");

        notificationsAdapter = new NotificationsAdapter(this, notifications);
        notificationsListView.setAdapter(notificationsAdapter);
    }
}
