This is a maven springboot project.
To setup the project, please run mvn clean compile install.
Run the springboot application class EmployeeUtilityApplication.java
The utility can be accessed via swagger as openapi is set up: http://localhost:<port>/swagger-ui/index.html#/employee-controller/getEmployeeList. The input is the absolute path to input csv file.
Sample csv file is uploaded in /src/test/resources/valid.csv
