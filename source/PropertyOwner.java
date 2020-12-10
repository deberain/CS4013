import java.time.LocalDate;
import java.util.ArrayList;

public class PropertyOwner {
    private String name;
    private Address address;
    private String eircode;
    private ArrayList<Property> properties;
    private ArrayList<Payment> payments;
    private double taxDue = 0;
    private double taxOverdue = 0;

    public PropertyOwner() {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
    }

    public PropertyOwner(String name, Address address, String eircode) {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.eircode = eircode;
    }


    public void registerProperty(Property property) {
        properties.add(property);
    }

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
    

    public ArrayList<Property> displayProperties() {
    	ArrayList<Property> propsWithTaxToPay= new ArrayList<Property>();
    	for(Property prop:properties) {
    		prop.calculateTax();
    		if(prop.getBalance() > 0) {
    			propsWithTaxToPay.add(prop);
    		}
    	}
    	return propsWithTaxToPay;
    }

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

    public String getName() {
        return name;
    }

    public double getTaxDue() {
        return taxDue + taxOverdue;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public ArrayList<Tax> getAllTaxes() {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Property property : properties) {
            temp.addAll(property.getTaxes());
        }

        return temp;
    }

    public ArrayList<Tax> getAllUnpaidTaxes() {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Property property : properties) {
            temp.addAll(property.getAllTaxUnpaid());
        }

        return temp;
    }

    public ArrayList<Tax> getAllUnpaidTaxes(int year) {
        ArrayList<Tax> temp = new ArrayList<>();

        for (Property property : properties) {
            temp.addAll(property.getAllTaxUnpaid(year));
        }

        return temp;
    }

    public String format() {
        return name + ", " + address + ", " + eircode;
    }
    
    public String getEircode() {
    	return this.eircode;
    }
    
    public Address getAddress() {
    	return this.address;
    }
    
    @Override
    public boolean equals(Object other) {
    	PropertyOwner otherOwner = (PropertyOwner) other;
        return this.name.contentEquals(otherOwner.name) && this.eircode.contentEquals(otherOwner.eircode) && this.address.equals(otherOwner.address);
    }
    
    public String propsAndTaxFormated() {
    	String returnVal = "";
    	for(Property prop : properties) {
    		returnVal = returnVal.concat(prop.toString()+": "+prop.getBalance()+"\n"); 
    	}
    	return returnVal;
    }

    public void displayPropBalancesByYear() {
        for (Property property : properties) {
            System.out.println("Property: " + property.format());

            for (Tax tax : property.getTaxes()) {
                System.out.println(tax);
            }

        }
    }

}
