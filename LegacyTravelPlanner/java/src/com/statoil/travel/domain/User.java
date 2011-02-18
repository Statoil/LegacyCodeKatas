package com.statoil.travel.domain;

public class User {
	String name;
	
	public static User withName(String name) {
		User user = new User();
		user.setName(name);
		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
