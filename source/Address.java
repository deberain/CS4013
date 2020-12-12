

/**
 *
 * Address Class created to represent the address of a property
 *
 * Author: Denis O'Sullivan
 *
 */
public class Address {
    private String street;
    private String city;
    private String county;

    /**
     *
     * No-Arg Constructor for Address
     *
     */
    public Address() {}

    /**
     *
     * Constructor for Addresses
     *
     * @param street A string containing the street of the property
     * @param city A string containing the city of the property
     * @param county A string containing the county of the property
     */
    public Address(String street, String city, String county) {
        this.street = street;
        this.city = city;
        this.county = county;
    }

    /**
     *
     * General String format for an Address
     *
     * @return returns a convenient String representation of the address
     */
    @Override
    public String toString() {
        return street + ", " + city + ", " + county;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equalsIgnoreCase(address.street) && city.equalsIgnoreCase(address.city) && county.equalsIgnoreCase(address.county);
    }

    /**
     *
     * gets the street section of the address
     *
     * @return String containing street
     */
    public String getStreet() {
        return street;
    }

    /**
     *
     * gets the city section of the address
     *
     * @return String containing city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * gets the county section of the address
     *
     * @return String containing county
     */
    public String getCounty() {
        return county;
    }
}
