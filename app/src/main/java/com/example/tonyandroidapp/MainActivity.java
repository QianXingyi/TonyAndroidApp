package com.example.tonyandroidapp;





import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;



public class MainActivity extends Activity {

	private Boolean isOpened=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				if(isOpened==false)
				{startActivity(new Intent(getApplicationContext(),LoginActivity.class));
				finish();}
				return false;
			}
		}).sendEmptyMessageDelayed(0,10000);//延时10秒

	}
	public void onClicked(View v){
		switch(v.getId()){
		case R.id.editBtn:
			Intent i1=new Intent();
			ComponentName component=new ComponentName(getApplicationContext(), LoginActivity.class);
			i1.setComponent(component);
			startActivity(i1);
			isOpened=true;
			finish();
			break;
		case R.id.button2:

			Intent i2=new Intent();
			i2.setAction(Intent.ACTION_CALL);
			i2.setData(Uri.parse("tel:18952669536"));
			startActivity(i2);
			break;

		}

	}
}
