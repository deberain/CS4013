import java.util.ArrayList;

public class PropertyOwnersList {
    
    private ArrayList<PropertyOwner> owners;
    
    public PropertyOwnersList() {
        owners = new ArrayList<>();
    }
    
    public void registerOwner(String street, Address address, String eircode) {
        owners.add(new PropertyOwner(street, address, eircode));
    }
    
    public void registerProperty(PropertyOwner owner, Address address, String eircode, double estMarketValue,String locationCategory, boolean principlePrivateResidence) {
        for (PropertyOwner propowner : owners) {
            if (propowner.getName().equals(owner.getName())) {
                propowner.registerProperty(new Property(propowner, address, eircode, estMarketValue, locationCategory, principlePrivateResidence));
            }
        }
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
}
