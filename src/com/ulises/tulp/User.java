package com.ulises.tulp;

public class User {
	private String name;
	private long points;
	private String mail;
	public User(String mail) {
		super();
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long l) {
		this.points = l;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
