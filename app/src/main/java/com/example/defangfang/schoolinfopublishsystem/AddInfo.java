package com.example.defangfang.schoolinfopublishsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
import com.example.defangfang.schoolinfopublishsystem.Common.Common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddInfo extends AppCompatActivity {
    private EditText addInfoTitle;
    private EditText addInfoContent;
    private Button addInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        addInfoTitle = (EditText)findViewById(R.id.add_info_title);
        addInfoContent = (EditText)findViewById(R.id.add_info_detail);
        addInfoButton = (Button)findViewById(R.id.add_info_publish);
        addInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = addInfoTitle.getText().toString();
                String content = addInfoContent.getText().toString();
                if(title.equals("")||content.equals("")){
                    Toast.makeText(AddInfo.this,"标题或内容不能为空！",Toast.LENGTH_SHORT).show();
                } else {
                    Map<String,Object> params = new HashMap<>();
                    params.put("writername",getIntent().getStringExtra("writername"));
                    params.put("title",title);
                    params.put("content",content);
                    params.put("publishtime",(new Date()).getTime());
                    params.put("writerId", Common.getClientId());
                    AVCloud.callFunctionInBackground("addInfo", params, new FunctionCallback<Object>() {
                        @Override
                        public void done(Object object, AVException e) {
                            if(e == null){
                                if(object.toString().equals("true")){
                                    Toast.makeText(AddInfo.this,"信息发布成功！即将跳转至主界面！",Toast.LENGTH_SHORT).show();
                                    try {
                                        Thread.sleep(4000);
                                        finish();
                                    } catch (Exception exe){
                                        exe.printStackTrace();
                                    }

                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
