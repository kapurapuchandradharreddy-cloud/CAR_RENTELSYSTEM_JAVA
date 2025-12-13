import java.util.ArrayList;

public class CustomerManager {
    private ArrayList<Customer> customers;
    private final String FILE = "customers.dat";

    public CustomerManager() {
        customers = StorageManager.load(FILE);
        if (customers.isEmpty()) preloadSample();
    }

    private void preloadSample() {
        customers.add(new Customer(1, "Chandradhar", "9999999999", "chandra@example.com"));
        customers.add(new Customer(2, "Ravi", "8888888888", "ravi@example.com"));
        save();
    }

    public ArrayList<Customer> getCustomers() { return customers; }

    public void save() { StorageManager.save(FILE, customers); }

    public Customer findById(int id) {
        for (Customer c : customers) if (c.getId() == id) return c;
        return null;
    }

    public void addCustomer(Customer c) {
        customers.add(c);
        save();
    }

    public int nextId() {
        int max = 0;
        for (Customer c : customers) if (c.getId() > max) max = c.getId();
        return max + 1;
    }

    public boolean removeCustomer(int id) {
        Customer c = findById(id);
        if (c == null) return false;
        customers.remove(c);
        save();
        return true;
    }
}

