
INSERT INTO users (
    id,
    username,
    password,
    role
) VALUES (
             gen_random_uuid(),
             'admin',
             '$2a$10$7EqJtq98hPqEX7fNZaFWoOa6zvYzX5eQof8I4oyAbQq8F3F4h1F8a',
             'ADMIN'
         );

INSERT INTO users (
    id,
    username,
    password,
    role
) VALUES (
             gen_random_uuid(),
             'user',
             '$2a$10$7EqJtq98hPqEX7fNZaFWoOa6zvYzX5eQof8I4oyAbQq8F3F4h1F8a',
             'USER'
         );
