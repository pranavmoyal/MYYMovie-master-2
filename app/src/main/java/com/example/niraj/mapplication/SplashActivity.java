package com.example.niraj.mapplication;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.api.GoogleApiClient;

public class SplashActivity extends AppCompatActivity {
    ImageView image;
    TextView text;
    TextView statusText;
    ViewSwitcher viewSwitcher;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            // final ImageView imageView = (ImageView) findViewById(R.id.imgProfilePic);
            //final LinearLayout linearLayout=(LinearLayout) findViewById(R.id.ll_img);
            //final LinearLayout linearLayout1=(LinearLayout) findViewById(R.id.ll_img1);

            image = (ImageView) findViewById(R.id.icon);


            Animation animBlinkIn_icon = AnimationUtils.loadAnimation(this, R.anim.blink);
            image.startAnimation(animBlinkIn_icon);

            final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
            final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
            final Button imageButton = (Button) findViewById(R.id.imageButton5);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1=new Intent(SplashActivity.this,Main2Activity.class);
                    startActivity(intent1);
                }
            });

            final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(10000L);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float height = backgroundOne.getHeight();
                    final float translationY = height * progress;
                    backgroundOne.setTranslationY(translationY);
                    backgroundTwo.setTranslationY(translationY - height);
                }
            });
            animator.start();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
