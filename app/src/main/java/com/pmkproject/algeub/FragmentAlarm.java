package com.pmkproject.algeub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FragmentAlarm extends Fragment {


    FloatingActionButton addBtn;

    RecyclerView recyclerView;
    ArrayList<ItemAlarm> items=new ArrayList<>();
    RecyclerAlarmAdapter adapter;

    //데이터관리같은건 여기서 거의 맨처음 실행되고 onPause 되도 여기를 재시작안함
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //onPause 됬다가 재시작됬을때 실행하는 부분이 이곳
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_alarm,container,false);
    }

    //위의 onCreateView가 실행된 후에 자동 실행되는 메소드입니당 즉 기능담당?
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn=view.findViewById(R.id.alarm_add);
        recyclerView=view.findViewById(R.id.alarm_recycler);

        adapter=new RecyclerAlarmAdapter(items,getActivity());
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(floatingListener);
    }

    View.OnClickListener floatingListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //add버튼이 눌러졌을경우
//            items.add(new ItemAlarm("김땡땡",300,true));
//            adapter.notifyDataSetChanged();

            Intent intent=new Intent(getActivity(),AlarmAddView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,10);

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(resultCode==getActivity().RESULT_OK){
                Toast.makeText(getActivity(), "잘 추가했습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //메뉴 옵션 관련//
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("알람");
    }

}
