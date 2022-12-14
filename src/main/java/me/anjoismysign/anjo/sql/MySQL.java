package me.anjoismysign.anjo.sql;

import java.sql.Connection;
import java.sql.SQLException;

public final class MySQL extends SQLDatabase {
    protected MySQL(String hostName, int port, String database, String user, String password) {
        super(hostName, port, database, user, password);
        dataSource.setJdbcUrl("jdbc:mysql://" + this.hostName + ':' + this.port + '/' + this.database);
    }

    /**
     * @return The connection
     * @see Connection
     */
    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            singleError("Failed to connect to 'MySQL' Database using following credentials: \n" +
                    "Hostname: " + hostName + "\n" +
                    "Port: " + port + "\n" +
                    "Database: " + database + "\n" +
                    "User: " + user + "\n\n" +
                    "Be sure that password match");
        }
        return connection;
    }
}