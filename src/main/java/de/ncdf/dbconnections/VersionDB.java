package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VersionDB {
	private LocalPreferences lp = null;
	private final int tableversion = 1;
	private String tablename = "versionen";
	
	
	public VersionDB() {
		lp = new LocalPreferences();
		lp.load();
		createTable();
	}
	
	/**
	 * Creates the table versions in db
	 * if table doesnt exist
	 */
	private void createTable() {
		lp.load();
		String hostn = lp.getHost();
		int port = lp.getPort();
		String usern = lp.getUsern();
		String userp = lp.getUserp();
		
		Connection con = null;
		Statement stmt = null;
		
		String sql = "CREATE TABLE IF NOT EXISTS `zeiterfassung`.`"+this.tablename+"`"
				+ " ( `db_id` INT NOT NULL AUTO_INCREMENT , "
				+ "`db_name` VARCHAR(40) NOT NULL , `db_version` "
				+ "INT NOT NULL , `db_update` TIMESTAMP on update CURRENT_TIMESTAMP NOT NULL ,"
				+ " PRIMARY KEY (`db_id`)) ENGINE = InnoDB;";
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+hostn+":"+port+"/zeiterfassung?user="+usern+"&password="+userp);
			stmt = con.createStatement();
			stmt.execute(sql);
			con.close();
		} catch (SQLException e) {
			System.err.println("remote db connection failed");
			e.printStackTrace();
		}
	}
	
	/**
	 * get Version of table in db
	 * @param tablename
	 * @return
	 */
	public int getVersion(String tablename) {
		int version = -1;
		lp.load();
		String hostn = lp.getHost();
		int port = lp.getPort();
		String usern = lp.getUsern();
		String userp = lp.getUserp();
		
		String sql = "SELECT db_version FROM `zeiterfassung`.`versionen`"
				+" WHERE db_name = ? LIMIT 1;";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+hostn+":"+port+"/zeiterfassung?user="+usern+"&password="+userp);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tablename);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				version = result.getInt("db_version");
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to get version for table: %s",tablename);
		}
		if(-1 == version) {
			System.err.printf("version for: %s doesn't exists\n",tablename);
		}
		return version;
	}
	
	/**
	 * creates a new version entry for a specific table
	 * @param name
	 */
	public void firstVersion(String tablename) {
		lp.load();
		String hostn = lp.getHost();
		int port = lp.getPort();
		String usern = lp.getUsern();
		String userp = lp.getUserp();
		String sql = "INSERT INTO `zeiterfassung`.`versionen` (db_name, db_version) VALUES (?,?);";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			
			con = DriverManager.getConnection("jdbc:mariadb://"+hostn+":"+port+"/zeiterfassung?user="+usern+"&password="+userp);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tablename);
			stmt.setInt(2, 1);
			stmt.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("inserting version for: %s failed\n",tablename);
		}
	}
	public void updateVersion(String tablename, int tableversion) {
		lp.load();
		String hostn = lp.getHost();
		int port = lp.getPort();
		String usern = lp.getUsern();
		String userp = lp.getUserp();
		String sql ="UPDATE `zeiterfassung`.`versionen` SET version=? WHERE db_name=?";
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+hostn+":"+port+"/zeiterfassung?user="+usern+"&password="+userp);
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, tableversion);
			stmt.setString(2, tablename);
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("updating version for: %s failed\n",tablename);
		}
	}
}
