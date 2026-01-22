```mermaid
sequenceDiagram
    actor User
    participant LoginFilter
    participant CustomerServlet
    participant CustomerService
    participant CustomerDAO
    participant DbConnection

    User ->> LoginFilter: POST /customer
    LoginFilter ->> LoginFilter: doFilter()
    LoginFilter ->> CustomerServlet: authorized request

    CustomerServlet ->> CustomerService: insert(customer)
    CustomerService ->> CustomerDAO: insert(customer)

    CustomerDAO ->> DbConnection: getConnection()
    DbConnection -->> CustomerDAO: Connection

    CustomerDAO -->> CustomerService: success
    CustomerService -->> CustomerServlet: success
    CustomerServlet -->> User: HTTP 201
