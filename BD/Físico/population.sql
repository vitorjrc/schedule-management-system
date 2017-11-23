
-- ------------------------------------------------------
-- ------------------------------------------------------
-- Povoamento inicial da base de dados
-- ------------------------------------------------------
-- ------------------------------------------------------
--
-- Esquema: "TrocaTurnos"
USE `trocaturnos` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;

ALTER TABLE trocaturnos.aluno CONVERT TO CHARACTER SET utf8;
ALTER TABLE trocaturnos.docente CONVERT TO CHARACTER SET utf8;
ALTER TABLE trocaturnos.docenteuc CONVERT TO CHARACTER SET utf8;
ALTER TABLE trocaturnos.uc CONVERT TO CHARACTER SET utf8;
ALTER TABLE trocaturnos.ucaluno CONVERT TO CHARACTER SET utf8;


-- DELETE FROM Aluno;
-- DELETE FROM Docente;
-- DELETE FROM UC;
-- DELETE FROM DocenteUC;
-- DELETE FROM UCAluno;


--
-- Povoamento da tabela "Aluno"
INSERT INTO Aluno
	(Numero, Nome, Curso_Nome)
	VALUES
		(78985, 'Maria Inês Machado', 'MIEI'),
		(77730, 'Vítor Campos', 'MIEI'),
		(79116, 'Sérgio Godinho', 'MIEI'),
		(77870, 'Diana Oliveira', 'MIEI'),
		(76111, 'Marcos Matos', 'MIEI'),
		(77321, 'Leonor Castro', 'MIEI'),
		(78404, 'Bruno Silva', 'MIEI'),
		(68503, 'Duarte Machado', 'MIEI'),
		(69545, 'Gonçalo Oliveira', 'MIEI'),
		(73435, 'Nuno Barreira', 'MIEI'),
		(74584, 'Rafael Silva', 'MIEI'),
		(81230, 'João Afonso', 'MIEI'),
		(78790, 'Rafaela Santos', 'MIEI'),
		(80828, 'Eduardo Barcelona', 'MIEI'),
		(69308, 'Alexandre Marques', 'MIEI'),
		(70920, 'André Maia', 'MIEI'),
		(70815, 'Paulo Silva', 'MIEI'),
		(76275, 'Sara Couto', 'MIEI'),
		(78633, 'Artur Oliveira', 'MIEI'),
		(73314, 'João Cruz', 'MIEI'),
		(81122, 'Catarina Araújo', 'MIEI'),
		(73941, 'Joaquim Rebelo', 'MIEI'),
		(72974, 'João Cunha', 'MIEI'),
		(80979, 'Ricardo Martins', 'MIEI'),
		(76935, 'Válter Carvalho', 'MIEI'),
		(75820, 'Miguel Cardoso', 'MIEI'),
		(70608, 'Beatriz Sousa', 'MIEI'),
		(77209, 'Juliana Pereira', 'MIEI'),
		(80885, 'Francisco Lira', 'MIEI'),
		(67848, 'João Soares', 'MIEI'),
		(72111, 'Alexandre Pedrosa', 'MIEI'),
		(78415, 'Sara Sampaio', 'MIEI'),
		(70629, 'Anderson Vítor', 'MIEI'),
		(66906, 'Hugo Gramacho', 'MIEI'),
		(78789, 'Francisco Xavier', 'MIEI'),
		(69634, 'Daniela Ribeiro', 'MIEI'),
		(71544, 'Daniel Fernandes', 'MIEI'),
		(80460, 'Daniel Macedo', 'MIEI'),
		(81682, 'Flávio Martins', 'MIEI'),
		(68892, 'Gil Sampaio', 'MIEI'),
		(51146, 'André Azevedo', 'MIEI')

	;


