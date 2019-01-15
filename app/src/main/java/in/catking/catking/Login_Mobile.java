package in.catking.catking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import in.catking.catking.spalsh_screen;

public class Login_Mobile extends AppCompatActivity {
    EditText mobile_number;
    String mobileText;
    Button getOTP;
    final String OTP_URL = "http://control.msg91.com/api/sendotp.php";
    final String AUTH_KEY = "256447A1O4p8Ed5c3a1e10";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static int SPLASH_TIME_OTP = 4000;
    private static int SPLASH_TIME_SPLASHSCREEN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__mobile);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        getOTP = (Button) findViewById(R.id.button_generateOTP);

        mobile_number = (EditText)findViewById(R.id.phone_number);


        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileText = mobile_number.getText().toString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do any action here. Now we are moving to next page
                        Intent mySuperIntent = new Intent(Login_Mobile.this, Splash_Screen_otp.class);
                        startActivity(mySuperIntent);
                        finish();
                    }
                }, SPLASH_TIME_SPLASHSCREEN);
                getOTP.setEnabled(false);
                sendOTP();
            }
        });
    }
    public void sendOTP(){
        final int randomOTP = (int)(Math.random() * 900000 + 100000);
        RequestParams param = new RequestParams();
        param.put("authkey",AUTH_KEY);
        param.put("otp",randomOTP);
        param.put("message","Your OTP for logging into CATKing app is: "+randomOTP+". Check out our courses at our website https://www.courses.catking.in");
        param.put("sender","CATKing");
        param.put("mobile",mobileText);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(OTP_URL, param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("OTP_AUTH", "otp sent Success");
                        final String randomOTP_String= Integer.toString(randomOTP);
                        Intent intentC = new Intent(getApplicationContext(),Login_otp.class);
                        intentC.putExtra("OTP",randomOTP_String);
                        startActivity(intentC);
                    }
                }, SPLASH_TIME_OTP);


//
//                Log.d("OTP_AUTH", "otp sent Success");
//                final String randomOTP_String= Integer.toString(randomOTP);
//                Intent intentC = new Intent(getApplicationContext(),Login_otp.class);
//                intentC.putExtra("OTP",randomOTP_String);
//                startActivity(intentC);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("OTP AUTH", "Failure");
                Toast.makeText(getApplicationContext(),"Failed to send OTP",Toast.LENGTH_LONG).show();
                Intent intentZ = new Intent(getApplicationContext(),Login_Mobile.class);
                startActivity(intentZ);
            }
        });
    }
}
