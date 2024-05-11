import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class MenuItem {
    protected String name;
    protected double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String confirmationMessage() {
        return "Item added to your order";
    }
}

class SideDish extends MenuItem {
    public SideDish(String name, double price) {
        super(name, price);
    }

    public String confirmationMessage() {
        return "Side Dish added to your order";
    }
}

class Pizza extends MenuItem {
    public Pizza(String name, double price) {
        super(name, price);
    }

    public String confirmationMessage() {
        return "Pizza added to your order";
    }
}

class Menu {
    private HashMap<Integer, MenuItem> menuItems;

    public Menu() {
        menuItems = new HashMap<>();
    }

    public void addMenuItem(int id, MenuItem menuItem) {
        menuItems.put(id, menuItem);
    }

    public void displayMenu() {
        System.out.println("Pizza Menu:");
        for (HashMap.Entry<Integer, MenuItem> entry : menuItems.entrySet()) {
            if (entry.getValue() instanceof Pizza) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getName() + " - $" + entry.getValue().getPrice());
            }
        }

        System.out.println("Side Dish Menu:");
        for (HashMap.Entry<Integer, MenuItem> entry : menuItems.entrySet()) {
            if (entry.getValue() instanceof SideDish) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getName() + " - $" + entry.getValue().getPrice());
            }
        }
    }

    public MenuItem getMenuItemById(int id) {
        return menuItems.get(id);
    }
}


class Customer {
    private String name;
    private String address;
    private String phone;

    public Customer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}

class Order {
    private Customer customer;
    private ArrayList<MenuItem> pizzas;
    private ArrayList<SideDish> sideDishes;
    private boolean payOnline;
    private String paymentMethod;
    private boolean addAnotherPizza;

    public Order(Customer customer) {
        this.customer = customer;
        pizzas = new ArrayList<>();
        sideDishes = new ArrayList<>();
        addAnotherPizza = false;
    }

    public void addItem(MenuItem item) {
        pizzas.add(item);
    }

    public void setPayOnline(boolean payOnline) {
        this.payOnline = payOnline;
    }

    public boolean isPayOnline() {
        return payOnline;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setAddAnotherPizza(boolean addAnotherPizza) {
        this.addAnotherPizza = addAnotherPizza;
    }

    public boolean isAddAnotherPizza() {
        return addAnotherPizza;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<MenuItem> getItems() {
        return pizzas;
    }

}

public class PizzaDeliverySystem {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.addMenuItem(1, new Pizza("Margherita", 8.99));
        menu.addMenuItem(2, new Pizza("Pepperoni", 9.99));
        menu.addMenuItem(3, new Pizza("Vegetarian", 10.99));
        menu.addMenuItem(4, new Pizza("Chicken", 11.99));
        menu.addMenuItem(5, new Pizza("Chicago Style", 12.99));

        menu.addMenuItem(6, new SideDish("Garlic Bread", 3.99));
        menu.addMenuItem(7, new SideDish("French Fries", 2.99));
        menu.addMenuItem(8, new SideDish("Salad", 4.49));

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name;
        while (true) {
            name = scanner.nextLine();
            if (name.matches(".*[a-zA-Z].*") && name.matches("[a-zA-Z0-9 ]+")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter at least one letter along with numbers");
            }
        }
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        String phone;
        do {
            System.out.print("Enter your phone number: ");
            phone = scanner.nextLine();
            if (!phone.matches("\\d+")) {
                System.out.println("Invalid input. Please enter numbers only for the phone number.");
            } else {
                break;
            }
        } while (true);

        Customer customer = new Customer(name, address, phone);
        Order order = new Order(customer);

        do {
            menu.displayMenu();
            System.out.print("Select Something (enter ID): ");
            int pizzaId = scanner.nextInt();
            MenuItem selectedItem = menu.getMenuItemById(pizzaId);
            if (selectedItem != null) {
                order.addItem(selectedItem);
                System.out.println(selectedItem.confirmationMessage());
            } else {
                System.out.println("Invalid pizza ID.");
            }

            String addAnother;
            do {
                System.out.print("Do you want to add anything else? (Y/N): ");
                addAnother = scanner.next();

                if (addAnother.equalsIgnoreCase("Y")) {
                    order.setAddAnotherPizza(true);
                    break;
                } else if (addAnother.equalsIgnoreCase("N")) {
                    order.setAddAnotherPizza(false);
                    break;
                } else {
                    System.out.println("Error: Wrong input. Please enter Y or N.");
                }
            } while (true);

        } while (order.isAddAnotherPizza());

        String payOption;
        do {
            System.out.print("Pay online? (Y/N): ");
            payOption = scanner.next();
            if (payOption.equalsIgnoreCase("Y")) {
                order.setPayOnline(true);
                boolean validInput = false;
                while (!validInput) {
                    System.out.print("Select payment method (D for Debit Card/G for Google Pay): ");
                    String paymentMethod = scanner.next().toUpperCase(); // Convert to uppercase for case-insensitive comparison
                    int asciiValue = (int) paymentMethod.charAt(0); // Convert first character to ASCII

                    switch (asciiValue) {
                        case 68: // ASCII value for 'D'
                            System.out.println("Payment will be processed online via Debit Card.");
                            validInput = true;
                            break;
                        case 71: // ASCII value for 'G'
                            System.out.println("Payment will be processed online via Google Pay.");
                            validInput = true;
                            break;
                        default:
                            System.out.println("Error: Wrong input. Please enter D or G.");
                            break;
                    }
                }
            } else if (payOption.equalsIgnoreCase("N")) {
                order.setPayOnline(false);
                System.out.println("Please pay in person upon delivery.");
                break;
            } else {
                System.out.println("Error: Wrong input. Please enter Y or N.");
            }
        } while (true);

        

        double totalAmount = order.getItems().stream().mapToDouble(MenuItem::getPrice).sum();
        System.out.println("Total Amount: $" + totalAmount);

        System.out.println("Thank you for your order!");
        System.out.println("Delivery Address: " + customer.getAddress());
        System.out.println("Contact Number: " + customer.getPhone());
        for (MenuItem pizza : order.getItems()) {
        }
    }
}
