package ch.makery.address.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnection {
    
    private static MySQLConnection conexao;

    private MySQLConnection() {
    
    }

    public static MySQLConnection getInstance() {
        if(conexao == null)
            conexao = new MySQLConnection();

        return conexao;
    }
    
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javafxtutorial", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
