# OMDB API

## Overview
The API is useful to fetch movies data from OMDB and save the data in Database.
The API also expose and API which fetch all the movies data stored in DB.

## Guidelines for Developer

1. Clone this Project

2. Create an Account in OMDB API.(http://www.omdbapi.com/)

3. After Account is created go to Profile-> My API keys and copy the key.

4. Replace the key in the below code of File : application.properties
```java
	pomdb.api.key=ceb0ac5f
```
5. Run the Application as Java Application.

## Guidelines for User

1.Hit the API using the path "/movies/search?t=" with valid Movies titles. User will be able to see the data based on User role.
2.Hit the API with Endpoint "/movies" to get All the movie details.

## Note
BASIC USER (user/123456) will be able to search only the movies after 2010 from OMDB and cannot fetch all the movies' data from DB.

PREMIUM USER (admin/123456) will be able to search all the movies from OMDB and can fetch all the available movies' data from DB along with filter and sorting.
