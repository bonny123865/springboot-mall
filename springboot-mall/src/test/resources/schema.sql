-- 因為下方會使用 H2 資料庫，所以 "email" 以"UNIQUE" 表示"唯一"
-- 如果使用 MySQL 資料庫， "email" 以"UNIQUE KEY" 表示"唯一"
CREATE TABLE IF NOT EXISTS "user"
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );

CREATE TABLE IF NOT EXISTS product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128)  NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );






