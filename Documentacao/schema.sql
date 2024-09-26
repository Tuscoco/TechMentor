-- CRIAR BANCO DE DADOS
CREATE DATABASE techmentor;

-- CRIAR TABELA DE PESSOA
CREATE TABLE pessoa (id INTEGER NOT NULL, nome VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, senha VARCHAR(40) NOT NULL, imagem VARCHAR(60),PRIMARY KEY(id));

-- CRIAR TABELA ALUNO
CREATE TABLE aluno (matricula INTEGER NOT NULL, id INTEGER NOT NULL, PRIMARY KEY(matricula), FOREIGN KEY(id) REFERENCES pessoa(id) ON DELETE SET NULL);

-- CRIAR TABELA COORDENADOR
CREATE TABLE coordenador (cod_coordenador INTEGER NOT NULL, id INTEGER NOT NULL, PRIMARY KEY(cod_coordenador), FOREIGN KEY(id) REFERENCES pessoa(id) ON DELETE SET NULL);

-- CRIAR TABELA MATERIA
CREATE TABLE materia (cod_materia INTEGER NOT NULL, nome VARCHAR(50) NOT NULL, PRIMARY KEY(cod_materia));

-- CRIAR TABELA MATERIA_ALUNO
CREATE TABLE materia_aluno (matricula INTEGER NOT NULL, cod_materia INTEGER NOT NULL, PRIMARY KEY(matricula, cod_materia), FOREIGN KEY(matricula) REFERENCES aluno(matricula) ON DELETE CASCADE, FOREIGN KEY(cod_materia) REFERENCES materia(cod_materia) ON DELETE CASCADE);

-- CRIAR TABELA EVENTO
CREATE TABLE evento (local VARCHAR(100) NOT NULL, data_hora VARCHAR(30), materia INTEGER NOT NULL, nome VARCHAR(50), PRIMARY KEY(local, data_hora), FOREIGN KEY(materia) REFERENCES materia(cod_materia) ON DELETE SET NULL);

-- CRIAR TABELA REPOSITORIO
CREATE TABLE repositorio (nome VARCHAR(50) NOT NULL, link VARCHAR(300) NOT NULL, descricao VARCHAR(300), cod_materia INTEGER NOT NULL, PRIMARY KEY(cod_materia), FOREIGN KEY(cod_materia) REFERENCES materia(cod_materia) ON DELETE SET NULL);

-- CRIAR TABELA MONITOR
CREATE TABLE monitor (matricula INTEGER NOT NULL, cod_materia INTEGER NOT NULL, esta_online BOOLEAN NOT NULL, PRIMARY KEY(matricula, cod_materia), FOREIGN KEY(matricula) REFERENCES aluno(matricula) ON DELETE CASCADE, FOREIGN KEY(cod_materia) REFERENCES materia(cod_materia) ON DELETE CASCADE);

-- CRIAR TABELA ATENDIMENTO
CREATE TABLE atendimento (matricula_a INTEGER NOT NULL, matricula_m INTEGER NOT NULL, horario VARCHAR(10) NOT NULL, data VARCHAR(20) NOT NULL, cod_materia INTEGER NOT NULL, tema_duvida VARCHAR(20) NOT NULL, descricao VARCHAR(100) NULL, duvida_sanada BOOLEAN NOT NULL, PRIMARY KEY(matricula_a, matricula_m, horario, data, cod_materia), FOREIGN KEY(matricula_a) REFERENCES aluno(matricula), FOREIGN KEY(matricula_m) REFERENCES monitor(matricula), FOREIGN KEY(cod_materia) REFERENCES monitor(cod_materia));

-- CRIAR TABELA CARGA_HORARIA
CREATE TABLE carga_horaria (dia_da_semana VARCHAR(10) NOT NULL, matricula INTEGER NOT NULL, cod_materia INTEGER NOT NULL, horario_entrada VARCHAR(20) NULL, horario_saida VARCHAR(20) NULL, PRIMARY KEY(dia_da_semana, matricula, cod_materia), FOREIGN KEY(matricula, cod_materia) REFERENCES monitor(matricula, cod_materia));

