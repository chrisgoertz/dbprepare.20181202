package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBParent {
	private LocalPreferences lp = null;
	private final String tablename = "person";
	private final String databasename = "zeiterfassung";
	private int tableversion = -1;
	
	public DBParent() {
		lp = new LocalPreferences();
		lp.load();
		//tableUpdate();
	}
	
	protected int createTable(String sql) {
		int retval = 1;
		lp.load();
		Connection con = null;
		Statement stmt = null;
				
		
		System.out.println(sql);
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+this.databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.createStatement();
			stmt.execute(sql);
			con.close();
			retval = 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("Failed to create table %s\n",this.tablename);
			retval = 2;
			
		}
		return retval;
	}
}
