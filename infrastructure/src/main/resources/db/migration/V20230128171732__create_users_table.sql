CREATE TABLE users
(
    id        VARCHAR(127) NOT NULL,
    password  VARCHAR(127),
    point     INT,
    user_rank VARCHAR(15),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;