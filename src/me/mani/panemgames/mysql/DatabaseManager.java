package me.mani.panemgames.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.mani.panemgames.PanemGames;

public class DatabaseManager {

	private MySQL MySQL;
	private Connection c = null;
	
	private static SettingsQuery settingsQuery;
	
	public DatabaseManager() {
		
	}
	
	public void setup(String host, String port, String database, String user, String password) {
		MySQL = new MySQL(PanemGames.getPanemGames(), host, port, database, user, password);
		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		settingsQuery = new SettingsQuery();
	}
	
	public Statement createStatement() {
		try {
			return c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SettingsQuery getSettings() {
		return settingsQuery;
	}
	
	public class SettingsQuery {
		
		public int getSetting(String settingName) {
			Statement statement = createStatement();
			try {
				ResultSet result = statement.executeQuery("SELECT * FROM panemgames WHERE settingInt = '" + settingName + "';");
				result.next();
				return result.getInt("valueInt");
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		public String getStringSetting(String settingName) {
			Statement statement = createStatement();
			try {
				ResultSet result = statement.executeQuery("SELECT * FROM panemgames WHERE settingString = '" + settingName + "'");
				result.next();
				return result.getString("valueString");
			} catch (SQLException e) {
				e.printStackTrace();
				return "";
			}
		}
		
		public void setSetting(String settingName, int value) {
			Statement statement = createStatement();
			try {
				statement.executeQuery("UPDATE panemgames SET valueInt = '" + value + "' WHERE settingInt = " + settingName + "");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void setStringSetting(String settingName, String value) {
			Statement statement = createStatement();
			try {
				statement.executeQuery("UPDATE panemgames SET valueString = '" + value + "' WHERE settingString = " + settingName + "");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
