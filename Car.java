import java.io.Serializable;

public class Car implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String model;
    private Category category;
    private double rentPerDay;
    private boolean available;

    public Car(int id, String model, Category category, double rentPerDay) {
        this.id = id;
        this.model = model;
        this.category = category;
        this.rentPerDay = rentPerDay;
        this.available = true;
    }

    public int getId() { return id; }
    public String getModel() { return model; }
    public Category getCategory() { return category; }
    public double getRentPerDay() { return rentPerDay; }
    public boolean isAvailable() { return available; }

    public void setModel(String model) { this.model = model; }
    public void setCategory(Category category) { this.category = category; }
    public void setRentPerDay(double rentPerDay) { this.rentPerDay = rentPerDay; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("ID:%d | %s | %s | ₹%.2f | %s",
                id, model, category, rentPerDay, (available ? "Available" : "Rented"));
    }
}

