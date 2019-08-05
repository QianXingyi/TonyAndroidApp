package com.example.tonyandroidapp;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SocketActivity extends Activity {

	private int map,read,write;
	private String name,uid,pass,phone,chName,chPhone;
	private String str="",fromWho,msgWhat,getMsg,realName,strMsg,saidMsg;
	private String[] strs,real,real2,said,said1,said2;
	private TextView titleView;
	private SharedPreferences shared,sharedPermission;
	private double lat=1;
	private double lon=1;
	private EditText input;
	private TextView show;
	private Button send,mapBtn;
	private Handler handler;
	private ClientThread clientThread;
	private Context thisContext;
	private String locationProvider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket);
		//Log.e("socket",map+","+read+","+write);
		sharedPermission=getSharedPreferences("Permission", MODE_PRIVATE);
		map=sharedPermission.getInt("map", 0);
		read=sharedPermission.getInt("read", 0);
		write=sharedPermission.getInt("write", 0);
		//Log.e("socket",map+","+read+","+write);

		Intent intent=getIntent();
		Bundle data1=intent.getExtras();
		name=data1.getString("Name", "add");
		uid=data1.getString("Id", "add");
		pass=data1.getString("Pass", "add");
		phone=data1.getString("Phone", "add");
		chName=data1.getString("chName","外星人");
		chPhone=data1.getString("chPhone","741");
		titleView= findViewById(R.id.titleView);
		titleView.setText("与"+chName+"("+chPhone+")"+"聊天中：\n");
		input = findViewById(R.id.input);
		send = findViewById(R.id.send);
		mapBtn= findViewById(R.id.map_btn);
		show = findViewById(R.id.show);
		show.setMovementMethod(ScrollingMovementMethod.getInstance());
		shared=getSharedPreferences("Msg", MODE_PRIVATE);
		strMsg=shared.getString("Msg", "Msg");
		Log.e(strMsg,strMsg);
		show.append(strMsg);
		show.append("\n以上是历史消息\n");
		Editor editor = shared.edit();
		editor.clear();
		editor.commit();
		thisContext=this;
		try {


			LocationManager man=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
			List<String> providers = man.getProviders(true);
			if(providers.contains(LocationManager.NETWORK_PROVIDER)){   
				locationProvider = LocationManager.NETWORK_PROVIDER;  
			}else if(providers.contains(LocationManager.GPS_PROVIDER)){  
				locationProvider = LocationManager.GPS_PROVIDER;  
			}else{  
				Toast.makeText(this, "未打开定位服务或未给应用授权！", Toast.LENGTH_SHORT).show();  
				return ;  
			}
			man.requestLocationUpdates(locationProvider, 5000, 50, new LocationListener() {



				@Override
				public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderDisabled(String arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLocationChanged(Location arg0) {
					// TODO Auto-generated method stub
					lat = arg0.getLatitude();
					lon = arg0.getLongitude();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 如果消息来自于子线程
				if (msg.what == 0x123)
				{
					getMsg=msg.obj.toString()+"\n";

					if(getMsg.contains(phone)&&getMsg.contains(chPhone))
						if(getMsg.contains("From:"+name+"("+phone+")To"))
						{
							strs=getMsg.split(chPhone+"@");
							str="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+name+":\n"+"\t"+strs[1];
						}
						else{
							strs=getMsg.split(phone+"@");
							real=getMsg.split(chPhone);
							real2=real[0].split(":");
							realName=real2[1].replace("(", "");
							saidMsg=strs[1];
							str="\n"+chName+"("+realName+"):\n"+strs[1];

							if(saidMsg.contains("<Map>"))
							{
								said=saidMsg.split(",");
								said1=said[0].split(">");
								said2=said[1].split("</");
								lat=Double.parseDouble(said1[1]);
								lon=Double.parseDouble(said2[0]);
								try {


									new AlertDialog.Builder(thisContext).setTitle("系统提示")//设置对话框标题  

									.setMessage("你的好友发来了一个定位，是否打开？")//设置显示的内容  

									.setPositiveButton("确定",new DialogInterface.OnClickListener() {  



										@Override  

										public void onClick(DialogInterface dialog, int which) {

											// TODO Auto-generated method stub
											if(read==1){
												try {
													Bundle data=new Bundle();
													data.putDouble("Lat", lat);
													data.putDouble("Lon", lon);
													Intent intent=new Intent(thisContext,MapActivity.class);
													intent.putExtras(data);
													startActivity(intent);
												} catch (Exception e) {
													// TODO: handle exception
												}

											}
											else
												Toast.makeText(thisContext, "您未开通该服务", Toast.LENGTH_SHORT).show();
										}  

									}).setNegativeButton("返回",new DialogInterface.OnClickListener() {

										@Override  

										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
										}  

									}).show();
								} catch (Exception e) {
									// TODO: handle exception
								}
							}

						}

					show.append(str);
				}
			}
		};
		clientThread = new ClientThread(handler);
		// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
		new Thread(clientThread).start();
		Log.e("Thread", "Thread");
		mapBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				try
				{
					// 当用户按下发送按钮后，将用户输入的数据封装成Message
					// 然后发送给子线程的Handler
					if(map==1){
						Message msg = new Message();
						msg.what = 0x345;
						msg.obj = "From:"+name+"("+phone+")To"+chPhone+"@"+"<Map>"+lat+","+lon+"</Map>";

						clientThread.revHandler.sendMessage(msg);
					}
					else
						Toast.makeText(thisContext, "您未开通该服务", Toast.LENGTH_SHORT).show();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});
		send.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("Threadclick", "Threadclick");

				try
				{
					// 当用户按下发送按钮后，将用户输入的数据封装成Message
					// 然后发送给子线程的Handler
					if(write==1){
						Message msg = new Message();
						msg.what = 0x345;
						msg.obj = "From:"+name+"("+phone+")To"+chPhone+"@"+input.getText().toString();
						clientThread.revHandler.sendMessage(msg);
						input.setText("");
					}
					else
						Toast.makeText(thisContext, "您未开通该服务", Toast.LENGTH_SHORT).show();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
