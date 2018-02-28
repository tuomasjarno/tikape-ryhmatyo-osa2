package tikape.tikaperyhmatyo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String dbAddress;

    public Database(String dbAddress) {
        this.dbAddress = dbAddress;
    }

    public Connection getConnection() throws SQLException {
        if (this.dbAddress != null && this.dbAddress.length() > 0) {
            return DriverManager.getConnection(this.dbAddress);
        }

        return DriverManager.getConnection("jdbc:sqlite:smoothies.db");
    }

}
