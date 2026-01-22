```mermaid
sequenceDiagram
    actor User
    participant LoginFilter
    participant RegisterServlet
    participant UserService
    participant Hashing
    participant UserDAO
    participant DbConnection

    User ->> LoginFilter: HTTP POST /login
    LoginFilter ->> LoginFilter: doFilter()
    
    LoginFilter ->> RegisterServlet: request forwarded
    RegisterServlet ->> UserService: addUser(user)

    UserService ->> Hashing: hashPassword(password)
    Hashing -->> UserService: hashedPassword

    UserService ->> UserDAO: insert(user)
    UserDAO ->> DbConnection: getConnection()
    DbConnection -->> UserDAO: Connection

    UserDAO -->> UserService: userCreated
    UserService -->> RegisterServlet: success
    RegisterServlet -->> User: HTTP 201 Created
