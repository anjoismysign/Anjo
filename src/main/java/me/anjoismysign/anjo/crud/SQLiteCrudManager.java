package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.entities.UpdatableSerializable;
import me.anjoismysign.anjo.entities.UpdatableSerializableHandler;
import me.anjoismysign.anjo.logger.Logger;
import me.anjoismysign.anjo.sql.SQLHolder;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SQLiteCrudManager<T extends Crudable> implements SQLCrudManager<T> {
    private SQLHolder holder;
    private final String database, tableName, primaryKeyName, crudableKeyTypeName;
    private final int primaryKeyLength;
    private final File path;
    private final Function<String, T> createFunction;
    private final Logger logger;

    protected SQLiteCrudManager(String database, File path, String tableName, String primaryKeyName,
                                int primaryKeyLength, String crudableKeyTypeName,
                                Function<String, T> createFunction,
                                Logger logger) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.primaryKeyLength = primaryKeyLength;
        this.crudableKeyTypeName = crudableKeyTypeName;
        this.createFunction = createFunction;
        this.database = database;
        this.path = path;
        this.logger = logger;
        load();
    }

    public void load() {
        holder = new SQLHolder(database, path, logger);
        if (holder.getDatabase().createTable(getTableName(), getPrimaryKeyName() +
                " VARCHAR(" + getPrimaryKeyLength() + ")," + getCrudableKeyTypeName() +
                " BLOB", getPrimaryKeyName()))
            logger.log("Create table " + getTableName() + " with primary key " + getPrimaryKeyName() +
                    " and type " + getCrudableKeyTypeName() + " " +
                    "was executed successfully.");
    }

    public void reload() {
        logger.log("Reloading database...");
        holder.disconnect();
        load();
    }

    @Override
    public String getCrudableKeyTypeName() {
        return crudableKeyTypeName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    @Override
    public int getPrimaryKeyLength() {
        return primaryKeyLength;
    }

    public Connection getConnection() {
        return this.holder.getDatabase().getConnection();
    }

    /**
     * @param id The primary key id
     * @return Whether the record exists
     */
    public boolean exists(String id) {
        boolean exists = this.holder.getDatabase().exists(getTableName(),
                getPrimaryKeyName(), id);
        if (exists)
            log("Record with id " + id + " exists.");
        else
            log("Record with id " + id + " does not exist.");
        return exists;
    }

    /**
     * Creates a new instance of the Crudable and registers it in the database
     * using the given identification. Will only update the identification.
     *
     * @param identification The identification to use.
     * @return The new instance of the Crudable.
     */
    @Override
    public T createAndRegister(String identification) {
        T crudable = create(identification);
        Connection connection = null;
        String sql = "INSERT OR IGNORE INTO " + getTableName();
        try {
            connection = this.holder.getDatabase().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql +
                    " (" + getPrimaryKeyName() + ") VALUES (?)");
            if (!exists(identification)) {
                preparedStatement.setString(1, identification);
                preparedStatement.executeUpdate();
                log("Created new record with id " + identification + ".");
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement.getConnection().close();
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
        return crudable;
    }

    /**
     * @param identification The identification to use
     * @return a new instance of the Crudable
     */
    @Override
    public T create(String identification) {
        return createFunction.apply(identification);
    }

    /**
     * @param id The id of the Crudable to get
     * @return The Crudable with the given id
     */
    @Override
    public T read(String id) {
        ResultSet resultSet = this.holder.getDatabase()
                .selectRowByPrimaryKey(getPrimaryKeyName(), id, getTableName());
        T crudable;
        try {
            if (resultSet.next()) {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                resultSet.close();
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
                @SuppressWarnings("unchecked") UpdatableSerializable<T> updatableSerializable = UpdatableSerializable.deserialize(bytes);
                crudable = updatableSerializable.getValue();
                log("Read record with id " + id + " successfully.");
                return crudable;
            } else {
                resultSet.close();
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
                return create();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return create();
    }

    /**
     * @param crudable The Crudable to be updated (defaults version to 0)
     */
    @Override
    public void update(T crudable) {
        update(crudable, 0);
    }

    /**
     * Updates the database with the given Crudable and version
     *
     * @param crudable The Crudable to update
     * @param version  The version to update to
     */
    @Override
    public void update(T crudable, int version) {
        UpdatableSerializableHandler<T> handler = newUpdatable(crudable, 0);
        String id = crudable.getIdentification();
        PreparedStatement statement = this.holder.getDatabase()
                .updateDataSet(getPrimaryKeyName(), getTableName(), crudableKeyTypePrepareStatement());
        try {
            statement.setBytes(1, handler.serialize());
            statement.setString(2, id);
            statement.executeUpdate();
            if (version != 0)
                log("Updated record with id " + id + " to version " + version + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                statement.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param id The id of the Crudable to delete
     */
    @Override
    public void delete(String id) {
        PreparedStatement preparedStatement = this.holder.getDatabase()
                .delete(getTableName(), getPrimaryKeyName());
        try {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            log("Deleted record with id " + id + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                preparedStatement.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param biConsumer First parameter is Crudable, second parameter is the version
     */
    public void forEachRecord(BiConsumer<T, Integer> biConsumer) {
        this.holder.getDatabase().selectAllFromDatabase(getTableName(), resultSet -> {
            try {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                @SuppressWarnings("unchecked") UpdatableSerializable<T> updatableSerializable = UpdatableSerializable.deserialize(bytes);
                T crudable = updatableSerializable.getValue();
                log("Read record with id " + crudable.getIdentification() + " successfully.");
                biConsumer.accept(crudable, updatableSerializable.getVersion());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private void log(String message) {
        if (logger != null)
            logger.log(message);
    }
}
