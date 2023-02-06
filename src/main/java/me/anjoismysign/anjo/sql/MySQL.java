package me.anjoismysign.anjo.sql;

import me.anjoismysign.anjo.logger.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public final class MySQL extends SQLDatabase {
    protected MySQL(String hostName, int port, String database, String user,
                    String password, Logger logger) {
        super(hostName, port, database, user, password, logger);
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
            getLogger().singleError("Failed to connect to 'MySQL' Database using following credentials: \n" +
                    "Hostname: " + hostName + "\n" +
                    "Port: " + port + "\n" +
                    "Database: " + database + "\n" +
                    "User: " + user + "\n\n" +
                    "Be sure that password match");
        }
        return connection;
    }
}