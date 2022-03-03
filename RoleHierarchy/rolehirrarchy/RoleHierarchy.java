package com.vikram.rolehirrarchy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

public class RoleHierarchy {
	
	public static void main(String[] arg) {
		Hierarchy hr = new Hierarchy();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter The Root Role");
		String rootRole = sc.next();
		hr.addRole(rootRole, null);
		System.out.println("Enter The No Of SubRoles For This Role " +  rootRole);
		int noOfSubRoles = sc.nextInt();
		List<String> subRoles = new ArrayList<>();
		for(int i =0 ;i< noOfSubRoles; i++) {
			System.out.println(" Name of the subRole ");
			String nameOfSubRole = sc.next();
			subRoles.add(nameOfSubRole);
		}
		hr.addSubrole(subRoles, rootRole);
		hr.DisplayRoleHirarchy();
		boolean addMoreUSer =  true;
		while(addMoreUSer) {
			System.out.println("Enter The  Role");
			String Role = sc.next();
			System.out.println("Enter The Reporting Role for " + Role);
			String superRole = sc.next();
			hr.addRole(Role, superRole);
			System.out.println("Enter The No Of SubRoles For This Role " +  Role);
			int noofSubRoles = sc.nextInt();
			List<String> subRoles1 = new ArrayList<>();
			for(int i =0 ;i< noofSubRoles; i++) {
				System.out.println(" Name of the subRole ");
				String nameofSubRole = sc.next();
				subRoles1.add(nameofSubRole);
			}
			hr.addSubrole(subRoles1, Role);
			System.out.println("Do you want Add Sum more role");
			String s = sc.next();
			if(s.equalsIgnoreCase("no")) {
				addMoreUSer = false;
			}
		}
		hr.DisplayRoleHirarchy();
		System.out.println("User for which you want get SubUsers");
		String s1 = sc.next();
		hr.displaySubRoles(s1);
	}
}
