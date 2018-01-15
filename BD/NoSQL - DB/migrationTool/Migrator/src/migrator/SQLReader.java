/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migrator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Version;

/**
 *
 * @author Vitor Castro
 */
public class SQLReader {

    private static String URL;
    private static String TABLE;
    private static String USERNAME;
    private static String PASSWORD;
    private static Connection cn;
    //private static final String PASSWORD = "admin1";

    public SQLReader() {

        URL = "localhost";
        TABLE = "TrocaTurnos";
        USERNAME = "admin";
        PASSWORD = "admin";
        cn = null;
    }

    // Iniciar a ligação à base de dados
    public static void iniciarCon() {

        try {

            cn = DriverManager.getConnection("jdbc:mysql://" + URL + "/" + TABLE + "?user=" + USERNAME + "&password=" + PASSWORD);

        } catch (SQLException e) {
        }

    }

    // Fechar ligação à base de dados
    public static void fecharCon() {

        try {

            cn.close();

        } catch (SQLException e) {
        }

    }

    public void migrateDocentes(ArrayList<Docente> d) {

        ResultSet docente = null;
        ResultSet uc = null;
        PreparedStatement stDocente = null;
        PreparedStatement stUcs = null;
        ArrayList<Docente> docentes = d;

        try {
            stDocente = cn.prepareStatement("SELECT * FROM Docente;"); // alterar
            docente = stDocente.executeQuery();
            while (docente.next()) {
                int numero = docente.getInt("Numero");
                String nome = docente.getString("Nome");
                String escola = docente.getString("Escola_Nome");
                // Ler as UCs em que aquele docente está inscrito
                ArrayList<UC> ucs = new ArrayList<UC>();
                stUcs = cn.prepareStatement(
                        "SELECT * FROM DocenteUC JOIN UC ON UC.Codigo = DocenteUC.UC_Codigo WHERE DocenteUC.Docente_Numero = " + numero); // obtem lista de ucs lecionadas pelo docente com o 'numero'
                uc = stUcs.executeQuery();
                while (uc.next()) {
                    int codigo = uc.getInt("Codigo");
                    String nomeUC = uc.getString("Nome");
                    int ano = uc.getInt("Ano");
                    int ects = uc.getInt("ECTS");
                    ucs.add(new UC(codigo, nomeUC, ano, ects));
                }
                docentes.add(new Docente(numero, nome, escola, ucs));

            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
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

    }

    public void migrateAlunos(ArrayList<Aluno> a) {

        ResultSet aluno = null;
        ResultSet turno = null;
        PreparedStatement stAluno = null;
        PreparedStatement stTurno = null;
        ArrayList<Aluno> alunos = a;

        try {
            stAluno = cn.prepareStatement("SELECT * FROM Aluno;"); // alterar
            aluno = stAluno.executeQuery();
            while (aluno.next()) {
                int numero = aluno.getInt("Numero");
                String nome = aluno.getString("Nome");
                String curso = aluno.getString("Curso_Nome");
                // Ler os turnos em que aquele aluno está inscrito
                ArrayList<Turno> turnos = new ArrayList<Turno>();
                stTurno = cn.prepareStatement("SELECT * FROM UCAluno JOIN UC ON UC.Codigo = UCAluno.UC_Codigo WHERE UCAluno.Aluno_Numero = " + numero); // obtem lista de turnos para o aluno com aquele 'numero'
                turno = stTurno.executeQuery();
                while (turno.next()) {
                    int codigo = turno.getInt("Codigo");
                    String nomeUC = turno.getString("Nome");
                    String turnoUC = turno.getString("Turno");
                    int anoLetivo = turno.getInt("AnoLetivo");
                    turnos.add(new Turno(codigo, nomeUC, turnoUC, anoLetivo));
                }
                alunos.add(new Aluno(numero, nome, curso, turnos));

            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class
                    .getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
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
                Logger lgr = Logger.getLogger(Version.class
                        .getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

    }

    void migrateUCS(ArrayList<AlunosUC> ucs) {

        ResultSet uc = null;
        ResultSet aluno = null;
        PreparedStatement stUc = null;
        PreparedStatement stAluno = null;
        ArrayList<AlunosUC> alunosUC = ucs;

        try {
            stUc = cn.prepareStatement("SELECT * FROM Aluno;"); // alterar
            uc = stUc.executeQuery();
            while (uc.next()) {
                int codigo = uc.getInt("Codigo");
                String nome = uc.getString("Nome");
                int ano = uc.getInt("Ano");
                int ects = uc.getInt("ECTS");
                // Ler os alunos que estao inscritos na uc
                ArrayList<Integer> alunos = new ArrayList<Integer>();
                stAluno = cn.prepareStatement("SELECT * FROM UC JOIN UCAluno ON UC.Codigo = UCAluno.UC_Codigo WHERE UCAluno.UC_Codigo = " + codigo); // obtem lista de turnos para o aluno com aquele 'numero'
                aluno = stAluno.executeQuery();
                while (aluno.next()) {
                    int numero = aluno.getInt("Aluno_Numero");
                    alunos.add(new Integer(numero));
                }
                alunosUC.add(new AlunosUC(codigo, nome, ano, ects, alunos));

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

}
