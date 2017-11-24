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
	SELECT Aluno.Nome, UC.Nome FROM Aluno
    INNER JOIN UCAluno ON UCAluno.Aluno_Numero = Aluno.Numero
    INNER JOIN UC ON UC.Codigo = UCAluno.UC_Codigo
    INNER JOIN DocenteUC ON DocenteUC.UC_Codigo = UC.Codigo
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
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET erro=1;
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



-- -----------------------------------------------------------------------
/*
 Adicionar um novo docente.
*/ 



-- -----------------------------------------------------------------------
/*
 Modificar o curso de um aluno.
*/ 



-- -----------------------------------------------------------------------

/*
 Modificar o nome de uma UC.
*/ 



-- -----------------------------------------------------------------------
/*
 Modificar o turno de um aluno a uma UC.
*/ 


-- -----------------------------------------------------------------------