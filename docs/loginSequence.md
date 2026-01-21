```mermaid
sequenceDiagram
    actor Client
    participant LoginFilter
    participant LoginServlet
    participant UserService
    participant UserDAO
    participant Hashing
    participant Jwt
    participant DbConnection

    Client ->> LoginFilter: HTTP POST /login
    LoginFilter ->> LoginFilter: doFilter()

    LoginFilter ->> LoginServlet: request forwarded
    LoginServlet ->> UserService: validateUser(username, password)

    UserService ->> UserDAO: findByUsername(username)
    UserDAO ->> DbConnection: getConnection()
    DbConnection -->> UserDAO: Connection
    UserDAO -->> UserService: User

    UserService ->> Hashing: verifyPassword(raw, hashed)
    Hashing -->> UserService: true

    UserService ->> Jwt: generateToken(username)
    Jwt -->> UserService: JWT token

    UserService -->> LoginServlet: success + token
    LoginServlet -->> Client: HTTP 200 (JWT)
