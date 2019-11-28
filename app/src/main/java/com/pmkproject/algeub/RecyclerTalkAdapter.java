package com.pmkproject.algeub;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerTalkAdapter extends RecyclerView.Adapter {

    ArrayList<ItemTalk> items;
    Context context;

    public RecyclerTalkAdapter(ArrayList<ItemTalk> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_talk,parent,false);
        VH holder=new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;
        ItemTalk itemTalk=items.get(position);
        vh.num.setText(itemTalk.getNum()+"");
        vh.title.setText(itemTalk.getTitle());
        vh.writer.setText(itemTalk.getWriter());
        vh.date.setText(itemTalk.getDate());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView num;
        TextView title;
        TextView writer;
        TextView date;

        public VH(@NonNull View itemView) {
            super(itemView);
            num=itemView.findViewById(R.id.talk_num);
            title=itemView.findViewById(R.id.talk_title);
            writer=itemView.findViewById(R.id.talk_writer);
            date=itemView.findViewById(R.id.talk_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    ItemTalk itemTalk=items.get(pos);
                    Intent intent=new Intent(context,BoardSActivity.class);
                    intent.putExtra("No",itemTalk.getNo());
                    intent.putExtra("Num",itemTalk.getNum());
                    intent.putExtra("Writer",itemTalk.getWriter());
                    intent.putExtra("Date",itemTalk.getDate());
                    intent.putExtra("Title",itemTalk.getTitle());
                    intent.putExtra("Content",itemTalk.getContent());
                    context.startActivity(intent);
                }
            });

        }

    }
}
