package zaynoun.ul.bbackapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import zaynoun.ul.bbackapplication.activities.RegisterActivity;
import zaynoun.ul.bbackapplication.activities.WelcomeActivity;

public class AdminDashboardActivity extends AppCompatActivity {
    ImageView pendingImageView,BackArrowButton,CompletedOrderButton;
    ConstraintLayout addItemLayout,MenuItemsLayout,ProfileLayout,AddUserLayout,orderDispatchLayout,LogOutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CompletedOrderButton=findViewById(R.id.CompletedOrderIcon);
        BackArrowButton=findViewById(R.id.dashboardBackArrow);
        pendingImageView=findViewById(R.id.pendingOrderIcon);
        addItemLayout=findViewById(R.id.admin_add_item);
        MenuItemsLayout=findViewById(R.id.admin_view_all_items_menu);
        ProfileLayout=findViewById(R.id.admin_see_profile_con);
        AddUserLayout=findViewById(R.id.admin_create_user_con);
        orderDispatchLayout=findViewById(R.id.admin_order_dispatch_con);
        LogOutLayout=findViewById(R.id.admin_logout_con);
        CompletedOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent completedIntent = new Intent(AdminDashboardActivity.this, CompletedOrdersActivity.class);
                startActivity(completedIntent);
            }
        });
        BackArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pendingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingIntent = new Intent(AdminDashboardActivity.this, PendingItemsActivity.class);
                startActivity(pendingIntent);
            }
        });
        addItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(AdminDashboardActivity.this, UploadHomeItemsActivity.class);
                startActivity(addItemIntent);
            }
        });
        MenuItemsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuItemsIntent = new Intent(AdminDashboardActivity.this, MainActivity.class);
                startActivity(menuItemsIntent);
            }
        });
        ProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(AdminDashboardActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        });
        AddUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addUserIntent = new Intent(AdminDashboardActivity.this, RegisterAdminActivity.class);
                startActivity(addUserIntent);
            }
        });
        orderDispatchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderDispatchIntent = new Intent(AdminDashboardActivity.this, OrderDispatchActivity.class);
                startActivity(orderDispatchIntent);
            }
        });
        LogOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboardActivity.this);
                builder.setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Clear SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear(); // Clears all stored preferences
                                editor.apply();

                                // Navigate to WelcomeActivity
                                Intent logoutIntent = new Intent(AdminDashboardActivity.this, WelcomeActivity.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(logoutIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                // Show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}