package com.sebulli.fakturama.hsqlconnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hsqldb.Database;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl.AclFormatException;

import com.sebulli.fakturama.dbconnector.IActivateDbServer;
import com.sebulli.fakturama.dbconnector.IDbConnection;

/**
 * Provides the implementation for an HSQL DB.
 * 
 */
public class HsqlConnectionProvider implements IDbConnection, IActivateDbServer {

	private static final String DEFAULT_HSQL_DATABASEPORT = "9002";
	private static final String DEFAULT_HSQL_DATABASENAME = "fakdbneu";
	private Server server;
	private String workspace;

	@Override
	public String getKey() {
		return "HSQL";
	}

	@Override
	public String getJdbcUrlPattern() {
		return "jdbc:hsqldb:file:///path/to/Database";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sebulli.fakturama.hsqlconnector.IActivateDbServer#activateServer(java
	 * .util.Properties)
	 */
	@Override
	public Properties activateServer(Properties props) {
		server = new Server();
		HsqlProperties hsqlProps = new HsqlProperties();
		String url = props.getProperty("url");
		workspace = props.getProperty("GENERAL_WORKSPACE");
		Pattern patt = Pattern.compile(".*?:file:(.*?);.*");
		Matcher m = patt.matcher(url);
		if (m.matches() && m.groupCount() > 0) {
			String dbFileName = m.group(1);
			hsqlProps.setProperty("server.database.0", dbFileName);
			props.put("hsqlfiledb", dbFileName);
			props.put("newfakdbname", DEFAULT_HSQL_DATABASENAME);
			// set up the rest of properties
		} else if(props.getProperty("url").endsWith(DEFAULT_HSQL_DATABASENAME)) {
			hsqlProps.setProperty("server.database.0", (String)props.get("hsqlfiledb"));
		}
		hsqlProps.setProperty("server.dbname.0", DEFAULT_HSQL_DATABASENAME);
		hsqlProps.setProperty("hsqldb.lob_compressed", "true");
		hsqlProps.setProperty("server.port", DEFAULT_HSQL_DATABASEPORT);
		hsqlProps.setProperty("hsqldb.lob_file_scale", "1");
		hsqlProps.setProperty("hsqldb.shutdown", "true");

		try {
			server.setProperties(hsqlProps);
			server.start();
		} catch (IOException | AclFormatException e) {
			e.printStackTrace();
		}
		props.put("runningfakdb", server.getDatabaseName(0, false));
		
//		FrameworkUtil.getBundle(IDbConnection.class).getBundleContext().registerService(IDbConnection.class.getName(), this, null);
		return props;
	}
	
	@Override
	public Connection getConnection() {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:"+DEFAULT_HSQL_DATABASEPORT+"/"+server.getDatabaseName(0, false), "SA", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	@Override
	public void stopServer() {
		server.shutdownCatalogs(Database.CLOSEMODE_COMPACT);
		server.stop();
		BackupManager bm = new BackupManager();
		bm.createBackup(workspace);
	}
}
