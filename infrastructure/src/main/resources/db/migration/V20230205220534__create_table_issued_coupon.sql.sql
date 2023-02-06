CREATE TABLE `issued_coupon`
(
    `id`        varchar(127) NOT NULL,
    `user_id`   varchar(127) NOT NULL,
    `coupon_id` varchar(127) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;