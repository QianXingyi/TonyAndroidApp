package com.example.tonyandroidapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeActivity extends Activity {
	private String name,id, pass, phone,pass1,newpass1,newpass2,newName;
	private TextView idText,phoneText;
	private EditText nameEdit,oldPass,newPass1,newPass2;
	private Button saveBtn,backBtn;
	private User s=new User();
	private MD5 md5=new MD5();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		Intent intent=getIntent();
		Bundle data=intent.getExtras();
		name=data.getString("Name", "NoName");
		id=data.getString("Id", "0");
		pass=data.getString("Pass", "NoPass");
		phone=data.getString("Phone", "1111111");
		saveBtn= findViewById(R.id.createBtn);
		backBtn= findViewById(R.id.backLogBtn);
		nameEdit= findViewById(R.id.nameEdit);
		idText= findViewById(R.id.idView1);
		phoneText= findViewById(R.id.phoneView1);
		oldPass= findViewById(R.id.editTextUserPass);
		newPass1= findViewById(R.id.editTextUserPass2);
		newPass2= findViewById(R.id.passText2);
		nameEdit.setText(name);
		idText.setText(id);
		phoneText.setText(phone);
		s.setId(id);
		s.setName(name);
		s.setPhone(phone);
		s.setPass(pass);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Log.e("x", oldPass.getText().toString());
				pass1=oldPass.getText().toString();
				newpass1=newPass1.getText().toString();
				newpass2=newPass2.getText().toString();
				newName=nameEdit.getText().toString();
				if(pass.equals(pass1))
				{
					if(newpass1.equals(newpass2))
					{
						if(newpass1.equals(pass))
							Toast.makeText(getApplicationContext(), "新旧密码不能相同哦！",
									Toast.LENGTH_SHORT).show();
						else{
							Thread th=new Thread(){
								@Override
								public void run() {

									HttpURLConnection conn;
									try {
										String url1="http://www.moecity.cn/Service1.asmx/UpdateUserName?uID="+id+"&newName="+newName;
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
										String url2="http://www.moecity.cn/Service1.asmx/UpdateUserPass?uID="+id+"&newPass="+ MD5.generatePassword(newpass1);
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
									}
									catch (MalformedURLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									s.setId(id);
									s.setName(nameEdit.getText().toString());
									s.setPhone(phone);
									s.setPass(newpass1);

								}
							};
							th.start();
							Toast.makeText(getApplicationContext(), "修改成功！",
									Toast.LENGTH_SHORT).show();
						}

					}else
					{
						Toast.makeText(getApplicationContext(), "两次输入的新密码要相同哦！",
								Toast.LENGTH_SHORT).show();
					}
				}else
					Toast.makeText(getApplicationContext(), "请输入正确密码！",
							Toast.LENGTH_SHORT).show();
			}


		});
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle data=new Bundle();
				data.putString("Id",s.getId());
				data.putString("Name",s.getName());
				data.putString("Phone",s.getPhone());
				data.putString("Pass",s.getPass());
				data.putBoolean("isUser", true);
				Intent intent=new Intent(ChangeActivity.this,CoreActivity.class);
				intent.putExtras(data);
				startActivity(intent);
				finish();
			}
		});

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Bundle data=new Bundle();
		data.putString("Id",s.getId());
		data.putString("Name",s.getName());
		data.putString("Phone",s.getPhone());
		data.putString("Pass",s.getPass());
		data.putBoolean("isUser", true);
		Intent intent=new Intent(ChangeActivity.this,CoreActivity.class);
		intent.putExtras(data);
		startActivity(intent);
		finish();
	}
}
