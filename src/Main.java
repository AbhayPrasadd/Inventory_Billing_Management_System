import models.BillItem;
import models.Product;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductService productService = new ProductService();
        BillingService billingService = new BillingService();

        while (true) {
            System.out.println("\n📋 Inventory & Billing System");
            System.out.println("1. Add Product");
            System.out.println("2. Edit Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Products");
            System.out.println("5. Create Bill");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Adding new product
                    System.out.print("Product name: ");
                    String name = scanner.nextLine();

                    System.out.print("Price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Stock quantity: ");
                    int stock = scanner.nextInt();

                    productService.addProduct(name, price, stock);
                    break;

                case 2:
                    // Editing existing product
                    System.out.print("Enter Product ID to edit: ");
                    int editId = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("New name: ");
                    String newName = scanner.nextLine();

                    System.out.print("New price: ");
                    double newPrice = scanner.nextDouble();

                    System.out.print("New stock quantity: ");
                    int newStock = scanner.nextInt();

                    productService.editProduct(editId, newName, newPrice, newStock);
                    break;

                case 3:
                    // Deleting a product
                    System.out.print("Enter Product ID to delete: ");
                    int delId = scanner.nextInt();
                    productService.deleteProduct(delId);
                    break;

                case 4:
                    // Viewing all products
                    List<Product> products = productService.getAllProducts();
                    System.out.println("\nAvailable Products:");
                    for (Product p : products) {
                        System.out.printf("ID: %d | %s | ₹%.2f | Stock: %d\n", 
                            p.getProductId(), p.getName(), p.getPrice(), p.getStockQty());
                    }
                    break;

                case 5:
                    // Creating a bill
                    System.out.print("Customer name: ");
                    String customer = scanner.nextLine();

                    List<BillItem> billItems = new ArrayList<>();

                    while (true) {
                        System.out.print("Enter product ID (0 to finish): ");
                        int prodId = scanner.nextInt();

                        if (prodId == 0) break;

                        Product selectedProduct = productService.getProductById(prodId);
                        if (selectedProduct == null) {
                            System.out.println(" Invalid product ID.");
                            continue;
                        }

                        System.out.print("Quantity: ");
                        int quantity = scanner.nextInt();

                        if (quantity > selectedProduct.getStockQty()) {
                            System.out.println(" Not enough stock.");
                            continue;
                        }

                        billItems.add(new BillItem(
                            selectedProduct.getProductId(),
                            selectedProduct.getName(),
                            quantity,
                            selectedProduct.getPrice()
                        ));
                    }

                    if (!billItems.isEmpty()) {
                        billingService.createBill(customer, billItems);
                    } else {
                        System.out.println(" No items in the bill.");
                    }
                    break;

                case 0:
                    System.out.println(" Goodbye!");
                    return;

                default:
                    System.out.println(" Invalid choice, please try again.");
            }
        }
    }
}
