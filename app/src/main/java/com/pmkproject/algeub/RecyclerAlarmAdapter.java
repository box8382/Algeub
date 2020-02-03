package com.pmkproject.algeub;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

public class RecyclerAlarmAdapter extends RecyclerView.Adapter {

    ArrayList<ItemAlarm> items;
    Context context;

    SQLiteDatabase db;
    String dbName="Data.db";
    String tableName="alarm";


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
        vh.time.setText(alarm.getTime());
        vh.onOff.setChecked(alarm.isOnOff());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView name;
        TextView time;
        SwitchMaterial onOff;

        public VH(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.alarm_item_name);
            time=itemView.findViewById(R.id.alarm_item_time);
            onOff=itemView.findViewById(R.id.alarm_item_onoff);

            onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int pos=getAdapterPosition();
                    ItemAlarm i=items.get(pos);
                    db=context.openOrCreateDatabase(dbName,context.MODE_PRIVATE,null);
                    int isOnOFF=b?1:0;

                    db.execSQL("UPDATE "+tableName+" SET onoff='"+isOnOFF+"' where num='"+i.getNum()+"'");
                    db.close();

                }
            });

        }
    }
}
