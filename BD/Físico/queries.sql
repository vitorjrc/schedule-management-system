/*
 Os alunos devem ser capazes de obter a lista de cadeiras 
 a que estão inscritos.
*/
DROP PROCEDURE IF EXISTS lista_cadeiras;
DELIMITER $$
CREATE PROCEDURE lista_cadeiras(id_aluno INT)
BEGIN
	SELECT UC.Nome FROM UC
		INNER JOIN UCAluno ON UCAluno.UC_Codigo = UC.Codigo
		WHERE UCAluno.Aluno_Numero = id_aluno;
END
$$
DELIMITER ;

CALL lista_cadeiras(78985);

-- -------------------------------------------------------------------

/*
 Os alunos devem ser capazes de obter a listagem de turnos
 a que estão inscritos.
*/

DROP PROCEDURE IF EXISTS lista_turnos;
DELIMITER $$
CREATE PROCEDURE lista_turnos(id_aluno INT)
BEGIN
	SELECT UC.Nome, UCAluno.Turno FROM UCAluno
		INNER JOIN UC ON UC.Codigo = UCAluno.UC_Codigo 
		WHERE UCAluno.Aluno_Numero = id_aluno;
END
$$
DELIMITER ;

CALL lista_turnos(78985);


-- --------------------------------------------------------------------
/*
 Os docentes devem ser capazes de obter a lista dos alunos das UC's
 que lecionam.
*/
DROP PROCEDURE IF EXISTS lista_alunos;
DELIMITER $$
CREATE PROCEDURE lista_alunos(id_docente INT)
BEGIN
	SELECT a.Nome as NomeAluno, b.Nome as NomeUC FROM Aluno a
    INNER JOIN UCAluno ON UCAluno.Aluno_Numero = a.Numero
    INNER JOIN UC b ON b.Codigo = UCAluno.UC_Codigo
    INNER JOIN DocenteUC ON DocenteUC.UC_Codigo = b.Codigo
    WHERE DocenteUC.Docente_Numero = id_docente;
END
$$
DELIMITER ;

CALL lista_alunos(79194);


-- ----------------------------------------------------------------------
/*
 O diretor de curso deve ser capaz de obter a listagem de todos os 
 alunos do curso.
*/
SELECT Aluno.Numero, Aluno.Nome FROM Aluno;


-- ----------------------------------------------------------------------
/*
 O diretor de curso deve ser capaz de aceder à lista
 de alunos e respectivas cadeiras.
*/
SELECT Aluno.Numero, Aluno.Nome, UC.Nome FROM Aluno
	INNER JOIN UCAluno ON UCAluno.Aluno_Numero = Aluno.Numero
    INNER JOIN UC ON UC.Codigo = UCAluno.UC_Codigo;


-- ----------------------------------------------------------------------    
 /*
 O diretor de curso deve conseguir saber o total de UC's que
 estão a ser lecionadas no curso.
*/   
SELECT COUNT(Codigo) AS numero_ucs FROM UC;

-- ----------------------------------------------------------------------    
 /*
 O diretor de curso deve conseguir saber o total de Alunos
 que o curso tem atualmente.
*/   
SELECT COUNT(Numero) AS numero_alunos FROM Aluno;

-- ----------------------------------------------------------------------    
 /*
 O diretor de curso deve ser capaz de saber qual o top 3
 de cadeiras com mais alunos inscritos, e respetivos docentes.
*/   
SELECT UC.Nome, COUNT(*), Docente.Nome FROM UC
	INNER JOIN UCAluno ON UCAluno.UC_Codigo = UC.Codigo
    INNER JOIN Aluno ON Aluno.Numero = UCAluno.Aluno_Numero
    INNER JOIN DocenteUC ON DocenteUC.UC_Codigo = UC.Codigo
    INNER JOIN Docente ON Docente.Numero = DocenteUC.Docente_Numero
    GROUP BY UC.Nome
    ORDER BY COUNT(*) DESC
    LIMIT 3;

-- -----------------------------------------------------------------------
								-- TRANSAÇÕES
                                
/*
 Adicionar um novo aluno.
*/  
DROP PROCEDURE IF EXISTS inserir_aluno;
DELIMITER $$
CREATE PROCEDURE inserir_aluno(IN numero INT, 
							  IN nome VARCHAR(45), 
							  IN curso VARCHAR(10))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	START TRANSACTION;

	INSERT INTO Aluno VALUES (numero, nome, curso);

	IF erro 
		THEN ROLLBACK;
		ELSE COMMIT;
	END IF;
END
$$
DELIMITER ;

-- -----------------------------------------------------------------------
/*
 Adicionar uma nova UC.
*/ 	
DROP PROCEDURE IF EXISTS inserir_uc;
DELIMITER $$
CREATE PROCEDURE inserir_uc(IN codigo INT, 
							IN nome VARCHAR(45), 
							IN ano INT,
							IN ects INT)
