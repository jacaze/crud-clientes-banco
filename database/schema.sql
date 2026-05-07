CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(255),
    numero INT,
    cep VARCHAR(20),
    municipio VARCHAR(100)
);

CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    cpf VARCHAR(11) UNIQUE,
    nome VARCHAR(50),
    telefone VARCHAR(15),
    endereco_id INT,
    CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE contas (
    id SERIAL PRIMARY KEY,
    saldo DECIMAL(10,2),
    tipo_conta VARCHAR(20),
    ativa BOOLEAN,
    cliente_id INT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);