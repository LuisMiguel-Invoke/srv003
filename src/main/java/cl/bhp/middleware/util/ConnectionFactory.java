package cl.bhp.middleware.util;

import cl.bhp.middleware.exception.ServiceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {
	
	Connection conn = null;
	static PropertiesUtil prop = new PropertiesUtil();
	
	static public enum DataBaseSchema {
		CLIENTE, NEGOCIO;
	}

	public static Connection getConnection(DataBaseSchema shema) throws ServiceException, SQLException, ClassNotFoundException {

			String user = "";
			String password = "";
			String host = "";
			String port = "";
			String databaseName = "";

			Properties props = new Properties();

			switch (shema) {
			case CLIENTE:
				
				user = prop.getLocalProperties().getProperty("postgress.cliente.user");
				password = prop.getLocalProperties().getProperty("postgress.cliente.password");
				host = prop.getLocalProperties().getProperty("postgress.cliente.host");
				port = prop.getLocalProperties().getProperty("postgress.cliente.port");
				databaseName = prop.getLocalProperties().getProperty("postgress.cliente.databaseName");

				// TODO cambiar a archivo properties valor
//				props.setProperty("loginTimeout", "2000");
				props.setProperty("user", user);
				props.setProperty("password", password);
				
				return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + databaseName,props);
			case NEGOCIO:
				user = prop.getLocalProperties().getProperty("postgress.negocio.user");
				password = prop.getLocalProperties().getProperty("postgress.negocio.password");
				host = prop.getLocalProperties().getProperty("postgress.negocio.host");
				port = prop.getLocalProperties().getProperty("postgress.negocio.port");
				databaseName = prop.getLocalProperties().getProperty("postgress.negocio.databaseName");

				// TODO cambiar a archivo properties valor
//				props.setProperty("loginTimeout", "2000");
				props.setProperty("user", user);
				props.setProperty("password", password);

				return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + databaseName, props);
			default:
				return null;

			}
	}
	public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
}