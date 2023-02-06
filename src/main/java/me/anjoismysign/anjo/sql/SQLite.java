package me.anjoismysign.anjo.sql;

import me.anjoismysign.anjo.logger.Logger;

import java.io.File;
import java.sql.Connection;

public final class SQLite extends SQLDatabase {
    protected SQLite(String name, File path, Logger logger) {
        super(null, 0, name, null, null, logger);
        File file = new File(path, database.endsWith(".db") ? database : database + ".db");
        try {
            if (!file.exists()) file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl("jdbc:sqlite:" + file.getAbsolutePath());
    }

    /**
     * @return The connection
     * @see Connection
     */
    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().error("Failed to connect to 'SQLite' using database: " + database);
        }
        return connection;
    }
}