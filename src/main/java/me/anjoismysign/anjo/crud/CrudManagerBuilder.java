package me.anjoismysign.anjo.crud;

import me.anjoismysign.anjo.logger.Logger;

import java.io.File;
import java.util.function.Supplier;

public class CrudManagerBuilder {
    public static <T extends Crudable> MySQLManager<T> MYSQL(String hostname, int port, String database,
                                                             String user, String password, String tableName,
                                                             String primaryKeyName, int primaryKeyLength,
                                                             String crudableKeyTypeName, Supplier<T> createSupplier,
                                                             Logger logger) {
        return new MySQLManager<>(hostname, port, database, user, password, tableName, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createSupplier, logger);
    }

    public static <T extends Crudable> MySQLManager<T> MYSQL(String hostname, int port, String database,
                                                             String user, String password, String tableName,
                                                             String primaryKeyName, int primaryKeyLength,
                                                             String crudableKeyTypeName, Supplier<T> createSupplier) {
        return new MySQLManager<>(hostname, port, database, user, password, tableName, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createSupplier, null);
    }

    public static <T extends Crudable> SQLiteCrudManager<T> SQLITE(String database, File path, String tableName,
                                                                   String primaryKeyName, int primaryKeyLength,
                                                                   String crudableKeyTypeName, Supplier<T> createSupplier,
                                                                   Logger logger) {
        return new SQLiteCrudManager<>(database, path, tableName, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createSupplier, logger);
    }

    public static <T extends Crudable> SQLiteCrudManager<T> SQLITE(String database, File path, String table,
                                                                   String primaryKeyName, int primaryKeyLength,
                                                                   String crudableKeyTypeName, Supplier<T> createSupplier) {
        return new SQLiteCrudManager<>(database, path, table, primaryKeyName,
                primaryKeyLength, crudableKeyTypeName, createSupplier, null);
    }
}
