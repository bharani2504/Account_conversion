

# Minor to Major Account Conversion – Java Servlet & JDBC

##  Description

MinorToMajorAccountConversion is a Java Servlet–based banking application that automates the conversion of minor bank accounts to major accounts once the account holder reaches the eligible age. The system uses MySQL for data storage. The application is packaged as a WAR file and deployed on an Apache Tomcat server, demonstrating core Java web development concepts with JDBC integration.

---

##  Features

* Automatic conversion of **Minor accounts to Major accounts** based on date of birth
* Customer, Account, and Nominee data management using relational database design
* Business rule validation to prevent duplicate or invalid account conversions
* Implemented using **plain Java Servlets** without any frameworks
* Secure and reusable **JDBC database connection utility** with proper exception handling
* Data stored and retrieved using **MySQL** with optimized SQL queries

---

##  Technologies Used

* **Java (Servlets)**
* **JDBC**
* **MySQL**
* **Apache Tomcat**
* **Maven**

---

 ## Project Structure
 
* **Servlets** – Handle HTTP requests and responses
* **Services** – Business logic for account conversion
* **DAOs** – Database operations using JDBC
* **MySQL** – Data storage
 
---
 
## Features
 
### Customer
 
* Create customer
* Get all customers
 
### Account
 
* Create account
* Get account by Customer ID
* Update account
* Automatic **Minor to Major conversion** based on age
 
### Nominee
 
* Add nominee to account
* Get nominee by account ID
* Update nominee
* Delete nominee by account ID
 
### Account Conversion
 
* Automatically checks customer age
* Converts **Minor account to Major** when age ≥ 18
* Maintains nominee linkage during conversion
 
---
 
## API Endpoints
 
### Customer APIs
 
* POST `/customer`
* GET `/customer`
 
### Account APIs
 
* POST `/account`
* GET `/account?id=1`
 
### Nominee APIs
 
* POST `/nominee`
* GET `/nominee?accountId=1`
* DELETE `/nominee?id=1`
 
### Account Conversion
 
* Account conversion runs automatically using a scheduler
* During conversion, account type is updated from **MINOR** to **MAJOR**
 
---
 
## Sample Requests
 
### Sample customer request JSON
 
```json
{
  "customerName": "Bharanidharan S",
  "dateOfBirth": "2006-08-15",
  "gender": "Male",
  "phoneNo": "9876543210",
  "email": "bharani@gmail.com",
  "address": "Chennai",
  "aadharNo":"697880289845"

}
```
 
### Sample account request JSON
 
```json
{
   "customerId": 1,
  "accountNumber":12348621782,
  "accountType": "MINOR"
  
}
```
 
### Sample nominee request JSON
 
```json
{
  "accountId": 1,
  "nomineeName": "Ramesh",
  "relation": "Father",
 "guardian" : true
}
```
 
---
 
## Account Conversion Logic
 
* System calculates customer age using **Date of Birth**
* If age ≥ 18:
 
  * Account type is updated from **MINOR → MAJOR**
  * Nominee details remain linked using **account_id**
* Conversion is handled automatically by a **scheduler**
 
---
 


