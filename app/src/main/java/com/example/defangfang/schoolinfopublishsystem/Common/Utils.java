package com.example.defangfang.schoolinfopublishsystem.Common;

import android.util.Log;

import com.example.defangfang.schoolinfopublishsystem.module.Information;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by defangfang on 2017/5/10.
 */

public class Utils {

    public static final String TAG = "SIPSLog";
    public static ArrayList<Information> parseInfosFromJson(Object object){
        ArrayList<Information> informationArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(object.toString());
            for(int i = 0;i<jsonArray.length();i++){
                Information information = new Information();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                information.setId(jsonObject.getString("objectId"));
                jsonObject = jsonObject.getJSONObject("serverData");
                information.setTitle(jsonObject.getString("title"));
                information.setDetail(jsonObject.getString("detail"));
                information.setPublishTime(new Date(jsonObject.getLong("time")));
                //Log.i(TAG, "parseInfosFromJson: "+information.getPublishTime().toString());
                information.setWriterId(jsonObject.getString("writerId"));
                information.setWritername(jsonObject.getString("writername"));
                informationArrayList.add(information);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return informationArrayList;
    }
}
