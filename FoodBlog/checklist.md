
# Spring Boot Application Project - Coursework Checklist

## Project Structure for Spring Boot Applications

### Requirements Checklist

#### Core Development
- [X] Use Spring Boot with Spring Data JPA for entity implementation and relationships.
- [X] Follow the MVC architecture.

#### Entities and Attributes

##### Blog
- [X] Implement the Blog entity with attributes defined in the main description.
- [X] Establish relationships:
  - [X] One-to-many with Comment
  - [X] Many-to-one with User
  - [X] Many-to-many with Tag

##### Comment
- [X] Implement the Comment entity with attributes: Text, Timestamp, Likes, Comment By etc ...
- [X] Establish a many-to-one relationship with Blog.

##### User
- [X] Implement the User entity with attributes: Username, Email, Password, Registration Date etc ...
- [X] Establish relationships:
  - [X] One-to-many with Blog, Job, Post
  - [X] One-to-one with Address

##### Address
- [X] Implement the Address entity with attributes: Street, City, State, Zip Code, Country.

##### Tag
- [X] Implement the Tag entity with attributes: Name, Description.
- [X] Establish a many-to-many relationship with Blog, Job, Post.

#### Aspect-Oriented Programming
- [X] Create a `@Around` logging aspect with Spring AOP to log method executions focusing on CRUD operations.

#### Testing
- [ ] Write tests for all services and controllers using JUnit and Mockito.
  - [X] Happy paths for controller classes
  - [ ] Sad paths for controller classes
  - [X] Happy paths for service classes
  - [ ] Sad paths for controller classes

#### Version Control Workflow
- [X] Use Git for version control with a structured branch approach:
  - [X] `main` branch for stable releases.
  - [X] `develop` branch for ongoing development.
  - [X] Feature branches off `develop` for new features.

#### Advanced Challenge
- [X] Develop a standalone Notification Microservice to handle event-triggered communications as per the main document.
- [X] Implement API integrations using DTOs for event notifications.

#### Notification Microservice Functionality
- [X] Ensure the service is triggered by comments added to posts, blogs, or job listings.
- [X] Use Rest template to communicate with this microservice from the main application.

## Submission Guidelines
- [ ] Submit your project through a GitHub repository.
- [ ] Include a README with detailed instructions on running the project and executing tests.

## Adherence to Software Engineering Principles
- [X] DRY: Avoid code duplication by creating reusable methods or classes.
- [X] SoC: Assign specific concerns or tasks to each class or component.
- [X] SRP: Ensure each class or method has a single responsibility.
- [X] OCP: Design for future enhancements without modifying existing code.

## Plagiarism Guidelines
- [X] Do not copy code without understanding its functionality and logic.
- [X] Avoid using or incorporating code from other students.
- [X] Limit the use of AI-generated code to understanding concepts, not for direct copying.