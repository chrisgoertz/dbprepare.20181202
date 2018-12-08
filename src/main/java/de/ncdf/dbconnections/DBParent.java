package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBParent {
	private LocalPreferences lp = null;
	private  String tablename;
	private  String databasename;
	//private int tableversion = -1;
	
	
	/**
	 * General purpose database-table object
	 * @param tablename
	 * @param databasename
	 */
	public DBParent(String tablename, String databasename) {
		this.tablename = tablename;
		this.databasename = databasename;
		lp = new LocalPreferences();
		lp.load();
		
	}
	public DBParent() {
		lp = new LocalPreferences();
		lp.load();
		//tableUpdate();
	}
	
	
	/**
	 * checks if table exists in database
	 * @param tablename
	 * @return
	 */
	protected boolean isInDatabase(String tablename) {
		boolean retval = false;
		String sql = "show tables like '"+tablename+"';";
		lp.load();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+this.databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1).equals(tablename)){
					retval = true;
				}
				System.out.println(rs.getString(1));
			}
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch tables\n");
		}
		
		return retval;
	}
	
	/**
	 * create inital version of database table in db
	 * @param sql
	 * @return
	 */
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
