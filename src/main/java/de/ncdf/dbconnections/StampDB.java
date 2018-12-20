package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import de.ncdf.models.RFIDTag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StampDB extends DBParent{
	
	private LocalPreferences lp = null;
	static final String tablename = "stampevents";
	static final String databasename = "zeiterfassung";
	private int tableversion = -1;
	
	public StampDB() {
		
		lp = new LocalPreferences();
		lp.load();
		
		tableUpdate();
		
		
	}
	
	public StampDB(String tablename, String databasename) {
		super(tablename, databasename);
		// TODO Auto-generated constructor stub
	}

	private void tableUpdate() {
		VersionDB ver = new VersionDB();
		this.tableversion = ver.getVersion(tablename);
		System.out.printf("fetching tableversion of: %s = %d",tablename,this.tableversion);
		switch (this.tableversion) {
		case -1:
		{
			System.out.printf("creating table: %s\n",tablename);
			if(0 != createTable())
			{
				return;
			}
			ver.firstVersion(tablename);
			this.tableversion = ver.getVersion(tablename);
			System.out.printf("table: %s updated to v%d\n",tablename,this.tableversion);
		}
		case 0:
		{
			//execute if tableversion v0
			//should never be true
		}
		case 1:
		{
			//execute if tableversion v1
			//should be the normal case after table create
		}
		case 2:
			//execute if tableversion v2
			break;
			
		}
	}
	public static int createTable() {
		LocalPreferences lp = new LocalPreferences();
		int retval = 1;
		lp.load();
		Connection con = null;
		Statement stmt = null;
		
		String sql = "CREATE TABLE IF NOT EXISTS `"+databasename+"`.`"+tablename+"` ( `event_id` INT NOT NULL AUTO_INCREMENT , `rfid` VARCHAR(13) NOT NULL , `status` VARCHAR(20) NOT NULL , `timestmp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`event_id`)) ENGINE = InnoDB;";
		System.out.println(sql);
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.createStatement();
			stmt.execute(sql);
			con.close();
			retval = 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("Failed to create table %s\n",tablename);
			retval = 2;
			
		}
		VersionDB ver = new VersionDB();
		ver.firstVersion(tablename);
		return retval;
	}
	
	
	/**
	 * fetch list of every RFID entry in the db.
	 * !!might cause heavy load!!
	 * @return
	 */
	public static ObservableList<RFIDTag> getAll(){
		LocalPreferences lp = new LocalPreferences();
		lp.load();
		ObservableList<RFIDTag> results = FXCollections.observableArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `"+databasename+"`.`"+tablename+"`"
				+ "left join personen "
				+ "on `"+tablename+"`.rfid = personen.rfidString ;";
		
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				RFIDTag t = new RFIDTag(rs.getInt("event_id"),rs.getString("rfid"),rs.getString("status"),rs.getString("timestmp"));
				if(null != rs.getString("vorname") && null != rs.getString("nachname")) {
					t.setNameString(rs.getString("vorname")+" "+rs.getString("nachname"));
				}
				results.add(t);
				
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch * from %s\n",tablename);
		}
		return results;
	}
	
	public static ObservableList<RFIDTag> getRange(LocalDate from, LocalDate to){
		LocalPreferences lp = new LocalPreferences();
		lp.load();
		if(null == from || null == to) {
			System.err.println("nullpointer not allowed");
			return null;
		}
		ObservableList<RFIDTag> results = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `"+databasename+"`.`"+tablename+"` "
				+ " left join personen "
				+ "on `"+tablename+"`.rfid = personen.rfidString "
				+ " WHERE timestmp >= ? "
				+ " AND timestmp <= ?;";
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			//stmt.setString(1, from.toString());
			//stmt.setString(2, to.toString());
			stmt.setDate(1, Date.valueOf(from));
			stmt.setDate(2, Date.valueOf(to));
			rs = stmt.executeQuery();
			while(rs.next()) {
				RFIDTag t = new RFIDTag(rs.getInt("event_id"),rs.getString("rfid"),rs.getString("status"),rs.getString("timestmp"));
				if(null != rs.getString("vorname") && null != rs.getString("nachname")) {
					t.setNameString(rs.getString("vorname")+" "+rs.getString("nachname"));
				}
				results.add(t);
				
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch range : %s -> %s\n",from.toString() ,to.toString());
		}
		return results;
	}
	
	public static ObservableList<RFIDTag> getByTag(String tag){
		LocalPreferences lp = new LocalPreferences();
		lp.load();
		ObservableList<RFIDTag> results = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `"+databasename+"`.`"+tablename+"` WHERE rfid = ?;";
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tag);
			rs = stmt.executeQuery();
			while(rs.next()) {
				results.add(new RFIDTag(rs.getInt("event_id"),rs.getString("rfid"),rs.getString("status"),rs.getString("timestmp")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch * from %s where rfid = %s\n",tablename, tag);
		}
		return results;
	}
	
	
	
}
