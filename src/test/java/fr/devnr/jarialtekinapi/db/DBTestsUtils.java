package fr.devnr.jarialtekinapi.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;


public class DBTestsUtils {
	
	private static final String SQL_EmptyTables = "src/test/resources/sql/empty_tables.sql";

	
	public static void executeBatchFromFile(DataSource source, String filename) throws SQLException, IOException {
		Statement stmt;
		try (Connection c = source.getConnection()) {
			stmt = c.createStatement();
			try (
            	FileReader fileReader =  new FileReader(new File(filename));
            	BufferedReader reader = new BufferedReader(fileReader)
			) {
				String query = reader.readLine();
				while (query!=null) {
					if (!query.isEmpty() && !query.startsWith("--")) stmt.addBatch(query);
					query = reader.readLine();
				}
			}
			stmt.executeBatch();
		}
	}

	public static void emptyTables(DataSource source) {
		try {
			executeBatchFromFile(source, SQL_EmptyTables);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void initializeData(DataSource source, String filename) {
		emptyTables(source);
		try {
			executeBatchFromFile(source, filename);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
