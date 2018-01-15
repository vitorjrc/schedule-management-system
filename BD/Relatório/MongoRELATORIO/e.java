void migrateUCS(ArrayList<AlunosUC> ucs) {
        
        ResultSet uc = null;
        ResultSet aluno = null;
        PreparedStatement stUc = null;
        PreparedStatement stAluno = null;
        ArrayList<AlunosUC> alunosUC = ucs;

        try {
            stUc = cn.prepareStatement("SELECT * FROM Aluno;"); // alterar
            uc = stUc.executeQuery();
            while (aluucno.next()) {
                int numero = uc.getInt("Numero");
                String nome = uc.getString("Nome");
                String curso = uc.getString("Curso_Nome");
                // Ler os turnos em que aquele uc está inscrito
                ArrayList<Turno> turnos = new ArrayList<Turno>();
                stAluno = cn.prepareStatement("SELECT * FROM UCAluno JOIN UC ON UC.Codigo = UCAluno.UC_Codigo WHERE UCAluno.Aluno_Numero = " + numero); // obtem lista de turnos para o aluno com aquele 'numero'
                aluno = stAluno.executeQuery();
                while (aluno.next()) {
                    int codigo = aluno.getInt("Codigo");
                    String nomeUC = aluno.getString("Nome");
                    String turnoUC = aluno.getString("Turno");
                    int anoLetivo = aluno.getInt("AnoLetivo");
                    turnos.add(new Turno(codigo, nomeUC, turnoUC, anoLetivo));
                }
                alunosUC.add(new Aluno(numero, nome, curso, turnos));

            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class
                    .getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                if (aluno != null) {
                    aluno.close();
                }
                if (uc != null) {
                    uc.close();
                }
                if (stAluno != null) {
                    stAluno.close();
                }
                if (stUc != null) {
                    stUc.close();

                }
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Version.class
                        .getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }