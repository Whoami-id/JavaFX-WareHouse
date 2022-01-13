
package aleon;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.opencsv.CSVWriter;

public class CSVConnection2 {

    public void CSVFormating() throws SQLException, IOException {
        final DBConnection dbConnection = new DBConnection();
        final String query = "SELECT * FROM products";
        try (Connection connection = dbConnection.getConncetion()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {

                    final CSVWriter writer = new CSVWriter(new FileWriter("C:\\Users\\tajik\\Product2.csv"));
                    final ResultSetMetaData mData = resultSet.getMetaData();
                    mData.getColumnName(1);
                    final String line1[] = { mData.getColumnName(1), mData.getColumnName(2), mData.getColumnName(3),
                            mData.getColumnName(4), };
                    writer.writeNext(line1);
                    final String data[] = new String[4];
                    while (resultSet.next()) {
                        data[0] = new Integer(resultSet.getInt("id")).toString();
                        data[1] = resultSet.getString("name");
                        data[2] = new Integer(resultSet.getInt("quantity_to_buy")).toString();
                        data[3] = new Integer(resultSet.getInt("buy_if_less_than")).toString();
                        writer.writeNext(data);

                    }

                    writer.flush();
                    writer.close();

                }
            }

        }

    }

}
