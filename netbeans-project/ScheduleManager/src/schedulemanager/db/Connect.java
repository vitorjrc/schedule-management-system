
package schedulemanager.db;

import java.sql.DriverManager;
import java.sql.Connection;

public class Connect {

    private static final String URL = "localhost";
    private static final String TABLE = "trocaturnos";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin1";
    
    
    
    public static Connection connect() {
        
        try {
            
            Connection cn = DriverManager.getConnection("jdbc:mysql://"+ URL + "/" + TABLE + "?user=" + USERNAME + "&password=" + PASSWORD);
            System.out.println("conexao feita");
            return cn;
            
        } catch(Exception e) {
            System.out.println("Não conseguiu");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static void close(Connection connection) {
        
        try {
            
            connection.close();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
    }
    
    
    
    
    
}
