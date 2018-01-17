
package coursemanager.model;

import java.sql.DriverManager;
import java.sql.Connection;

public class Connect {

    private static final String URL = "localhost";
    private static final String TABLE = "trocaturnos";

    public static Connection connect(String username, String password) {

        try {

            Connection cn = DriverManager.getConnection("jdbc:mysql://"+ URL + "/" + TABLE  + "?user=" + username + "&password=" + password + "&noAccessToProcedureBodies=true");

            return cn;

        } catch(Exception e) {
            e.printStackTrace();
            //return null;
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
