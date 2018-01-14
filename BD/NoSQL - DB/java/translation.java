public class SQL {
	
	public static void main(String[] args) throws Exception {

		SQLreader sql = new SQLreader(); // classe que tem tem os para ler

		List<Docente> docentes = new ArrayList<>();
		List<Turno> turnos = new ArrayList<>();
		List<Aluno> alunos = new ArrayList<>();

		FileWriter docentesFile;
		FileWriter turnosFile;
		FileWriter alunosFile;

		ObjectMapper doc = new ObjectMapper();
		doc.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		doc.writerWithDefaultPrettyPrinter();

		ObjectMapper tur = new ObjectMapper();
		tur.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		tur.writerWithDefaultPrettyPrinter();

		ObjectMapper alu = new ObjectMapper();
		alu.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		alu.writerWithDefaultPrettyPrinter();

		sql.iniciarCon(); // iniciar connection a base de dados

		sql.migrateDocentes(docentes);
		sql.migrateTurnos(turnos);
		sql.migrateAlunos(alunos);

		docentesFile = new FileWriter("docentes.json");
		doc.writeValue(docentesFile,docentes);
		docentesFile.close();
		System.out.println("Sucessfully made JSON Object to file docentes.");

		turnosFile = new FileWriter("turnos.json");
		doc.writeValue(turnosFile,turnos);
		turnosFile.close();
		System.out.println("Sucessfully made JSON Object to file turnos.");

		alunosFile = new FileWriter("alunos.json");
		doc.writeValue(alunosFile,alunos);
		alunosFile.close();
		System.out.println("Sucessfully made JSON Object to file alunos.");

		sql.fecharCon();

		System.out.println("Docentes: " +  doc);
		System.out.println("Turnos: " +  tur);
		System.out.println("Alunos: " +  alu);

	}
}

public class SQLreader() {
	
	public iniciarCon() {
		// iniciar conexão
	}

	public fecharCon() {
		// fechar conexão
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