import java.sql.DriverManager;
import java.sql.Connection;

public class SQLReader() {
	
	private static final String URL = "localhost";
    private static final String TABLE = "TrocaTurnos";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final Connection cn = null;
    //private static final String PASSWORD = "admin1";

    // Iniciar a ligação à base de dados
    public static void iniciarCon() {

        try {

            cn = DriverManager.getConnection("jdbc:mysql://"+ URL + "/" + TABLE + "?user=" + USERNAME + "&password=" + PASSWORD);

        } catch(Exception e) {

            e.printStackTrace();
        }

    }

    // Fechar ligação à base de dados
    public static void fecharCon() {

        try {

            cn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


	public void migrateDocentes(List<Docente> d) {

		ResultSet docente =  null;
		ResultSet uc = null;
		PreparedStatement stDocente = null;
		PreparedStatement stUcs = null;
		ArrayList<Docente> docentes = d;

		try {
			stDocente = cn.prepareStatement("SELECT * FROM Docente;"); // alterar
			docente = stDocente.executeQuery();
			while(docente.next()) {
				int numero = doc.getInt("Numero");
				String nome = doc.getString("Nome");
				String escola = doc.getString("Escola_Nome");
				// Ler as UCs em que aquele docente está inscrito
				ArrayList<UC> ucs = new ArrayList<UC>();
				stUcs = cn.prepareStatement("SELECT * FROM DocenteUC 
														JOIN UC ON UC.Codigo = DocenteUC.UC_Codigo
														WHERE DocenteUC.Docente_Numero = " + numero); // obtem lista de ucs lecionadas pelo docente com o 'numero'
				uc = stUcs.executeQuery();
				while(uc.next()) {
					int codigo = uc.getInt("Codigo");
					String nome = uc.getString("Nome");
					int ano = uc.getInt("Ano");
					int ects = uc.getInt("ECTS");
					ucs.add(new UC(codigo, nome, ano, ects));
				}
				docentes.add(new Docente(numero, nome, escola, ucs));
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Version.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			try {
				if (uc != null) {
					uc.close();
				}
				if (docente != null) {
					docente.close();
				}
				if (stUcs != null) {
					stUcs.close();
				}
				if (stDocente != null) {
					stDocente.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(Version.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}

		return;
	}

	public void migrateAlunos(List<Aluno> a) {

		ResultSet aluno =  null;
		ResultSet turno = null;
		PreparedStatement stAluno = null;
		PreparedStatement stTurno = null;
		ArrayList<Aluno> alunos = a;

		try {
			stAluno = cn.prepareStatement("SELECT * FROM Aluno;"); // alterar
			aluno = stAluno.executeQuery();
			while(aluno.next()) {
				int numero = doc.getInt("Numero");
				String nome = doc.getString("Nome");
				String curso = doc.getString("Curso_Nome");
				// Ler os turnos em que aquele aluno está inscrito
				ArrayList<Turno> turnos = new ArrayList<Turno>();
				stTurno = cn.prepareStatement("SELECT * FROM UCAluno
															JOIN UC ON UC.Codigo = UCAluno.UC_Codigo
														WHERE UCAluno.Aluno_Numero = " + numero); // obtem lista de turnos para o aluno com aquele 'numero'
				turno = stTurno.executeQuery();
				while(turno.next()) {
					int codigo = turno.getInt("Codigo");
					String nome = turno.getString("Nome");
					int ano = turno.getInt("Ano");
					int ects = turno.getInt("ECTS");
					turnos.add(new Turno(codigo, nome, ano, ects));
				}
				alunos.add(new Aluno(numero, nome, curso, turnos));
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Version.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		finally {
			try {
				if (turno != null) {
					turno.close();
				}
				if (aluno != null) {
					aluno.close();
				}
				if (stTurno != null) {
					stTurno.close();
				}
				if (stAluno != null) {
					stAluno.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(Version.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}

		return;
	}


}