import models.*;
import java.sql.*;
import java.util.*;

public class BillingService {
    ProductService ps = new ProductService();

    public void createBill(String customerName, List<BillItem> items) {
        double total = 0;
        for (BillItem item : items) total += item.lineTotal;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement insertBill = conn.prepareStatement(
                "INSERT INTO bills (customer_name, total_amount) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertBill.setString(1, customerName);
            insertBill.setDouble(2, total);
            insertBill.executeUpdate();

            ResultSet keys = insertBill.getGeneratedKeys();
            if (keys.next()) {
                int billId = keys.getInt(1);

                for (BillItem item : items) {
                    PreparedStatement insertItem = conn.prepareStatement(
                        "INSERT INTO bill_items (bill_id, product_id, quantity, line_total) VALUES (?, ?, ?, ?)");
                    insertItem.setInt(1, billId);
                    insertItem.setInt(2, item.productId);
                    insertItem.setInt(3, item.quantity);
                    insertItem.setDouble(4, item.lineTotal);
                    insertItem.executeUpdate();

                    Product prod = ps.getProductById(item.productId);
                    ps.updateStock(item.productId, prod.getStockQty() - item.quantity);
                }

                conn.commit();
                System.out.println("🧾 Receipt for " + customerName);
                for (BillItem item : items) {
                    System.out.printf("- %s | %d x %.2f = %.2f\n", item.productName, item.quantity, item.price, item.lineTotal);
                }
                System.out.printf("Total: ₹%.2f\n", total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
