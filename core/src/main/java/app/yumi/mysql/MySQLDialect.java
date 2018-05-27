package app.yumi.mysql;

import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.MySQLStorageEngine;

public class MySQLDialect extends MySQL57Dialect {

  @Override
  protected MySQLStorageEngine getDefaultMySQLStorageEngine() {
    return InnoDBStorageEngine.INSTANCE;
  }

  @Override
  public String getTableTypeString() {
    return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
  }
}
