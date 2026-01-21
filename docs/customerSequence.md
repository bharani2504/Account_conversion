```mermaid
sequenceDiagram
    actor Client
    participant LoginFilter
    participant CustomerServlet
    participant CustomerService
    participant CustomerDAO
    participant DbConnection

    Client ->> LoginFilter: POST /customer
    LoginFilter ->> LoginFilter: doFilter()
    LoginFilter ->> CustomerServlet: authorized request

    CustomerServlet ->> CustomerService: insert(customer)
    CustomerService ->> CustomerDAO: insert(customer)

    CustomerDAO ->> DbConnection: getConnection()
    DbConnection -->> CustomerDAO: Connection

    CustomerDAO -->> CustomerService: success
    CustomerService -->> CustomerServlet: success
    CustomerServlet -->> Client: HTTP 201
