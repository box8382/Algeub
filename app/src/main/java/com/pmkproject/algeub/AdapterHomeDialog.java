package com.pmkproject.algeub;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHomeDialog extends RecyclerView.Adapter {

    Context context;
    DialogHome dialogHome;
    ArrayList<View> views=new ArrayList<>();

    public AdapterHomeDialog(Context context,DialogHome dialogHome) {
        this.context = context;
        this.dialogHome=dialogHome;
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
        CardView cardView,cardView2;

        public VH(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.dialog_home_item_name);
            clock=itemView.findViewById(R.id.dialog_home_item_clock);
            pay=itemView.findViewById(R.id.dialog_home_item_pay);
            cardView=itemView.findViewById(R.id.dialog_home_item_cardview);
            cardView2=itemView.findViewById(R.id.name_layout);

            views.add(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    for(int i=0;i<views.size();i++){
                        //views.get(i).setBackgroundColor(Color.rgb(183,148,246));
                        CardView c=views.get(i).findViewById(R.id.dialog_home_item_cardview);
                        c.setCardBackgroundColor(Color.rgb(183,148,246));
                        c=views.get(i).findViewById(R.id.name_layout);
                        c.setCardBackgroundColor(Color.rgb(153,101,244));
                    }
                    cardView.setCardBackgroundColor(Color.rgb(166,166,166));
                    cardView2.setCardBackgroundColor(Color.rgb(116,116,116));
                    dialogHome.setPosition(pos+1);

                }
            });
        }


    }

}
