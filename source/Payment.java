public class Payment {
    private PropertyOwner owner;
    private Property property;
    private int year;
    private double amountPaid;
    private double taxRemaining;

    public Payment() {}

    public Payment(PropertyOwner owner, Property property, int year, double amountPaid, double taxRemaining) {
        this.owner = owner;
        this.property = property;
        this.year = year;
        this.amountPaid = amountPaid;
        this.taxRemaining = taxRemaining;
    }

    public String ownerFormat() {
        return "Year: " + year + ", Property: " + property.getAddress() + ", Amount paid: " + amountPaid + ", Balance Remaining: " + taxRemaining;
    }

    public String propertyFormat() {
        return "Year: " + year + ", Owner: " + owner.getName() + ", Amount Paid: " + ", Tax Remaining: " + taxRemaining;
    }

    public double getBalance() {
        return taxRemaining;
    }

    public int getYear() {
        return year;
    }
}
