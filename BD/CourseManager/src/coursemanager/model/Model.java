package coursemanager.model;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Version;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */
public class Model {
    
    private Connect connection = null;
    private static Connection cn = null;
    private int user;

    public Model() {
        Connect connection = new Connect();
        
    }
    
    public int login(String userID, String userPassword) {
        
        if (userID.substring(0, 1).equals("D") && connection.connect(userID, userPassword) != null) {
            user = 2;
            cn = connection.connect(userID, userPassword);
            return 2;
        }
        
        else if (userID.substring(0, 1).equals("A") && connection.connect(userID, userPassword) != null) {
            user = 1;
            cn = connection.connect(userID, userPassword);
            return 1;
        }
        
        else if (userID.equals("admin") && connection.connect(userID, userPassword) != null) {
            user = 0;
            cn = connection.connect(userID, userPassword);
            return 0;
        }
        
        else return -1;
        
    }
    
    public ArrayList<String> getUCs() {

        ResultSet uc = null;
        PreparedStatement stUc = null;
        ArrayList<String> ucs = new ArrayList<>();

        try {
            
            stUc = cn.prepareStatement("SELECT UC.nome FROM UC;");
            uc = stUc.executeQuery();
            while (uc.next()) {
                String name = uc.getString("nome");
                ucs.add(name);
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return ucs;
    }
    
    public HashMap<String, String> showStudentUCs(int studentID) {

        ResultSet uc = null;
        HashMap<String, String> ucs = new HashMap<>();

        try {
            
            String query = "{call lista_turnos(?)}"; 
            CallableStatement statement = cn.prepareCall(query);  
            statement.setInt(1, studentID);
            boolean results = statement.execute(); 
            while (results) {
                uc = statement.getResultSet();
                while (uc.next()) {
                    String name = uc.getString("Nome");
                    String shift = uc.getString("Turno");
                    ucs.put(name, shift);
                }
                uc.close();
                results = statement.getMoreResults();
            } 
            
        statement.close();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return ucs;
    }
    
    public HashMap<String, String> showTeacherStudents(int teacherID) {

        ResultSet uc = null;
        HashMap<String, String> ucs = new HashMap<>();

        try {
            
            String query = "{call lista_alunos(?)}"; 
            CallableStatement statement = cn.prepareCall(query);  
            statement.setInt(1, teacherID);
            boolean results = statement.execute(); 
            while (results) {
                uc = statement.getResultSet();
                while (uc.next()) {
                    String name = uc.getString("NomeAluno");
                    String ucName = uc.getString("NomeUC");
                    ucs.put(name, ucName);
                }
                uc.close();
                results = statement.getMoreResults();
            } 
            
        statement.close();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return ucs;
    }
    
    public HashMap<String, String> showAllStudents() {

        ResultSet uc = null;
        PreparedStatement stUc = null;
        HashMap<String, String> ucs = new HashMap<>();

        try {
            
            stUc = cn.prepareStatement("SELECT Aluno.Numero, Aluno.Nome FROM Aluno;");
            uc = stUc.executeQuery();
            while (uc.next()) {
                String number = uc.getString("numero");
                String name = uc.getString("nome");
                ucs.put(number, name);
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return ucs;
    }
    
    public String numberOfUCs() {

        ResultSet uc = null;
        PreparedStatement stUc = null;

        try {
            
            stUc = cn.prepareStatement("SELECT COUNT(Codigo) AS numero_ucs FROM UC;");
            uc = stUc.executeQuery();
            if (uc.next()) {
                int number = uc.getInt("numero_ucs");
                return Integer.toString(number);
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return null;
    }
    
    public String numberOfStudents() {

        ResultSet uc = null;
        PreparedStatement stUc = null;

        try {
            
            stUc = cn.prepareStatement("SELECT COUNT(Numero) AS numero_alunos FROM Aluno;");
            uc = stUc.executeQuery();
            if (uc.next()) {
                int number = uc.getInt("numero_alunos");
                return Integer.toString(number);
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return null;
    }
    
    public HashMap<String, ArrayList<String>> getTop() {

        ResultSet uc = null;
        PreparedStatement stUc = null;
        HashMap<String, ArrayList<String>> ucs = new HashMap<>();

        try {
            
            stUc = cn.prepareStatement("SELECT UC.Nome, COUNT(*), d.Nome as DocenteNome FROM UC\n" +
                "INNER JOIN UCAluno ON UCAluno.UC_Codigo = UC.Codigo\n" +
                "INNER JOIN Aluno ON Aluno.Numero = UCAluno.Aluno_Numero\n" +
                "INNER JOIN DocenteUC ON DocenteUC.UC_Codigo = UC.Codigo\n" +
                "INNER JOIN Docente d ON d.Numero = DocenteUC.Docente_Numero\n" +
                "GROUP BY UC.Nome\n" +
                "ORDER BY COUNT(*) DESC\n" +
                "LIMIT 3;");
            uc = stUc.executeQuery();
            while (uc.next()) {
                ArrayList<String> info = new ArrayList<>();
                String name = uc.getString("nome");
                String teacher = uc.getString("DocenteNome");
                String number = Integer.toString(uc.getInt("COUNT(*)"));
                info.add(teacher);
                info.add(number);
                ucs.put(name, info);
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return ucs;
    }
    
    public boolean createUC(int id, String name, int year, int ECTS) {
            
        boolean result = false;

        try {
            
            String query = "{call inserir_uc(?,?,?,?)}"; 
            CallableStatement statement = cn.prepareCall(query);  
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setInt(3, year);
            statement.setInt(4, ECTS);
            result = statement.execute(); 
            
        statement.close();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return result;
    }
    
    public boolean changeCourseName(String course, String newName) {
        
        boolean result = false;
        ResultSet uc = null;
        PreparedStatement stUc = null;
        int idCourse = 0;

        try {
            
            stUc = cn.prepareStatement("SELECT uc.Codigo FROM UC where uc.Nome = ?");
            stUc.setString(1, course);
            uc = stUc.executeQuery();
            if (uc.next()) {
                idCourse = uc.getInt("Codigo");
            }
            
            String query = "{call modificar_uc_nome(?,?)}"; 
            CallableStatement statement = cn.prepareCall(query);  
            statement.setInt(2, idCourse);
            statement.setString(1, newName);
            result = statement.execute(); 
            
        statement.close();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return result;
    }
    
    public boolean createTeacher(int id, String name, String school) {
            
        boolean result = false;

        try {
            
            String query = "{call inserir_docente(?,?,?)}"; 
            CallableStatement statement = cn.prepareCall(query);  
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, school);
            result = statement.execute(); 
            
        statement.close();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return result;
    }
    
    public boolean createStudent(int id, String name, String school) {
        
        boolean result = false;

        try {
            
            String query = "{call inserir_aluno(?,?,?)}"; 
            CallableStatement statement = cn.prepareCall(query);  
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, school);
            result = statement.execute(); 
            
        statement.close();

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        return result;
    }
    
    
    
        
    
}
