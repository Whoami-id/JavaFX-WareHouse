
package aleon;

public class Product {
    // create Variable
    private final int id;
    private final String name;
    private final int purchaseQuantity;
    private final int buyIfLessThan;

    /**
     * Create the Constructor
     *
     * @param id id of product.
     * @param name name of product.
     * @param purchaseQuantity quantity that should be bought.
     * @param buyIfLessThan quantity that not should be less than it.
     */
    public Product(final int id, final String name, final int purchaseQuantity, final int buyIfLessThan) {
        this.id = id;
        this.name = name;
        this.purchaseQuantity = purchaseQuantity;
        this.buyIfLessThan = buyIfLessThan;
    }

    /**
     * get the id of product.
     * 
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * get the name of product.
     * 
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * get quantity to buy.
     * 
     * @return purchaseQuantity.
     */
    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    /**
     * get the minimal Quantity.
     * 
     * @return buyIfLessThan.
     */
    public int getBuyIfLessThan() {
        return buyIfLessThan;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", purchaseQuantity=" + purchaseQuantity + ", buyIfLessThan="
                + buyIfLessThan + "]";
    }

}
