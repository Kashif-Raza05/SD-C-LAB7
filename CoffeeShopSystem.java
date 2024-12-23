public class CoffeeShopSystem {
    private static int orderCounter = 0;

    
    public synchronized static int createOrder() {
        orderCounter++;
        System.out.println("Order #" + orderCounter + " placed.");
        return orderCounter;
    }

    // Barista class to simulate the work of preparing coffee
    static class Barista extends Thread {
        private final int orderId;
        private final String drinkType;

        // Constructor for Barista to initialize order details
        public Barista(int orderId, String drinkType) {
            this.orderId = orderId;
            this.drinkType = drinkType;
        }

        // Method to simulate preparing the coffee
        @Override
        public void run() {
            try {
                System.out.println("Barista started preparing order #" + orderId + " for " + drinkType);
                Thread.sleep(2000); 
                System.out.println("Barista completed order #" + orderId + " for " + drinkType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // customers placing orders
    static class Customer extends Thread {
        private final String drinkType;

       
        public Customer(String drinkType) {
            this.drinkType = drinkType;
        }

        @Override
        public void run() {
            int orderId = createOrder(); // Create a new order
            // Create a Barista to prepare the order
            Barista barista = new Barista(orderId, drinkType);
            barista.start(); // Start the barista thread
            try {
                barista.join(); // Wait for this barista to finish before continuing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Creating multiple customers and assigning them drink orders
        Customer customer1 = new Customer("Espresso");
        Customer customer2 = new Customer("Cappuccino");
        Customer customer3 = new Customer("Latte");

        customer1.start();
        customer2.start();
        customer3.start();

        try {
            customer1.join();
            customer2.join();
            customer3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        System.out.println("All orders have been completed.");
    }
}
