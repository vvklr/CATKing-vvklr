package in.catking.gkapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.catking.gkapp.utils.GifImageView;

public class Splash_Screen_otp extends AppCompatActivity {
    TextView sending_View;
    Button reMOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.smartphone_drib);
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


//        mKenBurns = (in.gkapp.gkapp.view.kbv.KenBurnsView) findViewById(R.id.ken_burns_images);
//        mLogo = (ImageView) findViewById(R.id.splash_logo);
////        welcomeText = (TextView) findViewById(R.id.Sending_OTP_text);
////        setAnimation(SPLASH_SCREEN_OPTION_3);
//
//        //Code to start timer and take action after the timer ends
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setAnimation(SPLASH_SCREEN_OPTION_3);
//            }
//        }, SPLASH_TIME);
////    }
////    private void setAnimation(String category) {
////        if (category.equals(SPLASH_SCREEN_OPTION_3)) {
////            mKenBurns.setImageResource(R.drawable.splash_bg);
////            animation2();
////            animation3();
////        }
////    }
//
////    private void animation1() {
////        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
////        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
////        scaleXAnimation.setDuration(1200);
////        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
////        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
////        scaleYAnimation.setDuration(1200);
////        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
////        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
////        alphaAnimation.setDuration(1200);
////        AnimatorSet animatorSet = new AnimatorSet();
////        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
////        animatorSet.setStartDelay(500);
////        animatorSet.start();
////    }
//
////    private void animation2() {
////        mLogo.setAlpha(1.0F);
////        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
////        mLogo.startAnimation(anim);
////    }
//
////    private void animation3() {
////        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
////        alphaAnimation.setStartDelay(1700);
////        alphaAnimation.setDuration(500);
////        alphaAnimation.start();
////    }

    }
}
