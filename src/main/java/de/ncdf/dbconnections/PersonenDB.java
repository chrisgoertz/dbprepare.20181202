package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.ncdf.models.Person;

public class PersonenDB extends DBParent{
	protected LocalPreferences lp = null;
	static final String tablename = "personen";
	static final String databasename = "zeiterfassung";
	
	protected int tableversion = -1;
	
	public PersonenDB() {
		lp = new LocalPreferences();
		lp.load();
		tableUpdate();
	}
	public PersonenDB(String tablename, String databasename) {
		super(tablename, databasename);
		// TODO Auto-generated constructor stub
	}

	private void tableUpdate() {
		VersionDB ver = new VersionDB();
		this.tableversion = ver.getVersion(tablename);
		switch (this.tableversion) {
		case -1:
		{
			System.out.printf("creating table: %s\n",tablename);
			if(0 != this.createTable())
			{
				return;
			}
			ver.firstVersion(tablename);
			this.tableversion = ver.getVersion(tablename);
			
		}
		case 0:
		{
			
		}
		case 1:
		{
			
		}
		case 2:
			
			break;
			
		}
	}
	public void insert(Person p ) {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO "+tablename
				+ " (rfidString, role, vorname, nachname, geschlecht, strasse, wohnort," //7
				+ " postleitzahl, telefonnummer, emailadresse, geburtstag, eintrittsdatum)" //5
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?);";
				
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			stmt.setString(1, p.getRfidString());
			stmt.setString(2, p.getRole());
			stmt.setString(3, p.getVorname());
			stmt.setString(4, p.getNachname());
			stmt.setString(5, p.getGeschlecht());
			stmt.setString(6, p.getStrasse());
			stmt.setString(7, p.getWohnort());
			stmt.setInt(8, p.getPostleitzahl());
			stmt.setString(9, p.getTelefonnummer());
			stmt.setString(10, p.getEmailadresse());
			stmt.setDate(11, Date.valueOf(p.getGeburtstag()));
			stmt.setDate(12, Date.valueOf(p.getEintrittsdatum()));
			stmt.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to insert into %s\n",tablename);
		}
	}
	public static int createTable() {
		System.out.println("creating table : personen");
		String sql = "CREATE TABLE IF NOT EXISTS `"+databasename+"`.`"+tablename+"`"
				+ " ( `person_id` INT NOT NULL AUTO_INCREMENT , "
				+ "`rfidString` VARCHAR(14) NOT NULL , "
				+ "`role` VARCHAR(20) NOT NULL , "
				+ "`vorname` VARCHAR(20) NOT NULL , "
				+ "`nachname` VARCHAR(20) NOT NULL , "
				+ "`geschlecht` VARCHAR(20) NOT NULL , "
				+ "`strasse` VARCHAR(50) NOT NULL , "
				+ "`wohnort` VARCHAR(30) NOT NULL , "
				+ "`postleitzahl` INT NOT NULL , "
				+ "`telefonnummer` VARCHAR(20) NOT NULL , "
				+ "`emailadresse` VARCHAR(40) NOT NULL , "
				+ "`geburtstag` DATE NOT NULL , "
				+ "`eintrittsdatum` DATE NOT NULL , "
				+ "PRIMARY KEY (`person_id`)) ENGINE = InnoDB;";
		
		LocalPreferences lp = new LocalPreferences();
		int retval = 1;
		lp.load();
		Connection con = null;
		Statement stmt = null;
		
		//String sql = "CREATE TABLE IF NOT EXISTS `"+databasename+"`.`"+tablename+"` ( `event_id` INT NOT NULL AUTO_INCREMENT , `rfid` VARCHAR(13) NOT NULL , `status` VARCHAR(20) NOT NULL , `timestmp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`event_id`)) ENGINE = InnoDB;";
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
	
}
