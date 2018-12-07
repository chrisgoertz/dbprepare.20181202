package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.ncdf.models.Person;

public class PersonenDB extends DBParent{
	protected LocalPreferences lp = null;
	protected final String tablename = "personen";
	protected final String databasename = "zeiterfassung";
	
	protected int tableversion = -1;
	String createSQL = "CREATE TABLE IF NOT EXISTS `"+this.databasename+"`.`"+this.tablename+"`"
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
		this.tableversion = ver.getVersion(this.tablename);
		switch (this.tableversion) {
		case -1:
		{
			System.out.printf("creating table: %s\n",this.tablename);
			if(0 != this.createTable(createSQL))
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
	public void insert(Person p ) {
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO "+this.tablename
				+ " (rfidString, role, vorname, nachname, geschlecht, strasse, wohnort," //7
				+ " postleitzahl, telefonnummer, emailadresse, geburtstag, eintrittsdatum)" //5
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?);";
				
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+this.databasename+"?user="+lp.getUsern()+"&password="+lp.getUserp());
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
			System.err.printf("failed to insert into %s\n",this.tablename);
		}
	}
	
	
}
