```mermaid

sequenceDiagram
actor Client
participant LoginFilter
participant NomineeServlet
participant NomineeService
participant NomineeDAO
participant DbConnection

    Client ->> LoginFilter: POST /nominee
    LoginFilter ->> LoginFilter: doFilter()
    LoginFilter ->> NomineeServlet: authorized

    NomineeServlet ->> NomineeService: insert(nominee)
    NomineeService ->> NomineeDAO: insert(nominee)

    NomineeDAO ->> DbConnection: getConnection()
    DbConnection -->> NomineeDAO: Connection

    NomineeDAO -->> NomineeService: success
    NomineeService -->> NomineeServlet: success
    NomineeServlet -->> Client: HTTP 201
