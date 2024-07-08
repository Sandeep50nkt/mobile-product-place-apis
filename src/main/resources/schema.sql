CREATE table product
(
    id                 INTEGER auto_increment PRIMARY KEY,
    name               VARCHAR   NOT NULL,
    image_url          VARCHAR,
    description        VARCHAR,
    brand              VARCHAR   NOT NULL,
    modified_by        VARCHAR   NOT NULL,
    available          VARCHAR default true,
    CREATE_TIMESTAMP   TIMESTAMP NOT NULL,
    MODIFIED_TIMESTAMP TIMESTAMP NOT NULL,
    version            INTEGER   NOT NULL
);

CREATE table product_history_info
(
    id               INTEGER auto_increment PRIMARY KEY,
    product_id       INTEGER   NOT NULL,
    user_id          VARCHAR   NOT NULL,
    status           VARCHAR,
    CREATE_TIMESTAMP TIMESTAMP NOT NULL,
    foreign key (product_id) references product (id)
);