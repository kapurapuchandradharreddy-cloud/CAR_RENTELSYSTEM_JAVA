import java.io.Serializable;
import java.util.Date;

public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;

    private int rentalId;
    private int carId;
    private int customerId;
    private int days;
    private double totalAmount;
    private Date rentDate;
    private Date returnDate; // null if not returned
    private RentalStatus status;

    public Rental(int rentalId, int carId, int customerId, int days, double totalAmount) {
        this.rentalId = rentalId;
        this.carId = carId;
        this.customerId = customerId;
        this.days = days;
        this.totalAmount = totalAmount;
        this.rentDate = new Date();
        this.returnDate = null;
        this.status = RentalStatus.ACTIVE;
    }

    public int getRentalId() { return rentalId; }
    public int getCarId() { return carId; }
    public int getCustomerId() { return customerId; }
    public int getDays() { return days; }
    public double getTotalAmount() { return totalAmount; }
    public Date getRentDate() { return rentDate; }
    public Date getReturnDate() { return returnDate; }
    public RentalStatus getStatus() { return status; }

    public void completeRental() {
        this.status = RentalStatus.COMPLETED;
        this.returnDate = new Date();
    }

    @Override
    public String toString() {
        return String.format("RentalID:%d | CarID:%d | CustID:%d | Days:%d | Amount:₹%.2f | %s | Rented:%s | Returned:%s",
                rentalId, carId, customerId, days, totalAmount, status,
                rentDate.toString(), (returnDate==null? "-" : returnDate.toString()));
    }
}

