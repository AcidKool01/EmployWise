## DONE ALL THE LEVELS OF THE PROJECT ##


# How to Run Project
    - Open The Project Folder in IDEs like Intellij, Eclipse, Spring Tool Suite, etc
    - Run the "AssignmentApplication.class"

				OR

    - Also Included the jar file.
    - To run jar file, at cmd => java -jar emp-app.jar

    
    - Server is set to run port no = 8089, can be changed under Application.properties
    - URL = http://localhost:8089/

    - Credentials also need to be filled for email in application.properties


# How to Setup Database:
    - Used MongoDB Atlas for this Project as NoSQL DB.
    - For Configuring the Database, 
          - Go to application.properties File from the resources folder.
	    - enter the uri


# AWS Deployment
    - The same is deployed on AWS EC2.
    - URL = http://ec2-54-64-155-83.ap-northeast-1.compute.amazonaws.com:8089/
    
    ## Example Endpoints:

	# GET ALL USERS:
		- http://ec2-54-64-155-83.ap-northeast-1.compute.amazonaws.com:8089/employee


# APIs:
	

   # For initializing the database with dummy values (with UUIDs auto generated)
	
	-  GET /employee/initialize


   # Adding a User

	-  POST /employee

	- Input JSON Structure:
		
		{
  			"employeeName": "John Doe",
  			"phoneNumber": "+1234567890",
  			"email": "john.doe@example.com",
  			"reportsTo": "managerId", // Employee ID of the reporting manager
  			"profileImage": "http://short.url/abc123"
		}
		

   # Get All Users

	- GET /employee


   # Delete By ID

	- DELETE /employee/{id}


   # Update By ID

	- PUT /employee/{id}

	- Input JSON Structure:
		
		{
  			"employeeName": "John Doe",
  			"phoneNumber": "+1234567890",
  			"email": "john.doe@example.com",
  			"reportsTo": "managerId", // Employee ID of the reporting manager
  			"profileImage": "http://short.url/abc123"
		}


   # Get Nth Level Manager Of Employee

	- GET /employee/{employeeId}/manager/{level}


   # Get Sorted List of Employees By Field

	- GET /employee/sort/{field}


   # Get Pagination And Sorted By Field

	- GET /employee/{offset}/{pagesize}/{field}
