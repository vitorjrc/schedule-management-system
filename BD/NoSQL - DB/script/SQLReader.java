public class SQLReader() {
	
	private static final String URL = "localhost";
    private static final String TABLE = "TrocaTurnos";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    //private static final String PASSWORD = "admin1";

    // Iniciar a ligação à base de dados
    public static Connection iniciarCon() {

        try {

            Connection cn = DriverManager.getConnection("jdbc:mysql://"+ URL + "/" + TABLE + "?user=" + USERNAME + "&password=" + PASSWORD);

            return cn;

        } catch(Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // Fechar ligação à base de dados
    public static void fecharCon(Connection connection) {

        try {

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


	public void migrateDocentes(List<Docente> d) {

		ResultSet doc =  null;
		PreparedStatement stDOC = null;
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