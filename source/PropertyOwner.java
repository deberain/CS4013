import java.util.ArrayList;

public class PropertyOwner {
    private String name;
    private Address address;
    private String eircode;
    private ArrayList<Property> properties;
    private ArrayList<Payment> payments;
    private double taxDue;
    private double taxOverdue;

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
    
    public PropertyOwner(String name, String[] address, String eircode) {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        this.name = name;
        this.address = new Address(address[0], address[1], address[2]);
        this.eircode = eircode;
    }

    public void registerProperty(Property property) {
        properties.add(property);
    }

    public void payTax(double amount, Property property, int year) {
        taxDue -= amount;
        payments.add(new Payment(this, property, year, amount, taxDue));
        property.addPayment(this, year, amount);
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

    public double getBalance(int year) {
        for (int i = payments.size()-1; i >= 0; i--) {
            if (payments.get(i).getYear() == year ) {
                return payments.get(i).getBalance();
            }
        }
        return -1;
    }

    public double getBalance(Property p) {
        return p.getBalance();
    }

    public void calculateTax() {
        if (taxDue > 0) {
            taxOverdue = taxDue;
            taxDue = 0;
        }

        for (Property property : properties) {
            taxDue += property.calculateTax();
        }
        taxDue += taxOverdue;
    }

    public String getName() {
        return name;
    }

    public double getTaxDue() {
        return taxDue;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
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
    	if(this.name.contentEquals(otherOwner.name)&&this.eircode.contentEquals(otherOwner.eircode)&&this.address.equals(otherOwner.address)) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public String propsAndTaxFormated() {
    	String returnVal = "";
    	for(Property prop : properties) {
    		returnVal = returnVal.concat(prop.toString()+": "+prop.getBalance()+"\n"); 
    	}
    	return returnVal;
    }

}
