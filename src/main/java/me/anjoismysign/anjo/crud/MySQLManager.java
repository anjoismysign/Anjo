package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.entities.UpdatableSerializable;
import me.anjoismysign.anjo.entities.UpdatableSerializableHandler;
import me.anjoismysign.anjo.logger.Logger;
import me.anjoismysign.anjo.sql.SQLHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MySQLManager<T extends Crudable> implements SQLCrudManager<T> {
    private SQLHolder holder;
    private final String hostname, database, user, password, tableName, primaryKeyName, crudableKeyTypeName;
    private final int primaryKeyLength, port;
    private final Function<String, T> createFunction;
    private final Logger logger;

    protected MySQLManager(String hostName, int port, String database, String user, String password, String tableName,
                           String primaryKeyName, int primaryKeyLength, String crudableKeyTypeName,
                           Function<String, T> createFunction, Logger logger) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.primaryKeyLength = primaryKeyLength;
        this.crudableKeyTypeName = crudableKeyTypeName;
        this.createFunction = createFunction;
        this.hostname = hostName;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.logger = logger;
        load();
    }

    public void load() {
        holder = new SQLHolder(hostname, port, database, user, password, logger);
        holder.getDatabase().createTable(getTableName(), getPrimaryKeyName() +
                " VARCHAR(" + getPrimaryKeyLength() + ")," + getCrudableKeyTypeName() +
                " BLOB", getPrimaryKeyName());
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
        return this.holder.getDatabase().exists(getTableName(),
                getPrimaryKeyName(), id);
    }

    /**
     * Updates the database with the given Crudable and version
     *
     * @param Crudable The Crudable to update
     * @param version  The version to update to
     */
    @Override
    public void update(T Crudable, int version) {
        UpdatableSerializableHandler<T> handler = newUpdatable(Crudable, 0);
        String id = Crudable.getIdentification();
        PreparedStatement statement = this.holder.getDatabase()
                .updateDataSet(getPrimaryKeyName(), getTableName(), getCrudableKeyTypeName());
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
     * Creates a new instance of the Crudable and registers it in the database
     * using the given identification.
     *
     * @param identification The identification to register the Crudable with.
     * @return The new instance of the Crudable.
     */
    @Override
    public T createAndRegister(String identification) {
        T Crudable = create(identification);
        Connection connection = null;
//        String sql = "INSERT IGNORE INTO " + TABLE_NAME(); //MySQL
        String sql = "INSERT OR IGNORE INTO " + getTableName();
        try {
            connection = this.holder.getDatabase().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql +
                    " (" + getPrimaryKeyName() + ") VALUES (?)");
            try {
                if (!exists(identification)) {
                    preparedStatement.setString(1, identification);
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
        return Crudable;
    }

    /**
     * @param identification The identification that the Crudable should be created with
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
        T Crudable;
        try {
            if (resultSet.next()) {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                resultSet.close();
                resultSet.getStatement().close();
                resultSet.getStatement().getConnection().close();
                @SuppressWarnings("unchecked") UpdatableSerializable<T> updatableSerializable = UpdatableSerializable.deserialize(bytes);
                Crudable = updatableSerializable.getValue();
                return Crudable;
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
     * @param Crudable The Crudable to be updated
     */
    @Override
    public void update(T Crudable) {
        update(Crudable, 0);
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

    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * @param biConsumer First parameter is Crudable, second parameter is the version
     */
    public void forEachRecord(BiConsumer<T, Integer> biConsumer) {
        this.holder.getDatabase().selectAllFromDatabase(getTableName(), resultSet -> {
            try {
                byte[] bytes = resultSet.getBytes(getCrudableKeyTypeName());
                @SuppressWarnings("unchecked") UpdatableSerializable<T> updatableSerializable = UpdatableSerializable.deserialize(bytes);
                T Crudable = updatableSerializable.getValue();
                biConsumer.accept(Crudable, updatableSerializable.getVersion());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
