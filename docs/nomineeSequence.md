```mermaid

sequenceDiagram
actor User
participant LoginFilter
participant NomineeServlet
participant NomineeService
participant NomineeDAO
participant DbConnection

    User ->> LoginFilter: POST /nominee
    LoginFilter ->> LoginFilter: doFilter()
    LoginFilter ->> NomineeServlet: authorized

    NomineeServlet ->> NomineeService: insert(nominee)
    NomineeService ->> NomineeDAO: insert(nominee)

    NomineeDAO ->> DbConnection: getConnection()
    DbConnection -->> NomineeDAO: Connection

    NomineeDAO -->> NomineeService: success
    NomineeService -->> NomineeServlet: success
    NomineeServlet -->> User: HTTP 201
