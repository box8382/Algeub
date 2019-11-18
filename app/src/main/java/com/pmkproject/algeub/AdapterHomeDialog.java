package com.pmkproject.algeub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHomeDialog extends RecyclerView.Adapter {

    Context context;

    public AdapterHomeDialog(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_dialog_home,parent,false);
        VH holder=new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;
        GItemPay itemPay=G.GlobalListDatas.get(position);
        vh.name.setText(itemPay.getTitle());
        vh.pay.setText(itemPay.getPay()+" 원");

        String sStart=String.format("%02d:%02d",itemPay.getStartClock()/60,itemPay.getStartClock()%60);
        String sLast=String.format("%02d:%02d",itemPay.getLastClock()/60,itemPay.getLastClock()%60);

        vh.clock.setText(sStart+" ~ "+sLast+" 시간");

    }

    @Override
    public int getItemCount() {
        return G.GlobalListDatas.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TitleTextView name;
        TitleTextView clock;
        TitleTextView pay;

        public VH(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.dialog_home_item_name);
            clock=itemView.findViewById(R.id.dialog_home_item_clock);
            pay=itemView.findViewById(R.id.dialog_home_item_pay);
        }
    }
}
