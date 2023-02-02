CREATE TABLE `concert`
(
    `id`                  varchar(127) NOT NULL,
    `name`                varchar(100) NOT NULL,
    `place`               varchar(255) NOT NULL,
    `start_date`          datetime     NOT NULL,
    `time`                int          NOT NULL,
    `category`            varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;