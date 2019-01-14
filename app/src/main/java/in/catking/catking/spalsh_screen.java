package in.catking.catking;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import in.catking.catking.font.MaterialDesignIconsTextView;
import in.catking.catking.view.kbv.KenBurnsView;

public class spalsh_screen extends AppCompatActivity {

        public static final String SPLASH_SCREEN_OPTION_3 = "Down + fade in + Ken Burns";
        private in.catking.catking.view.kbv.KenBurnsView mKenBurns;
        private in.catking.catking.font.MaterialDesignIconsTextView mLogo;
        private TextView welcomeText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_NO_TITLE); //Removing ActionBar
            getSupportActionBar().hide();
            setContentView(R.layout.activity_spalsh_screen);

            mKenBurns = (in.catking.catking.view.kbv.KenBurnsView) findViewById(R.id.ken_burns_images);
            mLogo = (in.catking.catking.font.MaterialDesignIconsTextView) findViewById(R.id.splash_logo);
            welcomeText = (TextView) findViewById(R.id.Sending_OTP_text);
            setAnimation(SPLASH_SCREEN_OPTION_3);
        }

        /** Animation depends on category.
         * */
        private void setAnimation(String category) {
            if (category.equals(SPLASH_SCREEN_OPTION_3)) {
                mKenBurns.setImageResource(R.drawable.splash_bg);
                animation2();
                animation3();
            }
        }

        private void animation1() {
            ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
            scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleXAnimation.setDuration(1200);
            ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
            scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleYAnimation.setDuration(1200);
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
            alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            alphaAnimation.setDuration(1200);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
            animatorSet.setStartDelay(500);
            animatorSet.start();
        }

        private void animation2() {
            mLogo.setAlpha(1.0F);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
            mLogo.startAnimation(anim);
        }

        private void animation3() {
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
            alphaAnimation.setStartDelay(1700);
            alphaAnimation.setDuration(500);
            alphaAnimation.start();
        }
    }