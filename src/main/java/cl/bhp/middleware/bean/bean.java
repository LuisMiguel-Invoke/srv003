package cl.bhp.middleware.bean;

import cl.bhp.middleware.dao.DataAccessObject;
import cl.bhp.middleware.exception.ServiceException;

import java.sql.SQLException;

import org.apache.camel.Exchange;
import org.json.JSONObject;

/**
 * Clase que permite la realización de forward de peticiones generadas desde
 * una ruta camel, busca realizar validaciones previas antes de ir por el 
 * negocio en búsqueda de resultados
 * @author Luis Oliveros
 */

public class bean {

	/**
	 * 
	 * 
	 * @param ex rut
	 * @return 
	 * @throws ServiceException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	@SuppressWarnings("unused")
	public JSONObject beneficios(Exchange ex)  throws ServiceException, ClassNotFoundException, SQLException {
		String rut = null;
		rut = (String) ex.getIn().getHeader("rut");
		
		if(rut == null) {
			throw new ServiceException("400");
		}	
		
		return new DataAccessObject().obtenerbeneficios(rut);
	}
	
}
