package com.dao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {

	private static Properties properties = getProperties();

	/*
	 * Restituisce una connessione sui parametri definiti nel config.properties
	 */
	public static Connection getConnection() {

		Connection connection = null;

		String endpoint = properties.getProperty("endpoint");
		String port = properties.getProperty("port");
		String schema = properties.getProperty("schema");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		String url = "jdbc:mysql://" + endpoint + ":" + port + "/" + schema;

		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException sqle) {
			System.err.println("Creazione connessione fallita...");
			sqle.printStackTrace();
		}
		return connection;
	}

	/*
	 * Apre una transazione sulla connessione
	 */
	public static void openTransaction(Connection connection) throws SQLException {
		connection.setAutoCommit(false);
	}

	/*
	 * Restituisce un PreparedStatement
	 */
	public static PreparedStatement getPreparedStatement(Connection connection, String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	/*
	 * Restituisce un PreparedStatement che contiene le informazioni sulle chiavi
	 * primarie che vengono generate da eventuali azioni dml sul PreparedStatement
	 * in questione
	 */
	public static PreparedStatement getPreparedStatementWithKeys(Connection connection, String sql)
			throws SQLException {
		return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}

	/*
	 * Restituisce un ResultSet su uno Statement
	 */
	public static ResultSet getResultSetOnSt(Connection connection, String query) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	/*
	 * Restituisce un ResultSet su un PreparedStatement
	 */
	public static ResultSet getResultSetOnPS(PreparedStatement ps) throws SQLException {
		return ps.executeQuery();
	}

	/*
	 * Restituisce il numero di record interessati dall'esecuzione dello script Sql
	 * nel caso di una DML, 0 nel caso di una DDL
	 */
	public static Integer executeSql(Connection connection, String sql) throws SQLException {
		Statement st = connection.createStatement();
		Integer result = st.executeUpdate(sql);
		return result;
	}

	/*
	 * Restituisce il numero di record interessati dall'esecuzione dello script Sql
	 * sul PreparedStatement dato: un numero che potrebbe essere maggiore di 0 nel
	 * caso di una DML, 0 nel caso di una DDL
	 */
	public static Integer executeSqlOnPs(PreparedStatement preparedStatement) throws SQLException {
		Integer result = preparedStatement.executeUpdate();
		return result;
	}

	/*
	 * Imposta il livello di isolamento piu' sicuro
	 */
	public static void setBestTransactionIsolationLevel(Connection connection) throws SQLException {
		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	}

	/*
	 * Esegue il commit di tutte le operazioni pendenti della transazione
	 */
	public static void doCommit(Connection connection) throws SQLException {
		connection.commit();
	}

	/*
	 * Esegue il rollback di tutte le operazioni della transazione
	 */
	public static void doRollback(Connection connection) throws SQLException {
		connection.rollback();
	}

	/*
	 * Chiude la connessione
	 */
	public static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException sqle) {
			System.err.println("Errore nella chiusura della connessione...");
			sqle.printStackTrace();
		}
	}

	private static Properties getProperties() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = ConnectionManager.class.getResourceAsStream("config.properties");
			prop.load(input);
			input.close();
		} catch (IOException ioe) {
			System.out.println("Lettura file di properties fallita...");
			ioe.printStackTrace();
		}
		return prop;
	}

}
