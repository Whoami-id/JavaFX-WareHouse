
package aleon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    /**
     * crates the connection to database.
     *
     * @return connection to database.
     * @throws SQLException if connection failed.
     */
    public Connection getConncetion() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/products_database?useSSL=false", "janatan",
                "aleon123");

    }

}
