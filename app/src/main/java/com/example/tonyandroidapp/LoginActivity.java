package com.example.tonyandroidapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	private Button logBtn,newBtn;
	private EditText phoneEd,passEd;
	private String[] strs;
	private String passGet,pass,phone,id,name,str;
	private int map,read,write;
	private SharedPreferences shared;
	private User s=new User();
	private MD5 md5=new MD5();

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		logBtn= findViewById(R.id.login_btn);
		newBtn= findViewById(R.id.new_btn);
		phoneEd= findViewById(R.id.phoneText);
		passEd= findViewById(R.id.passText);
		logBtn.setOnClickListener(this);
		newBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.login_btn:

			str="";
			phone=phoneEd.getText().toString();
			pass=passEd.getText().toString();

			Thread th4=new Thread(){
				@Override
				public void run() {

					HttpURLConnection conn;
					try {
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
							try {
								strs=str.split("uPass>");
								strs=strs[1].split("</");
								passGet=strs[0];
								s.setPass(pass);

								strs=str.split("uID>");
								strs=strs[1].split("</");
								id=strs[0];
								s.setId(id);

								strs=str.split("uname>");
								strs=strs[1].split("</");
								name=strs[0];
								s.setName(name);

								s.setPhone(phone);


							} catch (Exception e) {
								// TODO: handle exception
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
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if(MD5.generatePassword(pass).equals(passGet))
				{
					Thread th=new Thread(){
						@Override
						public void run() {

							HttpURLConnection conn;
							try {
								String url2="http://www.moecity.cn/Service1.asmx/FindUserPermission?uID="+s.getId();
								URL u2=new URL(url2);
								conn=(HttpURLConnection) u2.openConnection();
								conn.connect();


								int recode2=conn.getResponseCode();
								//Log.e("12345678", recode2+"");
								if(recode2==200){
									InputStream in2 = conn.getInputStream();
									str="";
									int n2;
									byte[] buffer2=new byte[512];
									while((n2=in2.read(buffer2))>0){
										str+=new String(buffer2,0,n2);
									}
									Log.e("tresr", str);
									if(str.contains("<details>pID1uID"+s.getId()+"pTimes1</details>"))
										map=1;
									else map=0;
									if(str.contains("<details>pID2uID"+s.getId()+"pTimes1</details>"))
										read=1;
									else read=0;
									if(str.contains("<details>pID3uID"+s.getId()+"pTimes1</details>"))
										write=1;
									else write=0;
									//Log.e("permission", map+","+read+","+write);
									shared=getSharedPreferences("Permission", MODE_PRIVATE);
									Editor editor = shared.edit();
									editor.putInt("map", map);
									editor.putInt("read", read);
									editor.putInt("write", write);
									editor.commit();



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
					th.start();


					Bundle data=new Bundle();
					data.putString("Id",s.getId());
					data.putString("Name",s.getName());
					data.putString("Phone",s.getPhone());
					data.putString("Pass",s.getPass());
					Intent intent=new Intent(LoginActivity.this,CoreActivity.class);
					intent.putExtras(data);
					startActivity(intent);
					finish();
				}

				else{
					Toast.makeText(getApplicationContext(), "账号或密码错误，或者未连接到服务器！",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			break;
		case R.id.new_btn:
			Intent intent=new Intent(LoginActivity.this,NewUserActivity.class);
			startActivity(intent);


			break;
		default:
			break;
		}

	}

}
