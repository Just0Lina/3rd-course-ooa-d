CREATE TABLE IF NOT EXISTS  litera_vibe.comments
(
    id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id   bigint REFERENCES  litera_vibe.users ON DELETE CASCADE,
    book_id bigint REFERENCES  litera_vibe.books ON DELETE CASCADE,
    date      timestamp,
    content   text NOT NULL CHECK (trim(content) <> '')
);


CREATE TABLE IF NOT EXISTS  litera_vibe.marks
(
    user_id   bigint REFERENCES  litera_vibe.users,
    book_id bigint REFERENCES  litera_vibe.books ON DELETE CASCADE,
    mark      smallint CHECK (mark > 0 AND mark <= 5),
    PRIMARY KEY (user_id, book_id)
);


CREATE TABLE IF NOT EXISTS  litera_vibe.avg_marks
(
    book_id bigint REFERENCES  litera_vibe.books PRIMARY KEY,
    avg_mark  real CHECK (avg_mark > 0),
    quantity  bigint CHECK (quantity >= 0)
);

CREATE OR REPLACE FUNCTION add_avg_mark()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM  litera_vibe.avg_marks WHERE book_id = NEW.book_id) THEN
        UPDATE  litera_vibe.avg_marks
        SET avg_mark = (SELECT AVG(mark) FROM  litera_vibe.marks WHERE book_id = NEW.book_id),
            quantity = quantity + 1
        WHERE book_id = NEW.book_id;
    ELSE
        INSERT INTO  litera_vibe.avg_marks (book_id, avg_mark, quantity)
        VALUES (NEW.book_id, NEW.mark, 1);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION update_avg_mark()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM  litera_vibe.avg_marks WHERE book_id = NEW.book_id) THEN
        UPDATE  litera_vibe.avg_marks
        SET avg_mark = (SELECT AVG(mark) FROM marks WHERE book_id = NEW.book_id)
        WHERE book_id = NEW.book_id;
    ELSE
        INSERT INTO  litera_vibe.avg_marks (book_id, avg_mark, quantity)
        VALUES (NEW.book_id, NEW.mark, 1);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION remove_avg_mark()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE  litera_vibe.avg_marks
    SET avg_mark = (SELECT AVG(mark) FROM  litera_vibe.marks WHERE book_id = OLD.book_id),
        quantity = quantity - 1
    WHERE book_id = OLD.book_id;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE TRIGGER add_avg_mark_trigger
    AFTER INSERT
    ON  litera_vibe.marks
    FOR EACH ROW
EXECUTE FUNCTION add_avg_mark();

CREATE OR REPLACE TRIGGER remove_avg_mark_trigger
    AFTER DELETE
    ON  litera_vibe.marks
    FOR EACH ROW
EXECUTE FUNCTION remove_avg_mark();

CREATE OR REPLACE TRIGGER update_avg_mark_trigger
    AFTER UPDATE
    ON  litera_vibe.marks
    FOR EACH ROW
EXECUTE FUNCTION update_avg_mark();

