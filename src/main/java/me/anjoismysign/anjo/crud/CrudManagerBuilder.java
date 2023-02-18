package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.logger.Logger;

import java.io.File;
import java.util.function.Function;

public class CrudManagerBuilder {
    public static <T extends Crudable> MySQLCrudManager<T> MYSQL(String hostname, int port, String database,
                                                                 String user, String password, String tableName,
                                                                 String primaryKeyName, int primaryKeyLength,
                                                                 String crudableKeyTypeName, Function<String, T> createFunction,
                                                                 Logger logger) {
        return new MySQLCrudManager<>(hostname, port, database, user, password, tableName, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createFunction, logger);
    }

    public static <T extends Crudable> MySQLCrudManager<T> MYSQL(String hostname, int port, String database,
                                                                 String user, String password, String tableName,
                                                                 String primaryKeyName, int primaryKeyLength,
                                                                 String crudableKeyTypeName, Function<String, T> createFunction) {
        return new MySQLCrudManager<>(hostname, port, database, user, password, tableName, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createFunction, null);
    }

    public static <T extends Crudable> SQLiteCrudManager<T> SQLITE(String database, File path, String tableName,
                                                                   String primaryKeyName, int primaryKeyLength,
                                                                   String crudableKeyTypeName, Function<String, T> createFunction,
                                                                   Logger logger) {
        return new SQLiteCrudManager<>(database, path, tableName, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createFunction, logger);
    }

    public static <T extends Crudable> SQLiteCrudManager<T> SQLITE(String database, File path, String table,
                                                                   String primaryKeyName, int primaryKeyLength,
                                                                   String crudableKeyTypeName, Function<String, T> createFunction) {
        return new SQLiteCrudManager<>(database, path, table, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createFunction, null);
    }
}
