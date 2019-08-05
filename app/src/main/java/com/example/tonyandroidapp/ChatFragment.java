package com.example.tonyandroidapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ChatFragment extends Fragment implements OnClickListener{
	private MemberDAO memberDAO;
	private Button but;
	private ListView list;
	private TextView textView;
	private List<Map<String, String>> arrayList=new ArrayList<Map<String, String>>();
	private SimpleAdapter simpleAdapter;
	private Member[] members;
	private int id;
	private String chatname;
	private Boolean isChanged=false;
	private Timer timer;
	private String name,z;
	private String uid;
	private String pass;
	private String phone;
	private void setData(){
		members=memberDAO.getAll();
		Map<String, String> map;  
		if(members==null){   
			return;
		}  
		arrayList.clear();
		int count=members.length;  
		if(count!=0){  
			for(int i=0;i<count;i++){  
				map=new HashMap<String, String>(); 
				map.put("id", members[i].getId().toString());
				map.put("name", members[i].getName());  
				map.put("info", members[i].getInfo());  
				arrayList.add(map);  
			}  
		}  
		simpleAdapter=new SimpleAdapter(getActivity(),   
				arrayList, R.layout.simple_item, new String[]{"id","name","info"},  
				new int[]{R.id.id,R.id.name,R.id.info});  
		list.setAdapter(simpleAdapter);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_chat, null);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		but= getActivity().findViewById(R.id.editBtn);
		but.setOnClickListener(this);
		super.onActivityCreated(savedInstanceState);
		name=getActivity().getIntent().getStringExtra("Name");
		uid=getActivity().getIntent().getStringExtra("Id");
		pass=getActivity().getIntent().getStringExtra("Pass");
		phone=getActivity().getIntent().getStringExtra("Phone");
		memberDAO=new MemberDAO(this.getActivity());
		list= getActivity().findViewById(R.id.listView1);
		textView= getActivity().findViewById(R.id.textViewFriends);
		setData();
		try {
			textView.setText("共有"+memberDAO.getAll().length+"个好友");
		} catch (Exception e) {
			// TODO: handle exception
		}



		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int id,
					long arg3) {
				// TODO Auto-generated method stub

				Bundle data1=new Bundle();
				data1.putString("Id",uid);
				data1.putString("Name",name);
				data1.putString("Phone",phone);
				data1.putString("Pass",pass);
				data1.putString("chPhone", members[id].getInfo());
				data1.putString("chName", members[id].getName());
				Intent intent3=new Intent(getActivity(),SocketActivity.class);
				intent3.putExtras(data1);
				startActivity(intent3);

			}

		});
		//单击用户，编辑用户信息
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				//id=arg2+1;
				TextView tv= arg1.findViewById(R.id.id);
				String str=tv.getText().toString();
				id=Integer.parseInt(str);

				chatname=members[arg2].getName();
				new AlertDialog.Builder(getActivity()).setTitle("系统提示")//设置对话框标题  

				.setMessage("是否删除好友:"+chatname)//设置显示的内容  

				.setPositiveButton("确定",new DialogInterface.OnClickListener() {//娣诲姞纭畾鎸夐挳  



					@Override  

					public void onClick(DialogInterface dialog, int which) {//纭畾鎸夐挳鐨勫搷搴斾簨浠�  

						// TODO Auto-generated method stub
						memberDAO.deleteById(id);
						//isChanged=true;

						//reFresh();
						setData();

					}  

				}).setNegativeButton("返回",new DialogInterface.OnClickListener() {

					@Override  

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}  

				}).show();
				return false;
			}
		});

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.editBtn:
			Bundle data1=new Bundle();
			data1.putString("Id",uid);
			data1.putString("Name",name);
			data1.putString("Phone",phone);
			data1.putString("Pass",pass);
			Intent intent3=new Intent(getActivity(),AddActivity.class);
			intent3.putExtras(data1);
			startActivity(intent3);
			getActivity().finish();
			break;
		default:
			setData();
			break;
		}
	}
}
