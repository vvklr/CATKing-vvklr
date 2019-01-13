package in.catking.catking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_otp extends AppCompatActivity {

    Button verify_OTP;
    EditText otp_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        verify_OTP = (Button) findViewById(R.id.button_verify_OTP);
        otp_text = (EditText)findViewById(R.id.received_OTP);
        Intent myOTP = getIntent();
        final String otpText = myOTP.getStringExtra("OTP");

        verify_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp_Received = otp_text.getText().toString();
                boolean Aa = otpText.equalsIgnoreCase(otp_Received);
                if (Aa == true){
                    Log.d("OTP_AUTH", "otp verify Success");
                    setLogin("v_login","v_success",getApplicationContext());
                    Intent intentD = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intentD);

                }else{
                    Log.d("OTP_AUTH", "otp verify unSuccess");
                    Log.d("OTP_AUTH", "because given otp is: "+otpText+"and provided otp is: "+otp_Received);
                }
            }
        });
    }
    public static void setLogin(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
