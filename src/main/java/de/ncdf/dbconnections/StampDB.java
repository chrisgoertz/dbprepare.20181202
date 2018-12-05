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

public class StampDB {
	
	private LocalPreferences lp = null;
	private final String tablename = "stampevents";
	private final String databasename = "zeiterfassung";
	private int tableversion = -1;
	
	public StampDB() {
		lp = new LocalPreferences();
		lp.load();
		tableUpdate();
		
		
	}
	private void tableUpdate() {
		VersionDB ver = new VersionDB();
		this.tableversion = ver.getVersion(this.tablename);
		switch (this.tableversion) {
		case -1:
		{
			System.out.printf("creating table: %s\n",this.tablename);
			if(0 != createTable())
			{
				return;
			}
			ver.firstVersion(this.tablename);
			this.tableversion = ver.getVersion(this.tablename);
			System.out.printf("table: %s updated to v%d\n",this.tablename,this.tableversion);
		}
		case 0:
		{
			System.out.println("case0");
			System.out.printf("tableupdate further versions");
		}
		case 1:
		{
			System.out.println("case1");
			System.out.printf("tablev %d of table %s",this.tableversion,this.tablename);
		}
		case 2:
			System.out.println("case2");
			break;
			
		}
	}
	private int createTable() {
		int retval = 1;
		lp.load();
		Connection con = null;
		Statement stmt = null;
		
		String sql = "CREATE TABLE IF NOT EXISTS `"+this.databasename+"`.`"+this.tablename+"` ( `event_id` INT NOT NULL AUTO_INCREMENT , `rfid` VARCHAR(13) NOT NULL , `status` VARCHAR(20) NOT NULL , `timestmp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`event_id`)) ENGINE = InnoDB;";
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
	
	
	/**
	 * fetch list of every RFID entry in the db.
	 * !!might cause heavy load!!
	 * @return
	 */
	public ObservableList<RFIDTag> getAll(){
		ObservableList<RFIDTag> results = FXCollections.observableArrayList();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `"+this.databasename+"`.`"+this.tablename+";";
		
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+this.databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				results.add(new RFIDTag(rs.getInt("event_id"),rs.getString("rfid"),rs.getString("status"),rs.getString("timestmp")));
				
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch * from %s\n",this.tablename);
		}
		return results;
	}
	
	public ObservableList<RFIDTag> getRange(LocalDate from, LocalDate to){
		if(null == from || null == to) {
			System.err.println("nullpointer not allowed");
			return null;
		}
		ObservableList<RFIDTag> results = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `"+this.databasename+"`.`"+this.tablename+"` "
				+ "WHERE timestmp >= ? "
				+ "AND timestmp <= ?;";
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+this.databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			//stmt.setString(1, from.toString());
			//stmt.setString(2, to.toString());
			stmt.setDate(1, Date.valueOf(from));
			stmt.setDate(2, Date.valueOf(to));
			rs = stmt.executeQuery();
			while(rs.next()) {
				results.add(new RFIDTag(rs.getInt("event_id"),rs.getString("rfid"),rs.getString("status"),rs.getString("timestmp")));
				
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch range : %s -> %s\n",from.toString() ,to.toString());
		}
		return results;
	}
	
	public ObservableList<RFIDTag> getByTag(String tag){
		ObservableList<RFIDTag> results = FXCollections.observableArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM `"+this.databasename+"`.`"+this.tablename+"` WHERE rfid = ?;";
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+this.databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			stmt.setString(1, tag);
			rs = stmt.executeQuery();
			while(rs.next()) {
				results.add(new RFIDTag(rs.getInt("event_id"),rs.getString("rfid"),rs.getString("status"),rs.getString("timestmp")));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to fetch * from %s where rfid = %s\n",this.tablename, tag);
		}
		return results;
	}
	
	
	
}
