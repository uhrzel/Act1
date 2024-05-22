package margarette;

public class Address_22033701 {
    private String street;
    private String suburb;
    private String state;
    private String postcode;

   public Address_22033701(String street, String suburb, String state, String postcode) {
    this.street = street;
    this.suburb = suburb;
    this.state = state;
    this.postcode = postcode;
}

    // Getters and Setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getSuburb() { return suburb; }
    public void setSuburb(String suburb) { this.suburb = suburb; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }

    @Override
    public String toString() {
        return street + ", " + suburb + ", " + state + ", " + postcode;
    }
}
