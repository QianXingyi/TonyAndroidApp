package com.example.tonyandroidapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.tonyandroidapp.MyService.MyBinder;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CoreActivity extends Activity implements OnClickListener{
	private FragmentManager fm;
	private FragmentTransaction ft;
	private Button meBtn,userBtn,chatBtn,payBtn;
	private List<Button> btnList = new ArrayList<Button>();
	private Intent service;
	private MyBinder binder;
	private ServiceConnection conn;
	private Timer timer;
	private String x,y,z;
	private int msgNum=0,msgText=0;
	private String name;
	private String id;
	private String pass;
	private String phone;
	private Boolean isUser;
	private SharedPreferences shared,sharedMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_core);
		service=new Intent(getApplicationContext(),MyService.class);
		Intent intent=getIntent();
		Bundle data=intent.getExtras();
		name=data.getString("Name", "NoName");
		id=data.getString("Id", "0");
		pass=data.getString("Pass", "NoPass");
		phone=data.getString("Phone", "1111111");
		isUser=data.getBoolean("isUser", false);
		shared=getSharedPreferences("Phone", MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putString("Phone", phone);
		editor.commit();
		Bundle data1=new Bundle();
		data1.putString("Id",id);
		data1.putString("Name",name);
		data1.putString("Phone",phone);
		data1.putString("Pass",pass);
		Intent intent2=new Intent(CoreActivity.this,ChangeActivity.class);
		intent2.putExtras(data1);
		Intent intent3=new Intent(CoreActivity.this,AddActivity.class);
		intent3.putExtras(data1);
		findById();

		conn=new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onServiceConnected(ComponentName arg0, IBinder arg1) {
				// TODO Auto-generated method stub
				binder=(MyBinder) arg1;
				Log.e("MyService", "get Bind");

			}
		};
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		setBackgroundColorById(R.id.userinfo_btn);
		ft.replace(R.id.fragment_content, new ChatFragment());
		ft.commit();
		chatBtn.performClick();
		if(isUser)userBtn.performClick();
		timer=new Timer();
		setTimerTask();
	}

	private void setTimerTask() {
		// TODO Auto-generated method stub
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();  
				message.what = 1;  
				doActionHandler.sendMessage(message);
			}
		}, 500,1000);
	}
	private Handler doActionHandler = new Handler() {  
		private boolean isStart;

		@Override  
		public void handleMessage(Message msg) {  
			super.handleMessage(msg);  
			int msgId = msg.what;  
			switch (msgId) {  
			case 1:  
				// do some action  
				if(!isStart)
					isStart=bindService(service, conn , Context.BIND_AUTO_CREATE);
				else if(isStart){
					x=binder.chatInfo();
					//chatBtn.setText(x);
					z=binder.chatPre();
					sharedMsg=getSharedPreferences("Msg", MODE_PRIVATE);
					Editor editor = sharedMsg.edit();
					editor.putString("Msg", z);
					editor.commit();
					msgText=binder.getmsgText();
					if(msgText==1&&(!y.equals(x)))
						msgNum++;
					btnOperat();
					y=x;
				}
				break;  
			default:  
				break;  
			}  
		}

		private void btnOperat() {
			// TODO Auto-generated method stub
			if(msgNum==0)
				chatBtn.setText("Chatting");
			else
				chatBtn.setText(msgNum+" news");
		}  
	}; 


	private void findById() {
		// TODO Auto-generated method stub
		meBtn= findViewById(R.id.aboutme_btn);
		chatBtn= findViewById(R.id.chat_btn);
		userBtn= findViewById(R.id.userinfo_btn);
		payBtn= findViewById(R.id.pay_btn);
		meBtn.setOnClickListener(this);
		userBtn.setOnClickListener(this);
		chatBtn.setOnClickListener(this);
		btnList.add(meBtn);
		btnList.add(chatBtn);
		btnList.add(userBtn);
	}
	private void setBackgroundColorById(int btnId) {
		for (Button btn : btnList) {
			if (btn.getId() == btnId) {
				btn.setBackgroundColor(Color.rgb(30, 185, 242));
			} else {
				btn.setBackgroundColor(Color.WHITE);

			}
		}
	}

	@Override
	public void onClick(View v) {
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.aboutme_btn:
			ft.replace(R.id.fragment_content, new AboutFragment());
			setBackgroundColorById(R.id.aboutme_btn);
			ft.commit();

			break;
		case R.id.chat_btn:
			ft.replace(R.id.fragment_content, new ChatFragment());
			setBackgroundColorById(R.id.chat_btn);
			ft.commit();
			msgNum=0;
			//startActivity(new Intent(getApplicationContext(),FriendsActivity.class));
			break;
		case R.id.userinfo_btn:
			ft.replace(R.id.fragment_content, new UserFragment());
			setBackgroundColorById(R.id.userinfo_btn);
			ft.commit();
			break;
		default:
			break;
		}

	}
}
