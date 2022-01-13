
package aleon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBStatement {

    private final DBConnection dbConnection = new DBConnection();

    /**
     * tests if Connection has been established.
     *
     * @return true if it connected, false if connection failed.
     * @throws SQLException if it can not to connect.
     */
    public boolean checkConnection() throws SQLException {
        try (Connection connection = dbConnection.getConncetion()) {
            if (connection != null) {
                return true;
            } else {
                return false;

            }
        }

    }

    /**
     * inserts Products.
     *
     * @param dateTime get the local time.
     * @param count get the count of product.
     * @param id get the id of product.
     * @throws SQLException if the insertion failed.
     */
    public void insertData(final long dateTime, final int count, final int id) throws SQLException {

        final String insert = "INSERT INTO states (date_of_last_update, actual_on_stock, id_product) VALUES ("
                + dateTime + ", " + count + ", " + id + " )";
        query(insert);

    }

    /**
     * creates the query to database.
     *
     * @param query which is send to database.
     * @throws SQLException query can not be send.
     */
    private void query(final String query) throws SQLException {

        try (Connection connection = dbConnection.getConncetion(); //
                Statement statement = connection.createStatement()) {
            if (statement != null) {
                statement.execute(query);
            }
        }

    }

    /**
     * gets Products from table in database.
     *
     * @return list that includes Products.
     * @throws SQLException if can not connect to database.
     */
    public List<Product> getProducts() throws SQLException {
        final List<Product> products = new ArrayList<>();
        final String data = "SELECT id, name, quantity_to_buy, buy_if_less_than FROM products";

        // try-with-resources
        try (Connection connection = dbConnection.getConncetion(); //
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(data)) {
                while (resultSet.next()) {
                    final int id = resultSet.getInt("id");
                    final String name = resultSet.getString("name");
                    final int purchaseQuantity = resultSet.getInt("quantity_to_buy");
                    final int buyIfLessThan = resultSet.getInt("buy_if_less_than");
                    final Product product = new Product(id, name, purchaseQuantity, buyIfLessThan);
                    products.add(product);
                }
            }
        }

        return products;

    }

    /**
     * adds new Products.
     *
     * @param name name of product.
     * @param purchaseQuantity quantity that should be bought.
     * @param buyIfLessThan quantity that not should be less than it.
     * @throws SQLException if insertion not works.
     */
    public void insrtNewProduct(final String name, final int purchaseQuantity, final int buyIfLessThan)
            throws SQLException {
        final String insertProduct = "INSERT INTO products (name, quantity_to_buy, buy_if_less_than) VALUES  ('" + name
                + "', " + purchaseQuantity + ", " + buyIfLessThan + " )";
        query(insertProduct);
    }

    /**
     * shows products in the table.
     *
     * @return list that includes Products.
     * @throws SQLif can not connect to database.
     */
    public List<ProductStock> showProduct() throws SQLException {
        final List<ProductStock> productsForTable = new ArrayList<>();

        final String data = " SELECT * FROM ( "
                + " select id, name, quantity_to_buy, buy_if_less_than from products ) t1 LEFT JOIN "
                + "  (  SELECT a.id_product, a.date_of_last_update, a.actual_on_stock FROM states a "
                + " INNER JOIN (  SELECT id_product, MAX(date_of_last_update) date_of_last_update "
                + " FROM states GROUP BY id_product"
                + " ) b ON a.id_product = b.id_product AND a.date_of_last_update = b.date_of_last_update ) t2 "
                + "ON (t1.id = t2.id_product); ";
        // checks the Connection.
        try (Connection connection = dbConnection.getConncetion(); //
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(data)) {
                while (resultSet.next()) {
                    final int id = resultSet.getInt("id");
                    final String name = resultSet.getString("name");
                    final int purchaseQuantity = resultSet.getInt("quantity_to_buy");
                    final int buyIfLessThan = resultSet.getInt("buy_if_less_than");
                    final long dateTime = resultSet.getLong("date_of_last_update");
                    final int actualOnStock = resultSet.getInt("actual_on_stock");
                    final ProductStock productStock = new ProductStock(id, name, purchaseQuantity, buyIfLessThan,
                            dateTime, actualOnStock);
                    productsForTable.add(productStock);

                }
            }
        }

        return productsForTable;

    }

    /**
     * deletes Products.
     */
    public void deleteProduct(final Product product) throws SQLException {

        final String queryToStates = "DELETE FROM  states WHERE  id_product = " + product.getId();
        query(queryToStates);
        final String queryToProduct = "DELETE FROM  products WHERE  id = " + product.getId();
        query(queryToProduct);

    }

}
