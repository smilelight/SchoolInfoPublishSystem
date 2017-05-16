package com.example.defangfang.schoolinfopublishsystem;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.defangfang.schoolinfopublishsystem.module.Information;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by defangfang on 2017/5/10.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private List<Information> mInformationList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View infoView;
        CircleImageView infoImage;
        TextView infotitle;
        TextView infowriter;
        TextView infodate;
        Button infodetail;

        public ViewHolder(View view){
            super(view);
            infoView = view;
            infoImage = (CircleImageView)view.findViewById(R.id.info_image);
            infotitle = (TextView)view.findViewById(R.id.info_title);
            infowriter = (TextView)view.findViewById(R.id.info_writer);
            infodate = (TextView)view.findViewById(R.id.info_date);
            infodetail = (Button)view.findViewById(R.id.info_detail);
        }
    }

    public InfoAdapter(List<Information> informationList){
        mInformationList = informationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.infomation,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Information information = mInformationList.get(position);
        holder.infotitle.setText(information.getTitle());
        holder.infowriter.setText(information.getWritername());
        Date date = information.getPublishTime();
        final String strdate = ""+(date.getYear()+1900)+"年"+(date.getMonth()+1)+"月"+(date.getDay()+7)+"日"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        holder.infodate.setText(strdate);
        holder.infodetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),InfoDetail.class);
                intent.putExtra("writer",information.getWritername());
                intent.putExtra("title",information.getTitle());
                intent.putExtra("content",information.getDetail());
                intent.putExtra("date",strdate);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInformationList.size();
    }
}
