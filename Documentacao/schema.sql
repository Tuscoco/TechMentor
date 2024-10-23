-- CRIAR BANCO DE DADOS
CREATE DATABASE techmentor;

-- CRIAR TABELA DE PESSOA
CREATE TABLE pessoa (
    id INTEGER NOT NULL, 
    nome VARCHAR(100) NOT NULL, 
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(20),
    foto BYTEA, 
    tipo_usuario INTEGER NOT NULL,
    PRIMARY KEY(id)
);

-- CRIAR TABELA MATERIA
CREATE TABLE materia (
    id_materia INTEGER NOT NULL, 
    nome VARCHAR(50) NOT NULL, 
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
    PRIMARY KEY(id_monitor, id_materia), 
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
    FOREIGN KEY(id_monitor, id_materia) REFERENCES monitoria(id_monitor, id_materia)
);

-- CRIAR TABELA CARGA_HORARIA
CREATE TABLE carga_horaria (
    id_monitor INTEGER NOT NULL, 
    id_materia INTEGER, 
    dia_semana VARCHAR(20) NOT NULL, 
    horario_entrada TIME, 
    horario_saida TIME, 
    PRIMARY KEY(id_monitor, id_materia, dia_semana), 
    FOREIGN KEY(id_monitor, id_materia) REFERENCES monitoria(id_monitor, id_materia)
);
