
/*package org.route;

import Config.AppConfig;
import Exceptions.StoreException;
import org.route.model.Customer;
import org.route.model.Governorate;
import org.route.model.Order;
import org.route.model.ShoppingCart;
import org.route.service.CheckoutService;
import org.route.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

        public class Main {
            public static void main(String[] args) {
                try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {

                    Scanner input = new Scanner(System.in);
                    ProductService productService = context.getBean(ProductService.class);
                    CheckoutService checkoutService = context.getBean(CheckoutService.class);

                    Map<Integer, ShoppingCart> carts = new HashMap<>();
                    int cartCounter = 1;

                    boolean running = true;
                    while (running) {
                        System.out.println("\n========== NILEMART ==========");
                        System.out.println("1. Show products");
                        System.out.println("2. Create a cart");
                        System.out.println("3. Add a product to a cart");
                        System.out.println("4. Show a cart");
                        System.out.println("5. Check out a cart");
                        System.out.println("0. Exit");
                        System.out.print("Choose an option: ");

                        try {
                            int choice = Integer.parseInt(input.nextLine());

                            switch (choice) {

                                //1. Show products
                                case 1 -> productService.displayProducts();

                                //2. Create a cart
                                case 2 -> {
                                    ShoppingCart newCart = context.getBean(ShoppingCart.class);
                                    newCart.setCartNumber(cartCounter);
                                    carts.put(cartCounter, newCart);
                                    System.out.println("Cart created successfully with ID: " + cartCounter);
                                    cartCounter++;
                                }
                                //3. Add a product to a cart
                                case 3 -> {
                                    System.out.print("Enter Cart ID: ");
                                    int cartId = Integer.parseInt(input.nextLine());
                                    ShoppingCart cartToAdd = carts.get(cartId);
                                    if (cartToAdd == null) {
                                        throw new StoreException("Cart not found!");
                                    }
                                    System.out.print("Enter Product SKU: ");
                                    String sku = input.nextLine();
                                    System.out.print("Enter Quantity: ");
                                    int qty = Integer.parseInt(input.nextLine());

                                    cartToAdd.addItem(sku, qty);
                                    System.out.println("Product added to cart successfully.");
                                }

                                //4. Show a cart
                                case 4 -> {
                                    System.out.print("Enter Cart ID: ");
                                    int cartId = Integer.parseInt(input.nextLine());
                                    ShoppingCart cartToShow = carts.get(cartId);
                                    if (cartToShow == null) {
                                        throw new StoreException("Cart not found!");
                                    }
                                    System.out.println("Cart Items: " + cartToShow.getLines());
                                }

                                //5. Check out a cart
                                case 5 -> {
                                    System.out.print("Enter Cart ID to checkout: ");
                                    int checkId = Integer.parseInt(input.nextLine());
                                    ShoppingCart cartToCheckout = carts.get(checkId);
                                    if (cartToCheckout == null) {
                                        throw new StoreException("Cart not found!");
                                    }
                                    System.out.print("Enter Governorate (e.g., Cairo, Giza): ");
                                    String govInput = input.nextLine().trim();
                                    Governorate gov = Governorate.valueOf(govInput.substring(0, 1).toUpperCase() + govInput.substring(1).toLowerCase());
                                    if (cartToCheckout.getCustomer() == null) {
                                        Customer guestCustomer = new Customer("Guest User","01225426951","guest@nilemart.com");
                                        cartToCheckout.setCustomer(guestCustomer);
                                    }
                                    Order order = checkoutService.placeOrder(cartToCheckout, gov);
                                    System.out.println("Order placed successfully! Order Number: " + order.orderNumber());
                                }

                                case 0 -> {
                                    running = false;
                                    System.out.println("Exiting application...");
                                }

                                default -> throw new StoreException("Invalid input! please try again.");
                            }

                        } catch (StoreException e) {
                            System.out.println("[Error] " + e.getMessage());

                        } catch (Exception e) {
                            System.out.println("[Error] Invalid input format. Please try again.");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


 */







