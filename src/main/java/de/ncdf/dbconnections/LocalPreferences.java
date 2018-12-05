package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LocalPreferences {
	
	private String host = "";
	private int port = 0;
	private String usern = "";
	private String userp = "";
	
	
	
	public static Connection getLocalConnection() {
		String url = "jdbc:sqlite:preferences.db";
		
		Connection con = null;
	
		try {
			// create a connection to the database
			con = DriverManager.getConnection(url);
		}
		catch(SQLException e) {
			System.err.println("opening local database failed");
			e.printStackTrace();
		}
		finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println("error closing local database connection");
					e.printStackTrace();
				}
			}
		}
		return con;
	}
	public static void mkTable() {
		String url = "jdbc:sqlite:preferences.db";
		
		String sql = "CREATE TABLE IF NOT EXISTS `preferences` (\r\n" + 
				"	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,\r\n" + 
				"	`host`	TEXT,\r\n" + 
				"	`port`	INTEGER,\r\n" + 
				"	`usern`	TEXT,\r\n" + 
				"	`userp`	TEXT\r\n" + 
				");";
		Statement stmt = null;
		Connection con = null;
		try {
			System.out.println("mkt preferences");
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			stmt.execute(sql);
			
		} catch (SQLException e) {

			System.out.println("mkt failed");
			e.printStackTrace();
		}
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		if (0 >= port) {
			System.err.println("setting portnumber failed");
			return;
		}
		this.port = port;
	}
	public String getUsern() {
		return usern;
	}
	public void setUsern(String usern) {
		this.usern = usern;
	}
	public String getUserp() {
		return userp;
	}
	public void setUserp(String userp) {
		this.userp = userp;
	}
	/**
	 * put preferences to local db
	 */
	public void save() {
		if(countRows() <= 0) {
			insertFirst();
			return;
		}
		String url = "jdbc:sqlite:preferences.db";
		String sql = "UPDATE preferences set host=?, port=?, usern=?, userp=? where id=1;";
		Connection con = null;
		PreparedStatement stmt= null;
		try {
			con = DriverManager.getConnection(url);
			stmt = con.prepareStatement(sql);
			stmt.setString(1, this.host);
			stmt.setInt(2, this.port);
			stmt.setString(3,this.usern);
			stmt.setString(4, this.userp);
			stmt.executeUpdate();
			con.close();
		}
		catch(SQLException e) {
			System.err.println("saven to db failed");
			e.printStackTrace();
		}
			
	}
	/**
	 * inserts first row to local preferences db,
	 * if no dataset is available
	 */
	public void insertFirst() {
		if(countRows() >= 1) {
			System.err.println("failed to create first dataset\n dataset already exists");
			return;
		}
		System.out.println("inserting first dataset");
		String url = "jdbc:sqlite:preferences.db";
		String sql = "INSERT INTO preferences (id, host, port, usern, userp) values (?,?,?,?,?);";
		Connection con = null;
		PreparedStatement stmt= null;
		try {
			con = DriverManager.getConnection(url);
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.setString(2, this.host);
			stmt.setInt(3, this.port);
			stmt.setString(4,this.usern);
			stmt.setString(5, this.userp);
			stmt.executeUpdate();
			con.close();
		}
		catch(SQLException e) {
			System.err.println("save to db failed");
			e.printStackTrace();
		}
			
	}
	private int countRows() {
		String url = "jdbc:sqlite:preferences.db";
		String sql = "SELECT Count(*) FROM preferences;";
		Connection con = null;
		Statement stmt = null;
		int retval = -1;
		try {
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			ResultSet result;
			result = stmt.executeQuery(sql);
			while(result.next()) {
				retval = result.getInt("Count(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("failed to fetch row count in local db");
		}
		return retval;
	}
	
	/**
	 * fetch preferences from local db
	 */
	public void load() {
		String url = "jdbc:sqlite:preferences.db";
		String sql = "SELECT * FROM preferences WHERE id = 1 LIMIT 1;";
		Connection con = null;
		Statement stmt = null;
		try {
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			ResultSet result;
			result = stmt.executeQuery(sql);
			while(result.next()) {
				this.host = result.getString("host");
				this.port = result.getInt("port");
				this.usern = result.getString("usern");
				this.userp = result.getString("userp");
			}
		} catch (SQLException e) {
			System.err.println("fetching preferences from db failed");
			e.printStackTrace();
		}
	}
}
