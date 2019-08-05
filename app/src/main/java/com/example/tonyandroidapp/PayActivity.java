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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

public class PayActivity extends Activity {
	private int map,read,write;
	private String name,id,pass,phone;
	private SharedPreferences shared;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay);
		Intent intent=getIntent();
		Bundle data1=intent.getExtras();
		name=data1.getString("Name", "pay");
		id=data1.getString("Id", "pay");
		pass=data1.getString("Pass", "pay");
		phone=data1.getString("Phone", "pay");
		map=data1.getInt("map", 0);
		read=data1.getInt("read",0);
		write=data1.getInt("write", 0);

		Thread th=new Thread(){


			@Override
			public void run() {

				HttpURLConnection conn;
				try {
					String url1="http://www.moecity.cn/Service1.asmx/UpdateUserPermission?uID="+id+"&pID=1&pTime="+map;
					URL u=new URL(url1);
					conn=(HttpURLConnection) u.openConnection();
					conn.connect();
					int recode=conn.getResponseCode();
					if(recode==200){
						InputStream in = conn.getInputStream();
						int n;
						byte[] buffer=new byte[512];
						while((n=in.read(buffer))>0)
							Log.e("new", "new");
					}
					String url2="http://www.moecity.cn/Service1.asmx/UpdateUserPermission?uID="+id+"&pID=2&pTime="+read;
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
					String url3="http://www.moecity.cn/Service1.asmx/UpdateUserPermission?uID="+id+"&pID=3&pTime="+write;
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
					shared=getSharedPreferences("Permission", MODE_PRIVATE);
					Editor editor = shared.edit();
					editor.clear();
					editor.commit();
					editor.putInt("map", map);
					editor.putInt("read", read);
					editor.putInt("write", write);
					editor.commit();
					Log.e("Pay",map+","+read+","+write);
				}
				catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		};
		th.start();
		new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				finish();
				return false;
			}
		}).sendEmptyMessageDelayed(0,5000);
	}

}
