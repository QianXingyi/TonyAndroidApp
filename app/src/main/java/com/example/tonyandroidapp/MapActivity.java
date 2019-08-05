package com.example.tonyandroidapp;

import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {
	private TextView text1;
	private MapView mapview;
	private BaiduMap map;
	private double lat;
	private double lon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		Intent intent=getIntent();
		Bundle data=intent.getExtras();

		lat=data.getDouble("Lat", lat);
		lon=data.getDouble("Lon", lon);
		mapview= findViewById(R.id.bmapView);

		//make cit center
		map=mapview.getMap();
		LatLng point=new LatLng(lat, lon);//cit gps info
		centerPoint(point);
		//add a mark to point
		addIcon(point);     
		text1= findViewById(R.id.maptextView1);
		text1.setText("lat:"+lat+"lon"+lon);
	}
	private void centerPoint(LatLng point) {
		// TODO Auto-generated method stub
		MapStatus arg0=new MapStatus.Builder().target(point).zoom(18).build();
		MapStatusUpdate status=MapStatusUpdateFactory.newMapStatus(arg0);
		map.setMapStatus(status);

	}
	private void addIcon(LatLng point) {
		// TODO Auto-generated method stub
		MarkerOptions option=new MarkerOptions();
		option.position(point);
		option.icon(BitmapDescriptorFactory.fromResource(R.drawable.dot));
		map.addOverlay(option);

	}
	@Override
	protected void onDestroy() {  
		super.onDestroy();  

		mapview.onDestroy();  
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  

		mapview.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  

		mapview.onPause();  
	}  
}
