/**
 * Interface for the provided DB connections.
 */
package com.sebulli.fakturama.dbconnector;

import java.sql.Connection;

/**
 * This interface describes a JDBC connection handler. 
 *
 */
public interface IDbConnection {

	/**
	 * The unique key of this provider
	 * 
	 * @return key
	 */
	public String getKey();
	
	/**
	 * How must the JDBC URL looks like?
	 * 
	 * @return pattern for jdbc url
	 */
	public String getJdbcUrlPattern();
	
	default Connection getConnection() {
		return null;
	}
}
