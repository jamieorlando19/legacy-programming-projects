package com.warthog.services;

/** Class which utilizes Resin connection pooling to connect to a 
 * database.
 * 	@author Christopher R. Cali (CRC)
 * 	@version 1.0
 *
 * File: DbServices.java
 *
 * 	Sample usage of the DbServices class:
 * 		
 * 		DbServices db = new DbServices();
 * 		if (db.connect()) {
 * 			Connection conn = db.getConnection();
 * 			
 * 			..jdbc/sql stuff..
 *
 * 			db.releaseConnection();
 * 			}
 * 			
 * Usage Note: 
 * 	In order to use this class properly, you must 
 * 	configure JNDI within your resin/tomcat servlet container
 * 	in order to utilize the container-specific connpool.
 *	Consult the proper documentation to accomplish this. Upon
 *	completion, you must edit the JNDI_INITIAL and JNDI_PROVIDER
 *	instances within this class to reflect your configuration.
 * 	
 * May 9, 2003: CRC finalized comments and implementation.
 *
 * &#0169 2003 Christopher R. Cali.  May be freely distributed for 
 * non-commerical purposes only. Proper attribution required.
 */

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class DbServices
{
	private DataSource pool = null;
	private Connection conn = null;
	private final String JNDI_INITIAL = "java:comp/env";
	private final String JNDI_PROVIDER = "jdbc/warthog";
	
	/** Constructor for DbService code taken from the Resin web 
	 * server documentation at 
	 * http://www.caucho.com/resin/ref/db-config.xtp
	 */
	public DbServices() {

		try { 
			Context env = (Context) new InitialContext().
				lookup(JNDI_INITIAL);
			pool = (DataSource) env.lookup(JNDI_PROVIDER);
			} 
		catch (NamingException e) { 
			System.out.println("Naming Exception: " + e);
			e.printStackTrace();
			}
	}	

	/** Method gets a connection from the pool.
	 * 	@return conn the connection
	 */
	public boolean connect() {
		
		try {
			conn = pool.getConnection();
			return true;
			}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
			}
		}

	/** Method releases the connection back to the pool.
	 */
	public void releaseConnection() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
				}
			}
		catch(SQLException e) {
			System.out.println
				("SQL Exception closing connection!");
			e.printStackTrace();
			}
		}

	/** Method returns the connection object
	 * 	@return the connection object
	 */
	public Connection getConnection() {
		
		if (conn != null) {
			return (this.conn);
			}
		else {
			if (connect()) {
				return(this.conn);
				}
			else {
				return null;
				}
			}
		}
	}
