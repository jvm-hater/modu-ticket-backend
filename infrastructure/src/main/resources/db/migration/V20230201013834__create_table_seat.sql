CREATE TABLE `seat`
(
    `id`                  varchar(127) NOT NULL,
    `concert_id`          varchar(127) NOT NULL,
    `tier`                varchar(100) NOT NULL,
    `amount`              bigint       NOT NULL,
    `total_quantity`      int          NOT NULL,
    `quantity_left`       int          NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`concert_id`) REFERENCES concert (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;