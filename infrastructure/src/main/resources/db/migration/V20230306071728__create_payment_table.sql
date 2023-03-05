CREATE TABLE payment(
    id varchar (127) NOT NULL,
    pg_transaction_id varchar (127) NOT NULL,
    approved_amount varchar (127) NOT NULL,
    reservation_id varchar (127),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;