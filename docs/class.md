erDiagram

    USERS {
    BIGINT user_id PK
    VARCHAR username UNIQUE
    VARCHAR password
    }

    CUSTOMER {
        BIGINT customer_id PK
        BIGINT user_id FK
        VARCHAR customer_name
        DATE date_of_birth
        VARCHAR gender
        VARCHAR phone_no
        VARCHAR email
        VARCHAR address
        VARCHAR aadhar_no
        VARCHAR customer_status
    }

    ACCOUNT {
        BIGINT account_id PK
        BIGINT customer_id FK
        VARCHAR account_number UNIQUE
        VARCHAR account_type
        VARCHAR account_status
    }

    NOMINEE {
        INT nominee_id PK
        BIGINT account_id FK
        VARCHAR nominee_name
        VARCHAR relationship
        BOOLEAN is_guardian
    }

    USERS ||--|| CUSTOMER : "has"
    CUSTOMER ||--o{ ACCOUNT : "owns"
    ACCOUNT ||--o{ NOMINEE : "has"
