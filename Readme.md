Project Summary of Student application / service

This is a service created using spring boot to add student records, to get
list of all students in an institute, to find all students studying in
a particular department in that institute and also to search particular 
student's data.

Technology pre-requisition to run this service in a computer
1. JDK 11 or above version
2. Maven
3. An IDE to run the service Or, using commands
4. OR Docker if and only if wish to run in a docker container

If you wish to run the service, using commands, please run first below command 
at the root directory of this service.
[./mvnw package]
following the below command
[java -jar target/*.jar]

The service should be UP on http://localhost:8080.

If you wish to run it as docker container, please run the below commands in order:
1. To create the docker image locally, run in the root directory [docker build -t student:latest .]
or, you can pull it from dockerHub with command [docker pull prasantag/student-spring-boot:latest]
2. Run [docker run -p8080:8080 student:latest] or [docker run -p8080:8080 prasantag/student-spring-boot:latest]
3. The service should be UP on http://localhost:8080.

APIs developed and how to test through Postman or any other similar tools:
1. To add student POST URL localhost:8080/student, REQUEST BODY [{
   "firstName": "John",
   "lastName": "Leon",
   "dob":"2002-07-18",
   "departmentName": "History",
   "courseName": "Bachelor of Arts"
   }]
2. To find all the students GET URL localhost:8080/student
3. To search a student by ID GET URL localhost:8080/student/id/1
4. To find all the student by their department name GET URL localhost:8080/student/departmentName/History
