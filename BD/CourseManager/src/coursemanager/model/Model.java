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
        
    
}
