package com.example.defangfang.schoolinfopublishsystem;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
import com.example.defangfang.schoolinfopublishsystem.Common.Common;
import com.example.defangfang.schoolinfopublishsystem.Common.Utils;
import com.example.defangfang.schoolinfopublishsystem.module.Information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //find
    private FrameLayout findlayout;
    private LinearLayout minelayout;
    private LinearLayout managelayout;
    private LinearLayout findbut;
    private LinearLayout minebut;
    private LinearLayout managebut;
    private SwipeRefreshLayout findSwipe;
    private FloatingActionButton findFloatBut;
    private ArrayList<Information> mInformationArrayList;
    private InfoAdapter adapter;

    //mine
    private TextView minename;

    //manage
    private Button manageedit;
    private SwipeRefreshLayout manageSwipe;
    private ArrayList<Information> manageInfoList;
    private MyInfoAdapter myInfoadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find
        findbut = (LinearLayout)findViewById(R.id.main_find);
        minebut = (LinearLayout)findViewById(R.id.main_mine);
        managebut = (LinearLayout)findViewById(R.id.main_manage);
        findlayout = (FrameLayout)findViewById(R.id.find);
        managelayout = (LinearLayout)findViewById(R.id.manage);
        minelayout = (LinearLayout)findViewById(R.id.mine);
        findSwipe = (SwipeRefreshLayout)findViewById(R.id.find_swipe);
        findSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateInfos();
            }
        });
        findFloatBut = (FloatingActionButton)findViewById(R.id.find_float_button);
        findFloatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"haha,you clicked the FloatingActionButton!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,AddInfo.class);
                intent.putExtra("writername",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });
        findbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFindView();
            }
        });
        minebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMineView();
            }
        });
        managebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setManageView();
            }
        });
        //mine
        minename = (TextView)findViewById(R.id.mine_name);
        minename.setText(getIntent().getStringExtra("name"));
        //manage
        manageedit = (Button)findViewById(R.id.manage_edit);
        manageSwipe = (SwipeRefreshLayout)findViewById(R.id.manage_swipe);
        manageedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyInfoAdapter.isediting){
                    manageedit.setText(R.string.save);
                    MyInfoAdapter.isediting = true;

                } else {
                    manageedit.setText(R.string.edit);
                    MyInfoAdapter.isediting = false;
                }
                myInfoadapter.notifyDataSetChanged();
            }
        });
        manageSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateInfosById();
            }
        });
    }

    /*boolean flag = false;
    int textcolor = 0;
    int imagecolor = 0;
     private void setfindview(View v){
         findlayout.setVisibility(View.GONE);
         managelayout.setVisibility(View.GONE);
         minelayout.setVisibility(View.VISIBLE);
         if(!flag){
             findtext.setTextColor(getResources().getColor(R.color.colorGreen));
             //findimage.setColorFilter(ContextCompat.getColor(v.getContext(),R.color.colorGreen));
             Drawable drawable = ContextCompat.getDrawable(this,android.R.drawable.ic_menu_search);
             Drawable drawable1 = DrawableCompat.wrap(drawable).mutate();
             DrawableCompat.setTint(drawable1,getResources().getColor(R.color.colorGreen));
             findimage.setImageDrawable(drawable1);
         }
         else {
             //textcolor = findtext.getCurrentTextColor();
             findtext.setTextColor(textcolor);
             //findimage.setColorFilter(ContextCompat.getColor(v.getContext(),R.color.colorGreen));
             Drawable drawable = ContextCompat.getDrawable(this,android.R.drawable.ic_menu_search);
             Drawable drawable1 = DrawableCompat.wrap(drawable).mutate();
             DrawableCompat.setTint(drawable1,getResources().getColor(R.color.colorWhite));
             findimage.setImageDrawable(drawable1);
         }
         flag = !flag;
     }
     */
    private void setFindView(){
        findlayout.setVisibility(View.VISIBLE);
        managelayout.setVisibility(View.GONE);
        minelayout.setVisibility(View.GONE);
    }
    private void setMineView(){
        findlayout.setVisibility(View.GONE);
        managelayout.setVisibility(View.GONE);
        minelayout.setVisibility(View.VISIBLE);
    }
    private void setManageView(){
        findlayout.setVisibility(View.GONE);
        managelayout.setVisibility(View.VISIBLE);
        minelayout.setVisibility(View.GONE);
    }
    private void updateInfos(){
        AVCloud.rpcFunctionInBackground("getAllInfos", null, new FunctionCallback<Object>() {
            @Override
            public void done(final Object object, AVException e) {
               if(e == null){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           mInformationArrayList = Utils.parseInfosFromJson(object);
                           RecyclerView recyclerView = (RecyclerView)findViewById(R.id.find_recyclerview);
                           LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                           recyclerView.setLayoutManager(layoutManager);
                           adapter = new InfoAdapter(mInformationArrayList);
                           recyclerView.setAdapter(adapter);
                           //Toast.makeText(MainActivity.this,infoList.get(0).getWritername(),Toast.LENGTH_SHORT).show();
                           //Log.i(Utils.TAG, "data:"+infoList.get(0).getWritername());
                           //adapter = new InfoAdapter(mInformationArrayList);
                           //adapter.notifyDataSetChanged();
                           if(findSwipe.isRefreshing()){
                               findSwipe.setRefreshing(false);
                           }
                       }
                   });

               }
            }
        });
    }
    private void updateInfosById(){
        Map<String,String> params = new HashMap<>();
        params.put("id", Common.getClientId());
        AVCloud.rpcFunctionInBackground("getInfosById", params, new FunctionCallback<Object>() {
            @Override
            public void done(final Object object, AVException e) {
                if(e == null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            manageInfoList = Utils.parseInfosFromJson(object);
                            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.manage_recycle);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            myInfoadapter = new MyInfoAdapter(manageInfoList);
                            recyclerView.setAdapter(myInfoadapter);
                            //Toast.makeText(MainActivity.this,infoList.get(0).getWritername(),Toast.LENGTH_SHORT).show();
                            //Log.i(Utils.TAG, "data:"+infoList.get(0).getWritername());
                            //adapter = new InfoAdapter(mInformationArrayList);
                            //adapter.notifyDataSetChanged();
                            if(manageSwipe.isRefreshing()){
                                manageSwipe.setRefreshing(false);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //find
        setFindView();
        updateInfos();

        //manage
        updateInfosById();

    }
}
