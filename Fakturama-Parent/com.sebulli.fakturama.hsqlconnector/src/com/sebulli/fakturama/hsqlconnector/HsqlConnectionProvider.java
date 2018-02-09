package com.sebulli.fakturama.hsqlconnector;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private Server server;

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
		Pattern patt = Pattern.compile(".*?:file:(.*?);.*");
		Matcher m = patt.matcher(url);
		if (m.matches() && m.groupCount() > 0) {
			String dbFileName = m.group(1);
//			UUID randomUUID = UUID.randomUUID();
//			randomUUID.toString();
			hsqlProps.setProperty("server.database.0", dbFileName);
			props.put("hsqlfiledb", dbFileName);
			props.put("newfakdbname", "fakdbneu");
			// set up the rest of properties
		} else if(props.getProperty("url").endsWith("fakdbneu")) {
			hsqlProps.setProperty("server.database.0", (String)props.get("hsqlfiledb"));
		}
		hsqlProps.setProperty("server.dbname.0", "fakdbneu");
		hsqlProps.setProperty("hsqldb.lob_compressed", "true");
		hsqlProps.setProperty("server.port", "9002");

		try {
			server.setProperties(hsqlProps);
			server.start();

		} catch (IOException | AclFormatException e) {
			e.printStackTrace();
		}
		props.put("runningfakdb", server.getDatabaseName(0, false));
		
		return props;
	}

	@Override
	public void stopServer() {
		server.shutdown();
	}
}
