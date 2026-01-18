-- =========================
-- USERS FOR AUTH TESTING
-- =========================

INSERT INTO users (
    id,
    username,
    password,
    role,
    enabled,
    created_at
) VALUES (
             gen_random_uuid(),
             'librarian',
             '$2a$10$7EqJtq98hPqEX7fNZaFWoOa6zvYzX5eQof8I4oyAbQq8F3F4h1F8a',
             'LIBRARIAN',
             true,
             now()
         );

INSERT INTO users (
    id,
    username,
    password,
    role,
    enabled,
    created_at
) VALUES (
             gen_random_uuid(),
             'member',
             '$2a$10$7EqJtq98hPqEX7fNZaFWoOa6zvYzX5eQof8I4oyAbQq8F3F4h1F8a',
             'MEMBER',
             true,
             now()
         );
