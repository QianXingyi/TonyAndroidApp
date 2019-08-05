package com.example.tonyandroidapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
	private Handler handler;
	// 定义与服务器通信的子线程
	private ClientThread clientThread;
	private String str="",phone,fromWho,msgWhat,getMsg,saveMsg,strMsg="";
	private String[] strs;
	private int msgText=0;
	private SharedPreferences shared,sharedMsg;
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new MyBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		shared=getSharedPreferences("Phone", MODE_PRIVATE);
		phone=shared.getString("Phone", "NoPhone");
		//Log.e(phone, phone);
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				msgText=0;
				// 如果消息来自于子线程
				if (msg.what == 0x123)
				{
					getMsg=msg.obj.toString()+"\n";
					if(getMsg.contains("To"+phone+"@"))
					{msgText=1;
					str=str+"\n" + msg.obj.toString();
					strs=getMsg.split("To"+phone+"@");
					fromWho=strs[0];
					msgWhat=strs[1];
					Intent bintent=new Intent(MyService.this, CoreActivity.class);
					NotificationCompat.Builder mBuilder =new NotificationCompat.Builder(MyService.this,"default")
					.setContentTitle(fromWho).setContentText(msgWhat).
					setSmallIcon(R.drawable.qt_logo)
					.setTicker("您有新消息").
					setDefaults(Notification.DEFAULT_VIBRATE);
					NotificationManager mNotificationManager=
							(NotificationManager) MyService.this.getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(0,mBuilder.build());
					getMsg="";
					strMsg=strMsg+fromWho+"\n"+msgWhat+"\n";
					}
				}
			}
		};
		clientThread = new ClientThread(handler);
		// 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
		new Thread(clientThread).start();
		Log.e("MyService", "on Create");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	public class MyBinder extends Binder {

		public String chatInfo() {
			//Log.e("test", str);
			return str;
		}
		public int getmsgText() {
			return msgText;
		}
		public String chatPre() {
			saveMsg=strMsg;

			//Log.e("Msg",strMsg);
			return saveMsg;
		}
	}
}
