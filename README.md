# SPARK

## Introduction
 
Individual project for SparkX Proffessional Development Program-2020. A Covid Management System to manage hospitals and patients enabling displaying patient and hospital statistics by hospital level, district level and country level. 

Backend developed using Java & MySQL.
Frontend developed using HTML,CSS and JavaScript.

- Backend: [View Repository](https://github.com/ishaniMadhuwanthi/spark_ncms_backend)
+ Frontend:[View Repository](https://github.com/ishaniMadhuwanthi/spark_ncms_frontend)

## About Project

National COVID Management System-web application backend design. Following functionalities are included in the system.

- In the system there are four main entities: patient, Moh officer, Doctor and director.
+ Only patients can register to the system and a bed is allocated for them. If beds are not available, then they are put into a queue.
- When a patient is discharged, the director can remove the patient and make bed available.
+ Moh can manage the system. They can add/delete/update hospitals, add/delete/update doctors and directors.

# Documents 

System functionalities have developed by considering following documents .
- SRS Document (Software Requirement Specification Document)
+ SDD Document (Software Design Documents)

You can view the above documents from this repository.

## Instructions

1. Clone the repository
2. Install Dependencies
3. Create a database on MySQL and import the tables. You can find the table set from ncms.sql from the repository.

   Database name: **ncms**
4. Update the database connection parameters in src/main/java/lk/ishane/database/DBConnectionPool.java file.

```java
basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");    
basicDataSource.setUrl("jdbc:mysql://localhost:3306/ncms");         
basicDataSource.setUsername("enter your MySQL username");                            
basicDataSource.setPassword("enter your database password"); 
```

## Dependencies     

- javax: javaee-web-api:8.0.1
+ javax.servlet:  javax.servlet-api :3.1.0         
- com.google.code.gson:gson:  2.8.6
+ Org.apache.commons:commons-dbcp2:2.7.0
- Mysql:mysql-connector-java:8.0.21



