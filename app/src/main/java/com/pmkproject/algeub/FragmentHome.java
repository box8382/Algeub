package com.pmkproject.algeub;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentHome extends Fragment {

    CalendarView calendarView;
    boolean isEdit=false;
    List<EventDay> events=new ArrayList<>();
    ArrayList<ItemHomeCalendar> itemHomeCalendars=new ArrayList<>();

    SQLiteDatabase db; //데이터 베이스
    String dbName="Data.db";
    String tableName="home";


    RelativeLayout dataBox;
    RelativeLayout deliveryBox;
    TextView tvDelivery;
    //TitleTextView tvMonthPay;     //이번달 총 임금
    TitleTextView tvDate;         //날짜
    TitleTextView tvName;         //아르바이트 명
    TitleTextView tvPay;          //시급
    TitleTextView tvPayToday;     //오늘 예상 금액
    TitleTextView tvTime;

    int deliveryPay=0;
    int sum;
    int count=0;

    //데이터관리같은건 여기서 거의 맨처음 실행되고 onPause 되도 여기를 재시작안함
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=getActivity().openOrCreateDatabase(dbName, getActivity().MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"(num integer, " +
                "calendar INTEGER, position INTEGER);");

        Cursor cursor=db.rawQuery("select * from "+tableName, null);
        if(cursor==null) return;

        while (cursor.moveToNext()) {
            int num = cursor.getInt(0);
            Long cal = cursor.getLong(1);
            int pos = cursor.getInt(2);

            itemHomeCalendars.add(new ItemHomeCalendar(cal, pos,num));
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(cal);
            events.add(new EventDay(calendar,R.drawable.ic_clock));

            count=num;
            Log.e("aaa",count+" 저장되어있던 카운트값");
        }

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

        dataBox=view.findViewById(R.id.home_databox);
        deliveryBox=view.findViewById(R.id.home_delivery_visible);
        tvDelivery=view.findViewById(R.id.home_delivery);
        //tvMonthPay=view.findViewById(R.id.home_pay_month);
        tvDate=view.findViewById(R.id.home_date);
        tvName=view.findViewById(R.id.home_name);
        tvPay=view.findViewById(R.id.home_pay);
        tvPayToday=view.findViewById(R.id.home_pay_today);
        tvTime=view.findViewById(R.id.home_time);


        calendarView=view.findViewById(R.id.calendar);
        calendarView.setEvents(events);

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
                        if(calendar.getTimeInMillis()==itemHomeCalendars.get(i).getCalendar()){
//                            itemHomeCalendars.remove(i);
//                            db.delete(tableName,"num"+"="+itemHomeCalendars.get(i).cnt,null);
                            //데이터변경했을때 db수정해야되는데 .....!!!!!! 임시방편..

                            Toast.makeText(getActivity(), "데이터 제거후 다시 저장해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    itemHomeCalendars.add(new ItemHomeCalendar(calendar.getTimeInMillis(),position-1,count));

                    Log.e("aaa",count+" 데이터베이스에 저장되는 카운트값");
                    db.execSQL("INSERT INTO "+tableName+"(num ,calendar, position) VALUES('"+count+"','"+calendar.getTimeInMillis()+"','"+(position-1)+"')");


                    events.add(day);

                    calendarView.setEvents(events);

                    count++;


                }

                @Override
                public void onNegativeClicked() {

                    for(int i=0;i<itemHomeCalendars.size();i++){
                        if(calendar.getTimeInMillis()==itemHomeCalendars.get(i).getCalendar()){

                            db.delete(tableName,"num"+"="+itemHomeCalendars.get(i).cnt,null);
                            Log.e("aaa",itemHomeCalendars.get(i).cnt+" 데이터베이스 삭제하는 카운트 값");

                            itemHomeCalendars.remove(i);
                            events.remove(i);
                            calendarView.setEvents(events);
                        }
                    }

                }
            });
            dialogHome.show();

        }
    };

    OnDayClickListener unEditMode=new OnDayClickListener() {
        @Override
        public void onDayClick(EventDay eventDay) {
            Calendar calendar=eventDay.getCalendar();

            tvDate.setText("저장되어있는 아르바이트가 없습니다");
            dataBox.setVisibility(View.GONE);

            SimpleDateFormat format=new SimpleDateFormat("yyyy");
            int year=Integer.parseInt(format.format(calendar.getTimeInMillis())); //년도
            format=new SimpleDateFormat("MM");
            int month=Integer.parseInt(format.format(calendar.getTimeInMillis())); //월
            format=new SimpleDateFormat("dd");
            int day=Integer.parseInt(format.format(calendar.getTimeInMillis())); //일

            for(int i=0;i<itemHomeCalendars.size();i++){
                ItemHomeCalendar item=itemHomeCalendars.get(i);
                if(calendar.getTimeInMillis()==item.getCalendar()){

                    GItemPay itemPay=G.GlobalListDatas.get(item.getPosition());
                    String title=itemPay.getTitle();
                    int pay=itemPay.getPay();
                    int start=itemPay.getStartClock();
                    int last=itemPay.getLastClock();

                    String sStart=String.format("%02d:%02d",start/60,start%60);
                    String sLast=String.format("%02d:%02d",last/60,last%60);

                    //아르바이트 일한시간 * 시급 계산식
                    int t=Math.abs(last-start);
                    int totalPay;
                    int night=0;


                    //휴식시간이 있다면?
                    if(itemPay.getFree()!=0){
                        t=Math.abs(last-start)-itemPay.getFree();
                    }

                    totalPay=Integer.parseInt(String.valueOf(pay*(t/60)+Math.round(((double)pay/60)*(t%60))));


                    //야간수당이 체크되어있다면?
                    if(itemPay.getNightPay()){
                        if(start>1320){
                            night=start-1320;
                        }else if(start<360){
                            night=360-start;
                        }

                        if(last>1320){
                            night+=last-1320;
                        }else if(last<360){
                            night+=360-last;
                        }

                        night=Integer.parseInt(String.valueOf(pay/2*(night/60)+Math.round(((double)pay/2/60)*(night%60))));
                    }

                    sum=totalPay+night;


                    //tvMonthPay.setText("이번달 예상 임금은 총 0 원 입니다");
                    tvDate.setText(year+"년 "+month+"월 "+day+"일");
                    tvName.setText(title);
                    tvTime.setText(sStart+" ~ "+sLast);
                    DecimalFormat myFormatter = new DecimalFormat("###,###");
                    String changePay = myFormatter.format(pay);
                    tvPay.setText("시급 : "+changePay+" 원");
                    String changeNum = myFormatter.format(sum);
                    tvPayToday.setText("예상 금액 : "+(changeNum)+" 원");

                    dataBox.setVisibility(View.VISIBLE);
                    break;
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
