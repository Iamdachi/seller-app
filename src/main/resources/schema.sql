CREATE TABLE users
(
    id IDENTITY PRIMARY KEY,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL UNIQUE,
    created_at      TIMESTAMP    NOT NULL,
    role            VARCHAR(50)  NOT NULL,
    email_confirmed BOOLEAN      NOT NULL,
    admin_approved  BOOLEAN      NOT NULL
);


CREATE TABLE comments
(
    id IDENTITY PRIMARY KEY,
    message    CLOB      NOT NULL,
    rating     INT       NOT NULL CHECK (rating >= 0 AND rating <= 10),

    -- NULL allowed → anonymous author
    author_id  BIGINT,

    -- must always point to a seller
    seller_id  BIGINT    NOT NULL,

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_comment_author
        FOREIGN KEY (author_id) REFERENCES users (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_comment_seller
        FOREIGN KEY (seller_id) REFERENCES users (id)
            ON DELETE CASCADE
);



CREATE TABLE game_objects
(
    id IDENTITY PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    text       CLOB         NOT NULL,
    user_id    BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL,

    CONSTRAINT fk_game_object_author
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE
);


CREATE TABLE seller_ratings
(
    seller_id     BIGINT PRIMARY KEY,
    avg_rating DOUBLE NOT NULL,
    comment_count INT       NOT NULL,
    updated_at    TIMESTAMP NOT NULL,

    CONSTRAINT fk_seller_rating_user
        FOREIGN KEY (seller_id) REFERENCES users (id)
            ON DELETE CASCADE
);
