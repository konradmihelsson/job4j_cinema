CREATE TABLE IF NOT EXISTS accounts
(
    id       SERIAL PRIMARY KEY,
    username TEXT,
    email    TEXT,
    phone    TEXT
);
CREATE TABLE IF NOT EXISTS tickets
(
    id         SERIAL PRIMARY KEY,
    session_id INT,
    line       INT,
    seat       INT,
    account_id INT REFERENCES accounts (id),
    UNIQUE (session_id, line, seat)
);
