{
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

    public void registerProperty(Property property) {
        properties.add(property);
    }

    public void payTax(double amount, Property property, int year) {
        taxDue -= amount;
        payments.add(new Payment(this, property, year, amount, taxDue));
        property.addPayment(this, year, amount);
    }

    public void displayProprties() {

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
}
