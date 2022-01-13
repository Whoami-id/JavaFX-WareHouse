
package aleon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVConnection {

    public void csvFormat() throws SQLException {
        try {
            final DBConnection dbConnection = new DBConnection();
            final PrintWriter pw = new PrintWriter(new File("C:\\Users\\tajik\\Product.csv"));
            final StringBuilder sb = new StringBuilder();
            sb.append("id");
            sb.append(";");
            sb.append("name");
            sb.append(";");
            sb.append("max");
            sb.append(";");
            sb.append("min");
            sb.append("\n");

            final String query = "SELECT * FROM products";
            try (Connection connection = dbConnection.getConncetion()) {
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(query)) {

                        while (resultSet.next()) {
                            sb.append(resultSet.getInt("id"));
                            sb.append(";");
                            sb.append(resultSet.getString("name"));
                            sb.append(";");
                            sb.append(resultSet.getInt("quantity_to_buy"));
                            sb.append(";");
                            sb.append(resultSet.getInt("buy_if_less_than"));
                            sb.append("\n");

                        }

                        pw.write(sb.toString());
                        pw.close();

                    }
                }
            }

        } catch (final FileNotFoundException ex) {

        }

    }

}
