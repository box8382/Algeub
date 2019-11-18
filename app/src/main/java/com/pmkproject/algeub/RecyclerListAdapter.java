package com.pmkproject.algeub;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class RecyclerListAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ItemPay> pays;

    SQLiteDatabase db; //데이터 베이스
    String dbName="Data.db";
    String tableName="list";

    public RecyclerListAdapter(Context context, ArrayList<ItemPay> pays) {
        this.context = context;
        this.pays = pays;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_pay,parent,false);
        VH holder=new VH(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH)holder;
        ItemPay itemPay=pays.get(position);

        vh.title.setText(itemPay.getTitle());
        vh.clock.setText(itemPay.getClock());
        vh.pay.setText(itemPay.getPay());
        if(itemPay.getText()!=null) {
            vh.text.setText(itemPay.getText());
        }else{
            vh.text.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return pays.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TitleTextView title;
        TitleTextView clock;
        TitleTextView pay;
        TitleTextView text;
        View line;

        public VH(@NonNull final View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.pay_title);
            clock=itemView.findViewById(R.id.pay_clock);
            pay=itemView.findViewById(R.id.pay);
            text=itemView.findViewById(R.id.pay_text);
            line=itemView.findViewById(R.id.pay_line);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int pos=getAdapterPosition();
                    new AlertDialog.Builder(context).setMessage("삭제하시겠습니까?").setNegativeButton("취소",null).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ItemPay itemPay=pays.get(pos);
                            pays.remove(pos);

                            G.GlobalListDatas.remove(pos);

                            notifyItemRemoved(pos);
                            db=context.openOrCreateDatabase(dbName,context.MODE_PRIVATE,null);

                            Log.e("QQQ","삭제할때 누른놈의 넘버값은 : "+ itemPay.getNum());
                            db.delete(tableName,"num"+"="+itemPay.getNum(),null);

                        }
                    }).create().show();
                    return false;
                }
            });

        }
    }
}
