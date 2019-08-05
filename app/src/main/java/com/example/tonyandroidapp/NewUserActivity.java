package com.example.tonyandroidapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity implements OnClickListener{
	private EditText nameText,phoneText,passText,passText2;
	private Button createBtn,backBtn;
	private String name,pass1,pass2,phone,str;
	private Context context;
	private MD5 md5=new MD5();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newuser);
		context=this;
		findById();
	}

	private void findById() {
		// TODO Auto-generated method stub
		nameText= findViewById(R.id.editTextUserName);
		phoneText= findViewById(R.id.editTextUserPhone);
		passText= findViewById(R.id.editTextUserPass);
		passText2= findViewById(R.id.editTextUserPass2);
		createBtn= findViewById(R.id.createBtn);
		backBtn= findViewById(R.id.backLogBtn);
		createBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub  
		switch (arg0.getId()) {
		case R.id.backLogBtn:
			finish();
			break;
		case R.id.createBtn:
			name=nameText.getText().toString();
			pass1=passText.getText().toString();
			pass2=passText2.getText().toString();
			phone=phoneText.getText().toString();
			Thread th4=new Thread(){


				@Override
				public void run() {

					HttpURLConnection conn;
					try {
						Looper.prepare();
						String url1="http://www.moecity.cn/Service1.asmx/FindUserByPhone?searchstring="+phone;
						URL u=new URL(url1);
						conn=(HttpURLConnection) u.openConnection();
						conn.connect();


						int recode=conn.getResponseCode();
						if(recode==200){
							InputStream in = conn.getInputStream();
							str="";
							int n;
							byte[] buffer=new byte[512];
							while((n=in.read(buffer))>0){
								str+=new String(buffer,0,n);
							}
							if(str.contains("<uPhone>"))
							{
								Toast.makeText(getBaseContext(), "该手机号已被使用",
										Toast.LENGTH_SHORT).show();
								Looper.loop();
							}

							else{
								if(pass1.equals(pass2))
								{
									String url2="http://www.moecity.cn/Service1.asmx/InsertUserDetails?uname="+name+"&uPass=0"+"&uPhone="+phone;
									URL u2=new URL(url2);
									conn=(HttpURLConnection) u2.openConnection();
									conn.connect();
									int recode2=conn.getResponseCode();
									if(recode2==200){
										InputStream in2 = conn.getInputStream();
										int n2;
										byte[] buffer2=new byte[512];
										while((n2=in2.read(buffer2))>0)
											Log.e("new", "new");		
									}
									String url0="http://www.moecity.cn/Service1.asmx/UpdateUserPassByPhone?uPhone="+phone+"&newPass="+ MD5.generatePassword(pass1);
									URL u0=new URL(url0);
									conn=(HttpURLConnection) u0.openConnection();
									conn.connect();
									int recode0=conn.getResponseCode();
									if(recode0==200){
										InputStream in0 = conn.getInputStream();
										int n0;
										byte[] buffer0=new byte[512];
										while((n0=in0.read(buffer0))>0)
											Log.e("new", "new");
									}
									String url3="http://www.moecity.cn/Service1.asmx/AddUserPermissionByPhoneAndPName?uPhone="+phone+"&pName=MapAccess&pTime=0";
									URL u3=new URL(url3);
									conn=(HttpURLConnection) u3.openConnection();
									conn.connect();
									int recode3=conn.getResponseCode();
									if(recode3==200){
										InputStream in3 = conn.getInputStream();
										int n3;
										byte[] buffer3=new byte[512];
										while((n3=in3.read(buffer3))>0)
											Log.e("new", "new");
									}
									String url4="http://www.moecity.cn/Service1.asmx/AddUserPermissionByPhoneAndPName?uPhone="+phone+"&pName=Readable&pTime=0";
									URL u4=new URL(url4);
									conn=(HttpURLConnection) u4.openConnection();
									conn.connect();
									int recode4=conn.getResponseCode();
									if(recode4==200){
										InputStream in4 = conn.getInputStream();
										int n4;
										byte[] buffer4=new byte[512];
										while((n4=in4.read(buffer4))>0)
											Log.e("new", "new");
									}
									String url5="http://www.moecity.cn/Service1.asmx/AddUserPermissionByPhoneAndPName?uPhone="+phone+"&pName=Writeable&pTime=0";
									URL u5=new URL(url5);
									conn=(HttpURLConnection) u5.openConnection();
									conn.connect();
									int recode5=conn.getResponseCode();
									if(recode5==200){
										InputStream in5 = conn.getInputStream();
										int n5;
										byte[] buffer5=new byte[512];
										while((n5=in5.read(buffer5))>0)
											Log.e("new", "new");
										Toast.makeText(getBaseContext(), "欢迎你的加入！",
												Toast.LENGTH_SHORT).show();
										Intent intent=new Intent(context,LoginActivity.class);
										startActivity(intent);
									}
								}
								else
									Toast.makeText(getBaseContext(), "确保两次密码输入一致！",
											Toast.LENGTH_SHORT).show();
								Looper.loop();
							}
						}
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			th4.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
