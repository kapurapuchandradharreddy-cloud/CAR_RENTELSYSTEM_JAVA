import java.util.Scanner;

public class CarRentalApp {
    private CarManager carManager;
    private CustomerManager customerManager;
    private RentalManager rentalManager;
    private Scanner sc;

    public CarRentalApp() {
        carManager = new CarManager();
        customerManager = new CustomerManager();
        rentalManager = new RentalManager();
        sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CarRentalApp app = new CarRentalApp();
        app.run();
    }

    private void run() {
        int choice;
        do {
            printMainMenu();
            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> manageCars();
                case 2 -> manageCustomers();
                case 3 -> manageRentals();
                case 4 -> reports();
                case 5 -> saveAndExit();
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    private void printMainMenu() {
        System.out.println("\n=== CAR RENTAL SYSTEM ===");
        System.out.println("1. Cars");
        System.out.println("2. Customers");
        System.out.println("3. Rentals");
        System.out.println("4. Reports");
        System.out.println("5. Save & Exit");
    }

    // ===== Cars Menu =====
    private void manageCars() {
        int c;
        do {
            System.out.println("\n-- Cars Menu --");
            System.out.println("1. List all cars");
            System.out.println("2. List available cars");
            System.out.println("3. Add car");
            System.out.println("4. Update car");
            System.out.println("5. Remove car");
            System.out.println("6. Search car by ID");
            System.out.println("7. Back");
            c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> listAllCars();
                case 2 -> listAvailableCars();
                case 3 -> addCar();
                case 4 -> updateCar();
                case 5 -> removeCar();
                case 6 -> searchCar();
                case 7 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } while (c != 7);
    }

    private void listAllCars() {
        System.out.println("\n--- All Cars ---");
        for (Car car : carManager.getCars()) System.out.println(car);
    }

    private void listAvailableCars() {
        System.out.println("\n--- Available Cars ---");
        boolean found=false;
        for (Car car : carManager.getCars()) {
            if (car.isAvailable()) {
                System.out.println(car);
                found=true;
            }
        }
        if (!found) System.out.println("No available cars.");
    }

    private void addCar() {
        int id = carManager.nextId();
        System.out.println("Assigned Car ID: " + id);
        String model = readString("Model: ");
        String cat = readString("Category (HATCHBACK/SEDAN/SUV/MUV/VAN/COUPE/CONVERTIBLE/LUXURY/OTHER): ");
        Category category = Category.fromString(cat);
        double price = readDouble("Rent per day: ");
        Car c = new Car(id, model, category, price);
        carManager.addCar(c);
        System.out.println("Car added: " + c);
    }

    private void updateCar() {
        int id = readInt("Car ID to update: ");
        Car car = carManager.findById(id);
        if (car == null) { System.out.println("Car not found."); return; }
        System.out.println("Current: " + car);
        String model = readString("New model (blank to keep): ");
        if (!model.isBlank()) car.setModel(model);
        String cat = readString("New category (blank to keep): ");
        if (!cat.isBlank()) car.setCategory(Category.fromString(cat));
        String priceStr = readString("New rent/day (blank to keep): ");
        if (!priceStr.isBlank()) {
            try { car.setRentPerDay(Double.parseDouble(priceStr)); }
            catch (NumberFormatException e) { System.out.println("Invalid number."); }
        }
        carManager.save();
        System.out.println("Updated: " + car);
    }

    private void removeCar() {
        int id = readInt("Car ID to remove: ");
        boolean ok = carManager.removeCar(id);
        if (ok) System.out.println("Car removed.");
        else System.out.println("Cannot remove (not found or currently rented).");
    }

    private void searchCar() {
        int id = readInt("Car ID: ");
        Car car = carManager.findById(id);
        if (car == null) System.out.println("Car not found.");
        else System.out.println(car);
    }

    // ===== Customers Menu =====
    private void manageCustomers() {
        int c;
        do {
            System.out.println("\n-- Customers Menu --");
            System.out.println("1. List customers");
            System.out.println("2. Add customer");
            System.out.println("3. Update customer");
            System.out.println("4. Remove customer");
            System.out.println("5. Search customer by ID");
            System.out.println("6. Back");
            c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> listCustomers();
                case 2 -> addCustomer();
                case 3 -> updateCustomer();
                case 4 -> removeCustomer();
                case 5 -> searchCustomer();
                case 6 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } while (c != 6);
    }

    private void listCustomers() {
        System.out.println("\n--- Customers ---");
        for (Customer cu : customerManager.getCustomers()) System.out.println(cu);
    }

    private void addCustomer() {
        int id = customerManager.nextId();
        System.out.println("Assigned Customer ID: " + id);
        String name = readString("Name: ");
        String phone = readString("Phone: ");
        String email = readString("Email: ");
        Customer cu = new Customer(id, name, phone, email);
        customerManager.addCustomer(cu);
        System.out.println("Customer added: " + cu);
    }

    private void updateCustomer() {
        int id = readInt("Customer ID to update: ");
        Customer cu = customerManager.findById(id);
        if (cu == null) { System.out.println("Customer not found."); return; }
        System.out.println("Current: " + cu);
        String name = readString("New name (blank to keep): ");
        if (!name.isBlank()) cu.setName(name);
        String phone = readString("New phone (blank to keep): ");
        if (!phone.isBlank()) cu.setPhone(phone);
        String email = readString("New email (blank to keep): ");
        if (!email.isBlank()) cu.setEmail(email);
        customerManager.save();
        System.out.println("Updated: " + cu);
    }

    private void removeCustomer() {
        int id = readInt("Customer ID to remove: ");
        // ensure no active rentals
        for (Rental r : rentalManager.getRentals()) {
            if (r.getCustomerId() == id && r.getStatus() == RentalStatus.ACTIVE) {
                System.out.println("Cannot remove: customer has active rentals.");
                return;
            }
        }
        boolean ok = customerManager.removeCustomer(id);
        if (ok) System.out.println("Customer removed.");
        else System.out.println("Customer not found.");
    }

    private void searchCustomer() {
        int id = readInt("Customer ID: ");
        Customer cu = customerManager.findById(id);
        if (cu == null) System.out.println("Customer not found.");
        else System.out.println(cu);
    }

    // ===== Rentals Menu =====
    private void manageRentals() {
        int c;
        do {
            System.out.println("\n-- Rentals Menu --");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. View active rentals");
            System.out.println("4. View rental history");
            System.out.println("5. Back");
            c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> rentFlow();
                case 2 -> returnFlow();
                case 3 -> viewActiveRentals();
                case 4 -> viewAllRentals();
                case 5 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } while (c != 5);
    }

    private void rentFlow() {
        listAvailableCars();
        int carId = readInt("Enter Car ID to rent: ");
        Car car = carManager.findById(carId);
        if (car == null) { System.out.println("Car not found."); return; }
        if (!car.isAvailable()) { System.out.println("Car not available."); return; }
        int custId = readInt("Enter Customer ID: ");
        Customer cu = customerManager.findById(custId);
        if (cu == null) { System.out.println("Customer not found."); return; }
        int days = readInt("Enter number of days: ");
        if (days <= 0) { System.out.println("Days must be > 0."); return; }
        double total = days * car.getRentPerDay();
        Rental r = rentalManager.createRental(carId, custId, days, total);
        car.setAvailable(false);
        carManager.save();
        System.out.println("Rental created:\n" + r);
    }

    private void returnFlow() {
        int rentalId = readInt("Enter Rental ID to return: ");
        Rental r = rentalManager.findById(rentalId);
        if (r == null) { System.out.println("Rental not found."); return; }
        if (r.getStatus() == RentalStatus.COMPLETED) { System.out.println("Rental already completed."); return; }
        boolean ok = rentalManager.completeRental(rentalId);
        if (ok) {
            Car car = carManager.findById(r.getCarId());
            if (car != null) car.setAvailable(true);
            carManager.save();
            System.out.println("Rental completed. Total: ₹" + r.getTotalAmount());
        } else {
            System.out.println("Could not complete rental.");
        }
    }

    private void viewActiveRentals() {
        System.out.println("\n--- Active Rentals ---");
        boolean found=false;
        for (Rental r : rentalManager.getRentals()) {
            if (r.getStatus() == RentalStatus.ACTIVE) {
                System.out.println(r);
                found=true;
            }
        }
        if (!found) System.out.println("No active rentals.");
    }

    private void viewAllRentals() {
        System.out.println("\n--- Rental History ---");
        for (Rental r : rentalManager.getRentals()) System.out.println(r);
    }

    // ===== Reports =====
    private void reports() {
        int c;
        do {
            System.out.println("\n-- Reports --");
            System.out.println("1. Total income (completed rentals)");
            System.out.println("2. Cars by category");
            System.out.println("3. Back");
            c = readInt("Enter choice: ");
            switch (c) {
                case 1 -> incomeReport();
                case 2 -> carsByCategory();
                case 3 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } while (c != 3);
    }

    private void incomeReport() {
        double sum = 0;
        for (Rental r : rentalManager.getRentals()) if (r.getStatus() == RentalStatus.COMPLETED) sum += r.getTotalAmount();
        System.out.println("Total income (completed rentals): ₹" + String.format("%.2f", sum));
    }

    private void carsByCategory() {
        java.util.Map<Category, Integer> map = new java.util.EnumMap<>(Category.class);
        for (Category cat : Category.values()) map.put(cat, 0);
        for (Car c : carManager.getCars()) map.put(c.getCategory(), map.getOrDefault(c.getCategory(), 0) + 1);
        System.out.println("\nCars by category:");
        for (java.util.Map.Entry<Category, Integer> e : map.entrySet()) System.out.println(e.getKey() + " -> " + e.getValue());
    }

    // ===== Utilities =====
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try { return Integer.parseInt(line.trim()); }
            catch (Exception e) { System.out.println("Invalid integer. Try again."); }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine();
            try { return Double.parseDouble(line.trim()); }
            catch (Exception e) { System.out.println("Invalid number. Try again."); }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private void saveAndExit() {
        System.out.println("Saving data...");
        carManager.save();
        customerManager.save();
        rentalManager.save();
        System.out.println("Saved. Goodbye!");
    }
}
