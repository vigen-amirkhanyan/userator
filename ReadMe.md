# userator
Service responsible for registering, logging in, verifying and extending a user session.
It is dependent on a MYSQL server running
## Prerequisites
- Java 8 (bigher versions might work)
- MySQL ^5.7.X recommended

## MySQL Setup
- Please apply the table creation script in /src/main/resources/schema.sql
- This will setup a table that combines user information and access token information

## Setup Environment Variables
```properties
MYSQL_HOST
MYSQL_PORT
MYSQL_DB
MYSQL_USER
MYSQL_PASSWORD
```

## Authentication and authorization Flow Steps
(API documentation below)
1) Register a user from the Register API
2) On successful register, user needs to login using email and password
3) Upon successful login, user will get back an access token and expiration timestamp
4) User can use the access token to get user information and verify access token by calling the Verify and Refresh endpoint 
5) Upon successful verify and extend call, user will get back user information(such as role, name) and an extended timestamp

#Video Tutorial
https://www.youtube.com/watch?v=QFKLSFgRJUQ

# API
## Register
### POST /v1/register?name={NAME}&email={EMAIL}&password={PASSWORD}&role={ROLE}
Register a user given, name, email, password and role
Returns 201 on successful creation
```
UserEntity Created
```

## Login
### POST /v1/login?email={EMAIL}&password={PASSWORD}
Login a user given an email and password.
Returns 200 on successful login & 401 on unsuccessful login.
Upon successful login, user will get an accessToken and an expiration timestamp.
```json
{
  "accessToken": "69a827f9",
  "expiresAt": "2020-04-02T00:31:10.707+0000"
}
```

## Verify and Extend
### POST /v1/verifyAndExtend?accessToken={ACCESS_TOKEN}
API returns the user information & extends the expiration of the access token by 15 minutes
Upon successful login, user will get an accessToken and an expiration timestamp.
```json
{
  "name": "vigen",
  "email": "v@live.com",
  "role": "admin",
  "accessToken": "6d2cd0ac",
  "accessTokenExpiresAt": "2020-04-02T00:36:37.756+0000"
}
```

