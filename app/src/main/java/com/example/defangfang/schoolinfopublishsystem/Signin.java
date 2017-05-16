package com.example.defangfang.schoolinfopublishsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        name = (EditText)findViewById(R.id.name_signin);
        password = (EditText)findViewById(R.id.password_signin);
        signin = (Button)findViewById(R.id.login_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strname = name.getText().toString();
                final String strpas = password.getText().toString();
                if(strname.equals("")||strpas.equals("")){
                    Toast.makeText(Signin.this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String,String> params = new HashMap<>();
                    params.put("name",strname);
                    params.put("password",strpas);
                    AVCloud.callFunctionInBackground("signin", params, new FunctionCallback<Object>() {
                        @Override
                        public void done(Object object, AVException e) {
                            if(e == null){
                                if (object.toString().equals("true")){
                                    Toast.makeText(Signin.this,"注册成功！即将跳转至登录界面",Toast.LENGTH_SHORT).show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try{
                                                Thread.sleep(2000);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(Signin.this,Login.class);
                                            intent.putExtra("name",strname);
                                            intent.putExtra("password",strpas);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).start();
                                }
                                else {
                                    Toast.makeText(Signin.this,"账号已被注册！请换一个账号！",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Signin.this,"注册失败！请稍后重试！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
