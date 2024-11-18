-- CRIAR BANCO DE DADOS
CREATE DATABASE techmentor;

-- CRIAR TABELA DE PESSOA
CREATE TABLE pessoa (
    id INTEGER NOT NULL, 
    nome VARCHAR(100) NOT NULL, 
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100),
    foto TEXT, 
    tipo_usuario INTEGER NOT NULL,
    PRIMARY KEY(id)
);

-- CRIAR TABELA MATERIA
CREATE TABLE materia (
    id_materia INTEGER NOT NULL, 
    nome VARCHAR(100) NOT NULL, 
    PRIMARY KEY(id_materia)
);

-- CRIAR TABELA EVENTO
CREATE TABLE evento (
    id SERIAL PRIMARY KEY, 
    local VARCHAR(100) NOT NULL, 
    data TEXT,
    hora TEXT, 
    id_materia INTEGER,
    nome VARCHAR(50), 
    FOREIGN KEY(id_materia) REFERENCES materia(id_materia) ON DELETE SET NULL
);

-- CRIAR TABELA REPOSITORIO
CREATE TABLE repositorio (
    id SERIAL PRIMARY KEY, 
    nome VARCHAR(50) NOT NULL, 
    link TEXT NOT NULL
);

-- CRIAR TABELA MONITORIA
CREATE TABLE monitoria (
    id_monitor INTEGER, 
    id_materia INTEGER, 
    online BOOLEAN, 
    sala INTEGER,
    foto1 TEXT,
    foto2 TEXT,
    PRIMARY KEY(id_monitor), 
    FOREIGN KEY(id_monitor) REFERENCES pessoa(id) ON DELETE CASCADE, 
    FOREIGN KEY(id_materia) REFERENCES materia(id_materia) ON DELETE CASCADE
);

-- CRIAR TABELA ATENDIMENTO
CREATE TABLE atendimento (
    id SERIAL PRIMARY KEY, 
    id_monitor INTEGER NOT NULL, 
    id_aluno INTEGER NOT NULL, 
    data TEXT, 
    id_materia INTEGER NOT NULL, 
    descricao TEXT,
    tema_duvida TEXT,
    duvida_sanada BOOLEAN,
    FOREIGN KEY(id_aluno) REFERENCES pessoa(id), 
    FOREIGN KEY(id_monitor) REFERENCES monitoria(id_monitor),
    FOREIGN KEY(id_materia) REFERENCES materia(id_materia)
);

-- CRIAR TABELA PONTO
CREATE TABLE ponto (
    id_monitor INTEGER NOT NULL, 
    data VARCHAR(20), 
    PRIMARY KEY(id_monitor), 
    FOREIGN KEY(id_monitor) REFERENCES monitoria(id_monitor)
);
