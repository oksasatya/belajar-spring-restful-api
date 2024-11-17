# User Api Spec

## Register User
Endpoint : POST /api/users


Request Body : 
```json
{
  "username" : "oksa",
  "password" : "rahasia",
  "name" : "oksatesting"
}
```

Response Body (Success):
```json
{
  "data" : "OK"
}
```
Response Body (failed):
```json
{
  "errors" : "Username mus not blank" 
}
```

## Login user
Endpoint : POST /api/auth/login
  Request Body :
```json
{
  "username" : "oksa",
  "password" : "rahasia"
}
```

Response Body (Success):
```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt": 2312332321 //milisecond
  }
}
```
Response Body (failed,401):
```json
{
  "errors" : "Username or password Wrong!" 
}
```
## Get user
Endpoint : Get /api/users/current

Request Header :

- X-API-TOKEN: Token

Response Body (Success):
```json
{
  "data" : {
    "username" : "usernameUser",
    "name" : "name User"
  }
}
```
Response Body (failed,401):
```json
{
  "errors" : "Unauthorized!" 
}
```

## Update user
Endpoint : PATCH /api/users/current

Request Header :

- X-API-TOKEN: Token

  Request Body :
```json
{
  "name" : "oksa", //Put if only want to update name
  "password" : "newPassword"
}
```

Response Body (Success):
```json
{
  "data" : {
    "username" : "usernameUser",
    "name" : "name User"
  }
}
```
Response Body (failed,401):
```json
{
  "errors" : "Unauthorized!" 
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN: Token

Response Body (Success) :
```json
{
  "data" : "OK"
}
```