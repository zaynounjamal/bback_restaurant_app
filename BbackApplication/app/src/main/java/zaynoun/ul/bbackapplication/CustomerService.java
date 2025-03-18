package zaynoun.ul.bbackapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomerService extends AppCompatActivity {
    Button dialButton,chatButton,instagramButton,facebookbutton,locationbutton,bbackmenubutton;
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_service);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dialButton = findViewById(R.id.DialButton);
        bbackmenubutton = findViewById(R.id.Menu);
        chatButton = findViewById(R.id.ChatButton);
        instagramButton = findViewById(R.id.InstagramButton);
        facebookbutton = findViewById(R.id.FaceBookButton);
        locationbutton = findViewById(R.id.Location);
        backImage = findViewById(R.id.backCustomerService);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(CustomerService.this,MainActivity.class);
                startActivity(backIntent);
            }
        });

      dialButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Uri phoneNumber=Uri.parse("tel:05433009");
              Intent dialIntent=new Intent(Intent.ACTION_DIAL,phoneNumber);
              try {
                  startActivity(dialIntent);
              }catch (ActivityNotFoundException e) {
                  e.printStackTrace();
              }
          }
      });

      chatButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String url = "https://api.whatsapp.com/send?phone=96176433009";
              Intent i = new Intent(Intent.ACTION_VIEW);
              i.setData(Uri.parse(url));
              startActivity(i);
          }
      });
      instagramButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String url = "https://www.instagram.com/b.back.restaurant?igsh=OXdiYXE2YWk5cjZ6";
              Intent i = new Intent(Intent.ACTION_VIEW);
              i.setData(Uri.parse(url));
              try{
                  startActivity(i);
              }
              catch (ActivityNotFoundException e){
                  e.printStackTrace();
              }
              }
          });
      facebookbutton.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v) {
              String url = "https://www.facebook.com/share/1ArtRghgeW/?mibextid=wwXIfr";
              Intent i = new Intent(Intent.ACTION_VIEW);
              i.setData(Uri.parse(url));
              try{
                  startActivity(i);
              }
              catch (ActivityNotFoundException e){
                  e.printStackTrace();
              }
          }
      });
      locationbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Uri uri = Uri.parse("https://maps.app.goo.gl/52vUrDr678UzxaSw5?g_st=com.google.maps.preview.copy");
              Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
              try {
                  startActivity(mapIntent);
              } catch (ActivityNotFoundException e) {
                  e.printStackTrace();
              }
          }
      });
      bbackmenubutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Uri menuUri=  Uri.parse("https://rest.thrubits.com/restaurant/b-back");
              Intent menuIntent= new Intent(Intent.ACTION_VIEW,menuUri);
              try {
                  startActivity(menuIntent);
              } catch (ActivityNotFoundException e) {
                  e.printStackTrace();
              }
          }
      });
    }
}