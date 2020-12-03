import java.util.ArrayList;

public class Property {
    private PropertyOwner owner;
    private Address address;
    private String eircode;
    private double estMarketValue;
    private String locationCategory;
    private boolean principlePrivateResidence;
    private double taxDue = 0;
    private double taxOverdue = 0; //tax overdue from previous year. Included in taxDue when calculating tax
    private double penaltyRate = 1.00;
    private ArrayList<Payment> payments;

    public Property() {
        payments = new ArrayList<>();
    }

    public Property(PropertyOwner owner, Address address, String eircode, double estMarketValue, String locationCategory, boolean principlePrivateResidence) {
        payments = new ArrayList<>();
        this.owner = owner;
        this.address = address;
        this.eircode = eircode;
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principlePrivateResidence = principlePrivateResidence;
    }

    public double calculateTax() {
        //if there is tax unpaid, note the amount in taxOverdue and increase the penalty rate by 0.07
        if (taxDue > 0) {
            taxOverdue = taxDue;
            taxDue = 0;
            penaltyRate += 0.07;
        } else {
            penaltyRate = 1.00;
            taxOverdue = 0;
        }

        //flat charge
        taxDue += 100;

        //tax on property value
        if (estMarketValue >= 150000 && estMarketValue <= 400000) {
            taxDue += (estMarketValue*0.01);
        } else if (estMarketValue <= 650000) {
            taxDue += (estMarketValue*0.02);
        } else if (estMarketValue > 650000) {
            taxDue += (estMarketValue*0.04);
        }

        //location category tax
        if (locationCategory.equals("City")) {
            taxDue += 100;
        } else if (locationCategory.equalsIgnoreCase("Large town")) {
            taxDue += 80;
        } else if (locationCategory.equalsIgnoreCase("Small town")) {
            taxDue += 60;
        } else if (locationCategory.equalsIgnoreCase("Village")) {
            taxDue += 50;
        } else {
            taxDue += 20;
        }

        //Not principle private residence charge
        if (!principlePrivateResidence) {
            taxDue += 100;
        }

        //Add the overdue tax from previous year, if any, to newly calculated tax
        taxDue += taxOverdue;

        return taxDue*penaltyRate; //apply penalty if applicable
    }

    public double getBalance() {
        return taxDue;
    }

    public String format() {
        return address + ", " + eircode;
    }

    public void addPayment(PropertyOwner Owner, int year, double amount) {
        taxDue -= amount;
        payments.add(new Payment(owner, this, year, amount, taxDue));
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }
}
