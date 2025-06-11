package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.logger.Logger;
import me.anjoismysign.anjo.sql.SQLHolder;
import me.anjoismysign.anjo.util.SerializableUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
        boolean isNewTable = holder.getDatabase().createTable(getTableName(), getPrimaryKeyName() +
                " VARCHAR(" + getPrimaryKeyLength() + ")," + getCrudableKeyTypeName() +
                " BLOB", getPrimaryKeyName());
        if (isNewTable)
            log("Create table " + getTableName() + " with primary key " + getPrimaryKeyName() +
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
    @Override
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
    public T create(String identification) {
        T crudable = createFunction.apply(identification);
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
        } catch (SQLException exception) {
            logger.singleError(exception.getMessage());
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
        }
        return crudable;
    }

    /**
     * Will attempt to read the Crudable with the given id from the database.
     * If not found/exists, will return null.
     *
     * @param id The id of the Crudable to get
     * @return The Crudable with the given id
     */
    @Nullable
    @Override
    public T readOrNull(String id) {
        return readOrGenerate(id, () -> null);
    }

    /**
     * Will attempt to read the Crudable with the given id from the database.
     * If not found, will create a new instance of the Crudable and register it
     * using the given id.
     *
     * @param id The id of the Crudable to get
     * @return The Crudable with the given id
     */
    @NotNull
    @Override
    public T read(String id) {
        return readOrGenerate(id, () -> create(id));
    }

    private T readOrGenerate(String id, Supplier<T> replacement) {
        ResultSet resultSet = this.holder.getDatabase()
                .selectRowByPrimaryKey(getPrimaryKeyName(), id, getTableName());
        T crudable;
        try {
            if (resultSet.next()) {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                resultSet.close();
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
                if (bytes == null) {
                    log("Record with id " + id + " has no data.");
                    return replacement.get();
                }
                crudable = (T) SerializableUtil.deserialize(bytes);
                log("Read record with id " + id + " successfully.");
                return crudable;
            } else {
                log("Record with id " + id + " does not exist.");
                resultSet.close();
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
                return replacement.get();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return replacement.get();
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
        String id = crudable.getIdentification();
        PreparedStatement statement = this.holder.getDatabase()
                .updateDataSet(getPrimaryKeyName(), getTableName(), crudableKeyTypePrepareStatement());
        try {
            statement.setBytes(1, SerializableUtil.serialize(crudable));
            statement.setString(2, id);
            statement.executeUpdate();
            if (version != 0)
                log("Updated record with id " + id + " to version " + version + ".");
            else
                log("Updated record with id " + id + ".");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                statement.close();
                statement.getConnection().close();
            } catch (SQLException exception) {
                exception.printStackTrace();
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
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                preparedStatement.getConnection().close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * @param biConsumer First parameter is Crudable, second parameter is the version
     */
    public void forEachRecord(Consumer<T> biConsumer) {
        this.holder.getDatabase().selectAllFromDatabase(getTableName(), resultSet -> {
            try {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                T crudable = (T) SerializableUtil.deserialize(bytes);
                log("Read record with id " + crudable.getIdentification() + " successfully.");
                biConsumer.accept(crudable);
            } catch (SQLException exception) {
                exception.printStackTrace();
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
