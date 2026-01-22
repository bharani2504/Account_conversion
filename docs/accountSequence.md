```mermaid
sequenceDiagram
    actor User
    participant LoginFilter
    participant AccountServlet
    participant AccountService
    participant AccountDAO
    participant DbConnection

    User ->> LoginFilter: POST /account
    LoginFilter ->> LoginFilter: doFilter()
    LoginFilter ->> AccountServlet: authorized

    AccountServlet ->> AccountService: insert(account)
    AccountService ->> AccountDAO: insert(account)

    AccountDAO ->> DbConnection: getConnection()
    DbConnection -->> AccountDAO: Connection

    AccountDAO -->> AccountService: success
    AccountService -->> AccountServlet: success
    AccountServlet -->> User: HTTP 201
