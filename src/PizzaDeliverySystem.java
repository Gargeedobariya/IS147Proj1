import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Pizza {
    private String name;
    private double price;

    public Pizza(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class SideDish {
    private String name;
    private double price;

    public SideDish(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Menu {
    private HashMap<Integer, Pizza> pizzas;
    private HashMap<Integer, SideDish> sideDishes;

    public Menu() {
        pizzas = new HashMap<>();
        sideDishes = new HashMap<>();
    }

    public void addPizza(int id, Pizza pizza) {
        pizzas.put(id, pizza);
    }

    public void addSideDish(int id, SideDish sideDish) {
        sideDishes.put(id, sideDish);
    }

    public void displayMenu() {
        System.out.println("Pizza Menu:");
        for (int id : pizzas.keySet()) {
            Pizza pizza = pizzas.get(id);
            System.out.println(id + ". " + pizza.getName() + " - $" + pizza.getPrice());
        }
        System.out.println("Do the following:");
        for (int id : sideDishes.keySet()) {
            SideDish sideDish = sideDishes.get(id);
            System.out.println(id + ". " + sideDish.getName() + " - $" + sideDish.getPrice());
        }
    }

    public Pizza getPizzaById(int id) {
        return pizzas.get(id);
    }

    public SideDish getSideDishById(int id) {
        return sideDishes.get(id);
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
    private ArrayList<Pizza> pizzas;
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

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void addSideDish(SideDish sideDish) {
        sideDishes.add(sideDish);
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

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public ArrayList<SideDish> getSideDishes() {
        return sideDishes;
    }
}

public class PizzaDeliverySystem {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.addPizza(1, new Pizza("Margherita", 8.99));
        menu.addPizza(2, new Pizza("Pepperoni", 9.99));
        menu.addPizza(3, new Pizza("Vegetarian", 10.99));
        menu.addPizza(4, new Pizza("Chicken", 11.99));
        menu.addPizza(5, new Pizza("Chicago Style", 12.99));

        menu.addPizza(6, new Pizza("Garlic Bread", 3.99));
        menu.addPizza(7, new Pizza("French Fries", 2.99));
        menu.addPizza(8, new Pizza("Salad", 4.49));

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();

        Customer customer = new Customer(name, address, phone);
        Order order = new Order(customer);

        do {
            menu.displayMenu();
            System.out.print("Select Something (enter ID): ");
            int pizzaId = scanner.nextInt();
            Pizza selectedPizza = menu.getPizzaById(pizzaId);
            if (selectedPizza != null) {
                order.addPizza(selectedPizza);
                System.out.println("Item added to your order.");
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
                System.out.print("Select payment method (Debit Card/Google Pay): ");
                String paymentMethod = scanner.next();
                order.setPaymentMethod(paymentMethod);
                System.out.println("Payment will be processed online via " + paymentMethod + ".");
                break;
            } else if (payOption.equalsIgnoreCase("N")) {
                order.setPayOnline(false);
                System.out.println("Please pay in person upon delivery.");
                break;
            } else {
                System.out.println("Error: Wrong input. Please enter Y or N.");
            }
        } while (true);

        

        double totalAmount = order.getPizzas().stream().mapToDouble(Pizza::getPrice).sum();
        totalAmount += order.getSideDishes().stream().mapToDouble(SideDish::getPrice).sum();
        System.out.println("Total Amount: $" + totalAmount);

        System.out.println("Thank you for your order!");
        System.out.println("Delivery Address: " + customer.getAddress());
        System.out.println("Contact Number: " + customer.getPhone());
        System.out.println("Payment Method: " + (order.isPayOnline() ? order.getPaymentMethod() : "In Person"));
        for (Pizza pizza : order.getPizzas()) {
        }
    }
}
