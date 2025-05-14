package models;

public class BillItem {
    public int productId;
    public String productName;
    public int quantity;
    public double price;
    public double lineTotal;

    public BillItem(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.lineTotal = quantity * price;
    }
}
