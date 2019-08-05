package com.example.tonyandroidapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MemberDAO extends SQLiteOpenHelper {

	public static final String DB_NAME="member.db";
	public static final int DB_VRESION=1;

	private static final String TABLE_NAME="member";
	private static final String SQL_CREATE_TABLE="create table "+TABLE_NAME+
			" (_id integer primary key autoincrement,"+
			" name text not null, info text);";
	public MemberDAO(Context context){
		this(context, "member.db", null, 1);
	}
	public MemberDAO(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	public void update(Member entity){
		SQLiteDatabase db=getReadableDatabase();
		String[] arg1=new String[3];
		arg1[0]=entity.getName();
		arg1[1]=entity.getInfo();
		arg1[2]=entity.getId().toString();
		db.execSQL("update "+TABLE_NAME+" set name=?,info=? where _id=?",arg1);
		db.close();
	}
	public void insert(Member entity){
		SQLiteDatabase db=this.getWritableDatabase();
		String[] args=new String[2];

		args[0]=entity.getName();
		args[1]=entity.getInfo();
		db.execSQL("INSERT INTO "+TABLE_NAME+" (name,info) VALUES (?,?)", args);
		db.close();

	}
	public void deleteById(Integer id){
		SQLiteDatabase db=this.getReadableDatabase();
		String[] args=new String[1];
		args[0]=id.toString();
		db.execSQL("delete from "+TABLE_NAME+" where _id=?",args);
		db.close();
	}

	public Member getById(Integer id){
		Member entity=new Member();
		SQLiteDatabase db=this.getReadableDatabase();
		String[] args=new String[1];
		args[0]=id.toString();
		Cursor cr;
		cr=db.rawQuery("select * from "+TABLE_NAME+" where _id=?",args);
		if (cr.moveToFirst())
		{
			entity.setId(cr.getInt(0));
			entity.setName(cr.getString(cr.getColumnIndex("name")));
			entity.setInfo(cr.getString(cr.getColumnIndex("info")));
		}
		db.close();
		return entity;
	}
	public Member[] getAll(){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cr;
		cr=db.query(TABLE_NAME, null, null, null, null, null, null);
		return ConvertToMember(cr);  
	}
	private Member[] ConvertToMember(Cursor cursor){  
		int resultCounts=cursor.getCount();  
		if(resultCounts==0||!cursor.moveToFirst()){  
			return null;  
		}  
		Member[] members=new Member[resultCounts];  
		for(int i=0;i<resultCounts;i++){  
			members[i]=new Member();  
			members[i].setId(cursor.getInt(0));  
			members[i].setName(cursor.getString(cursor.getColumnIndex("name")));
			members[i].setInfo(cursor.getString(cursor.getColumnIndex("info")));
			cursor.moveToNext();  
		}  
		return members;  
	}
}
