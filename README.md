# Project Webservice 

## Description
This project is a part of the course System Integration at Campus Mölndal. The project is a webservice that is built with Java and Spring Boot. The webservice is connected to a MySQL database and can handle CRUD operations. 
The webservice is a part of a larger project that is built with a microservice architecture, with Apache kafka facilitating communication between the services. The webservice is responsible for handling the user data.

My motivation behind building the project was to gain practical experience in system integration and microservice architecture. The project gave me valuable experience in building a webservice and connecting it to a database.
The project also gave me valuable experience in using Apache Kafka to facilitate communication between services, and enhanced my Java and Spring Boot skills.

## Installation
#### Javaversion: 17 java version 17.0.1
#### Database: MySQL
#### Apache Kafka
+ Download and install [IntelliJ](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
+ Download and install [MySQL](https://dev.mysql.com/downloads/mysql/)
+ Download and install [Apache Kafka](https://kafka.apache.org/downloads)
+ Clone the projects on GitHub [JavaApplication](https://github.com/clarabrorson/JavaApplication) and [KafkaWebService](https://github.com/clarabrorson/KafkaWebService)
+ Make sure that the kafka zookeeper and kafka server is running in terminal.
+ Create a database in MySQL with the name "bookstore" 
+ Open JavaApplication in your IDE (if necessary add KafkaWebService as a Module from excisting source)
+ Change the username and password in the application.properties file in the KafkaWebService module to your own username and password.
+ Launch Spring Boot from the SpringBootWebApplication class i KafkaWebService module.
+ Launch the program from the Main class in the JavaApplication module.
+ The program will start with a menu system where the user can choose between sending and receiving data.
+ Enjoy!

## Testing and Execution
+ To run the program, open the SpringBootWebApplication.java file in the KafkaWebService module and click on Run. Open the Main.java file in the JavaApplication module and click on Run.
+ To test the program, open the DatabaseTests.java file and the KafkaTests.java file in the separate test folders and click on Run.


## Dependencies
#### KafkaWebService
+ Spring Boot Starter Web
+ Spring kafka
+ Spring Boot Starter Data JPA 3.1.3
+ Spring Boot Starter Test
+ MySQL Connector Java 8.1.0
+ Junit Jupiter API 5.10.0

#### JavaApplication
+ Spring kafka 3.0.11
+ Json simple 1.1.1
+ Http Client 5.2.1
+ Springboot-kafka-web-service 0.0.1-SNAPSHOT
+ Junit Jupiter API 5.10.0

## License
MIT License

## Tests
#### KafkaWebService
+ To run the database tests, locate the DatabaseTests.java file in the test folder and click on Run the class
#### JavaApplication
+ To run kafka tests, locate the KafkaTests.java file in the test folder and click on Run the class


