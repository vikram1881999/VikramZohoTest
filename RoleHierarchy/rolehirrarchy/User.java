package com.vikram.rolehirrarchy;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	public String name;
	public Role userRole;
	public List<String> subUsers = new ArrayList<>();
	public User reportingUser;

}