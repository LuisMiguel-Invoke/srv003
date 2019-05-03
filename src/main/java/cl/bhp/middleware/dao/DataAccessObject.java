package cl.bhp.middleware.dao;

import cl.bhp.middleware.exception.ServiceException;
import cl.bhp.middleware.util.PropertiesUtil;
import cl.bhp.middleware.util.ConnectionFactory;
import cl.bhp.middleware.util.ConnectionFactory.DataBaseSchema;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * 
 * @author Luis Oliveros
 */

public class DataAccessObject {
	
	static PropertiesUtil prop = new PropertiesUtil();
	
	PropertiesUtil sqlProperties = new PropertiesUtil();
	Connection conCLIENTE = null;
	Connection conNEGOCIO = null;
	Statement stmtCLIENTE;
	Statement stmtNEGOCIO;
	
	private static final Logger LOGGER = Logger.getLogger(DataAccessObject.class);
	
	/**
	 * 
	 * 
	 * @param datos
	 * @return  
	 * @throws ServiceException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	public JSONObject obtenerbeneficios (String rut) throws ServiceException, ClassNotFoundException, SQLException {
		long init = System.currentTimeMillis();

		String URI = prop.getLocalProperties().getProperty("api.espocrm.uri");
		String auth = prop.getLocalProperties().getProperty("api.espocrm.auth");

		try {
			conCLIENTE = ConnectionFactory.getConnection(DataBaseSchema.CLIENTE);
			conNEGOCIO = ConnectionFactory.getConnection(DataBaseSchema.NEGOCIO);
			stmtCLIENTE = conCLIENTE.createStatement();
			stmtNEGOCIO = conNEGOCIO.createStatement();
	
			String queryValidKey = (String) sqlProperties.getLocalProperties().getProperty("sql.queryValidKey");
			System.out.println(queryValidKey);
			ResultSet Result = stmtCLIENTE.executeQuery(queryValidKey);
			Result.next();
			System.out.println(	Result.getString("nombre")+" / "+Result.getString("id"));
			System.out.println("3");
			
//			HttpResponse<String> responseUser = Unirest.post(URI)
//					  .header("Authorization", "Basic "+auth)
//					  .header("cache-control", "no-cache")
//					  .header("Content-Type", "application/json")
//					  .body(datos)
//					  .asString();
//					 		
//			System.out.println(responseUser.getStatus());
//			System.out.println(responseUser.getStatusText());
//			System.out.println(responseUser.getBody());
//			Json = new JSONObject(responseUser.getBody());
//			System.out.println(Json.toString());
//			reclamoId.put("reclamoId", Json.get("reclamoId"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("455");
		} finally {
			if (conCLIENTE != null || conNEGOCIO != null)
				try {
					conCLIENTE.close();
					conNEGOCIO.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new ServiceException("455");
				}
		}
		
		LOGGER.info("Tiempo en consulta EspoCRM "+(System.currentTimeMillis() - init)+" ms.");
		return null;	
	}
}
