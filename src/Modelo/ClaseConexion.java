
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ClaseConexion {
      
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO = "system";
    private static final String CONTRASENA = "";
    
   
    public static Connection getConexion() {
        try {
           
            Class.forName("oracle.jdbc.driver.OracleDriver");

         
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            
         
            return conexion;
        } catch (SQLException e) {
            System.out.println("Este es el error" + e);
              return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("este es el error de la clase" + ex);
              return null;
        }
      
    }
}

    

