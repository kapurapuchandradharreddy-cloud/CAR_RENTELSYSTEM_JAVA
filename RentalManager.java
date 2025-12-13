import java.util.ArrayList;

public class RentalManager {
    private ArrayList<Rental> rentals;
    private final String FILE = "rentals.dat";

    public RentalManager() {
        rentals = StorageManager.load(FILE);
    }

    public ArrayList<Rental> getRentals() { return rentals; }

    public void save() { StorageManager.save(FILE, rentals); }

    public Rental findById(int id) {
        for (Rental r : rentals) if (r.getRentalId() == id) return r;
        return null;
    }

    public int nextId() {
        int max = 0;
        for (Rental r : rentals) if (r.getRentalId() > max) max = r.getRentalId();
        return max + 1;
    }

    public Rental createRental(int carId, int customerId, int days, double totalAmount) {
        int id = nextId();
        Rental r = new Rental(id, carId, customerId, days, totalAmount);
        rentals.add(r);
        save();
        return r;
    }

    public boolean completeRental(int rentalId) {
        Rental r = findById(rentalId);
        if (r == null) return false;
        if (r.getStatus() == RentalStatus.COMPLETED) return false;
        r.completeRental();
        save();
        return true;
    }
}

