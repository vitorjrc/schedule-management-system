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
			stDOC = connection.prepareStatement("SELECT * FROM Docente;");
			doc = stDOC.executeQuery();
			while(doc.next()) {
				int numero = doc.getInt("Numero");
				String nome = doc.getString("Nome");
				String escola = doc.getString("Escola");
				stTUR = con.prepareStatement("SELECT * FROM UC, UCAluno")
			}
		}

	}

}