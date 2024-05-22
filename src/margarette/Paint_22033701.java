package margarette;

public class Paint_22033701 {
    private String barcode;
    private String brand;
    private String color;
    private String sheen;
    private double pricePerLitre;
    private double sqMetersPerLiter;

    // Constructor
    public Paint_22033701(String barcode, String brand, String color, String sheen, double pricePerLitre, double sqMetersPerLiter) {
        this.barcode = barcode;
        this.brand = brand;
        this.color = color;
        this.sheen = sheen;
        this.pricePerLitre = pricePerLitre;
        this.sqMetersPerLiter = sqMetersPerLiter;
    }

    // Getter and setter methods
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getSheen() { return sheen; }
    public void setSheen(String sheen) { this.sheen = sheen; }
    public double getPricePerLitre() { return pricePerLitre; }
    public void setPricePerLitre(double pricePerLitre) { this.pricePerLitre = pricePerLitre; }
    public double getSqMetersPerLiter() { return sqMetersPerLiter; }
    public void setSqMetersPerLiter(double sqMetersPerLiter) { this.sqMetersPerLiter = sqMetersPerLiter; }

    @Override
    public String toString() {
        return "Paint [barcode=" + barcode + ", brand=" + brand + ", color=" + color + ", sheen=" + sheen + 
               ", pricePerLitre=" + pricePerLitre + ", sqMetersPerLiter=" + sqMetersPerLiter + "]";
    }
}
