import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * Class created to represent a property
 *
 * Authors: Jonathan Falvey, Bryan Carty, Denis O'Sullivan
 *
 */
public class Property {
    private PropertyOwner owner;
    private Address address;
    private String eircode;
    private double estMarketValue;
    private String locationCategory;
    private boolean principlePrivateResidence;
    private double exTaxDue = 0;
    private double taxOverdue = 0; //tax overdue from previous year. Included in taxDue when calculating tax
    private ArrayList<Payment> payments;
    private ArrayList<Tax> taxes;

    /**
     *
     * Default No-arg constructor
     *
     */
    public Property() {
        payments = new ArrayList<>();
        taxes = new ArrayList<>();
    }

    /**
     *
     * Primary constructor for creating new properties
     *
     * @param owner owner of the property
     * @param address address of the property
     * @param eircode eircode for the address of the property
     * @param estMarketValue estimated market value of the property
     * @param locationCategory location of the property
     * @param principlePrivateResidence inidcates if property is principle private residence or not
     */
    public Property(PropertyOwner owner, Address address, String eircode, double estMarketValue, String locationCategory, boolean principlePrivateResidence) {
        payments = new ArrayList<>();
        taxes = new ArrayList<>();
        this.owner = owner;
        this.address = address;
        this.eircode = eircode;
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principlePrivateResidence = principlePrivateResidence;
    }

    /**
     *
     * Alternate constructor for properties
     *
     * @param estMarketValue estimated market value of the property
     * @param locationCategory location of the property
     * @param principlePrivateResidence inidcates if property is principle private residence or not
     */
    public Property(double estMarketValue, String locationCategory, boolean principlePrivateResidence) {
        payments = new ArrayList<>();
        taxes = new ArrayList<>();
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principlePrivateResidence = principlePrivateResidence;
    }

    /**
     *
     * Method for calculating tax due on a property using default tax formula
     *
     * @return returns tax due on this property
     */
    public double calculateTax() {
        //flat charge
        exTaxDue = 100;

        //tax on property value
        if (estMarketValue >= 150000 && estMarketValue <= 400000) {
            exTaxDue += (estMarketValue*0.01);
        } else if (estMarketValue <= 650000) {
            exTaxDue += (estMarketValue*0.02);
        } else if (estMarketValue > 650000) {
            exTaxDue += (estMarketValue*0.04);
        }

        //location category tax
        if (locationCategory.equalsIgnoreCase("City")) {
            exTaxDue += 100;
        } else if (locationCategory.equalsIgnoreCase("Large town")) {
            exTaxDue += 80;
        } else if (locationCategory.equalsIgnoreCase("Small town")) {
            exTaxDue += 60;
        } else if (locationCategory.equalsIgnoreCase("Village")) {
            exTaxDue += 50;
        } else {
            exTaxDue += 20;
        }

        //Not principle private residence charge
        if (!principlePrivateResidence) {
            exTaxDue += 100;
        }

        //Add a new Tax with the amount calculated
        if (!hasTaxForYear()) {
            taxes.add(new Tax(this.owner, this, exTaxDue));
        }


        return exTaxDue;
    }

    /**
     *
     * Method for testing experimental tax rates from a different tax calculation formula
     *
     * @param flatCharge flat tax charge
     * @param rateBetween150000And400000 percentage of market value (0.xx)
     * @param rateBetween400000And650000 percentage of market value (0.xx
     * @param rateAbove650000 percentage of market value (0.xx
     * @param cityTax charge to be paid if located in a city
     * @param largeTownTax charge to be paid if located in a large town
     * @param smallTownTax charge to be paid if located in a small town
     * @param villageTax charge to be paid if located in a village
     * @param countrysideTax charge to be paid if located in the countryside
     * @param pprTax charge to be paid if principle private residence
     * @return returns the theoretical tax value using the experimental tax calculation
     */
    public double calculateTax(double flatCharge, double rateBetween150000And400000, double rateBetween400000And650000, double rateAbove650000,
                               double cityTax, double largeTownTax, double smallTownTax, double villageTax, double countrysideTax,
                               double pprTax) {
        //flat charge
        double exTaxDue = flatCharge;

        //tax on property value
        if (estMarketValue >= 150000 && estMarketValue <= 400000) {
            exTaxDue += (estMarketValue*rateBetween150000And400000);
        } else if (estMarketValue <= 650000) {
            exTaxDue += (estMarketValue*rateBetween400000And650000);
        } else if (estMarketValue > 650000) {
            exTaxDue += (estMarketValue*rateAbove650000);
        }

        //location category tax
        if (locationCategory.equalsIgnoreCase("City")) {
            exTaxDue += cityTax;
        } else if (locationCategory.equalsIgnoreCase("Large town")) {
            exTaxDue += largeTownTax;
        } else if (locationCategory.equalsIgnoreCase("Small town")) {
            exTaxDue += smallTownTax;
        } else if (locationCategory.equalsIgnoreCase("Village")) {
            exTaxDue += villageTax;
        } else {
            exTaxDue += countrysideTax;
        }

        //Not principle private residence charge
        if (!principlePrivateResidence) {
            exTaxDue += pprTax;
        }

        return exTaxDue;
    }

