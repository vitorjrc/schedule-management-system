
import java.sql.DriverManager;
import java.sql.Connection;

public class Connect {

    // VAI SER ELIMINADA E SUBSTITUIDA POR SQLreader

    // Ler 
    public void getTurnos(int ucCodigo, String turno, int anoLetivo) throws ClassNotFoundException {

		ResultSet R = null;
		PreparedStatement stm = null;

		ArrayList<Docente> docentes = new ArrayList<>();
		ArrayList<Aluno> alunos = new ArrayList<>();

		try {
            PreparedStatement stm = con.prepareStatement("SELECT aluno.numero, aluno.nome, aluno.Curso_Nome FROM aluno
					JOIN ucaluno ON ucaluno.aluno_numero = aluno.numero
					JOIN UC ON uc.codigo = ucaluno.uc_codigo
    				where (uc.codigo = ? && ucaluno.turno = ? && ucaluno.anoletivo = ?)");
            stm.setInt(1, ucCodigo);
            stm.setString(2, turno);
            stm.setInt(3, anoLetivo);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                alunos.add(new Aluno(rs.getInt("numero"), rs.getString("nome"), rs.getString("curso")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

		try {
            PreparedStatement stm = con.prepareStatement("SELECT distinct docente.numero, docente.nome, docente.Escola_Nome FROM docente
				JOIN docenteuc ON docenteuc.docente_numero = docente.Numero
				JOIN uc ON uc.Codigo = docenteuc.uc_codigo
    			JOIN ucaluno on ucaluno.uc_codigo = uc.codigo
    			where (uc.codigo = ? && turno = ? && anoletivo = ?)");
            stm.setInt(1, ucCodigo);
            stm.setString(2, turno);
            stm.setInt(3, anoLetivo);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                docentes.add(new Docente(rs.getInt("numero"), rs.getString("nome"), rs.getString("Escola_Nome")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


	}

	/* PARA OS DOCENTES
	SELECT distinct docente.numero, docente.nome, docente.Escola_Nome FROM docente
	JOIN docenteuc ON docenteuc.docente_numero = docente.Numero
    JOIN uc ON uc.Codigo = docenteuc.uc_codigo
    JOIN ucaluno on ucaluno.uc_codigo = uc.codigo
    where (uc.codigo = 312 && turno = "PL2" && anoletivo = 1617);
    */
}
