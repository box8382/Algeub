package com.pmkproject.algeub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ConfigFragment extends PreferenceFragment {

    //SharedPreferences pref;

    //설정파일명
    String fileConfig="Config";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.config);

        //pref=PreferenceManager.getDefaultSharedPreferences(getActivity());


    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key=preference.getKey();
        if(key.equals("unlock")){

            new AlertDialog.Builder(getActivity()).setTitle("주의").setMessage("기존에 저장되어있는 패턴을 삭제하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //확인눌렀을때 패턴데이터 삭제
                    SharedPreferences sf=getActivity().getSharedPreferences(fileConfig,0);
                    SharedPreferences.Editor editor=sf.edit();
                    editor.remove("passward");
                    editor.commit();
                    Toast.makeText(getActivity(), "잠금기능을 제거하였습니다", Toast.LENGTH_SHORT).show();

                }
            }).setNegativeButton("취소",null).create().show();

        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