-- CONSIDERAR Q SAO TUDO CADEIRAS DE 1 SO SEMESTRE-------------------------------------------------
-- Povoamento da tabela "UC"
INSERT INTO UC
	(Codigo, Nome, Ano, ECTS)
	VALUES
		(365, 'Álgebra Linear', 1, 5),
		(423, 'Cálculo', 1, 5),
		(122, 'Algoritmos e Complexidade', 2, 5),
		(050, 'Programação Orientada aos Objetos', 2, 5),
		(403, 'Sistemas Operativos', 2, 5),
		(552, 'Redes de Computadores', 3, 5),
		(327, 'Cálculo de Programas', 2, 5),
		(667, 'Bases de Dados', 3, 5),
		(103, 'Laboratórios de Informática I', 1, 5),
		(210, 'Laboratórios de Informática II', 1, 5),
		(093, 'Lógica', 1, 5),
		(323, 'Programacão Funcional', 1, 5),
		(094, 'Desenvolvimento de Sistemas de Software', 3, 5),
		(039, 'Arquitetura de Computadores', 2, 5),
		(455, 'Programacão Imperativa', 1, 5),
		(312, 'Estatística Aplicada', 2, 5),
		(612, 'Sistemas de Computação', 1, 5),
		(112, 'Análise', 1, 5),
		(605, 'Engenharia Económica', 2, 5),
		(498, 'Introdução aos Sistemas Dinâmicos', 2, 5),
		(067, 'Electromagnetismo', 2, 5),
		(604, 'Computação Gráfica', 3, 5),
		(389, 'Processamento de Linguagens', 3, 5),
		(400, 'Elementos de Engenharia de Sistemas', 1, 5),
		(399, 'Métodos Numéricos e Otimização não Linear', 3, 5),
		(010, 'Laboratórios de Informática IV', 3, 5),
		(702, 'Comunicações por Computador', 3, 5)
	;


--
-- Povoamento da tabela "Docente"
INSERT INTO Docente
	(Numero, Nome, Escola)
	VALUES

		(18054, 'Amélia Campos' ,'Escola de Ciências'),
		(59341, 'Serafim Andrade', 'Escola de Engenharia'),
		(48592, 'Carla Santos', 'Escola de Ciências'),
		(41495, 'António Macedo', 'Escola de Engenharia'),
		(59479, 'José Frade', 'Escola de Engenharia'),
		(64850, 'Dulce Oliveira', 'Escola de Engenharia'),
		(79194, 'Valério Quintas', 'Escola de Ciências'),
		(24759, 'Maria Minho', 'Escola de Ciências'),
		(74123, 'Carlos Castro', 'Escola de Engenharia'),
		(13212, 'Ivone Costa', 'Escola de Engenharia'),
		(43923, 'Cornélio Belo', 'Escola de Engenharia'),
		(26548, 'Dinis Gabriel', 'Escola de Engenharia')
	;

--
-- Povoamento da tabela "DocenteUC"
INSERT INTO DocenteUC
	(Docente_Numero, UC_Codigo)
	VALUES
		(18054, 365),
		(18054, 093),
		(59341, 122),
		(59341, 604),
		(59341, 702),
		(48592, 423),
		(48592, 067),
		(41495, 403),
		(41495, 455),
		(41495, 327),
		(59479, 552),
		(64850, 323),
		(64850, 604),
		(64850, 455),
		(79194, 112),
		(79194, 093),
		(24759, 312),
		(24759, 498),
		(24759, 399),
		(74123, 389),
		(74123, 667),
		(13212, 050),
		(13212, 400),
		(43923, 103),
		(43923, 612),
		(43923, 605),
		(43923, 010),
		(26548, 210),
		(26548, 094),
		(26548, 039)

	;

