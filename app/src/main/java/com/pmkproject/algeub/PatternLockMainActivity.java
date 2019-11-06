package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class PatternLockMainActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    TextView tv;
    String patternPassward;

    //설정파일명
    String fileConfig="Config";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_main_lock);

        patternLockView=findViewById(R.id.pattern_lock_view_m);
        tv=findViewById(R.id.pattern_tv_m);

        patternLockView.addPatternLockListener(patternLockViewListener);

        //기존의 저장되있는 패턴값을 읽어옴
        SharedPreferences sf=getSharedPreferences(fileConfig,0);
        patternPassward=sf.getString("passward","");

    }

    PatternLockViewListener patternLockViewListener=new PatternLockViewListener() {
        @Override
        public void onStarted() { }
        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) { }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            //저장되있는 패턴과 지금 입력한 패턴 비교
            if(!patternPassward.equals(PatternLockUtils.patternToString(patternLockView, pattern))){
                Log.e("PASS",patternPassward);
                tv.setText("패턴이 일치하지않습니다");
                onCleared();
                return;
            }else{
                Intent intent=getIntent();
                setResult(RESULT_OK,intent);
                finish();
            }

        }

        @Override
        public void onCleared() {
            patternLockView.clearPattern();
        }
    };

}
