package me.anjoismysign.anjo.sql;

import com.zaxxer.hikari.HikariDataSource;
import me.anjoismysign.anjo.logger.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public abstract class SQLDatabase {
    protected final Logger logger;
    protected final String database;
    protected final String hostName;
    protected final int port;
    protected final String user;
    protected final String password;
    protected final HikariDataSource dataSource;
    protected final ThreadPoolExecutor executor;

    /**
     * Builds a new SQLDatabase instance
     *
     * @param hostName The hostname of the database
     * @param port     The port of the database
     * @param database The database name
     * @param user     The user of the database
     * @param password The password of the database
     */
    protected SQLDatabase(String hostName, int port, String database, String user,
                          String password, Logger logger) {
        this.logger = logger;
        this.dataSource = new HikariDataSource();
        this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.database = database;
        this.hostName = hostName;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dataSource.setUsername(user);
        this.dataSource.setPassword(password);
        this.dataSource.setMaximumPoolSize(200);
        this.dataSource.setMinimumIdle(5);
        this.dataSource.setLeakDetectionThreshold(15000);
        this.dataSource.setConnectionTimeout(1000);
    }

    /**
     * @return Connection to the database
     * @see Connection
     */
    public abstract Connection getConnection();

    /**
     * Disconnects from the database
     */
    public final void disconnect() {
        try {
            this.executor.shutdown();
            this.dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return The database name
     */
    public final String getDatabase() {
        return this.database;
    }

    /**
     * Selects all data from a row and returns it in a ResultSet
     *
     * @param keyType The type of key to use
     * @param key     The key to use
     * @param table   The table to select from
     * @return The ResultSet
     */
    public ResultSet selectRowByPrimaryKey(String keyType, String key, String table) {
        Connection connection;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + keyType + "=?");
            preparedStatement.setString(1, key);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates a data set in a table
     *
     * @param keyType The type of key to use
     * @param table   The table to update
     * @param values  The values to update
     * @return The PreparedStatement
     */
    public PreparedStatement updateDataSet(String keyType, String table, String values) {
        Connection connection;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + table + " SET " + values + " WHERE " + keyType + "=?");
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a data set from a table
     *
     * @param table   The table to delete from
     * @param keyType The type of key to use
     * @return The PreparedStatement
     */
    public PreparedStatement delete(String table, String keyType) {
        Connection connection;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + table + " WHERE " + keyType + "=?");
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new table
     *
     * @param table      The table to create
     * @param columns    The columns to create
     * @param primaryKey The primary key to use
     */
    public void createTable(String table, String columns, String primaryKey) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ",PRIMARY KEY(" + primaryKey + "))");
            try {
                preparedStatement.executeUpdate();
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                if (preparedStatement != null)
                    try {
                        preparedStatement.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                e.printStackTrace();
            } finally {
                if (preparedStatement != null)
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Confirms if a table exists
     *
     * @param table   The table to check
     * @param keyType The type of key to use
     * @param key     The key to use
     * @return "true" if table exists, "false" otherwise
     */
    public boolean exists(String table, String keyType, String key) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + keyType + "='" + key + "'");
            try {
                return preparedStatement.executeQuery().next();
            } catch (Throwable throwable) {
                if (preparedStatement != null)
                    try {
                        preparedStatement.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return false;
    }

    public void selectAllFromDatabase(String table, Consumer<ResultSet> forEach) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("select * from " + table);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                forEach.accept(resultSet);
            }
            resultSet.close();
            statement.close();
            statement.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
