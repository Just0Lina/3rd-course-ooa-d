-- Insert data into media_types
INSERT INTO litera_vibe.media_types (mime_type)
VALUES ('image/jpeg'),
       ('audio/mp3'),
       ('video/mp4'),
       ('application/pdf'),
       ('text/plain');

-- Insert data into media
INSERT INTO litera_vibe.media (type_id, file_data)
VALUES (1, E'\\x0123456789ABCDEF'),
       (2, E'\\x0123456789ABCDEF'),
       (3, E'\\x0123456789ABCDEF'),
       (4, E'\\x0123456789ABCDEF'),
       (5, E'\\x0123456789ABCDEF');

-- Insert data into users
INSERT INTO litera_vibe.users (login, password)
VALUES ('root', 'root'),
       ('Alice Smith', 'password2'),
       ('Bob Johnson', 'password3'),
       ('Eva Davis', 'password4'),
       ('Charlie Brown', 'password5');

-- Insert data into authors
INSERT INTO litera_vibe.authors (first_name, second_name, info)
VALUES ('J.K.', 'Rowling', 'Bestselling author of Harry Potter series'),
       ('George', 'Orwell', 'Known for 1984 and Animal Farm'),
       ('Jane', 'Austen', 'Classic author of Pride and Prejudice'),
       ('Stephen', 'King', 'Master of horror fiction'),
       ('Agatha', 'Christie', 'Queen of mystery novels');

-- Insert data into publishers
INSERT INTO litera_vibe.publishers (display_name, isbn, password)
VALUES ('Publisher A', '1234567890123', 'pubpassword1'),
       ('Publisher B', '2345678901234', 'pubpassword2'),
       ('Publisher C', '3456789012345', 'pubpassword3'),
       ('Publisher D', '4567890123456', 'pubpassword4'),
       ('Publisher E', '5678901234567', 'pubpassword5');

-- Insert data into books
INSERT INTO litera_vibe.books (name, media_id, pages, info, year, publisher_id)
VALUES ('Book 1', 1, 300, 'Fantasy novel', '2020-01-01', 1),
       ('Book 2', 2, 250, 'Dystopian fiction', '2018-05-15', 2),
       ('Book 3',  3, 400, 'Classic romance', '1813-01-28', 3),
       ('Book 4',  4, 500, 'Horror thriller', '1974-04-01', 4),
       ('Book 5', 5, 350, 'Mystery novel', '1920-01-01', 5);


-- Insert data into comments
INSERT INTO litera_vibe.comments (user_id, book_id, date, content)
VALUES (1, 1, CURRENT_TIMESTAMP, 'Great book!'),
       (2, 1, CURRENT_TIMESTAMP, 'I love the characters.'),
       (3, 2, CURRENT_TIMESTAMP, 'Thought-provoking.'),
       (4, 3, CURRENT_TIMESTAMP, 'Classic romance at its best.'),
       (5, 4, CURRENT_TIMESTAMP, 'Scary and thrilling!'),
       (1, 5, CURRENT_TIMESTAMP, 'Who is the murderer?');

-- Insert data into marks
INSERT INTO litera_vibe.marks (user_id, book_id, mark)
VALUES (1, 1, 4),
       (2, 1, 5),
       (3, 2, 4),
       (4, 3, 5),
       (5, 4, 4),
       (1, 5, 5);

-- Note: The triggers will automatically update the avg_marks table after the insert/update/delete operations.


-- Insert data into categories
INSERT INTO litera_vibe.categories (name)
VALUES ('Fantasy'),
       ('Science Fiction'),
       ('Romance'),
       ('Mystery'),
       ('Horror');

-- Insert data into categories_distribution
INSERT INTO litera_vibe.categories_distribution (category_id, book_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

-- Insert data into collections
INSERT INTO litera_vibe.collections (name)
VALUES ('Bestsellers'),
       ('Classic Novels'),
       ('Thrillers'),
       ('Science Fiction Favorites'),
       ('Must-Read Mysteries');

-- Insert data into collections_distribution
INSERT INTO litera_vibe.collections_distribution (collection_id, book_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);


INSERT INTO  litera_vibe.roles(name)
VALUES ('ADMIN');

INSERT INTO  litera_vibe.roles(name)
VALUES ('USER');

INSERT INTO  litera_vibe.roles(name)
VALUES ('PUBLISHER');

CREATE extension IF NOT EXISTS pgcrypto;



INSERT INTO litera_vibe.authors_books (author_id, book_id)
VALUES (1, 1), -- Author 1 associated with Book 1
       (1, 2), -- Author 1 associated with Book 2
       (2, 3), -- Author 2 associated with Book 3
       (2, 4), -- Author 2 associated with Book 4
       (3, 5); -- Author 3 associated with Book 5

INSERT INTO litera_vibe.user_role(user_id, role_id)
VALUES (2, 1);


CREATE extension IF NOT EXISTS pgcrypto;

UPDATE litera_vibe.users
SET password = crypt(password, gen_salt('bf', 8));