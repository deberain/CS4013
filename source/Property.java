import java.time.LocalDate;
import java.util.ArrayList;

public class Property {
    private PropertyOwner owner;
    private Address address;
    private String eircode;
    private double estMarketValue;
    private String locationCategory;
    private boolean principlePrivateResidence;
    private double exTaxDue = 0;
    private double taxOverdue = 0; //tax overdue from previous year. Included in taxDue when calculating tax
    private double penaltyRate = 1.00;
    private ArrayList<Payment> payments;
    private ArrayList<Tax> taxes;

    public Property() {
        payments = new ArrayList<>();
        taxes = new ArrayList<>();
    }

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

    public Property(double estMarketValue, String locationCategory, boolean principlePrivateResidence) {
        payments = new ArrayList<>();
        taxes = new ArrayList<>();
        this.estMarketValue = estMarketValue;
        this.locationCategory = locationCategory;
        this.principlePrivateResidence = principlePrivateResidence;
    }

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
        taxes.add(new Tax(this.owner, this, exTaxDue));

        return exTaxDue;
    }

    public double calculateTax(double flatCharge, double rateBetween150000And400000, double rateBetween400000And650000, double rateAbove650000,
                               double cityTax, double largeTownTax, double smallTownTax, double villageTax, double countrysideTax,
                               double pprTax
    ) {
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

    public double calculateTaxOverdue() {
        taxOverdue = 0;

        for (Tax tax : taxes) {
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
        return taxOverdue;
    }

    public double getBalance() {
        return exTaxDue + taxOverdue;
    }

    public String format() {
        return address + ", " + eircode;
    }

    public void addPayment(PropertyOwner Owner, int year, double amount) {
        if (year == LocalDate.now().getYear()) {
            exTaxDue -= amount;
        } else {
            taxOverdue -= amount;
        }

        payments.add(new Payment(owner, this, year, amount, getBalance()));
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public ArrayList<Tax> getTaxes() {
        return taxes;
    }
    
    @Override
    public String toString() {
    	return "Address: "+address.toString()+", "+this.eircode;
    }

    public void taxPaid(int year, double amount) {
        for (Tax tax : taxes) {
            if (tax.getYearDue() == year) {
                tax.taxPaid(amount);
                tax.yearPaid = LocalDate.now().getYear();
                break;
            }
        }
    }

    public ArrayList<Tax> getAllTaxUnpaid() {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Tax tax : taxes) {
            if (!tax.paid) {
                temp.add(tax);
            }
        }

        return temp;
    }

    public ArrayList<Tax> getAllTaxUnpaid(int year) {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Tax tax : taxes) {
            if ((tax.yearDue == year) && (tax.yearPaid > year)) {
                temp.add(tax);
            }
        }

        return temp;
    }

    public String getEircode() {
        return eircode;
    }
}
