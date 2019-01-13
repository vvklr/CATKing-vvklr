package in.catking.catking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Entry_To_CATKing extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String C_login = getLogin("v_login",getApplicationContext());
        Intent activityIntent;
        String Bb = "v_success";
        boolean Aa = Bb.equalsIgnoreCase(C_login);
        if (Aa == true) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, Login_Mobile.class);
        }

        startActivity(activityIntent);
        finish();
    }
    public static String getLogin(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}