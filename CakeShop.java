import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class CakeShop {
    static final int MAX_ORDERS = 100;
    static boolean[] orders = new boolean[MAX_ORDERS];
    static HashMap<String, Integer> priceMap = new HashMap<>();
    static HashMap<String, Integer> deliveryChargeMap = new HashMap<>();
    static HashMap<String, String> cakeMap = new HashMap<>();
    static HashMap<String, String> users = new HashMap<>();

    static {
        priceMap.put("Chocolate", 500);
        priceMap.put("Vanilla", 600);
        priceMap.put("Red Velvet", 700);

        deliveryChargeMap.put("Bantakal", 50);
        deliveryChargeMap.put("Udupi", 100);
        deliveryChargeMap.put("Kundapura", 150);

        cakeMap.put("Chocolate", "Choco Crunch Cake");
        cakeMap.put("Vanilla", "Vanilla Dream Cake");
        cakeMap.put("Red Velvet", "Velvet Bliss Cake");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/CakeShopDB","root","Rockmanishrao03");
    
        System.out.println(con);
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int choice;
        do {
            System.out.println("==== Welcome to CakeShop ====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> login(sc);
                case 2 -> register(sc);
                case 3 -> System.out.println("Thank you for visiting CakeShop!");
                default -> System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 3);
    }

    static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println("Login successful! Welcome, " + username + "!");
            menu(scanner);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    static void register(Scanner scanner) {
    System.out.print("Enter username: ");
    String username = scanner.next();

    System.out.print("Enter password: ");
    String password = scanner.next();

    System.out.print("Confirm password: ");
    String confirmPassword = scanner.next();

    if (!password.equals(confirmPassword)) {
        System.out.println("Passwords do not match. Registration failed.");
        return;
    }

    System.out.print("Enter contact number: ");
    String contactNumber = scanner.next();

    String email;
    do {
        System.out.print("Enter email: ");
        email = scanner.next();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email.");
        }
    } while (!isValidEmail(email));

    // Call insertData method to insert data into the database
    DatabaseOperations.insertData(username, password, contactNumber, email);

    users.put(username, password);
    System.out.println("Registration successful! You can now login.");
}


    static void menu(Scanner scanner) {
        int choice;
        do {
            System.out.println("==== CakeShop Menu ====");
            System.out.println("1. Order a Cake");
            System.out.println("2. Cancel an Order");
            System.out.println("3. Check Cake Availability");
            System.out.println("4. Get Cake Name from Cake Type");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> orderCake(scanner);
                case 2 -> cancelOrder(scanner);
                case 3 -> checkAvailability();
                case 4 -> getCakeName(scanner);
                case 5 -> System.out.println("Logged out. Thank you for visiting CakeShop!");
                default -> System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 5);
    }

    static void orderCake(Scanner scanner) {
        System.out.println("Select Cake Type:");
        System.out.println("1. Chocolate");
        System.out.println("2. Vanilla");
        System.out.println("3. Red Velvet");
        System.out.print("Enter your choice: ");
        int cakeChoice = scanner.nextInt();

        String cakeType;
        switch (cakeChoice) {
            case 1 -> cakeType = "Chocolate";
            case 2 -> cakeType = "Vanilla";
            case 3 -> cakeType = "Red Velvet";
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        System.out.println("Enter Weight (in kg): ");
        double weight = scanner.nextDouble();

        System.out.println("Select Delivery Location:");
        System.out.println("1. Bantakal");
        System.out.println("2. Udupi");
        System.out.println("3. Kundapura");
        System.out.print("Enter your choice: ");
        int locationChoice = scanner.nextInt();

        String location;
        switch (locationChoice) {
            case 1 -> location = "Bantakal";
            case 2 -> location = "Udupi";
            case 3 -> location = "Kundapura";
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        System.out.println("Select Payment Option:");
        System.out.println("1. Online Payment");
        System.out.println("2. Cash on Delivery");
        System.out.println("3. Card Payment");
        System.out.println("4. Coupons");
        System.out.print("Enter your choice: ");
        int paymentChoice = scanner.nextInt();

        String paymentOption;
        switch (paymentChoice) {
            case 1 -> paymentOption = "Online Payment";
            case 2 -> paymentOption = "Cash on Delivery";
            case 3 -> paymentOption = "Card Payment";
            case 4 -> paymentOption = "Coupons";
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        int orderNumber = new Random().nextInt(MAX_ORDERS) + 1;
        orders[orderNumber - 1] = true;

        int totalCost = priceMap.get(cakeType) * (int) weight + deliveryChargeMap.get(location);

        System.out.println("Order placed successfully!");
        System.out.println("Order Number: " + orderNumber);
        System.out.println("Cake Type: " + cakeType);
        System.out.println("Cake Name: " + cakeMap.get(cakeType));
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Delivery Location: " + location);
        System.out.println("Payment Option: " + paymentOption);
        System.out.println("Total Price: " + totalCost + " INR");

        // Simulate cake delivery after one hour
        new Thread(() -> {
            try {
                Thread.sleep(3600000); // 1 hour in milliseconds
                System.out.println("Cake delivered for Order Number " + orderNumber);
                System.out.println("Please rate our service (1-5): ");
                int rating = scanner.nextInt();
                System.out.println("Thank you for your review!");
            } catch (InterruptedException e) {
            }
        }).start();
    }

    static void cancelOrder(Scanner scanner) {
        System.out.print("Enter the order number to cancel the order: ");
        int orderNumber = scanner.nextInt();

        if (orderNumber < 1 || orderNumber > MAX_ORDERS) {
            System.out.println("Invalid order number.");
        } else if (!orders[orderNumber - 1]) {
            System.out.println("No order found for order number " + orderNumber + ".");
        } else {
            orders[orderNumber - 1] = false;
            System.out.println("Order " + orderNumber + " has been canceled.");
        }
    }

    static void checkAvailability() {
        System.out.println("Cake availability:");
        for (String cakeType : cakeMap.keySet()) {
            System.out.println("Cake Type: " + cakeType);
            System.out.println("Cake Name: " + cakeMap.get(cakeType));
            System.out.println("Price: " + priceMap.get(cakeType) + " INR per kg");
            System.out.println("-------------------------------");
        }
    }

    static void getCakeName(Scanner scanner) {
        System.out.print("Enter the cake type: ");
        String cakeType = scanner.next();

        String cakeName = cakeMap.get(cakeType);
        if (cakeName != null) {
            System.out.println("Cake name for cake type " + cakeType + " is " + cakeName);
        } else {
            System.out.println("Cake name not found for cake type " + cakeType);
        }
    }

    static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        return Pattern.matches(regex, email);
    }
}
