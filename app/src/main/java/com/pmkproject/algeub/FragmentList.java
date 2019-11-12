package com.pmkproject.algeub;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ItemPay> pays=new ArrayList<>();
    RecyclerListAdapter adapter;

    SQLiteDatabase db; //데이터 베이스
    String dbName="Data.db";
    String tableName="list";

    int count;


    //데이터관리같은건 여기서 거의 맨처음 실행되고 onPause 되도 여기를 재시작안함
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //기존데이터를 불러오는곳
        db=getActivity().openOrCreateDatabase(dbName, getActivity().MODE_PRIVATE,null);

        //만들어진 DB파일에 list라는 이름으로 테이블 생성 작업 수행
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+"(num integer, " +
                "name TEXT, pay INTEGER, start INTEGER, last INTEGER, memo TEXT, delivery INTEGER, nightPay INTEGER, free INTEGER);");

        Cursor cursor=db.rawQuery("select * from "+tableName, null);
        if(cursor==null) return;

        while (cursor.moveToNext()){
            //식별자키
            int num=cursor.getInt(0);
            String listName=cursor.getString(1);
            int listPay=cursor.getInt(2);
            int listStart=cursor.getInt(3);
            int listLast=cursor.getInt(4);
            String listMemo=cursor.getString(5);

            //수정기능 넣으면 다시 쓸거..
//            int listDelivery=cursor.getInt(6);
//            boolean listNightPay=(cursor.getInt(7)==1)?true:false;
//            int listFree=cursor.getInt(8);

            String sStart=String.format("%02d:%02d",listStart/60,listStart%60);
            String sLast=String.format("%02d:%02d",listLast/60,listLast%60);

            pays.add(new ItemPay(num,listName,sStart+" ~ "+sLast,listPay+" 원",listMemo));
//            Log.e("TAG",num+"몇번째꺼");
//            Log.e("TAG",listDelivery+"배달건당액수");
//            Log.e("TAG",listNightPay+"야간수당 체크여부");
//            Log.e("TAG",listFree+"자유시간");
            count=num;
        }
        db.close();

    }

    //onPause 됬다가 재시작됬을때 실행하는 부분이 이곳
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    //위의 onCreateView가 실행된 후에 자동 실행되는 메소드입니당 즉 기능담당?
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.list_recycler);
        adapter=new RecyclerListAdapter(getActivity(),pays);
        recyclerView.setAdapter(adapter);

        //불러온데이터를 한번 읽어오기!
        adapter.notifyDataSetChanged();



    }

    //메뉴 옵션관련
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list,menu);
        getActivity().setTitle("저장목록");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_list:
                //작성하기를 눌렀을때
                Intent intent=new Intent(getActivity(),AddListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                count++;
                intent.putExtra("PaySize",count);
                startActivityForResult(intent,10);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==getActivity().RESULT_OK){
                    //AddListActivity를 갔다가 돌아왔을때!
                    String listName=data.getStringExtra("ListName");
                    int listPay=data.getIntExtra("ListPay",0);
                    int listStart=data.getIntExtra("ListStart",480);
                    int listLast=data.getIntExtra("ListLast",1140);
                    String listMemo=data.getStringExtra("ListMemo");

                    String sStart=String.format("%02d:%02d",listStart/60,listStart%60);
                    String sLast=String.format("%02d:%02d",listLast/60,listLast%60);

                    pays.add(new ItemPay(count,listName,sStart+" ~ "+sLast,listPay+" 원",listMemo));
                    adapter.notifyItemInserted(pays.size()-1);

                }
                break;
        }
    }


}
