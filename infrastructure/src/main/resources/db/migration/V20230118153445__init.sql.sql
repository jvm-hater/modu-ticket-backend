CREATE TABLE `coupon`
(
    `id`                  varchar(127) NOT NULL,
    `name`                varchar(100) NOT NULL,
    `discount_rate`       int          NOT NULL,
    `concert_category`    varchar(100) NOT NULL,
    `max_discount_amount` int          NOT NULL,
    `use_start_date`      datetime     NOT NULL,
    `use_end_date`        datetime     NOT NULL,
    `issuable_quantity`   int          NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;