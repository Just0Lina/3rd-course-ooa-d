CREATE TABLE IF NOT EXISTS litera_vibe.media_types
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    mime_type varchar(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS litera_vibe.media
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type_id   bigint NOT NULL REFERENCES litera_vibe.media_types,
    file_data bytea  NOT NULL
);

CREATE TABLE IF NOT EXISTS litera_vibe.users
(
    id       bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login     varchar(64) UNIQUE NOT NULL,
    image_url bigint UNIQUE REFERENCES litera_vibe.media,
    password  varchar(64)        NOT NULL
);

CREATE TABLE IF NOT EXISTS litera_vibe.authors
(
    id         bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name  varchar(64) default ('Real')   NOT NULL,
    second_name varchar(64) default ('Author') NOT NULL,
    image_url   bigint UNIQUE REFERENCES litera_vibe.media,
    info        varchar(1024)
);

CREATE TABLE IF NOT EXISTS litera_vibe.publishers
(
    id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    display_name varchar(64) default ('publisher') NOT NULL,
    isbn         varchar(13),
    password     varchar(64)                       NOT NULL
);

CREATE TABLE IF NOT EXISTS litera_vibe.books
(
    id           bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name         varchar(128) NOT NULL,
    media_id     bigint       NOT NULL REFERENCES litera_vibe.media UNIQUE,
    pages        bigint CHECK (pages > 0),
    info         varchar(512),
    year         timestamp,
    publisher_id bigint references litera_vibe.publishers

);

CREATE TABLE IF NOT EXISTS litera_vibe.authors_books
(
    author_id bigint REFERENCES litera_vibe.authors ON DELETE CASCADE,
    book_id   bigint REFERENCES litera_vibe.books ON DELETE CASCADE,
    PRIMARY KEY (author_id, book_id)
);


CREATE TABLE IF NOT EXISTS litera_vibe.roles
(
    id   bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS litera_vibe.user_role
(
    user_id bigint REFERENCES litera_vibe.users ON DELETE CASCADE,
    role_id bigint REFERENCES litera_vibe.roles ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);





