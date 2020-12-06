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

    
    public void registerProperty(PropertyOwner owner, Property prop) {
    	owners.get(owners.indexOf(owner)).registerProperty(prop);
    }
    
    public ArrayList<Property> getProperties(PropertyOwner owner) {
        return owner.getProperties();
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

}
