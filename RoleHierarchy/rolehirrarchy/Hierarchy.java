package com.vikram.rolehirrarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hierarchy {

	public volatile List<Role> hierarchy = new ArrayList<>();
	public volatile List<User> userHierarchy =  new ArrayList<>();
	public volatile	Map<String,  List<String>> listUserRole =  new HashMap<String, List<String>>();
	
	/*
	 * Add role
	 * param1 = Name of the role
	 * param2 = Subrole of the role
	 * param3 = reportingRole of this Role
	 */
	public Role addRole(String roleName, String repoertingRole) {
		String exists = null;
		for(int i =0;  i< hierarchy.size() ; i++) {
		if(hierarchy.get(i).role.equalsIgnoreCase(roleName)) {
				exists = "yes";
		}
			
		}
		if(roleName.equalsIgnoreCase("ceo") && exists == null) {
			Role role = new Role();
			role.role = roleName;
			role.reportingRole = null;
			hierarchy.add(0, role);
		}
		if(exists == null) {
			Role role = new Role();
			role.role = roleName;
			role.reportingRole = getReportingRole(repoertingRole);
			hierarchy.add(role);
			return role;
		}
		return null;
	}
	
	public Role getReportingRole(String reportingRole) {
		for(int i = 0; i< hierarchy.size(); i++) {
			if(hierarchy.get(i).role.equalsIgnoreCase(reportingRole)) {
				Role superRole = hierarchy.get(i);
				return superRole;
			}	
		}
		return null;
	}
	
	/*
	 * Add Sub Role
	 * @param subrole-> List of all the provided Subrole 
	 * @param2 parentRole of the subRole
	 */
	public Role addSubrole(List<String> subrole, String parentRole ) {
		for(int i = 0;  i < hierarchy.size(); i++) {
			if(hierarchy.get(i).role.equalsIgnoreCase(parentRole)) {
				hierarchy.get(i).subRole.addAll(subrole);
				return hierarchy.get(i);
			}
		}
		System.out.println("Role doesnt exists please first add the role then add its Sub role");
		return null;
	}
	
	/*
	 * Add SubRole
	 * @param String subrole -> name of the subRole
	 * @param2
	 */
	public Role addSubRole(String subrole,String parentRole) {
		for(int i = 0;  i < hierarchy.size(); i++) {
			if(hierarchy.get(i).role.equalsIgnoreCase(parentRole)) {
				hierarchy.get(i).subRole.add(subrole);
				return hierarchy.get(i);
			}
		}
		return null;
	}
	
	/*
	 * DisplaySubRoles
	 */
	public void displaySubRoles(String role) {
		for(int i = 0;  i < hierarchy.size(); i++) {
			if(role.equalsIgnoreCase(hierarchy.get(i).role)) {
				List<String> subroles  =  hierarchy.get(i).subRole;
				for(int j = 0; j< subroles.size(); j++) {
					System.out.print(subroles.get(j)+ ",");
				}
			}
		}
	}
	
	
	/*
	 * Delete Roles 
	 * param1 = Name of the role
	 * param2 = name of transferRole
	 *
	 */
	public void deleteRole(String roleName, String transferredRole) {
		Iterator<Role> roles = hierarchy.iterator();
		String reportingRole = null;
		while(roles.hasNext()) {
			Role role = roles.next();
			if(role.role.equalsIgnoreCase(roleName)) {
				reportingRole = role.reportingRole.role;
				transferRole(transferredRole, roleName, reportingRole );
				
			}
		}
	}

	private void transferRole(String transferredRole, String deleteRole, String reportingRole) {
		Iterator<Role> roles = hierarchy.iterator();
		while(roles.hasNext()) {
			Role role = roles.next();
			if(role.role.equalsIgnoreCase(deleteRole)) {
				Iterator<String> subRole = role.subRole.iterator();
				while(subRole.hasNext()) {
					String subrole = subRole.next();
					if(subrole.equalsIgnoreCase(transferredRole)) {
						subRole.remove();
					}
				}
				role.role.replaceAll(deleteRole, transferredRole);
			}
			if(role.reportingRole.role.equalsIgnoreCase(reportingRole)) {
				List<String> subUserOfSuperRole = role.reportingRole.subRole;
				Iterator<String> subUserIterator =  subUserOfSuperRole.iterator();
				while(subUserIterator.hasNext()) {
					String subrole = subUserIterator.next();
					if(subrole.equalsIgnoreCase(deleteRole)) {
						subrole.replaceAll(subrole, transferredRole);
					}
				}
			}
		}
		
	}
	
	/*
	 * DisplayAllRolesHirarchy
	 */
	public void DisplayRoleHirarchy() {
		for(int i =0; i< hierarchy.size(); i++) {
			System.out.println(hierarchy.get(i).role+ "->" );
			getSubRoles(hierarchy.get(i).role);
		}
	}
	
	private void getSubRoles(String role) {
		for(int i = 0;  i < hierarchy.size(); i++) {
			if(role.equalsIgnoreCase(hierarchy.get(i).role)) {
				List<String> subroles  =  hierarchy.get(i).subRole;
				for(int j = 0; j< subroles.size(); j++) {
					System.out.print(subroles.get(j)+ ",");
				}
			}
		}
		
	}

	/*
	 * User ADD
	 * @param1 Username
	 * @Param2 UserRole 
	 */
	public User addUser(String name, String userRole, String reportingUser) {
		String exists = null;
		for(int i =0;  i< userHierarchy.size() ; i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(name)) {
				exists = "yes";
			}	
		}
		if(userRole.equalsIgnoreCase("ceo") && exists == null) {
			User  user  =  new User();
			user.name  =  name;
			Iterator<Role> roles =  hierarchy.iterator();
			while(roles.hasNext()) {
				Role role = roles.next();
				if(role.role.equalsIgnoreCase(userRole)) {
					user.userRole = role;
				}
			}
			user.reportingUser = null;
			userHierarchy.add(user);
			return user;
		}
		
		if(exists == null) {
			User  user  =  new User();
			user.name  =  name;
			Iterator<Role> roles =  hierarchy.iterator();
			while(roles.hasNext()) {
				Role role = roles.next();
				if(role.role.equalsIgnoreCase(userRole)) {
					user.userRole = role;
				}
			}
			user.reportingUser = getReportingUser(reportingUser, userRole);
			userHierarchy.add(user);
			return user;
		}
		return null;
	}
	
	private User getReportingUser(String reportingUser, String userRole) {
		for(int i = 0;  i < hierarchy.size(); i++) {
			if(hierarchy.get(i).role.equalsIgnoreCase(userRole)) {
				Role role = hierarchy.get(i).reportingRole;
				User superUser = checkUserWithReportingRole(role);
				return superUser;
			}
		}
		return null;
	}

	private User checkUserWithReportingRole(Role role) {
		for(int i = 0 ; i< userHierarchy.size(); i++) {
			if(role.role.equalsIgnoreCase(userHierarchy.get(i).userRole.role)) {
				return userHierarchy.get(i);
			}
		}
		return null;
	}
	

	/*
	 * Add SubUsers
	 * @param Name of Super user 
	 * @param1 List<String> subUsers
	 */
	public User addSubUser(List<String> subUser,String name) {
		for(int i =0; i < userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(name)) {
				userHierarchy.get(i).subUsers.addAll(subUser);
				return userHierarchy.get(i);
			}
		}
		System.out.println("First Add the USer then add its subroles");
		return null;
	}
	
	/*
	 * Add SubUser
	 * @param String subuser -> name of the subRole
	 * @param2 parentUser
	 */
	public User addSubUser(String subuser,String parentUser) {
		for(int i = 0;  i < userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(parentUser)) {
				userHierarchy.get(i).subUsers.add(subuser);
				return userHierarchy.get(i);
			}
		}
		return null;
	}
	

	/*
	 * Delete User
	 * @param username -> name of the user
	 */
	public void deleteUser(String userName) {
		Iterator<User> users = userHierarchy.iterator();
		while(users.hasNext()) {
			User user = users.next();
			if(user.name.equalsIgnoreCase(userName)) {
				String otherUserWithSameRole = replaceUserWithSameRole(userName);
				user.name.replaceAll(userName, otherUserWithSameRole);
				System.out.println(userName + " is replaced with the user of same role named=> " + otherUserWithSameRole);
			}
		}
		for(int i =0 ; i< userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(userName)) {
				 User  reportUser= userHierarchy.get(i).reportingUser;
				 replaceInReportingUserSubusers(reportUser, userName);
			}
		}
	}
	
	
	
	private void replaceInReportingUserSubusers(User reportUser, String userName) {
		for(int i = 0; i < userHierarchy.size(); i++) {
			if(reportUser.name.equalsIgnoreCase(userHierarchy.get(i).name)) {
				List<String> subUsers = reportUser.subUsers;
				for(int j = 0; j < subUsers.size(); j++) {
					if(subUsers.get(i).equalsIgnoreCase(userName)) {
						String userWithSameRole = replaceUserWithSameRole(userName);
						subUsers.get(i).replaceAll(userName, userWithSameRole);
					}
				}
 			}
		}
		
	}

	private String replaceUserWithSameRole(String userName) {
		for(int i = 0; i< userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(userName)) {
				return getUserWithSameRole(userHierarchy.get(i).userRole.role, userName);
			}
		}
		return null;
		
	}
	
	private String getUserWithSameRole(String role, String userName) {
		for(int i =0; i<userHierarchy.size(); i++) {
			if(userHierarchy.get(i).userRole.role.equalsIgnoreCase(role) && userHierarchy.get(i).name != userName) {
				return userHierarchy.get(i).name;
			}
		}
		return null;
	}

	/*
	 * DisplaySubUser
	 */
	public void displaySubUsers(String name) {
		for(int i = 0; i< userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(name)) {
				for(int j=0; j<userHierarchy.get(i).subUsers.size(); j++) {
					System.out.print("subuser  " + userHierarchy.get(i).subUsers.get(j));
				}
			}
		}
	}
	
	/*
	 * Display SubUsers For All Users
	 */
	public void displaySubUSersForAllUsers() {
		for(int i = 0; i < userHierarchy.size(); i++) {
			List<String> subUsers = userHierarchy.get(i).subUsers;
			System.out.println(userHierarchy.get(i).name + " -> ");
			for(int j =0; j < subUsers.size(); j++) {
				System.out.print(subUsers.get(j)+ " , ");
			}
		}
	}
	
	/*
	 * NO of the Users From The Top
	 * @param name of the user
	 */
	public void noOfUsersFromTop(String name) {
		listUserRole =  makeHireracchyMap();
		int hieghtCount = 0;
		Set<String> roles = listUserRole.keySet();
		Iterator<String> iterator = roles.iterator();
		while(iterator.hasNext()) {
			List<String> users = listUserRole.get(iterator.next());
			for(int i = 0 ; i < users.size(); i++) {
				if(users.get(i).equalsIgnoreCase(name)) {
					System.out.println("No of Users from top is "+ hieghtCount);
				}
				hieghtCount++;
			}
		}
	}
	
	
	/*
	 * Height of Role Hierarchy
	 */
	public void heightOfRoleHierarchy() {
		listUserRole= makeHireracchyMap();
		Set<String> keySet = listUserRole.keySet();
		System.out.println("Hieght of the hirarchy is " +keySet.size());
	}
	
	private Map<String, List<String>> makeHireracchyMap() {
		Map<String ,  List<String>> userRoleLists = new HashMap<String, List<String>>();
		for(int i = 0;  i < hierarchy.size() ; i++) {
			List<String> userList = new ArrayList<>();
			for(int j= 0; j < userHierarchy.size(); j++) {
				if(userHierarchy.get(j).userRole.role == hierarchy.get(i).role ) {
					userList.add(userHierarchy.get(j).name);
				}
			}
			userRoleLists.put(hierarchy.get(i).role, userList);
		}
		return userRoleLists;	
	}
	

	/*
	 * Common Boss
	 * @param1 Name of the User1
	 * @param2 name of the User2
	 */
	public String commonBoss(String name, String name1) {
		for(int i= 0 ; i < userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(name)) {
				for(int j = 0;  j < userHierarchy.size(); j++) {
					if(userHierarchy.get(j).name.equalsIgnoreCase(name1)) {
						String bossname = bossHelper(name, name1);
						if(bossname != null) {
							System.out.println("common boss for "+ name + " and " + name1 + " is "+ bossname);
							return bossname;
						}
					}
				}
			}
		}
		return "either boss doesnt exist or one of the user is ceo";
	}
	
	public String bossHelper(String name, String name1) {
		User user = null;
		User user1 = null;
		for(int i = 0;  i < userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(name)) {
				user = userHierarchy.get(i);
			}
		}
		for(int i = 0;  i < userHierarchy.size(); i++) {
			if(userHierarchy.get(i).name.equalsIgnoreCase(name1)) {
				user1 = userHierarchy.get(i);
			}
		}
		if(user.userRole.reportingRole.role.equalsIgnoreCase(user1.userRole.reportingRole.role)) {
			return user.userRole.reportingRole.role;
		}
		else {
			List<Role> reprtingRoleList = new ArrayList<>();
			reprtingRoleList.add(reprtingRole(user.userRole.reportingRole,  user1.userRole.reportingRole));
			Collections.reverse(reprtingRoleList);
			String roleName = null;
			for(int i = 0;  i < reprtingRoleList.size(); i++) {
				if(reprtingRoleList.get(i).role != null) {
					return reprtingRoleList.get(i).role;
				}
			}
			return null;
		}
	}
	
	private Role reprtingRole(Role role, Role role2) {
		if(role.reportingRole ==  null || role2.reportingRole == null) {
			return  null;
		}
		else if (role.reportingRole.role.equalsIgnoreCase(role2.reportingRole.role)) {
			return role.reportingRole;
		}
		
		return reprtingRole(role.reportingRole, role2.reportingRole);
	}
	
}
