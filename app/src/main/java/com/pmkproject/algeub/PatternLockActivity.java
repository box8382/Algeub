package com.pmkproject.algeub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import org.w3c.dom.Text;

import java.util.List;

public class PatternLockActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    TextView tv;
    String patternPassward1;
    String patternPassward2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);

        patternLockView=findViewById(R.id.pattern_lock_view);
        tv=findViewById(R.id.pattern_tv);

        patternLockView.addPatternLockListener(patternLockViewListener);

    }

    //패턴에 관한 리스너
     PatternLockViewListener patternLockViewListener=new PatternLockViewListener() {
         @Override
         public void onStarted() {
             Log.e("TAG", "패턴시작");
         }

         @Override
         public void onProgress(List<PatternLockView.Dot> progressPattern) {
             Log.e("TAG", "Pattern Progress :" + PatternLockUtils.patternToString(patternLockView, progressPattern));
         }

         @Override
         public void onComplete(List<PatternLockView.Dot> pattern) {
             //최소 3줄이상 해야함
             if(PatternLockUtils.patternToString(patternLockView, pattern).length()<=2){
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

}
