package in.catking.catking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login_otp extends AppCompatActivity {
    public static final MediaType FORM_DATA_TYPE = MediaType.parse("application/text; charset=utf-8");
    public static final String URL = "https://docs.google.com/forms/d/e/1FAIpQLScmD3vpWBGs-4OfSTG4pHi0xAzoCDysGQBOfngbAim-TTH-mA/formResponse";

    public static final String NAME_KEY = "entry.1415353320";
    public static final String PHONE_KEY = "entry.1365389173";
    public static final String EMAIL_KEY = "entry.1347260737";

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
                    Intent myDATA = getIntent();
                    String Aname = myDATA.getStringExtra("NAME");
                    String Aphone = myDATA.getStringExtra("PHONE");
                    String Aemail = myDATA.getStringExtra("EMAIL");
                    Log.d("ABB","getting values of "+Aname +" "+Aphone+"  "+Aemail);
                    PostForm postData = new PostForm();
                    postData.execute(URL,Aname,Aphone,Aemail);
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
    public class PostForm extends AsyncTask<String, Void, Boolean> {

        @Override
        public Boolean doInBackground(String... params) {
            Boolean result = true;
            String url = params[0];
            String name = params[1];
            String phone = params[2];
            String email = params[3];
            String postBody="";

            try {
                postBody = NAME_KEY+"=" + URLEncoder.encode(name,"UTF-8") +
                        "&" + PHONE_KEY + "=" + URLEncoder.encode(phone,"UTF-8") +
                        "&" + EMAIL_KEY + "=" + URLEncoder.encode(email,"UTF-8");
                Log.d("ABB","post body success");
            } catch (UnsupportedEncodingException ex) {
                Log.d("ABB","post body failed");
                result=false;
            }
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add(NAME_KEY, name )
                        .add(PHONE_KEY, phone)
                        .add(EMAIL_KEY,email)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post( body )
                        .build();
                Response response = client.newCall( request ).execute();
                Log.d("ABB","Http client success");
            } catch (IOException e) {
                result=false;
                Log.d("ABB","Http client Failed");
            }
            return result;
        }
        @Override
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(getApplicationContext(),result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
        }
    }
}
