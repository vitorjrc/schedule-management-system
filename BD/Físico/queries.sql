/*
 Os alunos devem ser capazes de obter a lista de cadeiras 
 a que estão inscritos.
*/

DROP PROCEDURE IF EXISTS lista_cadeiras;
DELIMITER $$
CREATE PROCEDURE lista_cadeiras(id_aluno INT)
BEGIN
	SELECT UC.Nome FROM UC
		JOIN UCAluno ON UCAluno.UC_Codigo = UC.Codigo
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
		JOIN UC ON UC.Codigo = UCAluno.UC_Codigo 
		WHERE UCAluno.Aluno_Numero = id_aluno;
END
$$
DELIMITER ;

CALL lista_turnos(78985);
-- --------------------------------------------------------------------

-- funcao procedure de ver qual cadeira q tem mais alunos inscritos

-- codigo de calculo: 423
SELECT Docente.Nome FROM Docente
	INNER JOIN DocenteUC ON Docente.Numero = DocenteUC.Docente_Numero
	WHERE DocenteUC.UC_Codigo = 423;