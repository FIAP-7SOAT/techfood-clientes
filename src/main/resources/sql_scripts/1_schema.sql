-- Table: tb_clients
CREATE TABLE IF NOT EXISTS tb_clients (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT tb_clients_pkey PRIMARY KEY (id),
    CONSTRAINT tb_clients_cpf_key UNIQUE (cpf),
    CONSTRAINT tb_clients_email_key UNIQUE (email)
);