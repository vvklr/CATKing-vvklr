package in.catking.gkapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.catking.gkapp.utils.GifImageView;

public class splash_loadquiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_loadquiz);
        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.smartphone_drib);
    }
}
