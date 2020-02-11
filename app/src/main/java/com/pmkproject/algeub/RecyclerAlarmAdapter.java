package com.pmkproject.algeub;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerAlarmAdapter extends RecyclerView.Adapter {

    ArrayList<ItemAlarm> items;
    Context context;

    SQLiteDatabase db;
    String dbName="Data.db";
    String tableName="alarm";

    AlarmManager alarmManager;
//    PendingIntent pendingIntent;
    Intent intent;


    public RecyclerAlarmAdapter(ArrayList<ItemAlarm> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_alarm,parent,false);
        VH holder=new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;
        ItemAlarm alarm=items.get(position);
        vh.name.setText(alarm.getName());
        vh.week.setText(alarm.getWeek());
        vh.ampm.setText(alarm.getAmpm());
        vh.time.setText(alarm.getTime());
        vh.onOff.setChecked(alarm.isOnOff());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView name;
        TextView week;
        TextView ampm;
        TextView time;
        SwitchMaterial onOff;
        CardView cardView;

        public VH(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.alarm_item_name);
            week=itemView.findViewById(R.id.alarm_item_week);
            ampm=itemView.findViewById(R.id.alarm_item_ampm);
            time=itemView.findViewById(R.id.alarm_item_time);
            onOff=itemView.findViewById(R.id.alarm_item_onoff);
            cardView=itemView.findViewById(R.id.alarm_item_card);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final int pos=getAdapterPosition();
                    ItemAlarm i=items.get(pos);

                    new AlertDialog.Builder(context).setMessage("삭제하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db=context.openOrCreateDatabase(dbName,context.MODE_PRIVATE,null);
                            db.execSQL("DELETE FROM "+tableName+" WHERE num="+i.getNum());
                            db.close();
                            items.remove(pos);
                            notifyDataSetChanged();
                            //todo 알람이 체크되있는상태에서 제거를할시 알람도 같이꺼줘야될거같음 고민
                        }
                    }).setNegativeButton("취소",null).create().show();

                    return false;
                }
            });

            onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int pos=getAdapterPosition();
                    ItemAlarm i=items.get(pos);
                    if(b){
                        cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.puble300));
                        name.setTextColor(ContextCompat.getColor(context,R.color.black));
                    }else {
                        cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.puble200));
                        name.setTextColor(ContextCompat.getColor(context,R.color.grey));
                    }

                    if(b){
                        String[] arr=i.getTime().split(":");
                        int pm=i.getAmpm()=="오후"?12:0;
                        alarmStart(Integer.parseInt(arr[0])+pm,Integer.parseInt(arr[1]),i.getNum(),i.getName(),i.getWeek());
                    }else {
                        alarmStop(i.getNum());
                    }

                    db=context.openOrCreateDatabase(dbName,context.MODE_PRIVATE,null);
                    int isOnOFF=b?1:0;

                    db.execSQL("UPDATE "+tableName+" SET onoff='"+isOnOFF+"' where num='"+i.getNum()+"'");
                    db.close();

                }
            });

        }

    }

    public void alarmStart(int hour, int min,int num,String name,String week){
        String[] weeks;
        if(week.equals("매일")){
            weeks=new String[]{"월","화","수","목","금","토","일"};
        }else{
            weeks=week.split(" ");
        }


        alarmManager=(AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Calendar calendar=Calendar.getInstance();
        intent=new Intent(context,AlarmReciver.class);


        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 10);
        if(Calendar.getInstance().getTimeInMillis()>calendar.getTimeInMillis()){
            calendar.add(Calendar.DATE,1);
        }

        intent.putExtra("name",name);
        intent.putExtra("hour",hour);
        intent.putExtra("min",min);
        intent.putExtra("weeks",weeks);


        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,num,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        long oneday=24*60*60*1000;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),oneday,pendingIntent);
    }

    public void alarmStop(int num){

        if(alarmManager!=null && intent!=null){
            Toast.makeText(context, "알람종료", Toast.LENGTH_SHORT).show();

            PendingIntent pendingIntent=PendingIntent.getBroadcast(context,num,intent,PendingIntent.FLAG_NO_CREATE);
            alarmManager.cancel(pendingIntent);
        }

    }
}
