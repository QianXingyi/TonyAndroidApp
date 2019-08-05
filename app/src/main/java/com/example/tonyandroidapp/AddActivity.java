package com.example.tonyandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {
	private MemberDAO memberDAO=new MemberDAO(this);
	private EditText edit1,edit2;
	private Button but1,but2;
	private int id;
	private Member[] members;
	private String name;
	private String uid;
	private String pass;
	private String phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);
		Intent intent=getIntent();
		Bundle data1=intent.getExtras();
		name=data1.getString("Name", "add");
		uid=data1.getString("Id", "add");
		pass=data1.getString("Pass", "add");
		phone=data1.getString("Phone", "add");
		members=memberDAO.getAll();
		edit1= findViewById(R.id.phoneText);
		edit2= findViewById(R.id.passText);
		but1= findViewById(R.id.editBtn);
		but2= findViewById(R.id.button2);

	}
	public void onClicked(View v){
		switch (v.getId()) {
		case R.id.button2:
			Member member=new Member();
			member.setName(edit1.getText().toString()+"");
			member.setInfo(edit2.getText().toString()+"");
			memberDAO.insert(member);
		case R.id.editBtn:
			Bundle data1=new Bundle();
			data1.putString("Id",uid);
			data1.putString("Name",name);
			data1.putString("Phone",phone);
			data1.putString("Pass",pass);
			data1.putBoolean("isUser", false);
			Intent intent2=new Intent(AddActivity.this,CoreActivity.class);
			intent2.putExtras(data1);
			startActivity(intent2);
			finish();
			break;

		default:
			break;
		}
	}
}

