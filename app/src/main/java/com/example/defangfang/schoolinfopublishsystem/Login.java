package com.example.defangfang.schoolinfopublishsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FunctionCallback;
import com.example.defangfang.schoolinfopublishsystem.Common.Common;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText textname;
    private EditText textpassword;
    private CheckBox rempas;
    private CheckBox autologin;
    private Button signin;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textname = (EditText)findViewById(R.id.login_account);
        textpassword = (EditText)findViewById(R.id.login_password);
        rempas = (CheckBox)findViewById(R.id.login_rempas);
        autologin = (CheckBox)findViewById(R.id.login_autologin);
        signin = (Button)findViewById(R.id.login_signin);
        login = (Button)findViewById(R.id.login_login);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signin.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strname = textname.getText().toString();
                final String strpas = textpassword.getText().toString();
                if(strname.equals("")||strpas.equals("")){
                    Toast.makeText(Login.this,"账号或密码不能为空！",Toast.LENGTH_SHORT).show();
                } else {
                    Map<String,String> params = new HashMap<>();
                    params.put("name",strname);
                    params.put("password",strpas);
                    AVCloud.callFunctionInBackground("login", params, new FunctionCallback<Object>() {
                        @Override
                        public void done(Object object, AVException e) {
                            if(e == null){
                                if(!object.toString().equals("false")){
                                    Common.saveInfo(strname,strpas);
                                    Common.setClientId(object.toString());
                                    saveInfo();
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    intent.putExtra("name",strname);
                                    intent.putExtra("password",strpas);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this,"账号或密码错误！请重新输入！",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this,"没有此账号！请重新输入！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
        Intent intent = getIntent();
        if(intent.getStringExtra("name") != null){
            textname.setText(intent.getStringExtra("name"));
            textpassword.setText(intent.getStringExtra("password"));
        }
        initState();
    }
    private void initState(){
        SharedPreferences sp = getSharedPreferences(Common.INFOFILENAME,MODE_PRIVATE);
        String stracc = sp.getString(Common.NAME,"");
        String strpas = sp.getString(Common.PASSWORD,"");
        String strId = sp.getString(Common.CLIENTID,"");
        if (!stracc.equals("")){
            textname.setText(stracc);
           textpassword.setText(strpas);
            if(sp.getBoolean(Common.AUTOLOGIN,false)){
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(Login.this,MainActivity.class);
                intent.putExtra("name",stracc);
                intent.putExtra("password",strpas);
                startActivity(intent);
            }
        }
        if(sp.getBoolean(Common.SAVEINFO,false)){
            rempas.setChecked(true);
        }
        if(!strId.equals("")){
            Common.setClientId(strId);
        }
    }
    private void saveInfo(){
        if(rempas.isChecked()){
            SharedPreferences sp = getSharedPreferences(Common.INFOFILENAME,MODE_PRIVATE);
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(Common.NAME,textname.getText().toString());
            spe.putString(Common.PASSWORD,textpassword.getText().toString());
            spe.putString(Common.CLIENTID,Common.getClientId());
            spe.putBoolean(Common.SAVEINFO,true);
            spe.putBoolean(Common.AUTOLOGIN,autologin.isChecked());
            spe.apply();
        }
    }
}
