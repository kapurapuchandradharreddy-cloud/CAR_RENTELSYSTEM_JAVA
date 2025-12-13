import java.util.ArrayList;

public class CarManager {
    private ArrayList<Car> cars;
    private final String FILE = "cars.dat";

    public CarManager() {
        cars = StorageManager.load(FILE);
        if (cars.isEmpty()) preloadSample();
    }

    private void preloadSample() {
        cars.add(new Car(1, "Maruti Swift", Category.HATCHBACK, 1200));
        cars.add(new Car(2, "Hyundai Creta", Category.SUV, 2200));
        cars.add(new Car(3, "Toyota Innova", Category.MUV, 3000));
        save();
    }

    public ArrayList<Car> getCars() { return cars; }

    public void save() { StorageManager.save(FILE, cars); }

    public Car findById(int id) {
        for (Car c : cars) if (c.getId() == id) return c;
        return null;
    }

    public void addCar(Car c) {
        cars.add(c);
        save();
    }

    public boolean removeCar(int id) {
        Car c = findById(id);
        if (c == null) return false;
        if (!c.isAvailable()) return false;
        cars.remove(c);
        save();
        return true;
    }

    public int nextId() {
        int max = 0;
        for (Car c : cars) if (c.getId() > max) max = c.getId();
        return max + 1;
    }
}