--
-- Povoamento da tabela "UCAluno"
INSERT INTO UCAluno
	(AnoLetivo, Turno, UC_Codigo, Aluno_Numero)
	VALUES
		(1617, 'PL1', 365, 78985),
		(1617, 'PL1', 423, 78985),
		(1617, 'PL1', 122, 78985),
		(1617, 'PL1', 050, 77730),
		(1617, 'PL1', 403, 77730),
		(1617, 'PL1', 552, 77730),
		(1617, 'PL1', 423, 77730),
		(1617, 'PL1', 050, 79116),
		(1617, 'PL1', 403, 79116),
		(1617, 'PL1', 327, 79116),
		(1617, 'PL1', 365, 79116),
		(1617, 'PL1', 122, 77870),
		(1617, 'PL1', 667, 77870),
		(1617, 'PL1', 103, 77870),
		(1617, 'PL1', 667, 76111),
		(1617, 'PL1', 103, 76111),
		(1617, 'PL1', 093, 76111),
		(1617, 'PL1', 323, 76111),
		(1617, 'PL1', 667, 77321),
		(1617, 'PL1', 094, 77321),
		(1617, 'PL1', 039, 77321),
		(1617, 'PL2', 667, 78404),
		(1617, 'PL1', 094, 78404),
		(1617, 'PL1', 039, 78404),
		(1617, 'PL2', 050, 68503),
		(1617, 'PL2', 103, 68503),
		(1617, 'PL1', 093, 68503),
		(1617, 'PL1', 323, 68503),
		(1617, 'PL2', 039, 69545),
		(1617, 'PL1', 455, 69545),
		(1617, 'PL1', 312, 69545),
		(1617, 'PL2', 039, 73435),
		(1617, 'PL1', 455, 73435),
		(1617, 'PL1', 312, 73435),
		(1617, 'PL2', 667, 74584),
		(1617, 'PL2', 312, 81230),
		(1617, 'PL1', 612, 81230),
		(1617, 'PL1', 112, 81230),
		(1617, 'PL2', 050, 81230),
		(1617, 'PL1', 112, 78790),
		(1617, 'PL1', 612, 78790),
		(1617, 'PL2', 612, 80828),
		(1617, 'PL3', 667, 80828),
		(1617, 'PL2', 112, 80828),
		(1617, 'PL1', 605, 80828),
		(1617, 'PL1', 605, 69308),
		(1617, 'PL1', 498, 69308),
		(1617, 'PL1', 552, 69308),
		(1617, 'PL1', 498, 70920),
		(1617, 'PL2', 605, 70920),
		(1617, 'PL2', 498, 70815),
		(1617, 'PL1', 067, 70815),
		(1617, 'PL1', 604, 70815),
		(1617, 'PL1', 389, 70815),
		(1617, 'PL1', 604, 76275),
		(1617, 'PL1', 389, 76275),
		(1617, 'PL2', 389, 78633),
		(1617, 'PL1', 400, 78633),
		(1617, 'PL1', 399, 78633),
		(1617, 'PL1', 399, 73314),
		(1617, 'PL1', 400, 73314),
		(1617, 'PL2', 093, 73314),
		(1617, 'PL1', 010, 81122),
		(1617, 'PL1', 702, 81122),
		(1617, 'PL2', 400, 81122),
		(1617, 'PL1', 010, 73941),
		(1617, 'PL1', 702, 73941),
		(1617, 'PL2', 605, 73941),
		(1617, 'PL2', 010, 72974),
		(1617, 'PL2', 702, 72974),
		(1617, 'PL2', 702, 80979),
		(1617, 'PL3', 667, 80979),
		(1617, 'PL2', 103, 80979),
		(1617, 'PL2', 093, 80979),
		(1617, 'PL3', 702, 76935),
		(1617, 'PL2', 039, 76935),
		(1617, 'PL3', 050, 76935),
		(1617, 'PL1', 210, 75820),
		(1617, 'PL2', 094, 75820),
		(1617, 'PL2', 612, 75820),
		(1617, 'PL2', 604, 70608),
		(1617, 'PL2', 112, 70608),
		(1617, 'PL2', 498, 77209),
		(1617, 'PL1', 327, 77209),
		(1617, 'PL2', 423, 77209),
		(1617, 'PL2', 365, 80885),
		(1617, 'PL2', 389, 80885),
		(1617, 'PL2', 400, 80885),
		(1617, 'PL2', 323, 80885),
		(1617, 'PL3', 667, 67848),
		(1617, 'PL3', 702, 67848),
		(1617, 'PL2', 010, 67848),
		(1617, 'PL2', 399, 67848),
		(1617, 'PL3', 010, 72111),
		(1617, 'PL2', 399, 72111),
		(1617, 'PL3', 112, 72111),
		(1617, 'PL2', 365, 78415),
		(1617, 'PL2', 423, 78415),
		(1617, 'PL1', 122, 78415),
		(1617, 'PL3', 050, 78415),
		(1617, 'PL1', 403, 70629),
		(1617, 'PL1', 552, 70629),
		(1617, 'PL1', 327, 70629),
		(1617, 'PL4', 667, 66906),
		(1617, 'PL2', 103, 66906),
		(1617, 'PL1', 210, 66906),
		(1617, 'PL2', 093, 78789),
		(1617, 'PL2', 323, 78789),
		(1617, 'PL2', 094, 78789),
		(1617, 'PL1', 039, 78789),
		(1617, 'PL2', 455, 51146),
		(1617, 'PL2', 312, 51146),
		(1617, 'PL2', 612, 69634),
		(1617, 'PL3', 112, 69634),
		(1617, 'PL1', 605, 69634),
		(1617, 'PL2', 498, 71544),
		(1617, 'PL1', 067, 71544),
		(1617, 'PL2', 604, 71544),
		(1617, 'PL2', 389, 80460),
		(1617, 'PL2', 400, 80460),
		(1617, 'PL3', 399, 80460),
		(1617, 'PL3', 010, 81682),
		(1617, 'PL4', 702, 81682),
		(1617, 'PL4', 667, 81682),
		(1617, 'PL3', 010, 68892),
		(1617, 'PL4', 702, 68892),
		(1617, 'PL4', 667, 68892)

	;

--
-- Povoamento da tabela "Curso"
INSERT INTO Curso
	(Nome)
	VALUES
		('MIEI')

	;

-- ------------------------------------------------------
-- <fim>
-- ------------------------------------------------------
