package de.ncdf.dbconnections;

public class PersonenDB extends DBParent{
	private LocalPreferences lp = null;
	private final String tablename = "personen";
	private final String databasename = "zeiterfassung";
	
	private int tableversion = -1;
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
	
	
}
