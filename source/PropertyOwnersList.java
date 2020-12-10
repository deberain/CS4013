import java.util.ArrayList;

public class PropertyOwnersList {
    
    private ArrayList<PropertyOwner> owners;
    
    public PropertyOwnersList() {
        owners = new ArrayList<>();
    }

    public void registerOwner(PropertyOwner owner) {
    	owners.add(owner);
    }
    
    public void registerProperty(PropertyOwner owner, Address address, String eircode, double estMarketValue,String locationCategory, boolean principlePrivateResidence) {
        for (PropertyOwner propowner : owners) {
            if (propowner.getName().equals(owner.getName())) {
                propowner.registerProperty(new Property(propowner, address, eircode, estMarketValue, locationCategory, principlePrivateResidence));
            }
        }
    }

    public ArrayList<Property> getAllProperties() {
        ArrayList<Property> returnlist = new ArrayList<>();
        
        for (PropertyOwner o : owners) {
            returnlist.addAll(o.getProperties());
        }
        
        return returnlist;
    }
    
    public ArrayList<PropertyOwner> getOwners() {
        return owners;
    }

    public void getTaxPaymentData(Property choice) {
        for (Payment p : choice.getPayments()) {
            System.out.println(p.propertyFormat());
        }
    }

    public void getTaxPaymentData(PropertyOwner choice) {
        for (Payment p : choice.getPayments()) {
            System.out.println(p.ownerFormat());
        }

    }
    
    public PropertyOwner getPropOwner(String name, Address address, String eircode) {
    	PropertyOwner returnVal = null;
    	for(PropertyOwner ownerOfP:owners) {
    		if(ownerOfP.getName().contentEquals(name)&&ownerOfP.getEircode().contentEquals(eircode)&&ownerOfP.getAddress().equals(address)) {
    			returnVal = ownerOfP;
    		}
    	}
    	return returnVal;
    }

    public void calculateAllTax() {
        for (PropertyOwner owner : owners) {
            owner.calculateTax();
        }
        System.out.println("All taxes for current year are now calculated");
    }

    public void getAllOverdueTax(int year) {
        ArrayList<Tax> taxUnpaidInYear = new ArrayList<>();

        for (PropertyOwner owner : owners) {
            taxUnpaidInYear.addAll(owner.getAllUnpaidTaxes(year));
        }

        for (Tax tax : taxUnpaidInYear) {
            System.out.println(tax.managementFormat(year));
        }
    }

    public void getAllOverdueTax(int year, String area) {
        ArrayList<Tax> taxUnpaidInYear = new ArrayList<>();

        for (PropertyOwner owner : owners) {
            taxUnpaidInYear.addAll(owner.getAllUnpaidTaxes(year));
        }

        for (Tax tax : taxUnpaidInYear) {
            if (tax.prop.getEircode().substring(0, area.length()).equalsIgnoreCase(area)) {
                System.out.println(tax.managementFormat(year));
            }
        }
    }

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

        String stats = "Statisitcs for area " + area + ": \n" +
                        "Total tax paid: €" + totalTaxPaid + "\n" +
                        "Average Tax paid: €" + averageTaxPaid + "\n" +
                        "Number of property taxes paid: " + numTaxPaid + "\n" +
                        "Percentage of property taxes paid: " + percentageTaxPaid;

        return stats;
    }

    public void compareRevenue(double flat, double rate1, double rate2, double rate3, String location, double ppr) {
        //create three different sample properties for a decent estimate
        Property example = new Property(400000, "City", false);
        Property example2 = new Property(1000000, "Countryside", true);
        Property example3 = new Property(300000, "Small town", false);

        //calculate tax for all three sample properties
        double tax1 = example.calculateTax(100, 0.01, 0.02, 0.04, 100, 80, 60, 50, 20, 100);
        double tax2 = example2.calculateTax(100, 0.01, 0.02, 0.04, 100, 80, 60, 50, 20, 100);
        double tax3 = example3.calculateTax(100, 0.01, 0.02, 0.04, 100, 80, 60, 50, 20, 100);

        //revenue from default tax calculation
        double currentRevenue = tax1 + tax2 + tax3;

        String[] locationTaxes = location.split(", ");

        //calculate new tax for all three sample properties
        tax1 = example.calculateTax(flat, rate1, rate2, rate3, Double.parseDouble(locationTaxes[0]), Double.parseDouble(locationTaxes[1]), Double.parseDouble(locationTaxes[2]), Double.parseDouble(locationTaxes[3]), Double.parseDouble(locationTaxes[4]), ppr);
        tax2 = example2.calculateTax(flat, rate1, rate2, rate3, Double.parseDouble(locationTaxes[0]), Double.parseDouble(locationTaxes[1]), Double.parseDouble(locationTaxes[2]), Double.parseDouble(locationTaxes[3]), Double.parseDouble(locationTaxes[4]), ppr);
        tax3 = example3.calculateTax(flat, rate1, rate2, rate3, Double.parseDouble(locationTaxes[0]), Double.parseDouble(locationTaxes[1]), Double.parseDouble(locationTaxes[2]), Double.parseDouble(locationTaxes[3]), Double.parseDouble(locationTaxes[4]), ppr);

        double experimentalRevenue = tax1 + tax2 + tax3;

        double percentageChange = Math.abs(((experimentalRevenue - currentRevenue) / currentRevenue ) * 100);

        System.out.println("Sample revenue with current tax formula: " + currentRevenue);
        System.out.println("Sample revenue with experimental tax formula: " + experimentalRevenue);

        if (experimentalRevenue > currentRevenue) {
            System.out.println("We can expect a " + percentageChange +"% increase in revenue with the experimental formula");
        } else if (currentRevenue > experimentalRevenue) {
            System.out.println("We can expect a " + percentageChange +"% decrease in revenue with the experimental formula");
        } else {
            System.out.println("The revenue is the same with both formulae");
        }
    }
}
