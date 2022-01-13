
package aleon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVConnection1 {

    public void csvFormat() throws SQLException, FileNotFoundException {
        final DBConnection dbConnection = new DBConnection();

        final String query = "SELECT * FROM products";
        try (Connection connection = dbConnection.getConncetion()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {

                    final PrintWriter pw = new PrintWriter(new File("C:\\Users\\tajik\\Product3.csv"));
                    final StringBuilder sb = new StringBuilder();
                    sb.append("id");
                    sb.append(";");
                    sb.append("name");
                    sb.append(";");
                    sb.append("max");
                    sb.append(";");
                    sb.append("min");
                    sb.append("\r\n");
                    while (resultSet.next()) {
                        final int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        final int max = resultSet.getInt("quantity_to_buy");
                        final int min = resultSet.getInt("buy_if_less_than");

                        if (name.indexOf("\"") != -1 || name.indexOf(",") != -1 || name.indexOf(";") != -1) {
                            name = name.replaceAll("\"", "\"\"");
                            sb.append(id);
                            sb.append(";");
                            sb.append("\"");
                            sb.append(name);
                            sb.append("\"");
                            sb.append(max);
                            sb.append(";");
                            sb.append(min);
                            sb.append("\r\n");

                        } else {
                            sb.append(id);
                            sb.append(";");
                            sb.append(name);
                            sb.append(";");
                            sb.append(max);
                            sb.append(";");
                            sb.append(min);
                            sb.append("\r\n");
                        }

                    }
                    pw.write(sb.toString());
                    pw.close();
                    System.out.println("done");

                }
            }
        }
    }

}
