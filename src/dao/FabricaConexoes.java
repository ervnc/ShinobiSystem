package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexoes {
	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/shinobisystem?useSSL=false", "root", "root");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
