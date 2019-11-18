package com.pmkproject.algeub;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentHome extends Fragment {

    CalendarView calendarView;
    boolean isEdit=false;
    List<EventDay> events=new ArrayList<>();
    ArrayList<ItemHomeCalendar> itemHomeCalendars=new ArrayList<>();

    TextView tv;


    //데이터관리같은건 여기서 거의 맨처음 실행되고 onPause 되도 여기를 재시작안함
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //onPause 됬다가 재시작됬을때 실행하는 부분이 이곳
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Fragment에서 메뉴를 쓰기위함
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    //위의 onCreateView가 실행된 후에 자동 실행되는 메소드입니당 즉 기능담당?
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView=view.findViewById(R.id.calendar);
        calendarView.setEvents(events);

        tv=view.findViewById(R.id.tv);

        Log.e("edit","onViewCreate 실행");

    }


    //메뉴 옵션 관련///
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(isEdit){
            editMode();
        }else{
            unEditMode();
        }
        Log.e("edit","onCreateOptionsMenu 실행");

        inflater.inflate(R.menu.menu_home,menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //설정 옵션
            case R.id.config:
                Intent intent=new Intent(getActivity(), ConfigActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.edit:
                if(isEdit==false) {
                    editMode();
                }else if(isEdit==true){
                    unEditMode();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    /////////////////

    OnDayClickListener editMode=new OnDayClickListener() {
        @Override
        public void onDayClick(EventDay eventDay) {
            Calendar calendar=eventDay.getCalendar();


            SimpleDateFormat format=new SimpleDateFormat("yyyy");
            int year=Integer.parseInt(format.format(calendar.getTimeInMillis())); //년도
            format=new SimpleDateFormat("MM");
            int month=Integer.parseInt(format.format(calendar.getTimeInMillis())); //월
            format=new SimpleDateFormat("dd");
            int day=Integer.parseInt(format.format(calendar.getTimeInMillis())); //일


            DialogHome dialogHome=new DialogHome(getActivity(),year,month,day);
            dialogHome.setDialogHomeListener(new DialogHome.DialogHomeListener() {
                @Override
                public void onPositiveClicked(int position) {
                    if(position==0){

                        //저장을할때 아무것도 누르지 않은상태

                        return;
                    }

                    EventDay day=new EventDay(calendar,R.drawable.ic_clock);

                    for(int i=0;i<itemHomeCalendars.size();i++){
                        if(calendar.getTimeInMillis()==itemHomeCalendars.get(i).getCalendar().getTimeInMillis()){
                            itemHomeCalendars.remove(i);
                        }
                    }
                    itemHomeCalendars.add(new ItemHomeCalendar(calendar,position-1,day));

                    events.add(day);

                    calendarView.setEvents(events);

                }
            });
            dialogHome.show();

        }
    };

    OnDayClickListener unEditMode=new OnDayClickListener() {
        @Override
        public void onDayClick(EventDay eventDay) {
            Calendar calendar=eventDay.getCalendar();
            for(int i=0;i<itemHomeCalendars.size();i++){
                ItemHomeCalendar item=itemHomeCalendars.get(i);
                if(calendar.getTimeInMillis()==item.getCalendar().getTimeInMillis()){

                    GItemPay itemPay=G.GlobalListDatas.get(item.getPosition());
                    String title=itemPay.getTitle();
                    int pay=itemPay.getPay();

                    tv.setText("제목"+title+"\n 시급"+pay);
                    break;
                }else{
                    tv.setText("저장된 내용이 없습니다");
                }
            }


        }
    };

    void editMode(){
        getActivity().setTitle("편집모드");
        calendarView.setOnDayClickListener(editMode);

        isEdit=true;
    }

    void unEditMode(){
        getActivity().setTitle("알바달력");
        calendarView.setOnDayClickListener(unEditMode);

        isEdit=false;
    }

}
