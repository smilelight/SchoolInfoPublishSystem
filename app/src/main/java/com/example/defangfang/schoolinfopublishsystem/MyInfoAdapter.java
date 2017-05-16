package com.example.defangfang.schoolinfopublishsystem;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
import com.example.defangfang.schoolinfopublishsystem.module.Information;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by defangfang on 2017/5/11.
 */

public class MyInfoAdapter extends RecyclerView.Adapter<MyInfoAdapter.ViewHolder> {
    public static boolean isediting = false;
    private List<Information> mInformationList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View infoView;
        CircleImageView infoImage;
        TextView infotitle;
        TextView infodate;
        TextView infocontent;
        Button infodeilte;

        public ViewHolder(View view){
            super(view);
            infoView = view;
            infoImage = (CircleImageView)view.findViewById(R.id.myinfo_image);
            infotitle = (TextView)view.findViewById(R.id.myinfo_title);
            infodate = (TextView)view.findViewById(R.id.myinfo_date);
            infocontent = (TextView) view.findViewById(R.id.myinfo_content);
            infodeilte = (Button)view.findViewById(R.id.myinfo_delite);
        }
    }
    public MyInfoAdapter(List<Information> informationList){
        mInformationList = informationList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myinfo,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Information information = mInformationList.get(position);
        holder.infotitle.setText(information.getTitle());
        Date date = information.getPublishTime();
        final String strdate = ""+(date.getYear()+1900)+"年"+(date.getMonth()+1)+"月"+(date.getDay()+7)+"日"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        holder.infodate.setText(strdate);
        holder.infocontent.setText(information.getDetail());
        holder.infodeilte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String id = information.getId();
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                AVCloud.callFunctionInBackground("deliteInfoById", params, new FunctionCallback<Object>() {
                    @Override
                    public void done(Object object, AVException e) {
                        if(e == null){
                            if(object.toString().equals("true")){
                                Toast.makeText(v.getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                                mInformationList.remove(position);
                                notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(v.getContext(),"删除失败！请稍后重试！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        if(isediting){
            holder.infodeilte.setVisibility(View.VISIBLE);
        } else {
            holder.infodeilte.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return mInformationList.size();
    }
}