BEGIN
	DECLARE erro BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	START TRANSACTION;

	INSERT INTO UC VALUES (codigo, nome, ano, ects);

	IF erro 
		THEN ROLLBACK;
		ELSE COMMIT;
	END IF;
END
$$
DELIMITER ;

-- -----------------------------------------------------------------------
/*
 Adicionar um novo docente.
*/ 
DROP PROCEDURE IF EXISTS inserir_docente;
DELIMITER $$
CREATE PROCEDURE inserir_docente(IN numero INT, 
							IN nome VARCHAR(45), 
							IN escola VARCHAR(45))
BEGIN
	DECLARE erro BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	START TRANSACTION;

	INSERT INTO Docente VALUES (numero, nome, escola);

	IF erro 
		THEN ROLLBACK;
		ELSE COMMIT;
	END IF;
END
$$
DELIMITER ;

-- -----------------------------------------------------------------------
/*
 Modificar o curso de um aluno.
*/ 
DROP PROCEDURE IF EXISTS modificar_curso_aluno;
DELIMITER $$
CREATE PROCEDURE modificar_curso_aluno(IN curso_aluno VARCHAR(10),
									   IN numero_aluno INT)
BEGIN
	DECLARE erro BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	START TRANSACTION;

	UPDATE Aluno SET Curso = curso_aluno WHERE Aluno.Numero = numero_aluno;

	IF erro 
		THEN ROLLBACK;
		ELSE COMMIT;
	END IF;
END
$$
DELIMITER ;

-- -----------------------------------------------------------------------

/*
 Modificar o nome de uma UC.
*/ 
DROP PROCEDURE IF EXISTS modificar_uc_nome;
DELIMITER $$
CREATE PROCEDURE modificar_uc_nome(IN nome_uc VARCHAR(10),
								   IN codigo_uc INT)
BEGIN
	DECLARE erro BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	START TRANSACTION;

	UPDATE UC SET Nome = nome_uc WHERE UC.Codigo = codigo_uc;

	IF erro 
		THEN ROLLBACK;
		ELSE COMMIT;
	END IF;
END
$$
DELIMITER ;

-- -----------------------------------------------------------------------
/*
 Modificar o turno de um aluno a uma UC.
*/ 
DROP PROCEDURE IF EXISTS modificar_turno_aluno_uc;
DELIMITER $$
CREATE PROCEDURE modificar_turno_aluno_uc(IN turno_new VARCHAR(3),
										  IN codigo_uc INT,
                                          IN numero_aluno INT)
BEGIN
	DECLARE erro BOOL DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro = 1;
	START TRANSACTION;

	UPDATE UCAluno SET Turno = turno_new WHERE UCAluno.UC_Codigo = codigo_uc
											AND UCAluno.Aluno_Numero = numero_aluno;

	IF erro 
		THEN ROLLBACK;
		ELSE COMMIT;
	END IF;
END
$$
DELIMITER ;
-- -----------------------------------------------------------------------
								-- RESTRIÇÕES (triggers)
/*
 Duas UC's diferentes não podem ter o mesmo nome
*/ 
DROP TRIGGER IF EXISTS nomeUC;
DELIMITER $$
CREATE TRIGGER nomeUC -- nome trigger
BEFORE INSERT ON UC -- tabela e ação (insert)
FOR EACH ROW
BEGIN

DECLARE message VARCHAR(75);

IF (new.nome IN
	(SELECT nome FROM uc WHERE
			uc.nome = new.nome))
THEN 
	SET message = 'Já existe uma UC com o mesmo nome!';
	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = message;
END IF;
END 
$$
DELIMITER ;

-- TESTE DO TRIGGER
-- já existe a cadeira 'Álgebra Linear', vai dar erro
 INSERT INTO UC VALUES (841, 'Álgebra Linear', 3, 5);

-- -----------------------------------------------------------------------
								-- VISTAS DOS UTILIZADORES
								--  		&
								--   REGRAS DE ACESSO
drop user admin@localhost;
/*
 Criação de um administrador.
*/ 
CREATE USER 'admin'@'localhost';
	SET PASSWORD FOR 'admin'@'localhost' = 'admin1';

-- -----------------------------------------------------------------------
/*
 Criação do utilizador do tipo 'docente'.
 (só criei 3 para ser representativo)
*/
CREATE USER 'doc1'@'localhost';
	SET PASSWORD FOR 'doc1'@'localhost' = 'doc1';

CREATE USER 'doc2'@'localhost';
	SET PASSWORD FOR 'doc2'@'localhost' = 'doc2';

CREATE USER 'doc3'@'localhost';
	SET PASSWORD FOR 'doc3'@'localhost' = 'doc3';

-- -----------------------------------------------------------------------
/*
 Criação do utilizador do tipo 'aluno'.
 (só criei 3 para ser representativo)
*/
CREATE USER 'stud1'@'localhost';
	SET PASSWORD FOR 'stud1'@'localhost' = 'stud1';

CREATE USER 'stud2'@'localhost';
	SET PASSWORD FOR 'stud2'@'localhost' = 'stud2';

