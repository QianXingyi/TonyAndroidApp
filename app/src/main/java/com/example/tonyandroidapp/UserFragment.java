package com.example.tonyandroidapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserFragment extends Fragment {
	private String name,id,pass,phone;
	private TextView nameText,idText,phoneText;
	private Button changeBtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_user, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Intent intent=new Intent(getActivity(), LoginActivity.class);
		//Bundle data=new Bundle();
		name=getActivity().getIntent().getStringExtra("Name");
		id=getActivity().getIntent().getStringExtra("Id");
		pass=getActivity().getIntent().getStringExtra("Pass");
		phone=getActivity().getIntent().getStringExtra("Phone");
		changeBtn= getActivity().findViewById(R.id.editBtn);
		nameText= getActivity().findViewById(R.id.nameView);
		idText= getActivity().findViewById(R.id.idView);
		phoneText= getActivity().findViewById(R.id.phoneView);
		nameText.setText(name);
		idText.setText(id);
		phoneText.setText(phone);
		changeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Bundle data1=new Bundle();
				data1.putString("Id",id);
				data1.putString("Name",name);
				data1.putString("Phone",phone);
				data1.putString("Pass",pass);
				Intent intent2=new Intent(getActivity(),ChangeActivity.class);
				intent2.putExtras(data1);
				startActivity(intent2);
				getActivity().finish();

			}
		});

	}
}
