package in.catking.gkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.catking.gkapp.utils.GifImageView;

public class spalsh_screen extends AppCompatActivity {
    TextView sending_View;
    Button reMOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_nogif);

        Intent myO = getIntent();
        final String MOBI = myO.getStringExtra("mob");
        sending_View =(TextView)findViewById(R.id.sending_to_OTP);
        sending_View.setText("Please wait while we send your\n" +
                "OTP on your Mobile No. \n"+ MOBI);

        reMOB = (Button)findViewById(R.id.changeNo_splash);
        reMOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login_Mobile.class);
                startActivity(i);
            }
        });
    }
}