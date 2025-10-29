<div align="center">
<h1><strong>Unit and Integration Tests on a Simple Poll API 📊</strong></h1>
<img src="https://miro.medium.com/v2/0*NEfUPKhoRszjjdeW.png" alt="ecommerce" width="500"/>
</div>

<div align="center">
<br/>
<strong>Easy REST API for creating and voting on polls built with Java and Spring Boot.
</strong>
<br/>
<br/>
This project is not just a functional API; it served me as a "mock" to demonstrate a complete backend testing strategy,</br> separating Unit Tests from Integration Tests.  
</div>
<br/>



📡 Key Features
----------

<strong>Full REST API:</strong> Endpoints to create, retrieve, and vote on polls.
<br/>

<strong>Specific Business Logic:</strong> A dedicated endpoint (/vote) that handles the transactional logic of incrementing vote counts.
<br/>

<strong>In-Memory Database:</strong> H2 Database for quick startup and a clean+isolated testing environment.
<br/>


🧪 Implemented Testing Strategy
-----

This project demonstrates the two fundamental levels of backend application testing:



--> <strong>Unit Testing (with Mockito)</strong>

Objective: To test the "brain" (PollService) in total isolation.

How?</br>
I've used Mockito (@Mock) to create "fake" repositories. The tests (PollServiceTest.java) never touch a real database but only verify that the business logic (e.g., incrementing the vote counter) is correct and that the right repository methods are called.
</br>

--> <strong>Integration Testing (with MockMvc)</strong>

Objective: To test the entire process from the controller down to the database.

How?</br>
I've used @SpringBootTest to launch a full Spring application and MockMvc to simulate real HTTP calls (like Insomnia would) against our endpoints. The tests (PollControllerTest.java) verify the entire flow: the API call hits the PollController, which calls the PollService, which saves data to the real H2 database, and returns the correct HTTP response.




⚠️ Prerequisites
------------


Java Development Kit (JDK 21 or newer).

Apache Maven.

Docker Desktop installed and running.

An API client like Insomnia or Postman for testing the endpoints.
<br/>

🛠️ Tech Stack
-----

<strong>Java 21:</strong> The core programming language.

<strong>Spring Boot:</strong> The framework used to build each microservice.

<strong>Spring Data JPA:</strong> Manages database interaction.

<strong>H2 Database:</strong> The in-memory relational database.

<strong>Lombok:</strong> To reduce boilerplate code.

<strong>Maven:</strong> The project build and dependency management tool.

<strong>JUnit 5 & Mockito:</strong> For Unit Tests.

<strong>Spring Boot Test & MockMvc:</strong> For Integration Tests.




💻 Tools & Technologies
------------

<br/>
<p align="center">
<a href="#"><img src="https://img.shields.io/badge/macOS-000000?logo=apple&logoColor=F0F0F0" alt="macOS"></a>
<a href="#"><img src="https://img.shields.io/badge/Spotify-1ED760?logo=spotify&logoColor=white" alt="Spotify"></a> 
<a href="#"><img src="https://custom-icon-badges.demolab.com/badge/Visual%20Studio%20Code-0078d7.svg?logo=vsc&logoColor=white" alt="VSC"></a>
<a href="#"><img src="https://img.shields.io/badge/Insomnia-5849be?logo=insomnia&logoColor=white" alt="Insomnia"></a>
<a href="#"><img src="https://img.shields.io/badge/Spring-6DB33F?logo=spring&logoColor=fff&style=flat" alt="Spring"></a>
<a href="#"><img src="https://img.shields.io/badge/Apache%20Maven-C71A36?logo=apachemaven&logoColor=white" alt="Maven"></a>
<a href="#"><img src="https://img.shields.io/badge/JUnit5-25A162?logo=junit5&logoColor=fff&style=flat" alt="JUnit5"></a>  
<a href="#"><img src="https://img.shields.io/badge/H2%20Database-09476B?logo=h2database&logoColor=fff&style=flat" alt="H2"></a>  
<a href="#"><img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=flat" alt="Springboot"></a>    
<a href="#"><img src="https://img.shields.io/badge/GitHub-181717?logo=github&logoColor=white" alt="GitHub"></a> 


</p>
<br/>

▶️ Setup & Running the Application
-----

Clone this repository to your computer.

Open a terminal in the project's root directory (poll-api).

Run the application with this command:

<strong>mvn spring-boot:run</strong>


The API will be available at http://localhost:8080.


🧑‍🔬 Running the Automated Tests
-----

To run the complete test suite (4 tests in total: 2 unit and 2 integration):

<strong>mvn clean install</strong>


You will see in the terminal log that all tests are executed and the build will end with <strong>BUILD SUCCESS</strong>.



📋 Main API Endpoints
-----

<strong>*Create a Poll</strong>:
</br>
<strong>POST</strong> /api/polls

Body: {"question": "Your Question?", "options": ["Option A", "Option B"]}
</br> 


<strong>*Get All Polls</strong>:
</br>
<strong>GET</strong> /api/polls
</br>


<strong>*Get a Single Poll</strong>:
</br>
<strong>GET</strong> /api/polls/{id}
</br>


<strong>*Vote for an Option</strong>:
</br>
<strong>POST</strong> /api/polls/options/{optionId}/vote


🧑‍🎓 What i've learned: 
----

I've learned a professional testing strategy. I've built a simple API and then i've wrote two types of tests:

<strong>Unit Tests (Mockito)</strong>: To test business logic in isolation.

<strong>Integration Tests (MockMvc)</atrong>: To test the full API --> DB flow.

I've also wrote my first test line with the Triple A: Arrange, Act, Assert.


🤼 The biggest challenge: 
---

The main challenge in this project was understanding the conceptual difference: knowing what to mock for a unit test versus what to include in a full integration test, and why we use each one.

