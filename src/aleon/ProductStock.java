
package aleon;

public class ProductStock extends Product {

    private final long dateTime;
    private final int actualOnStock;

    /**
     * Create constructor
     *
     * @param id id of product.
     * @param name name of product.
     * @param purchaseQuantity quantity that should be bought.
     * @param buyIfLessThan quantity that not should be less than it.
     * @param dateTime the current time.
     * @param actualOnStock available in the warehouse.
     */
    public ProductStock(final int id, final String name, final int purchaseQuantity, final int buyIfLessThan,
            final long dateTime, final int actualOnStock) {
        super(id, name, purchaseQuantity, buyIfLessThan);
        this.dateTime = dateTime;
        this.actualOnStock = actualOnStock;

    }

    /**
     * get the DateTime.
     * 
     * @return dateTime.
     */
    public long getDateTime() {
        return dateTime;
    }

    /**
     * get the count of product in warehouse.
     * 
     * @return actualonStock.
     */
    public int getActualOnStock() {
        return actualOnStock;
    }

}
