package me.anjoismysign.anjo.sql;


import java.io.File;

public class SQLHolder {
    private final SQLDatabase database;

    public SQLHolder(String database, File file) {
        this.database = new SQLite(database, file);
    }

    public SQLHolder(String hostName, int port, String database, String user, String password) {
        this.database = new MySQL(hostName, port, database, user, password);
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