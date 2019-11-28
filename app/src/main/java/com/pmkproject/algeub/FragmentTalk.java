package com.pmkproject.algeub;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentTalk extends Fragment implements View.OnClickListener{

    private final int addBtnREQUST=50;
    SwipeRefreshLayout swipe;
    RelativeLayout board,talk;

    SignInButton signIn;
    TextView tv;
    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    RecyclerTalkAdapter adapter;
    ArrayList<ItemTalk> items=new ArrayList<>();

    CircleImageView ivProfile;
    TextView tvEmail;
    TextView tvNickName;
    RelativeLayout logoutBtn;

    LinearLayout login;
    RelativeLayout logout;

    FloatingActionButton addBtn;
    String serverUrl="http://boxoun.dothome.co.kr/Algeub/loadDBtoJson.php";

    String name;
    String email;
    Uri uri;

    void login(){
        board.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);

        talk.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
    }

    void logout(){
        board.setVisibility(View.GONE);
        login.setVisibility(View.GONE);

        talk.setVisibility(View.VISIBLE);
        logout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        //사용자가 로그인되있는 상태면 바로 다음으로 넘어감
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user){
        if(user==null){
            logout();
            return;
        }else {
            G.user=user;

            name=user.getDisplayName();
            email=user.getEmail();
            uri=user.getPhotoUrl();

            Glide.with(getActivity()).load(uri).into(ivProfile);
            tvEmail.setText(email);
            String nickName=email.split("@")[0];
            tvNickName.setText(nickName);

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadData();
                }
            });

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),BoardWriteActivity.class);
                    intent.putExtra("writer",nickName);
                    startActivity(intent);
                }
            });

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity()).setMessage("로그아웃 하시겠습니까?").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            mGoogleSignInClient.revokeAccess();
                            logout();

                        }
                    }).setNegativeButton("취소",null).create().show();

                }
            });

            login();

        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        mAuth = FirebaseAuth.getInstance();


    }

    //onPause 됬다가 재시작됬을때 실행하는 부분이 이곳
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_talk, container, false);

    }

    //위의 onCreateView가 실행된 후에 자동 실행되는 메소드입니당 즉 기능담당?
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signIn=view.findViewById(R.id.google_signin);
        signIn.setOnClickListener(this);
        swipe=view.findViewById(R.id.swipe);

        mAuth = FirebaseAuth.getInstance();

        board=view.findViewById(R.id.board_visibility);
        talk=view.findViewById(R.id.talk_visibility);


        recyclerView=view.findViewById(R.id.board_recycler);
        adapter=new RecyclerTalkAdapter(items,getActivity());
        recyclerView.setAdapter(adapter);


        ivProfile=getActivity().findViewById(R.id.header_profile);
        tvEmail=getActivity().findViewById(R.id.header_email);
        tvNickName=getActivity().findViewById(R.id.header_nick);
        logoutBtn=getActivity().findViewById(R.id.header_logout);
        addBtn=view.findViewById(R.id.board_add);

        login=getActivity().findViewById(R.id.login);
        logout=getActivity().findViewById(R.id.logout);








    }

    //구글 로그인 관련
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,10);
    }


    //로그인 성공했다면?
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Log.e("TAG","로그인성공");
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    Log.e("TAG","로그인실패");
                    updateUI(null);
                }

            }
        });

    }


    //구글 로그인 관련
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account=task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);

                }catch (ApiException e){

                }
                break;
        }
    }



    //메뉴 옵션관련//
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().setTitle("소통공간");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_signin:
                signIn();
                break;
        }
    }


    public void loadData(){

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                items.clear();
                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }

                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject=response.getJSONObject(i);

                        int no=jsonObject.getInt("no");
                        String nick=jsonObject.getString("nick");
                        String title=jsonObject.getString("title");
                        String content=jsonObject.getString("content");
                        String date=jsonObject.getString("date");

                        items.add(new ItemTalk(no,i+1,title,nick,date,content));

                        if(adapter!=null){
                            adapter.notifyItemInserted(items.size()-1);
                        }

                    }
                }catch (JSONException e){

                }

                if(swipe!=null) swipe.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
