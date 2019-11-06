package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class PatternLockChangeActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    TextView tv;
    String patternPassward1;
    String patternPassward2;

    //설정파일명
    String fileConfig="Config";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_change_lock);

        patternLockView=findViewById(R.id.pattern_lock_view_c);
        tv=findViewById(R.id.pattern_tv_c);

        patternLockView.addPatternLockListener(patternLockViewListener);

    }

    //패턴에 관한 리스너
     PatternLockViewListener patternLockViewListener=new PatternLockViewListener() {
         @Override
         public void onStarted() { }

         @Override
         public void onProgress(List<PatternLockView.Dot> progressPattern) { }

         @Override
         public void onComplete(List<PatternLockView.Dot> pattern) {
             //최소 3줄이상 해야함
             if(PatternLockUtils.patternToString(patternLockView, pattern).length()<=3){
                 tv.setText("최소 4개의 점을 이어야합니다");
                 onCleared();
                 return;
             }


             //패턴을 2번확인하는 알고리즘?
             if(patternPassward1==null) {
                 patternPassward1 = PatternLockUtils.patternToString(patternLockView, pattern);
                 onCleared();
                 tv.setText("한번더 입력해주세요");
             }else{
                 patternPassward2 = PatternLockUtils.patternToString(patternLockView, pattern);
                 onCleared();

                 if(patternPassward1.equals(patternPassward2)){
                     //여기에서 값 패스워드 값 저장
                     SavePassward();
                     Toast.makeText(PatternLockChangeActivity.this, "잠금설정 하였습니다", Toast.LENGTH_SHORT).show();
                     finish();
                 }else{
                     tv.setText("패턴이 일치하지않습니다");
                     patternPassward1=null;
                     patternPassward2=null;
                 }
             }


         }

         @Override
         public void onCleared() {
             patternLockView.clearPattern();
         }
     };

    public void SavePassward(){
        SharedPreferences sf=getSharedPreferences(fileConfig,0);
        SharedPreferences.Editor editor=sf.edit();
        editor.putString("passward",patternPassward2);
        editor.commit();
    }

}
