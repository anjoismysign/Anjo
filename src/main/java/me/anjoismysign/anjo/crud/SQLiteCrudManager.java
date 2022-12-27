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
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class SQLiteCrudManager<T extends Crudable> implements SQLCrudManager<T> {
    private SQLHolder holder;
    private final String database, tableName, primaryKeyName, crudableKeyTypeName;
    private final int primaryKeyLength;
    private final File path;
    private final Supplier<T> createSupplier;
    private final Optional<Logger> logger;

    protected SQLiteCrudManager(String database, File path, String tableName, String primaryKeyName,
                                int primaryKeyLength, String crudableKeyTypeName, Supplier<T> createSupplier,
                                Logger logger) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.primaryKeyLength = primaryKeyLength;
        this.crudableKeyTypeName = crudableKeyTypeName;
        this.createSupplier = createSupplier;
        this.database = database;
        this.path = path;
        this.logger = Optional.ofNullable(logger);
        load();
    }

    public void load() {
        holder = new SQLHolder(database, path);
        holder.getDatabase().createTable(getTableName(), getPrimaryKeyName() +
                " VARCHAR(" + getPrimaryKeyLength() + ")," + getCrudableKeyTypeName() +
                " BLOB", getPrimaryKeyName());
    }

    public void reload() {
        if (logger.isPresent())
            logger.get().log("Reloading database...");
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
        return this.holder.getDatabase().exists(getTableName(),
                getPrimaryKeyName(), id);
    }

    /**
     * Creates a new instance of the AnjoCrudable and registers it in the database.
     *
     * @return The new instance of the AnjoCrudable.
     */
    @Override
    public T createAndRegister() {
        T anjoCrudable = create();
        String id = anjoCrudable.getIdentification();
        Connection connection = null;
//        String sql = "INSERT IGNORE INTO " + TABLE_NAME(); //MySQL
        String sql = "INSERT OR IGNORE INTO " + getTableName();
        try {
            connection = this.holder.getDatabase().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql +
                    " (" + getPrimaryKeyName() + ") VALUES (?)");
            try {
                if (!exists(id)) {
                    preparedStatement.setString(1, id);
                    preparedStatement.executeUpdate();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                    preparedStatement.getConnection().close();
                }
            } catch (Throwable throwable) {
                if (preparedStatement != null)
                    try {
                        preparedStatement.close();
                        preparedStatement.getConnection().close();
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
        return anjoCrudable;
    }

    /**
     * @return a new instance of the AnjoCrudable
     */
    @Override
    public T create() {
        return createSupplier.get();
    }

    /**
     * @param id The id of the AnjoCrudable to get
     * @return The AnjoCrudable with the given id
     */
    @Override
    public T read(String id) {
        ResultSet resultSet = this.holder.getDatabase()
                .selectRowByPrimaryKey(getPrimaryKeyName(), id, getTableName());
        T anjoCrudable;
        try {
            if (resultSet.next()) {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                resultSet.close();
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
                @SuppressWarnings("unchecked") UpdatableSerializable<T> updatableSerializable = UpdatableSerializable.deserialize(bytes);
                anjoCrudable = updatableSerializable.getValue();
                return anjoCrudable;
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
     * @param anjoCrudable The AnjoCrudable to be updated (defaults version to 0)
     */
    @Override
    public void update(T anjoCrudable) {
        update(anjoCrudable, 0);
    }

    /**
     * Updates the database with the given AnjoCrudable and version
     *
     * @param anjoCrudable The AnjoCrudable to update
     * @param version      The version to update to
     */
    @Override
    public void update(T anjoCrudable, int version) {
        UpdatableSerializableHandler<T> handler = newUpdatable(anjoCrudable, 0);
        String id = anjoCrudable.getIdentification();
        PreparedStatement statement = this.holder.getDatabase()
                .updateDataSet(getPrimaryKeyName(), getTableName(), crudableKeyTypePrepareStatement());
        try {
            statement.setBytes(1, handler.serialize());
            statement.setString(2, id);
            statement.executeUpdate();
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
     * @param id The id of the AnjoCrudable to delete
     */
    @Override
    public void delete(String id) {
        PreparedStatement preparedStatement = this.holder.getDatabase()
                .delete(getTableName(), getPrimaryKeyName());
        try {
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
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
     * @param biConsumer First parameter is AnjoCrudable, second parameter is the version
     */
    public void forEachRecord(BiConsumer<T, Integer> biConsumer) {
        this.holder.getDatabase().selectAllFromDatabase(getTableName(), resultSet -> {
            try {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                @SuppressWarnings("unchecked") UpdatableSerializable<T> updatableSerializable = UpdatableSerializable.deserialize(bytes);
                T anjoCrudable = updatableSerializable.getValue();
                biConsumer.accept(anjoCrudable, updatableSerializable.getVersion());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Optional<Logger> getLogger() {
        return logger;
    }
}
