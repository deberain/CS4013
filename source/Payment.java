/**
 *
 * Class created to represent a receipt for tax payment on a property
 *
 * Author: Jonathan Falvey
 *
  */
public class Payment {
    private PropertyOwner owner;
    private Property property;
    private int year;
    private double amountPaid;
    private double taxRemaining;

    /**
     *
     * Default no-arg constructor
     *
     */
    public Payment() {}

    /**
     *
     * Constructor used to crate new payments
     *
     * @param owner the owner who has paid tax
     * @param property the property tax has been paid on
     * @param year the year this payment was made
     * @param amountPaid amount of tax that was paid
     * @param taxRemaining how much tax the owner must still pay
     */
    public Payment(PropertyOwner owner, Property property, int year, double amountPaid, double taxRemaining) {
        this.owner = owner;
        this.property = property;
        this.year = year;
        this.amountPaid = amountPaid;
        this.taxRemaining = taxRemaining;
    }

    /**
     *
     * Presents key information about the payment for property owners
     *
     * @return returns a convenient String format of the property
     */
    public String ownerFormat() {
        return "Year: " + year + ", Property: " + property.getAddress() + ", Amount paid: " + amountPaid + ", Balance Remaining: " + taxRemaining;
    }

    /**
     *
     * Presents key information about the payment for properties
     *
     * @return returns a convenient String format of the property
     */
    public String propertyFormat() {
        return "Year: " + year + ", Owner: " + owner.getName() + ", Amount Paid: " + ", Tax Remaining: " + taxRemaining;
    }

    /**
     *
     * summaries information about the payment for writing to a csv file
     *
     * @return csv line String
     */
    public String writeFormat() {
        return "Payment," + year + "," + amountPaid + "," + taxRemaining +"\n";
    }
}
