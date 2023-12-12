CREATE TABLE companies (
    id uuid PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    cnpj VARCHAR(100) NOT NULL,
    category VARCHAR(255) NOT NULL,
    distance NUMERIC,
    description TEXT,
    rating NUMERIC,
    opened BOOLEAN,
    active BOOLEAN,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);