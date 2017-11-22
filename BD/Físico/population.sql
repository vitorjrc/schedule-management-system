
-- ------------------------------------------------------
-- ------------------------------------------------------
-- Povoamento inicial da base de dados
-- ------------------------------------------------------
-- ------------------------------------------------------
-- VERIFICAR POR NUMEROS REPETIDOS!!!
-- FAZER FORWARD ENGINEERING OUTRA VEZ!!!
--
-- Esquema: "TrocaTurnos"
USE `TrocaTurnos` ;

--
-- Permissão para fazer operações de remoção de dados.
SET SQL_SAFE_UPDATES = 0;

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
		(26548, 039),

	;

--
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
		(323, 'Programação Funcional', 1, 5),
		(094, 'Desenvolvimento de Sistemas de Software', 3, 5),
		(039, 'Arquitetura de Computadores', 2, 5),
		(455, 'Programação Imperativa', 1, 5),
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
-- Povoamento da tabela "UCAluno"
INSERT INTO UCAluno
	(AnoLetivo, Tipo, UC_Codigo, Aluno_Numero)
	VALUES
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),
		(1617, 'TESTE', 0.10, ),
		(1617, 'TESTE', 0.15, ),

	;

--
-- Povoamento da tabela "Aluno"
INSERT INTO Aluno
	(Numero, Nome, Curso)
	VALUES
		(78985, 'Maria Inês Machado', 'Engenharia Informática'),
		(77730, 'Vítor Campos', 'Engenharia Informática'),
		(79116, 'Sérgio Godinho', 'Engenharia Informática'),
		(77870, 'Diana Oliveira', 'Engenharia Informática'),
		(76111, 'Marcos Matos', 'Engenharia Informática'),
		(77321, 'Leonor Castro', 'Engenharia Informática'),
		(78404, 'Bruno Silva', 'Engenharia Informática'),
		(68503, 'Duarte Machado', 'Engenharia Informática'),
		(69545, 'Gonçalo Oliveira', 'Engenharia Informática'),
		(73435, 'Nuno Barreira', 'Engenharia Informática'),
		(74584, 'Rafael Silva', 'Engenharia Informática'),
		(81230, 'João Afonso', 'Engenharia Informática'),
		(78790, 'Rafaela Santos', 'Engenharia Informática'),
		(80828, 'Eduardo Barcelona', 'Engenharia Informática'),
		(69308, 'Alexandre Marques', 'Engenharia Informática'),
		(70920, 'André Maia', 'Engenharia Informática'),
		(70815, 'Paulo Silva', 'Engenharia Informática'),
		(76275, 'Sara Couto', 'Engenharia Informática'),
		(78633, 'Artur Oliveira', 'Engenharia Informática'),
		(73314, 'João Cruz', 'Engenharia Informática'),
		(81122, 'Catarina Araújo', 'Engenharia Informática'),
		(73941, 'Joaquim Rebelo', 'Engenharia Informática'),
		(72974, 'João Cunha', 'Engenharia Informática'),
		(80979, 'Ricardo Martins', 'Engenharia Informática'),
		(76935, 'Válter Carvalho', 'Engenharia Informática'),
		(75820, 'Miguel Cardoso', 'Engenharia Informática'),
		(70608, 'Beatriz Sousa', 'Engenharia Informática'),
		(77209, 'Juliana Pereira', 'Engenharia Informática'),
		(80885, 'Francisco Lira', 'Engenharia Informática'),
		(67848, 'João Soares', 'Engenharia Informática'),
		(72111, 'Alexandre Pedrosa', 'Engenharia Informática'),
		(78415, 'Sara Sampaio', 'Engenharia Informática'),
		(70629, 'Anderson Vítor', 'Engenharia Informática'),
		(66906, 'Hugo Gramacho', 'Engenharia Informática'),
		(78789, 'Francisco Xavier', 'Engenharia Informática'),
		(65146, 'Alcino Teixeira', 'Engenharia Informática'),
		(69634, 'Daniela Ribeiro', 'Engenharia Informática'),
		(71544, 'Daniel Fernandes', 'Engenharia Informática'),
		(80460, 'Daniel Macedo', 'Engenharia Informática'),
		(81682, 'Flávio Martins', 'Engenharia Informática'),
		(68892, 'Gil Sampaio', 'Engenharia Informática')
	;

-- ------------------------------------------------------
-- <fim>
-- ------------------------------------------------------
