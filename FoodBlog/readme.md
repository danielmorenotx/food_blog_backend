# Inclusive Food Blog

The Inclusive Food Blog is a Java-based web blog designed to allow users to share their culinary adventures, discover new recipes, and connect with other food enthusiasts. This README provides an overview of the application's features, installation instructions, and usage guidelines. It also includes background information about the creation of the blog, work in progresses, and data documentation.

## Features

- **User Registration**: Users can create accounts to access the platform.
- **Recipe Posting**: Users can post their favorite recipes along with descriptions and images.
- **Commenting**: Users can comment on recipes to share feedback or ask questions.
- **Search Functionality**: Users can search for recipes based on keywords or categories.
- **User Profiles**: Each user has a profile page where they can view their posted recipes and activity.
- **Tagging**: Recipes can be tagged with categories or labels for easy organization.
- **Responsive Design**: The web application is designed to be accessible and user-friendly across devices.

## Data Structure
The blog currently stores objects of users, blogs, comments, tags, and addresses. Here is the JSON structure of each of these objects:
1. **User**:

    ```
    {
        "id": 1,
        "username": "user1",
        "email": "user1@example.com",
        "password": "password1",
        "registrationDate": "2024-05-05",
        "address": {
            "id": 1,
            "street": "123 Main St",
            "city": "Anytown",
            "state": "ST",
            "zipCode": "12345",
            "country": "US"
        },
        "comments": []
    }
    ```

2. **Blog**: 

    ```
    {
        "id": 1,
        "title": "Blog1",
        "content": "This is blog1 content",
        "likes": null,
        "creationDate": "2024-05-06T01:32:30.504+00:00",
        "lastModified": "2024-05-06T01:32:30.504+00:00",
        "comments": [],
        "user": {
            "id": 1,
            "username": "user1",
            "email": "user1@example.com",
            "password": "password1",
            "registrationDate": "2024-05-05",
            "address": {
                "id": 1,
                "street": "123 Main St",
                "city": "Anytown",
                "state": "ST",
                "zipCode": "12345",
                "country": "US"
            },
            "comments": []
        },
        "tags": []
    }
    ```         

3. **Comment**: 

    ```
   {
        "text": "new comment text",
        "likes": 1,
        "blogId": 1,
        "userId": 1
   }
    ```

4. **Tag**:

    ```
    {
        "id": 1,
        "name": "tag1",
        "description": "this is the first tag"
    }    
    ```

5. **Address**:
    ```
   {
        "id": 2,
        "street": "456 Elm St",
        "city": "Othertown",
        "state": "ST",
        "zipCode": "54321",
        "country": "US"
    }
    ```

## Microservice Functionality
This blog has the ability to notify the author of a blog when a user comments on it. This is done via a notification microservice.
When a comment is added, a method is run that sends the comment information to the Notification microservice.
That microservice then creates a notification in the database with the blog author ID, commenter ID, and the text of the notification.
There are methods that allow users to see all notifications belonging to them.

## Usage

- **User Registration**: Sign up for a new account to access the platform.
- **Post Blogs**: Share your own recipes by posting them on a blog.
- **Comment on Recipes**: Leave comments on recipes to share your thoughts or ask questions.

## Testing
The testing methods are made to mainly cover the major usages of this blog: creating users, posting blogs, and posting comments.

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- PostgreSQL (or your preferred database)
- HTML/CSS/JavaScript (for frontend)
- Maven (for dependency management)
- Git (for version control)

## Creator

[Daniel Moreno](https://git.generalassemb.ly/danielmoreno)

[LinkedIn](https://www.linkedin.com/feed/)

## Acknowledgements

Special thanks to Indeed, GA, and my fellow Boost Apprentices for inspiration and guidance.
