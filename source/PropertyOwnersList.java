import java.util.ArrayList;

/**
 *
 * Class created to act as a list of all property owners
 *
 */
public class PropertyOwnersList {

    private ArrayList<PropertyOwner> owners;

    /**
     *
     * Default constructor for the property owner list
     *
     */
    public PropertyOwnersList() {
        owners = new ArrayList<>();
    }

    /**
     *
     * adds an owner to the list
     *
     * @param owner owner to be added
     */
    public void registerOwner(PropertyOwner owner) {
    	owners.add(owner);
    }

    /**
     *
     * creates and registers a new property
     *
     * @param owner owner of the property
     * @param address address of the property
     * @param eircode eircode of the property's address
     * @param estMarketValue estimated market value of the property
     * @param locationCategory location category of the property
     * @param principlePrivateResidence indicates whether the property is principle private residence
     */
    public void registerProperty(PropertyOwner owner, Address address, String eircode, double estMarketValue,String locationCategory, boolean principlePrivateResidence) {
        for (PropertyOwner propowner : owners) {
            if (propowner.getName().equals(owner.getName())) {
                propowner.registerProperty(new Property(propowner, address, eircode, estMarketValue, locationCategory, principlePrivateResidence));
            }
        }
    }

    /**
     *
     * gets all registered properties
     *
     * @return arraylist of properties
     */
    public ArrayList<Property> getAllProperties() {
        ArrayList<Property> returnlist = new ArrayList<>();
        
        for (PropertyOwner o : owners) {
            returnlist.addAll(o.getProperties());
        }
        
        return returnlist;
    }

    /**
     *
     * gets all registered owners
     *
     * @return arraylist of owners
     */
    public ArrayList<PropertyOwner> getOwners() {
        return owners;
    }

    /**
     *
     * gets tax payment data for a property
     *
     * @param choice property to get data on
     */
    public String getTaxPaymentData(Property choice) {
    	String returnVal = "";
        if (choice.getPayments().size() != 0) {
            for (Payment p : choice.getPayments()) {
                returnVal += p.propertyFormat() + "\n";
            }
        } else {
            returnVal += "No payment history!" + "\n";
        }
        return returnVal;
    }

    /**
     *
     * gets tax payment data for a property owner
     *
     * @param choice owner to get data on
     */
    public String getTaxPaymentData(PropertyOwner choice) {
    	String returnVal = "";
        if (choice.getPayments().size() != 0) {
            for (Payment p : choice.getPayments()) {
                returnVal += p.ownerFormat() + "\n";
            }
        } else {
            returnVal += "No payment history!" + "\n";
        }
        return returnVal;
    }

    /**
     *
     * Used when user is logging in
     *
     * @param name full name of the owner
     * @param password account password of the owner
     * @return property owner that matches the name and password
     */
    public PropertyOwner getPropOwner(String name, String password) {
    	PropertyOwner returnVal = null;
    	for(PropertyOwner ownerOfP:owners) {
    		if(ownerOfP.getName().equalsIgnoreCase(name) && ownerOfP.getPassword().equals(password)) {
    			returnVal = ownerOfP;
    		}
    	}
    	return returnVal;
    }

    /**
     *
     * calculates tax for all owners and properties
     *
     */
    public void calculateAllTax() {
        for (PropertyOwner owner : owners) {
            owner.calculateTax();
        }
        System.out.println("All taxes for current year are now calculated");
    }

    /**
     *
     * gets the overdue tax of all owners during a specific year
     *
     * @param year year to get data from
     */
    public String getAllOverdueTax(int year) {
        ArrayList<Tax> taxUnpaidInYear = new ArrayList<>();
        String returnVal = "";

        for (PropertyOwner owner : owners) {
            taxUnpaidInYear.addAll(owner.getAllUnpaidTaxes(year));
        }

        for (Tax tax : taxUnpaidInYear) {
            returnVal += tax.managementFormat(year) + "\n";
        }
        
        return returnVal;
    }

    /**
     *
     * gets the overdue tax for all properties in a specific area during a specific year
     *
     * @param year year to get data from
     * @param area area to get data from
     */
    public String getAllOverdueTax(int year, String area) {
        ArrayList<Tax> taxUnpaidInYear = new ArrayList<>();
        String returnVal = "";

        for (PropertyOwner owner : owners) {
            taxUnpaidInYear.addAll(owner.getAllUnpaidTaxes(year));
        }

        for (Tax tax : taxUnpaidInYear) {
            if (tax.prop.getEircode().substring(0, area.length()).equalsIgnoreCase(area)) {
                returnVal += tax.managementFormat(year) + "\n";
            }
        }
        
        return returnVal;
    }

