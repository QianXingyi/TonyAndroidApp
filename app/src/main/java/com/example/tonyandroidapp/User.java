package com.example.tonyandroidapp;

public class User {
	private String id,name,phone,pass;
	User(){
		id="0";
		name="Noname";
		phone="000000";
		pass="NoPass";

	}

	User(String id,String name,String phone,String pass){
		name=this.name;
		id=this.id;
		phone=this.phone;
		pass=this.pass;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
