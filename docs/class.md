``` mermaid
classDiagram

%% =========================
%% SCHEDULER
%% =========================
class ConversionScheduler {
    +contextInitialized()
}

class Jobs {
    +execute()
}

ConversionScheduler --> Jobs

%% =========================
%% FILTER
%% =========================
class LoginFilter {
    +doFilter()
}

%% =========================
%% SERVLETS
%% =========================
class LoginServlet {
    +doPost()
}

class RegisterServlet {
    +doPost()
}

class CustomerServlet {
    +doPost()
    +doGet()
}

class AccountServlet {
    +doPost()
    +doGet()
}

class NomineeServlet {
    +doPost()
    +doGet()
    +doDelete()
}

LoginFilter --> CustomerServlet
LoginFilter --> AccountServlet
LoginFilter --> NomineeServlet

%% =========================
%% SERVICES
%% =========================
class UserService {
    +addUser()
    +validateUser()
}

class CustomerService {
    +insert()
    +findAll()
}

class AccountService {
    +insert()
    +findByCustomerId()
    +updateAccount()
}

class NomineeService {
    +insert()
    +findByAccountId()
    +delete()
}

LoginServlet --> UserService
RegisterServlet --> UserService
CustomerServlet --> CustomerService
AccountServlet --> AccountService
NomineeServlet --> NomineeService

Jobs --> AccountService

%% =========================
%% SECURITY / UTILITY
%% =========================
class Jwt {
    +generateToken()
    +validateToken()
    +extractUsername()
}

class Hashing {
    +hashPassword()
    +verifyPassword()
}

UserService --> Jwt
UserService --> Hashing
LoginFilter --> Jwt

%% =========================
%% DAO LAYER
%% =========================
class UserDAO {
    +insert()
    +findByUsername()
}

class CustomerDAO {
    +insert()
    +findAll()
}

class AccountDAO {
    +insert()
    +updateAccount()
    +findByCustomerId()
}

class NomineeDAO {
    +insert()
    +updateNominee()
    +deleteById()
}

UserService --> UserDAO
CustomerService --> CustomerDAO
AccountService --> AccountDAO
AccountService --> NomineeDAO
NomineeService --> NomineeDAO

%% =========================
%% DB CONFIG
%% =========================
class DbConnection {
    +getConnection()
}

UserDAO --> DbConnection
CustomerDAO --> DbConnection
AccountDAO --> DbConnection
NomineeDAO --> DbConnection

%% =========================
%% MODELS
%% =========================
class User {
    -userId
    -username
    -password
}

class Customer {
    -customerId
    -userId
    -customerName
    -dateOfBirth
    -gender
    -phoneNo
    -email
    -address
    -aadharNo
    -customerStatus
}

class Account {
    -accountId
    -customerId
    -accountNumber
    -accountType
    -accountStatus
}

class Nominee {
    -nomineeId
    -accountId
    -nomineeName
    -relationship
    -isGuardian
}

UserDAO --> User
CustomerDAO --> Customer
AccountDAO --> Account
NomineeDAO --> Nominee