CREATE USER 'stud3'@'localhost';
	SET PASSWORD FOR 'stud3'@'localhost' = 'stud3';

-- -----------------------------------------------------------------------
/*
 Permissões do administrador.

 Pode "fazer o que quiser".
*/
GRANT SELECT, INSERT, UPDATE, DELETE ON trocaturnos.* TO 'admin'@'localhost';

GRANT EXECUTE ON lista_turnos TO 'admin'@'localhost';

-- -----------------------------------------------------------------------
/*
 Permissões dos docentes.
 
 Pode consultar TODAS as tabelas, mas apenas inserir, remover
 e atualizar os dados da tabela UCAluno (por causa dos turnos, 
 fazer mais um turno, por exemplo)
*/

-- Permissão dos docentes em UCAluno
GRANT SELECT, INSERT, DELETE, UPDATE ON trocaturnos.UCAluno TO 'doc1'@'localhost',
									   						   'doc2'@'localhost',
									   						   'doc3'@'localhost';

-- Permissão dos docentes em Docente
GRANT SELECT ON trocaturnos.Docente TO 'doc1'@'localhost',
									   'doc2'@'localhost',
									   'doc3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Docente FROM 'doc1'@'localhost',
														  'doc2'@'localhost',
														  'doc3'@'localhost';

-- Permissão dos docentes em UC
GRANT SELECT ON trocaturnos.UC TO 'doc1'@'localhost',
								  'doc2'@'localhost',
								  'doc3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.UC FROM 'doc1'@'localhost',
												     'doc2'@'localhost',
												     'doc3'@'localhost';  

-- Permissão dos docentes em DocenteUC
GRANT SELECT ON trocaturnos.DocenteUC TO 'doc1'@'localhost',
									     'doc2'@'localhost',
									     'doc3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.DocenteUC FROM 'doc1'@'localhost',
														    'doc2'@'localhost',
														    'doc3'@'localhost';

-- Permissão dos docentes em Aluno
GRANT SELECT ON trocaturnos.Aluno TO 'doc1'@'localhost',
									 'doc2'@'localhost',
									 'doc3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Aluno FROM 'doc1'@'localhost',
													    'doc2'@'localhost',
													    'doc3'@'localhost';

-- Permissão dos docentes em Escola
GRANT SELECT ON trocaturnos.Escola TO 'doc1'@'localhost',
									  'doc2'@'localhost',
									  'doc3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Curso FROM 'doc1'@'localhost',
													    'doc2'@'localhost',
													    'doc3'@'localhost';

-- Permissão dos docentes em Curso
GRANT SELECT ON trocaturnos.Curso TO 'doc1'@'localhost',
									 'doc2'@'localhost',
									 'doc3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Curso FROM 'doc1'@'localhost',
													    'doc2'@'localhost',
													    'doc3'@'localhost';

-- -----------------------------------------------------------------------
/*
 Permissões dos alunos.
 Podem apenas consultar as tabelas UCAluno e UC.
*/

-- Permissão dos alunos em UC
GRANT SELECT ON trocaturnos.UC TO 'stud1'@'localhost',
								  'stud2'@'localhost',
								  'stud3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.UC FROM 'stud1'@'localhost',
												     'stud2'@'localhost',
												     'stud3'@'localhost';
-- Permissão dos alunos em UCAluno
GRANT SELECT ON trocaturnos.UCAluno TO 'stud1'@'localhost',
								       'stud2'@'localhost',
								       'stud3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.UCAluno FROM 'stud1'@'localhost',
												          'stud2'@'localhost',
												          'stud3'@'localhost';

-- Permissão dos alunos nas restantes tabelas (revoke de tudo)
REVOKE SELECT, INSERT, DELETE, UPDATE ON trocaturnos.DocenteUC FROM 'stud1'@'localhost',
												                    'stud2'@'localhost',
												                    'stud3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Docente FROM 'stud1'@'localhost',
												          'stud2'@'localhost',
												          'stud3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Escola FROM 'stud1'@'localhost',
												         'stud2'@'localhost',
												         'stud3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Aluno FROM 'stud1'@'localhost',
												        'stud2'@'localhost',
												        'stud3'@'localhost';

REVOKE INSERT, DELETE, UPDATE ON trocaturnos.Curso FROM 'stud1'@'localhost',
												        'stud2'@'localhost',
												        'stud3'@'localhost';

-- -----------------------------------------------------------------------
								-- VIEWS PARA UTILIZADORES
/*
 View correspondente à informação que um aluno pode ver.
*/ 
DROP VIEW IF EXISTS viewAluno;
DELIMITER $$
CREATE VIEW viewAluno AS
	SELECT DISTINCT u.nome, u.codigo, u.ano, u.ects, a.turno
	FROM UC u, UCAluno a
	WHERE u.codigo = a.UC_Codigo;

$$
DELIMITER ;
SELECT * FROM trocaturnos.viewAluno;

-- -----------------------------------------------------------------------

