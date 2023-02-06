package me.anjoismysign.anjo.sql;


import me.anjoismysign.anjo.logger.Logger;

import java.io.File;

public class SQLHolder {
    private final SQLDatabase database;

    public SQLHolder(String database, File file, Logger logger) {
        this.database = new SQLite(database, file, logger);
    }

    public SQLHolder(String hostName, int port, String database, String user,
                     String password, Logger logger) {
        this.database = new MySQL(hostName, port, database, user, password, logger);
    }

    /**
     * @return The database
     */
    public SQLDatabase getDatabase() {
        return this.database;
    }

    /**
     * Disconnects the database
     */
    public void disconnect() {
        this.database.disconnect();
    }
}