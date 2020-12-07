import java.util.ArrayList;

public class PropertyOwner {
    private String name;
    private Address address;
    private String eircode;
    private ArrayList<Property> properties;
    private ArrayList<Payment> payments;
    private double taxDue;//totalTaxDue
    private double taxOverdue;//totalTaxOverdue
    private ArrayList<Tax> ownerTaxPerProperty;

    public PropertyOwner() {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        ownerTaxPerProperty = new ArrayList<>();
    }

    public PropertyOwner(String name, Address address, String eircode) {
        properties = new ArrayList<>();
        payments = new ArrayList<>();
        this.name = name;
        this.address = address;
        this.eircode = eircode;
        ownerTaxPerProperty = new ArrayList<>();
    }


    public void registerProperty(Property property) {
        property.calculateTax();
    	properties.add(property);
        ownerTaxPerProperty.add(new Tax(this, property, property.getBalance()));   
    }

    public void payTax(double amount, Property property, int year) {
        taxDue -= amount;
        payments.add(new Payment(this, property, year, amount, taxDue));
        property.addPayment(this, year, amount);
        for(Tax tax:ownerTaxPerProperty) {
        	if(tax.getYearDue()==year) {
        		ownerTaxPerProperty.remove(tax);
        	}
        }
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
        return this.name.contentEquals(otherOwner.name) && this.eircode.contentEquals(otherOwner.eircode) && this.address.equals(otherOwner.address);
    }
    
    public String propsAndTaxFormated() {
    	String returnVal = "";
    	for(Property prop : properties) {
    		returnVal = returnVal.concat(prop.toString()+": "+prop.getBalance()+"\n"); 
    	}
    	return returnVal;
    }

    public ArrayList<Integer> getTaxYearDue(Property prop) {
    	ArrayList<Integer> yearsDue = new ArrayList<Integer>();
    	for(Tax tax:ownerTaxPerProperty) {
    		if(tax.getProperty() == prop) {
    			yearsDue.add(tax.getYearDue());
    		}
    	}
    	return yearsDue;
    }
}
}
