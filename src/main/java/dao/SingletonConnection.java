package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection conn;

    private SingletonConnection() {}

    public static Connection getCon() {
        try {
            String USUARIO = System.getenv("USUARIO");
            String SENHA = System.getenv("SENHA");
            String BD = System.getenv("BD");

            Class.forName("com.mysql.cj.jdbc.Driver"); 
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + BD, USUARIO, SENHA);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}