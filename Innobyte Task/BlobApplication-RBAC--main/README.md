# BlobApplication-RBAC-
This blog application is a full-featured system that allows users to manage posts and comments securely. It provides role-based access control, JWT-based authentication, and a clear structure for managing blog content, making it suitable for creating and maintaining blog platforms with secure user interaction.

Blog Application Documentation
This documentation provides an overview of the Blog application, its features, API endpoints, and usage. The application allows users to securely manage blog posts and comments, with user authentication and role-based access control. It is built using Spring Boot, MySQL, and Spring Security.

Features:
User Authentication: JWT-based token authentication for secure login.
Role-Based Access Control: Admin and regular users have different access levels.
CRUD Operations: Users can create, read, update, and delete blog posts and comments.
Password Encryption: User passwords are securely stored using Spring Security’s PasswordEncoder.
API Endpoints
Authentication
Login (Generate JWT Token)
POST /auth/login
Description: Authenticates the user and returns a JWT token.
Request Body:
json
Copy code
{
"email": "user@example.com",
"password": "password"
}
Response:
json
Copy code
{
"token": "jwt_token_here"
}
User Operations
Create a User
POST /users
Description: Creates a new user.
Request Body:
json
Copy code
{
"username": "new_user",
"email": "newuser@example.com",
"password": "password",
"roles": "ROLE_USER"
}
Response:
json
Copy code
{
"message": "User added to system"
}
Post Operations
Create a Post

POST /posts
Description: Creates a new blog post.
Request Headers:
Authorization: Bearer <JWT token>
Request Body:
json
Copy code
{
"title": "Post Title",
"content": "Post content here"
}
Response:
json
Copy code
{
"id": 1,
"title": "Post Title",
"content": "Post content here",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00",
"updatedAt": "2024-11-01T12:00:00"
}
Get All Posts

GET /posts
Description: Retrieves all blog posts.
Response:
json
Copy code
[
{
"id": 1,
"title": "Post Title",
"content": "Post content",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00",
"updatedAt": "2024-11-01T12:00:00"
}
]
Get a Post by ID

GET /posts/{id}
Description: Retrieves a single post by its ID.
Response:
json
Copy code
{
"id": 1,
"title": "Post Title",
"content": "Post content",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00",
"updatedAt": "2024-11-01T12:00:00"
}
Update a Post

PUT /posts/{id}
Description: Updates an existing post.
Request Headers:
Authorization: Bearer <JWT token>
Request Body:
json
Copy code
{
"title": "Updated Title",
"content": "Updated content"
}
Response:
json
Copy code
{
"id": 1,
"title": "Updated Title",
"content": "Updated content",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00",
"updatedAt": "2024-11-01T12:30:00"
}
Delete a Post

DELETE /posts/{id}
Description: Deletes a post.
Request Headers:
Authorization: Bearer <JWT token>
Response:
No Content (204)
Comment Operations
Create a Comment

POST /comments
Description: Creates a comment for a post.
Request Headers:
Authorization: Bearer <JWT token>
Request Parameters:
postId: The ID of the post the comment is associated with.
username: The username of the comment's author.
Request Body:
json
Copy code
{
"content": "This is a comment"
}
Response:
json
Copy code
{
"id": 1,
"content": "This is a comment",
"post": {
"id": 1,
"title": "Post Title"
},
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00"
}
Get All Comments for a Post

GET /comments
Description: Retrieves all comments for a specific post.
Request Parameters:
postId: The ID of the post.
Response:
json
Copy code
[
{
"id": 1,
"content": "This is a comment",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00"
}
]
Get a Comment by ID

GET /comments/{id}
Description: Retrieves a single comment by its ID.
Response:
json
Copy code
{
"id": 1,
"content": "This is a comment",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00"
}
Update a Comment

PUT /comments/{id}
Description: Updates an existing comment.
Request Headers:
Authorization: Bearer <JWT token>
Request Body:
json
Copy code
{
"content": "Updated comment content"
}
Response:
json
Copy code
{
"id": 1,
"content": "Updated comment content",
"author": {
"id": 1,
"username": "author_username"
},
"createdAt": "2024-11-01T12:00:00"
}
Delete a Comment

DELETE /comments/{id}
Description: Deletes a comment.
Request Headers:
Authorization: Bearer <JWT token>
Response:
No Content (204)
Security & Authorization
JWT Token:
All sensitive operations (POST, PUT, DELETE) require a valid JWT token.
Use the /auth/login endpoint to authenticate and obtain the token.
Roles:
The application supports role-based access control. Users with the role ROLE_ADMIN have full access to all operations.
Regular users (ROLE_USER) can only perform actions on their own posts and comments.
Error Handling
404 Not Found: Resource (post/comment/user) not found.
400 Bad Request: Invalid request data.
401 Unauthorized: Invalid or missing JWT token.
403 Forbidden: Insufficient permissions (e.g., trying to update someone else’s post).
Conclusion
This blog application provides secure management of posts and comments, utilizing Spring Boot, JWT authentication, and MySQL as the data store. With user roles and encrypted passwords, it ensures secure and efficient operation.