    /**
     *
     * Calculates any overdue tax for this property from previous years
     *
     * @return returns the total of overdue tax on this property
     */
    public double calculateTaxOverdue() {
        taxOverdue = 0;

        for (Tax tax : taxes) {
            if (!tax.paid) {
                //compound tax due for every year it is not paid
                double tempTax = 0;
                int yearsNotPaid = LocalDate.now().getYear() - tax.getYearDue();

                if (yearsNotPaid > 0) { //avoids recalculating tax from current year
                    tempTax = tax.taxDue;

                    for (int year = 0; year < yearsNotPaid; year++) {
                        tempTax *= 1.07;
                    }
                }

                taxOverdue += tempTax;
            }
        }
        return taxOverdue;
    }

    /**
     *
     * Used when reading in data from csv file
     *
     * @param tax tax to be added in
     */
    public void addTax(Tax tax) {
        taxes.add(tax);
    }

    /**
     *
     * gets the total tax due on this property
     *
     * @return total tax due
     */
    public double getBalance() {
        return exTaxDue + taxOverdue;
    }

    /**
     *
     * summarises key characteristics used to identify the property
     *
     * @return String representation of the property
     */
    public String format() {
        return address + ", " + eircode;
    }

    /**
     *
     * Adds a payment to the property
     *
     * @param Owner owner who made the payment
     * @param year year payment was made
     * @param amount amount that was paid
     */
    public void addPayment(PropertyOwner Owner, int year, double amount) {
        if (year == LocalDate.now().getYear()) {
            exTaxDue -= amount;
        } else {
            taxOverdue -= amount;
        }

        payments.add(new Payment(owner, this, year, amount, getBalance()));
    }

    /**
     *
     * Used for reading in payment data from file
     *
     * @param payment payment to be read in
     */
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    /**
     *
     * gets the address of the property
     *
     * @return returns address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * Gets all payments made for this property
     *
     * @return arraylist of payments
     */
    public ArrayList<Payment> getPayments() {
        ArrayList<Payment> temp = new ArrayList<>();
        HashSet<Payment> unique = new HashSet<>();

        for (Payment p : payments) {
            if (!unique.contains(p)) {
                unique.add(p);
                temp.add(p);
            }
        }

        return temp;
    }

    /**
     *
     * gets all taxes on this property
     *
     * @return arraylist of taxes
     */
    public ArrayList<Tax> getTaxes() {
        return taxes;
    }

    /**
     *
     * general string format of a property
     *
     * @return String representation of property
     */
    @Override
    public String toString() {
    	return "Address: "+address.toString()+", "+this.eircode;
    }

    /**
     *
     * invoked when tax is paid on a property. updates the tax to indicate that it has been paid and when it was paid
     *
     * @param year year tax was paid
     * @param amount amount that was paid
     */
    public void taxPaid(int year, double amount) {
        for (Tax tax : taxes) {
            if (tax.getYearDue() == year) {
                tax.taxPaid(amount);
                tax.yearPaid = LocalDate.now().getYear();
                break;
            }
        }
    }

    /**
     *
     * gets all unpaid taxes for this property
     *
     * @return arraylist of unpaid taxes
     */
    public ArrayList<Tax> getAllTaxUnpaid() {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Tax tax : taxes) {
            if (!tax.paid) {
                temp.add(tax);
            }
        }
        return temp;
    }

    /**
     *
     * gets all unpaid taxes for this property in a specific year
     *
     * @param year year to retrieve taxes from
     * @return arraylist of unpaid taxes
     */
    public ArrayList<Tax> getAllTaxUnpaid(int year) {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Tax tax : taxes) {
            if ((tax.yearDue == year) && (tax.yearPaid > year)) {
                temp.add(tax);
            }
        }

        return temp;
    }

    /**
     *
     * gets the eircode of this property
     *
     * @return eircode
     */
    public String getEircode() {
        return eircode;
    }

    /**
     *
     * used to avoid an issue with duplicated data. prevents anothe tax being created if a tax already exists for a year
     *
     * @return returns true if a tax already exists for that year
     */
    public boolean hasTaxForYear() {
        for (Tax tax : taxes) {
            if (tax.yearDue == LocalDate.now().getYear()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * summarises key information about property for writing to csv file
     *
     * @return String representation of property
     */
    public String writeFormat() {
        return "Property," + address.getStreet() + "," + address.getCity() + "," + address.getCounty() + "," + eircode + "," + estMarketValue + "," + locationCategory + "," + principlePrivateResidence +"\n";
    }
}
