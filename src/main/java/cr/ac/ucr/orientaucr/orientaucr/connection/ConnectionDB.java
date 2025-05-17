package cr.ac.ucr.orientaucr.orientaucr.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ConnectionDB {

    private final static String DATABASE = "guides_ucr";
    private final static String USER = "root";
    private final static String PASS = "";
    private final static int PORT = 3306;
    private final static String HOST = "localhost";
    private final static String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private static Connection connect;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al importar la  clase");
        }
        try {
            connect = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("se establecio a la coneccion ");
        } catch (SQLException e) {
            System.out.println("Error de connection");
        }
        return connect;

    }

}
