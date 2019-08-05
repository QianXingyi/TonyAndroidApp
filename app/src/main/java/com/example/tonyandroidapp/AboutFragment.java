package com.example.tonyandroidapp;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AboutFragment extends Fragment {
	private Button payBtn;
	private RadioGroup radioGroup;
	private int map,read,write;
	private String name,id,pass,phone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragement_me, null);  
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		payBtn= getActivity().findViewById(R.id.pay_btn);
		Intent intent=new Intent(getActivity(), LoginActivity.class);
		//Bundle data=new Bundle();
		name=getActivity().getIntent().getStringExtra("Name");
		id=getActivity().getIntent().getStringExtra("Id");
		pass=getActivity().getIntent().getStringExtra("Pass");
		phone=getActivity().getIntent().getStringExtra("Phone");
		payBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle data1=new Bundle();
				data1.putString("Id",id);
				data1.putString("Name",name);
				data1.putString("Phone",phone);
				data1.putString("Pass",pass);
				data1.putInt("map", map);
				data1.putInt("write", write);
				data1.putInt("read", read);
				Intent intent2=new Intent(getActivity(),PayActivity.class);
				intent2.putExtras(data1);
				startActivity(intent2);
			}
		});
		radioGroup= getActivity().findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.radioButton1:
					payBtn.setText("100 Yuan");
					read=write=map=1;
					break;
				case R.id.radioButton2:
					payBtn.setText("50 Yuan");
					read=write=1;
					map=0;
					break;
				case R.id.radioButton3:
					payBtn.setText("0 Yuan");
					read=write=map=0;
					break;

				default:
					break;
				}
			}
		});
	}
}