    /**
     *
     * gets tax payment statistics for a particular area
     *
     * @param area area to get stats from
     * @return String containing stats
     */
    public String getStatistics(String area) {
        //Get total tax paid (Euro), average tax paid, number and percentage of property taxes paid.
        double totalTaxPaid = 0;
        double numTaxPaid = 0;
        double averageTaxPaid; //Mean
        double numTax;
        double percentageTaxPaid;
        ArrayList<Tax> allTaxData = new ArrayList<>();
        ArrayList<Tax> temp = new ArrayList<>();

        //add all tax data to arraylist
        for (PropertyOwner owner : owners) {
            temp.addAll(owner.getAllTaxes());
        }

        //filter tax data to get region specific data
        for (Tax tax : temp) {
            if (tax.prop.getEircode().substring(0, area.length()).equalsIgnoreCase(area)) {
                allTaxData.add(tax);
            }
        }

        //get total tax paid
        for (Tax tax : allTaxData) {
            if (tax.paid) {
                totalTaxPaid += tax.valueWhenPaid;
                numTaxPaid++;
            }
        }

        //get number of taxes paid
        numTax = allTaxData.size();

        //get average Tax paid
        averageTaxPaid = totalTaxPaid / numTaxPaid;

        //Get percentage of tax paid
        percentageTaxPaid = (numTaxPaid / numTax) * 100;

        String stats = "Statistics for area " + area + ": \n" +
                        "Total tax paid: " + totalTaxPaid + "\n" +
                        "Average Tax paid: " + String.format("%.2f", averageTaxPaid) + "\n" +
                        "Number of property taxes paid: " + numTaxPaid + "\n" +
                        "Percentage of property taxes paid: " + String.format("%.2f", percentageTaxPaid) + "\n";

        return stats;
    }

    /**
     *
     * method for comparing the potential revenue changes if tax rates were to be changed
     *
     * @param flat flat charge
     * @param rate1 tax rate on market value between 150000 and 400000
     * @param rate2 tax rate on market value between 400000 and 650000
     * @param rate3 tax rate on market value above 650000
     * @param location string containing individual tax rates for location category tax
     * @param ppr charge for not principle private residence
     */
    public String compareRevenue(double flat, double rate1, double rate2, double rate3, String location, double ppr) {
        //create three different sample properties for a decent estimate
        Property example = new Property(400000, "City", false);
        Property example2 = new Property(1000000, "Countryside", true);
        Property example3 = new Property(300000, "Small town", false);
        String returnVal = "";

        //calculate tax for all three sample properties
        double tax1 = example.calculateTax(100, 0.01, 0.02, 0.04, 100, 80, 60, 50, 20, 100);
        double tax2 = example2.calculateTax(100, 0.01, 0.02, 0.04, 100, 80, 60, 50, 20, 100);
        double tax3 = example3.calculateTax(100, 0.01, 0.02, 0.04, 100, 80, 60, 50, 20, 100);

        //revenue from default tax calculation
        double currentRevenue = tax1 + tax2 + tax3;

        String[] locationTaxes = location.split(",");

        //calculate new tax for all three sample properties
        tax1 = example.calculateTax(flat, rate1, rate2, rate3, Double.parseDouble(locationTaxes[0]), Double.parseDouble(locationTaxes[1]), Double.parseDouble(locationTaxes[2]), Double.parseDouble(locationTaxes[3]), Double.parseDouble(locationTaxes[4]), ppr);
        tax2 = example2.calculateTax(flat, rate1, rate2, rate3, Double.parseDouble(locationTaxes[0]), Double.parseDouble(locationTaxes[1]), Double.parseDouble(locationTaxes[2]), Double.parseDouble(locationTaxes[3]), Double.parseDouble(locationTaxes[4]), ppr);
        tax3 = example3.calculateTax(flat, rate1, rate2, rate3, Double.parseDouble(locationTaxes[0]), Double.parseDouble(locationTaxes[1]), Double.parseDouble(locationTaxes[2]), Double.parseDouble(locationTaxes[3]), Double.parseDouble(locationTaxes[4]), ppr);

        double experimentalRevenue = tax1 + tax2 + tax3;

        double percentageChange = Math.abs(((experimentalRevenue - currentRevenue) / currentRevenue ) * 100);

        returnVal += "Sample revenue with current tax formula: " + String.format("%.2f",currentRevenue) + "\n";
        returnVal += "Sample revenue with experimental tax formula: " + String.format("%.2f",experimentalRevenue)+ "\n";

        if (experimentalRevenue > currentRevenue) {
            returnVal += "We can expect a " + String.format("%.2f",percentageChange) +"% increase in revenue with the experimental formula" + "\n";
        } else if (currentRevenue > experimentalRevenue) {
            returnVal += "We can expect a " + String.format("%.2f",percentageChange) +"% decrease in revenue with the experimental formula" + "\n";
        } else {
            returnVal += "The revenue is the same with both formulae" + "\n";
        }
        return returnVal;
    }
}
