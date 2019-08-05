package com.example.tonyandroidapp;

public class Member {
	private Integer id;
	private String name,info;
	Member(){
		name="Noname";
		info="Noinfo";

	}
	Member(String name,String info){
		name=this.name;
		info=this.info;
	}
	Member(Integer id,String name,String info){
		name=this.name;
		info=this.info;
		id=this.id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}


}
