```mermaid
sequenceDiagram
    actor Client
    participant LoginFilter
    participant AccountServlet
    participant AccountService
    participant AccountDAO
    participant DbConnection

    Client ->> LoginFilter: POST /account
    LoginFilter ->> LoginFilter: doFilter()
    LoginFilter ->> AccountServlet: authorized

    AccountServlet ->> AccountService: insert(account)
    AccountService ->> AccountDAO: insert(account)

    AccountDAO ->> DbConnection: getConnection()
    DbConnection -->> AccountDAO: Connection

    AccountDAO -->> AccountService: success
    AccountService -->> AccountServlet: success
    AccountServlet -->> Client: HTTP 201
