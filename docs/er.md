```mermaid
erDiagram

USERS {
    user_id BIGINT PK
    username VARCHAR UNIQUE
    password VARCHAR
    }

CUSTOMER {
    customer_id BIGINT PK
    user_id BIGINT FK
    customer_name VARCHAR
    date_of_birth DATE
    gender VARCHAR
    phone_no VARCHAR
    email VARCHAR
    address VARCHAR
    aadhar_no VARCHAR
    customer_status VARCHAR
}

ACCOUNT {
    account_id BIGINT PK
    customer_id BIGINT FK
    account_number VARCHAR UNIQUE
    account_type VARCHAR
    account_status VARCHAR
}

NOMINEE {
    nominee_id INT PK
    account_id BIGINT FK
    nominee_name VARCHAR
    relationship VARCHAR
    is_guardian BOOLEAN
}

USERS ||--|| CUSTOMER : has
CUSTOMER ||--o{ ACCOUNT : owns
ACCOUNT ||--o{ NOMINEE : has

```
