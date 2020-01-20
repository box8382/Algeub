package com.pmkproject.algeub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

public class RecyclerAlarmAdapter extends RecyclerView.Adapter {

    ArrayList<ItemAlarm> items;
    Context context;

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
        vh.time.setText(alarm.getTime()+"");
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
        }
    }
}
