package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {

    TitleTextView mainText;
    TitleTextView subText;
    ImageView mainImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        mainText=findViewById(R.id.main_title);
        subText=findViewById(R.id.sub_title);
        mainImg=findViewById(R.id.main_img);

        Animation mainAni=AnimationUtils.loadAnimation(this,R.anim.intro_maintext_and_mainimg);
        Animation subAni=AnimationUtils.loadAnimation(this,R.anim.intro_subtext);

        mainText.startAnimation(mainAni);
        mainImg.startAnimation(mainAni);
        subText.startAnimation(subAni);

        mainAni.setAnimationListener(mainListener);
        subAni.setAnimationListener(subListener);
    }

    Animation.AnimationListener mainListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}
        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent=new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.intro_main_activity,R.anim.intro_main_activity_notmove);
            finish();
        }
        @Override
        public void onAnimationRepeat(Animation animation) {}
    };


    Animation.AnimationListener subListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {}
        @Override
        public void onAnimationEnd(Animation animation) {
            subText.setVisibility(View.GONE);
        }
        @Override
        public void onAnimationRepeat(Animation animation) {}
    };

}
