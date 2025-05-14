package models;

public class Product {
    private int productId;
    private String name;
    private double price;
    private int stockQty;

    public Product(int productId, String name, double price, int stockQty) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
    }

    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQty() { return stockQty; }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }
}
