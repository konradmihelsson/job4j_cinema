CREATE TABLE accounts
(
    id       SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    email    TEXT NOT NULL UNIQUE,
    phone    TEXT NOT NULL UNIQUE
);
CREATE TABLE tickets
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL,
    line       INT NOT NULL,
    seat       INT NOT NULL,
    account_id INT NOT NULL REFERENCES accounts (id),
    UNIQUE (session_id, line, seat)
);
INSERT INTO accounts (username, email, phone) VALUES ('user', 'email', 'phone');
INSERT INTO tickets (session_id, line, seat, account_id) VALUES (1, 2, 2, 1);
INSERT INTO tickets (session_id, line, seat, account_id) VALUES (1, 2, 1, 1);
