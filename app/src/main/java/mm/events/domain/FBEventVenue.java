package mm.events.domain;

public class FBEventVenue {

    String city;
    String country;
    String street;
    String zip;

    public FBEventVenue(String city, String country, String street, String zip) {
        this.city = city;
        this.country = country;
        this.street = street;
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }
}
