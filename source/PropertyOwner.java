import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * Class created to represent a property owner
 *
 * Authors: Jonathan Falvey, Denis O'sullivan, Bryan carty
 *
 */
public class PropertyOwner {
    private String name;
    private Address address;
    private String eircode;
    private ArrayList<Property> properties;
    private ArrayList<Payment> payments;
    private double taxDue = 0;
    private double taxOverdue = 0;
    private String password;

    /**
     *
     * Default no-arg constructor
     *
     */
    public PropertyOwner() {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
    }

    /**
     *
     * default constructor for creating a new proeprty owner
     *
     * @param name full name of the owner
     * @param address address of the owner
     * @param eircode eircode of owner's address
     * @param password Owner's account password
     */
    public PropertyOwner(String name, Address address, String eircode, String password) {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.eircode = eircode;
        this.password = password;
    }
    
    public PropertyOwner(String name, String address, String eircode, String password) {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        String[] addressParts = address.split(", ");
        this.name = name;
        this.address = new Address(addressParts[0],addressParts[1],addressParts[2]);
        this.eircode = eircode;
        this.password = password;
    }

    /**
     *
     * Constructor for use when reading in data
     *
     * @param name full name of the owner
     * @param address address of the owner
     * @param eircode eircode of owner's address
     * @param taxDue owner's total tax for current year
     * @param taxOverdue owner's total overdue tax from previous years
     * @param password Owner's account password
     */
    public PropertyOwner(String name, Address address, String eircode, double taxDue, double taxOverdue, String password) {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.eircode = eircode;
        this.taxDue = taxDue;
        this.taxOverdue = taxOverdue;
        this.password = password;
    }

    /**
     *
     * registers a property under the owner's name
     *
     * @param property property to be registered
     */
    public void registerProperty(Property property) {
        properties.add(property);
    }

    /**
     *
     * method used for an owner to pay tax
     *
     * @param amount amount of tax being paid
     * @param property property tax is being paid on
     * @param year year tax is being paid
     */
    public void payTax(double amount, Property property, int year) {
        if (year == LocalDate.now().getYear()) {
            taxDue -= amount;
        } else {
            taxOverdue -= amount;
        }

        payments.add(new Payment(this, property, year, amount, getTaxDue()));
        property.addPayment(this, year, amount);
        property.taxPaid(year, amount);

    }

    /**
     *
     * adds a payment to both the owner's records and the properties records
     *
     * @param property property for payment ot be added to
     * @param payment payment being added
     */
    public void addPayment(Property property, Payment payment) {
        payments.add(payment);
        property.addPayment(payment);
    }

    /**
     *
     * gets all properties with tax to be paid
     *
     * @return arraylist of properties
     */
    public ArrayList<Property> displayProperties() {
    	ArrayList<Property> propsWithTaxToPay= new ArrayList<Property>();
    	for(Property prop:properties) {
    		if(prop.getBalance() > 0) {
    			propsWithTaxToPay.add(prop);
    		}
    	}
    	return propsWithTaxToPay;
    }

    /**
     *
     * calculates the owner's tax by calculating the tax on each owned property
     *
     */
    public void calculateTax() {
        //calculate tax for current year from all owned properties
        taxDue = 0;

        for (Property property : properties) {
            taxDue += property.calculateTax();
        }

        //calculate tax overdue from previous years
        taxOverdue = 0;

        for (Property property : properties) {
            taxOverdue += property.calculateTaxOverdue();
        }
    }

    /**
     *
     * @return the name of the owner
     */
    public String getName() {
        return name;
    }

    /**
     *
     * gets total tax due for owner
     *
     * @return total tax due
     */
    public double getTaxDue() {
        return taxDue + taxOverdue;
    }

    /**
     *
     * gets the owner's account password
     *
     * @return owner's password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * get's all the owner's properties
     *
     * @return arraylist of properties
     */
    public ArrayList<Property> getProperties() {
        return properties;
    }

    /**
     *
     * gets all the owner's payments
     *
     * @return arraylist of payments
     */
    public ArrayList<Payment> getPayments() {
        return payments;
    }

    /**
     *
     * gets all the owner's taxes
     *
     * @return arraylist of taxes
     */
    public ArrayList<Tax> getAllTaxes() {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Property property : properties) {
            temp.addAll(property.getTaxes());
        }

        return temp;
    }

    /**
     *
     * gets all the owner's taxes that were unpaid for a specific year
     *
     * @param year year to retrieve data from
     * @return arraylist of unpaid taxes
     */
    public ArrayList<Tax> getAllUnpaidTaxes(int year) {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Property property : properties) {
            temp.addAll(property.getAllTaxUnpaid(year));
        }

        return temp;
    }

    /**
     *
     * @return convenient string format of owner's key characteristics
     */
    public String format() {
        return name + ", " + address + ", " + eircode;
    }
    
    public String toString() {
    	return name;
    }

    /**
     *
     * overriden method to compare two property owners by name, address and eircode
     *
     * @param other other owner to be comapared with
     * @return returns true if owners are equal
     */
    @Override
    public boolean equals(Object other) {
    	PropertyOwner otherOwner = (PropertyOwner) other;
        return this.name.contentEquals(otherOwner.name) && this.eircode.contentEquals(otherOwner.eircode) && this.address.equals(otherOwner.address);
    }

    /**
     *
     * gets convenient string showcasing each property and their current balance
     *
     * @return String with properties and their balances
     */
    public String propsAndTaxFormated() {
    	String returnVal = "";
    	for(Property prop : properties) {
    		returnVal = returnVal.concat(prop.toString()+": "+prop.getBalance()+"\n");
    	}
    	return returnVal;
    }

    /**
     *
     * displays yearly taxes grouped by property
     *
     */
    public String displayPropBalancesByYear() {
    	String returnVal = "";
        for (Property property : properties) {
           returnVal += "Property: " + property.format() + "\n";

            for (Tax tax : property.getTaxes()) {
                returnVal += tax + "\n";
            }
        }
        return returnVal;
    }

    /**
     *
     * summarises key characteristics of property owner for writing to csv file
     *
     * @return csv String
     */
    public String writeFormat() {
        return "Owner," + name + "," + address.getStreet() + "," + address.getCity() + "," + address.getCounty() + "," + eircode + "," + taxDue + "," + taxOverdue + "," + password +"\n";
    }

}
