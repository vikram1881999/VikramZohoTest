# VikramZohoTest
I have Worked on Two Problems :- 1>Contact App FullStack 2> RoleHierarchy

1> Contacts App Fullstack
	
	Intsructions to Run Contacts App
		#Go To ContactAPP folder and to check the logic please go inside the folders .java files are in deep inside the Core folders so please do check them out
		1> I used Apache Sling FrameWork for this app and it requires Adobe Experience Manager to run it.
		2> After Setting up aem add core files  to  aem core and ui.apps file to ui.apps files of aem.
		3> Now run the aem(adobe exeperience Manager) on author instance
		4> Now go to sites and open any page and add Login Component to one page and ContactADD and ContactList component on otherpage
		5> Once the user login he will get redirceted to the page Where he can add Contact details on ContactADD component and as many he want.
		6> So When User Add Contact details and submit at the same time the servlet in core will get triggered and take all response and add those
			properties in node structure by calling userservice which is osgiservice.
		7> Now if user want to view all his ContactList then he can use ContactList Component. and all the list will be viewed.
		8> for frontend part i used bootstarp.
		9> And i didnt use database as aem stores information in form of nodes.


2> RoleHierarchy
	
	Instruction to run RoleHierarchy Standalone program
		#Go to RoleHierarchy folder
		1>Its a standalone program so i didnt use any framework
		2>I have implemented all the methods 1. Add Sub Role.
											2. Display Roles
											3. Delete Role.
											4. Add User.
											5. Display Users.
											6. Display Users and Sub Users.
											7. Delete User.
											8. Number of users from top
											9. Height of role hierachy.
											10. Common Boss
			so i have used comments and had mentioned functions so you can check them
		3>For running it you can go to RoleHierarchy.java file and run the program as you want and i have added some logic on RoleHierarchy.java 
			to run program so you can see and play around with it

ThankYou,
Viram Singh Chauhan
email = vikramsinghchauhan1881999@gmail.com			
