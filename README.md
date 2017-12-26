# CarRentalWS
Initial Settings...(app configuration, business configuration)
This project was developed over:
	- Java JDK 1.8
	- Ubuntu 16.10
	- Intellij IDEA ID from JetBrains
	
This is a Maven Project therefore the correspondent requisites are given by the artifact.WebService.xml
	
*** Setting the environment:

	A postgresql database is needed therefore proceed as following:  
		Follow the instalation of postgresql (if is not installed in your environment):
			https://www.digitalocean.com/community/tutorials/como-instalar-y-utilizar-postgresql-en-ubuntu-16-04-es
				
		Creation of a role (create a rol named: businessmodel, pass: test):
			$sudo -u postgres createuser --interactive
			change the password of the businessmodel user:
			postgres=# ALTER USER postgres PASSWORD 'test';
			
		Creation of a DB named: businessmodel:	
			$sudo -u postgres createdb businessModel
			
		Giving Admin privileges (user: businessmodel, password: test):
			$sudo adduser businessmodel
			
		Install graphic Database Manager like pgadmin:
			$sudo apt-get install pgadmin3
			
		
	For the Web Server, install glassfish 5.0, the setup of this server is done by default.
		Install glassfish server 5.0:	
			https://blogs.oracle.com/theaquarium/java-ee-8-is-final-and-glassfish-50-is-released

*** Read the statement of the problem to be familiar with the problem to solve: Documents/Problem_statement.pdf

*** Two parts were implemeted in this project(more details about each one in Documents/Solution.pdf):
	- Web Service server 
	- Tester module 

Since this project is a Maven Project, it should include all the needed libraries. Additionally, this project can be open either on 
Eclipse IDE or Intellij IDE. 

*** To test this project: 

	1. run the WebServer (Glassfish Server) initializated in class: MainApp.java
	2. run the tester Application implemeted in TestModule/Tester.java
	
