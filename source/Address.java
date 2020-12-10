public class Address {
    private String street;
    private String city;
    private String county;

    public Address() {}

    public Address(String street, String city, String county) {
        this.street = street;
        this.city = city;
        this.county = county;
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + county;
    }
}
