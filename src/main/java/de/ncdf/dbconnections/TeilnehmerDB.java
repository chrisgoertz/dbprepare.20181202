package de.ncdf.dbconnections;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.ncdf.models.Teilnehmer;

public class TeilnehmerDB extends PersonenDB {
	private static final String tablenamef = "personen";
	private static final String databasenamef = "zeiterfassung";
	//private final String tablename = "personen";
	//private final String databasename = "zeiterfassung";
	
	public static void insert(Teilnehmer t ) {
		LocalPreferences lp = new LocalPreferences();
		lp.load();
		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO "+tablenamef
				+ " (rfidString, role, vorname, nachname, geschlecht, strasse, wohnort," //7
				+ " postleitzahl, telefonnummer, emailadresse, geburtstag, eintrittsdatum)" //5
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?);";
				
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasenamef+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			stmt.setString(1, t.getRfidString());
			stmt.setString(2, t.getRole());
			stmt.setString(3, t.getVorname());
			stmt.setString(4, t.getNachname());
			stmt.setString(5, t.getGeschlecht());
			stmt.setString(6, t.getStrasse());
			stmt.setString(7, t.getWohnort());
			stmt.setInt(8, t.getPostleitzahl());
			stmt.setString(9, t.getTelefonnummer());
			stmt.setString(10, t.getEmailadresse());
			stmt.setDate(11, Date.valueOf(t.getGeburtstag()));
			stmt.setDate(12, Date.valueOf(t.getEintrittsdatum()));
			stmt.execute();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to insert into %s\n",tablenamef);
		}
	}
	public void update(Teilnehmer t) {
		LocalPreferences lp = new LocalPreferences();
		lp.load();
		Connection con = null;
		PreparedStatement stmt = null;
		
		String sql = "UPDATE "+tablenamef
				+ " SET rfidString = ?, "
				+ " role = ?,"
				+ " vorname = ?,"
				+ " nachname = ?,"
				+ " geschlecht = ?,"
				+ " strasse = ?,"
				+ " wohnort = ?,"
				+ " postleitzahl = ?,"
				+ " telefonnummer = ?,"
				+ " emailadresse = ?,"
				+ " geburtstag = ?,"
				+ " eintrittsdatum = ?"
				+ " WHERE person_id = ?;";
		try {
			con = DriverManager.getConnection("jdbc:mariadb://"+lp.getHost()+":"+lp.getPort()+"/"+databasenamef+"?user="+lp.getUsern()+"&password="+lp.getUserp());
			stmt = con.prepareStatement(sql);
			stmt.setString(1, t.getRfidString());
			stmt.setString(2, t.getRole());
			stmt.setString(3, t.getVorname());
			stmt.setString(4, t.getNachname());
			stmt.setString(5, t.getGeschlecht());
			stmt.setString(6, t.getStrasse());
			stmt.setString(7, t.getWohnort());
			stmt.setInt(8, t.getPostleitzahl());
			stmt.setString(9, t.getTelefonnummer());
			stmt.setString(10, t.getEmailadresse());
			stmt.setDate(11, Date.valueOf(t.getGeburtstag()));
			stmt.setDate(12, Date.valueOf(t.getEintrittsdatum()));
			stmt.setInt(13, t.getDbId());
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.printf("failed to update %s\n",tablenamef);
		}
	}
}
