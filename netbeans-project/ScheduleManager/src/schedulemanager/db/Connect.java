
package schedulemanager.db;

import java.sql.DriverManager;
import java.sql.Connection;

public class Connect {

    private static final String URL = "localhost";
    private static final String TABLE = "mydb";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    //private static final String PASSWORD = "admin1";
    
    public static Connection connect() {
        
        try {
            
            Connection cn = DriverManager.getConnection("jdbc:mysql://"+ URL + "/" + TABLE + "?user=" + USERNAME + "&password=" + PASSWORD);
           
            return cn;
            
        } catch(Exception e) {
        	
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
