# springboot_blog_project

# Spring Boot Application Project - Coursework Description

## Project Overview

You are required to develop a fully functional Spring Boot application based on the project structure provided. Which ever project you worked on in the Javascript project continue on that and build the backend. They were an Inclusive Community Blog, a Job Board for Underrepresented Groups, and a Social Media Website. Each component has its own set of entities and relationships which must be implemented and correctly linked as described in the project structure document.

# Project Structure for Spring Boot Applications

## Inclusive Community Blog Website

### Entities and Attributes

#### Blog

- **Attributes:** Title, Content, Likes, Creation Date, Last Modified
- **Relationships:**
  - One-to-many with Comment: A blog can have multiple comments but each comment belongs to only one blog.
  - Many-to-one with User: Multiple blogs can be authored by the same user.
  - Many-to-many with Tag: A blog can have multiple tags and each tag can be associated with multiple blogs.

#### Comment

- **Attributes:** Text, Timestamp, Likes, Comment By (username of commenter)
- **Relationships:**
  - Many-to-one with Blog: Each comment is associated with a single blog.

#### User

- **Attributes:** Username, Email, Password, Registration Date
- **Relationships:**
  - One-to-many with Blog: A user can author multiple blogs.
  - One-to-one with Address: Each user has one unique address.

#### Address

- **Attributes:** Street, City, State, Zip Code, Country

#### Tag

- **Attributes:** Name, Description
- **Relationships:**
  - Many-to-many with Blog: A tag can be associated with multiple blogs and vice versa.


## Requirements

### Core Development

- Implement all entities and relationships using Spring Boot with Spring Data JPA.
- Ensure your application follows the MVC architecture where applicable.
- Add all the CRUD operaion needed for the application

### Aspect-Oriented Programming

- Create a `@Around` logging aspect using Spring AOP that logs basic information about method executions, particularly focusing on CRUD operations across services (service layer).

### Testing

- Write comprehensive tests for all services and controllers using JUnit and Mockito.

### Version Control Workflow

- Use Git for version control with the following branch structure:
  - `main` branch for stable releases.
  - `develop` branch for development.
  - Feature branches off `develop` for new features.
- Practice regular commits and use pull requests for merging branches, enforcing code reviews before integration.

### Advanced Challenge - not bonus

#### Notification Microservice
- Develop a standalone microservice for notifications. And using DTOs:-

- When a post, job, blog gets a comment send the user name of the user who commented it and the id of the user who owns the post, job, blog to this service using Rest template and register this to the notification database.

- Add an endpoint in the main application where a user can see all notifications for them by id using a notification DTO. 

#### Functionality of the Notification Microservice

- Trigger Event: The primary trigger for the Notification Microservice is the action of a comment being added to a post. When a comment is posted, whether on a blog entry, job listing, or a social media post within your application, this initiates a communication process with the Notification Microservice.

- API Call to Notification Service:
  **From Comment Service:** Once a comment is successfully saved to the database by the commenting service or controller responsible for handling post interactions, an API call is made to the Notification Microservice.
  **Purpose of API Call:** This call informs the Notification Microservice that a new comment has been posted. It includes all necessary details such as the ID of the commented post, the User name of the commenter.

## Deliverables

- A complete Spring Boot project fulfilling all the above requirements.

## Submission Guidelines

- Submit your project through a GitHub repository.
- Ensure your repository contains a README with detailed instructions on how to run your project and execute tests.

## Evaluation Criteria

- Functionality: All components are working as expected.
- Code Quality: Clear, readable, and well-organized code.
- Testing: Extensive test demonstrating reliability.
- Documentation: Comprehensive Readme.md documentation.

## **Adhere to software engineering principles:**
- Don't Repeat Yourself (DRY): Identify and eliminate code duplication by extracting common functionality into reusable methods or classes.
- Separation of Concerns (SoC): Ensure that each class or component has a well-defined responsibility and focuses on a specific concern or task.
- Single Responsibility Principle (SRP): Design classes and methods to have a single responsibility, making them more cohesive and easier to maintain.
- Open/Closed Principle (OCP): Design classes and components to be open for extension but closed for modification, allowing for future enhancements without modifying the existing code.

## Plagiarism

It is essential to maintain academic integrity and ethical standards throughout the development of the application project. you must adhere to the following strict requirements regarding plagiarism:

1. **No Code Copying:**
- You are strictly prohibited from copying code that they do not fully understand.
- Copying code without comprehending its functionality, logic, and purpose defeats the learning objectives of the project and is considered a dishonesty.

2. **No Use of Other apprentice's Code:**
- You must not use, copy, or incorporate code written by other students.

3. **No Use of AI-Generated Code:**
- Students are strictly prohibited from over using code generated by artificial intelligence (AI) models, language models, or any other automated code generation tools. This means you can use AI to understand concepts and further your understanding on a topic, but you cant copy and past an AI generated code if you dont understand what it does and if you cant explain it during presentation.

You are **encouraged** to seek guidance from instructors, teaching assistants, or peer support resources if they encounter difficulties or have questions during the project development process. Asking for help and clarification is **encouraged** and **does not** constitute plagiarism.