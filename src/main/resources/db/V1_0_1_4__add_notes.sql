CREATE TABLE IF NOT EXISTS  litera_vibe.notes
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id   bigint REFERENCES  litera_vibe.users ON DELETE CASCADE,
    book_id bigint REFERENCES  litera_vibe.books ON DELETE CASCADE,
    content   text NOT NULL CHECK (trim(content) <> '')
);

