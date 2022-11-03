CREATE SEQUENCE  IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
   id BIGINT NOT NULL DEFAULT nextval('users_id_seq'),
   email TEXT NOT NULL,
   password TEXT,
   firstname TEXT,
   lastname TEXT,
   phone TEXT,
   CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER SEQUENCE users_id_seq OWNED BY users.id;