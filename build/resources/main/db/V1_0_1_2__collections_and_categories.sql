CREATE TABLE IF NOT EXISTS litera_vibe.categories
(
    id   bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(32) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS litera_vibe.categories_distribution
(
    category_id bigint references litera_vibe.categories,
    book_id   bigint references litera_vibe.books,
    PRIMARY KEY (category_id, book_id)
);


CREATE TABLE IF NOT EXISTS litera_vibe.collections
(
    id   bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS litera_vibe.collections_distribution
(
    collection_id bigint NOT NULL REFERENCES litera_vibe.collections ON DELETE CASCADE,
    book_id     bigint NOT NULL REFERENCES litera_vibe.books ON DELETE CASCADE,
    PRIMARY KEY (collection_id, book_id)
);
