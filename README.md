# DNA sequence mutant detector

Given a matrix of strings representing each row of a table (NxN) with the DNA sequence, a human is a mutant if it finds more than one configuration of identical four-letter strings, obliquely, horizontally or vertically.

The program will store the verified sequences, returning a 200 if mutant or a 403 if human.

It also has an extra service that exposes the ratio of mutants to humans.

---

## Demo

If you want to see the demo of this project deployed, you can visit: 

- http://ec2-13-58-185-14.us-east-2.compute.amazonaws.com:8080/swagger-ui.html

---

### Prerequisites 📋

In order to use the project you must have the following tools:

- GIT: Tool that will allow you to download the application from the repository.
- Java: JDK 11
- Maven
- IntelliJ (Recommended Code Editor)


### Installation 🔧
Clone this repository on your local machine:
```
git clone https://github.com/CCLR/mutants.git
``` 

Open IntelliJ, find the folder where the project was clone and open it.

IntelliJ should download the maven dependencies, and build the project.

### Deploy ⚙️

1. Go to class src/main/java/com/meli/exam/mutant/MutantApplication.java
2. Modify the default profile (dev) to the profile you want to deploy 
    - production: If you want to deploy in the production profile, you must go to application.properties and set the correct credentials of the IAM user to connect to the DynamoDB service
    - dev: If you want to deploy with the dev profile, you must In IntelliJ press Ctrl twice to open the Run Anything window and paste the following command
        ```
        mvn compile
        ``` 
3. Right click on this class and select Run 'MutantApplicat....main()'.

4. IntelliJ will start the MutantApplication with Spring Boot over port 8080.

_To verify the project started you can go to: `http://localhost:8080/swagger-ui.html`._

---

### Run coverage framework 🔩

_if you have the project deployed with the dev profile, you should stop it, in order not to have problems with the local DynamoDB server_

In IntelliJ press Ctrl twice to open the Run Anything window and paste the below command.

```
mvn test jacoco:report
```
Then, go to `target/site/jacoco/index.html` and open this page in the browser to see the project coverage

---

## Technology Stack 🛠️

* [Spring Boot](https://spring.io/projects/spring-boot/) - The web framework
* [Maven](https://maven.apache.org/) - Dependency handler
* [Lombok](https://projectlombok.org/) - Used to reduce boilerplate code for model/data objects
* [Swagger](https://swagger.io/) - API documentation
* [DynamoDB](https://aws.amazon.com/dynamodb/) - AWS noSQL database service
* [EC2](https://aws.amazon.com/es/ec2) -  Provides scalable computing capacity in AWS
* [Junit](https://junit.org/junit5/) - The unit testing framework
* [Jacoco](https://www.eclemma.org/jacoco/) - Java code coverage library



